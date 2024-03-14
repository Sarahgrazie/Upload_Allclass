# 모듈 import
import requests
import json
import os

# time 모듈
from datetime import datetime
from pytz import timezone

# pandas
import pandas as pd
import numpy as np

# warning
import warnings
warnings.filterwarnings("ignore")

# eBEST OPEN API 공통 클래스
class eBESTAPI:
    # 초기화 함수
    def __init__(self, access_token):
        # 기본 도메인, URL 설정
        self._domain = "https://openapi.ebestsec.co.kr:8080"
        self._path = None
        self._url = f'{self._domain}/{self._path}'
        
        # 토큰 주입
        self.access_token = access_token
        
        # 거래 코드와 바디 설정
        self.tr_cd = None
        self.body = None
        
        # 현재 시각 설정
        self.time_now = self.get_time_now()
        
        # 데이터프레임으로 저장할 블록 리스트 설정
        self.outblock_list = None
        # Select할 컬럼, 새로 명명할 컬럼 설정
        self._sel_col = None
        self._new_col_name = None
        
        # Request 보내고 받는 JSON 파일
        self._json_data = None
        # JSON 파일을 처리한 DF
        self._data_frame = None
        # DF를 저장하는 CSV
        self.output_csv = None
        
    def get_time_now(self):
        """ 현재 시각 반환 함수

        Returns:
            time_now : 현재 시각
        """
        KST = timezone('Asia/Seoul')
        time_now = datetime.now(KST)
        return time_now
        
    '''
    # Request 헤더 생성 함수
    /**
    * @param tr_cd : API 조회 코드
    * @return _header : Request 헤더
    */
    '''
    def make_header(self, tr_cd):
        _header = {
            "content-type"  : "application/json; charset=UTF-8"     # JSON 형식
            , "authorization" : "Bearer "+ self.access_token        # Bearer 붙여줘야 에러 안남
            , "tr_cd" : tr_cd
            , "tr_cont" : "N"       # 연속거래 여부
            , "tr_cont_key" : ""  # 연속일 경우 그전에 내려온 연속키 값 올림
            , "mac_address" : ""  # MAC주소 (법인일 경우 필수 세팅)
        }
        
        return _header
    
    # Request 생성 함수
    '''
    /**
     * @param path : 조회 경로
     * @param tr_cd : API 조회 코드
     * @param body : Request body
     * @return _json_data : JSON 데이터
     */
     '''
    def make_request(self, path, tr_cd, body):
        DOMAIN = self._domain
        url = f"{DOMAIN}/{path}"
        
        header=self.make_header(tr_cd)
        _res = requests.post(url, headers=header, data=json.dumps(body), timeout=3.2)
        _json_data = _res.json()
        if __name__ == "__main__":
            print(_json_data)
        
        return _json_data
    
    # JSON 데이터를 데이터프레임으로 변환하는 함수
    def make_df(self, _json_data, tr_cd=None, outblock_list=None, _sel_col=None, _new_col_name=None):
        """
        Args:
            _json_data: JSON 데이터
            tr_cd (optional): 거래코드
            outblock_list (optional): 데이터프레임으로 만들 블록 리스트
            _sel_col (optional): 선택할 컬럼명
            _new_col_name (optional): 새로운 컬럼명

        Returns:
            _data_frame : 데이터프레임
        """
        # 분리할 블록이 없으면 JSON 전체를 DF로 변환
        if outblock_list is None:
            _data_frame = pd.json_normalize(_json_data)
            return _data_frame
        
        # 빈 데이터프레임 생성
        _data_frame = pd.DataFrame()
        
        for outblock in outblock_list:
            # 각 아웃블록별 DF 생성
            _outblock_df = pd.json_normalize(_json_data[f"{tr_cd}{outblock}"])
            
            # 필요한 컬럼만 셀렉트
            if _sel_col is not None:
                for _col in _sel_col:
                    # 셀렉트하려는 컬럼이 DF 내에 없으면 리턴
                    if _col not in _outblock_df.columns:
                        print(f"에러 :{_col} 컬럼이 데이터프레임 내에 존재하지 않습니다.")
                        return
                _outblock_df = _outblock_df[_sel_col]
            
            # 컬럼명 변경
            if _new_col_name is not None:
                if len(_new_col_name) == len(_outblock_df.columns):
                    _outblock_df.columns = _new_col_name
                else:
                    print("에러 : 변경하려는 컬럼 수가 일치하지 않습니다.")
                    
            # 각 아웃블록별 DF를 최종 DF에 합치기
            _data_frame = pd.concat([_data_frame, _outblock_df], axis=1)
        
        # 최종 데이터프레임 리턴
        return _data_frame
    
    # 데이터프레임을 CSV로 저장하는 함수
    def save_csv(_data_frame, tr_cd):
        """
        Args:
            data_frame: 완성된 데이터프레임
            tr_cd : 거래코드
        """
        today_date = datetime.today().strftime('%Y%m%d')
        # 파일명 : "현재 디렉토리" / "오늘 날짜" + "_" + "tr_cd.csv"
        _path = os.path.join(os.getcwd(), f"{today_date}_{tr_cd}.csv")
        _data_frame.to_csv(_path, index=False)
        print("파일 저장을 완료하였습니다. :", _path)