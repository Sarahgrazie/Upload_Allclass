import pandas as pd
import numpy as np
import time
import multiprocessing
import os, glob

from exchange_calendars import get_calendar #주식개장일 라이브러리

import selectcode as sc
import nulltime

CORE=20

def func(codes,dic,days):
    
    TIMESET_SU=nulltime.timeset_su()
    TIMESET=nulltime.timeset()
    for code in codes:
        try:
            codest=time.time()
            #4 결측일 채워진 데이터 프레임 생성
            df1 = pd.read_csv(f'./save_data1/A{code}_20220110_20220128.csv')
            df2 = pd.read_csv(f'./save_data2/A{code}_20220203_20240105.csv')
            df3=pd.DataFrame(columns=['날짜','시간','시가','고가','저가','종가','거래량','거래대금'])
                
            df=pd.concat([df1,df2], ignore_index=True)
            if code in dic.keys() :
                for day in dic[code]:
                    #날짜 데이터 프레임 생성
                    _time_df=pd.DataFrame()
                    if (day==20221117) or (day==20231116): #수능일은 1시간 늦게 시작해서 1시간 늦게끝남
                        _time_df=pd.DataFrame(data=TIMESET_SU, columns=['시간'])    #빈시간들로만 데이터 프레임 생성   
                    else :
                        _time_df=pd.DataFrame(data=TIMESET, columns=['시간'])    #빈시간들로만 데이터 프레임 생성 
                        #=========================================================================       
                    _data_df=pd.DataFrame(columns=['날짜'])  
                    _etc_df=pd.DataFrame(columns=['시가','고가','저가','종가','거래량','거래대금']) #나머지 컬럼 데이터 프레임 생성
                    _temp_df= pd.concat([_data_df,_time_df,_etc_df],axis=1)     #열기준(가로)으로 로 합치기
                    #날짜,거래량 ,거래대금 채우기
                    _temp_df.fillna({'날짜':day,
                                    '거래량':0,
                                    '거래대금':0,
                                    },inplace=True)
                    df3=pd.concat([df3,_temp_df], axis=0)
                #반복 end====================================================================    
                df3=df3.astype({'날짜':int,'시간':int})
                df3.sort_values(by=['날짜','시간'],ascending=True, ignore_index=True, inplace=True) 
            #if end=====================================================================
            df=pd.concat([df,df3], ignore_index=True)
            df.sort_values(['날짜','시간'],ascending=True, ignore_index=True, inplace=True)
            
            
            # 거래 날짜가 모두있는 날에 거래 없는시간 채우기 
            # ※20220110 첫시작이 있어야함 - 없는애들 미리 제거함
            #추가될 행을 담을 임시 데이터프레임 생성
            new_dataframe=pd.DataFrame(columns=['날짜','시간','시가','고가','저가','종가','거래량','거래대금'])
            #첫행의 종가 가져오기
            close=df.iloc[0]['종가']

            for day in days: #날짜 단위로 반복 기간내 모든 개장일 490
                #거래일 day의 빈 시간대 확인하기
                _time_temp=[] #빈 시간대 담을 리스트 초기화
                if (day=='20221117') or (day=='20231116'): #수능일은 1시간 늦게 시작해서 1시간 늦게끝남
                    intersection= TIMESET_SU - set(df.loc[(df['날짜']==int(day)),'시간'].values.astype(str).tolist())
                    _time_temp = list(map(int,list(intersection)))
                    _time_temp.sort()
                else :
                    intersection= TIMESET - set(df.loc[(df['날짜']==int(day)),'시간'].values.astype(str).tolist())
                    _time_temp = list(map(int,list(intersection)))
                    _time_temp.sort()
                #if - else end=============================================================================================    
                if len(_time_temp)==0:  #시분대 381개 모두 있는경우
                    closeday=int(days[days.index(day)-1]) #전날추출
                    close= df.loc[(df['날짜']==closeday)]['종가'][-1:].values[0] #전날종가 추출
                    df.loc[df['날짜']==int(day)]=df.loc[df['날짜']==int(day)].fillna({'종가':close})
                    _temp_df=df.loc[df['날짜']==int(day)].fillna({'시가':close,
                                                                '고가':close,
                                                                '저가':close,
                                                                '종가':close})
                    new_dataframe=pd.concat([new_dataframe,_temp_df], axis=0)
                    
                else:
                    #빈시간대들로 만들어진 데이터프레임 생성
                    _data_df=pd.DataFrame(columns=['날짜'])                     #날짜 데이터 프레임 생성
                    _time_df=pd.DataFrame(data=_time_temp, columns=['시간'])    #빈시간들로만 데이터 프레임 생성 
                    _etc_df=pd.DataFrame(columns=['시가','고가','저가','종가','거래량','거래대금']) #나머지 컬럼 데이터 프레임 생성
                    _temp_df= pd.concat([_data_df,_time_df,_etc_df],axis=1)     #열기준(가로)으로 로 합치기
                        
                    #날짜,거래량 ,거래대금 채우기
                    _temp_df.fillna({'날짜':int(day),
                                    '거래량':0,
                                    '거래대금':0}, inplace=True)
                    
                    #day 해당되는 원본데이터와 결측치 데이터 합치기    
                    _temp_df=pd.concat([df.loc[df['날짜']==int(day)],_temp_df],axis=0)  #수집데이터에서 원하는 날짜에 해당되는 것만 분리해서 합치기
                    _temp_df.sort_values(by=['시간'], ascending=True, ignore_index=True, inplace=True) #시간으로 정렬
                    
                    #시고저종이 nan이면 전분의 종가로 채우기
                    for idx in range(0,_temp_df.shape[0]):
                        if _temp_df.iloc[idx][['종가']].isnull().values==True :
                            _temp_df.iloc[idx]=_temp_df.iloc[idx].fillna({'시가':close,
                                                                        '고가':close,
                                                                        '저가':close,
                                                                        '종가':close,
                                                                        })
                        close=_temp_df.iloc[idx]['종가'] #현재 줄의 인덱스의 종가 가져오기     
                    new_dataframe=pd.concat([new_dataframe,_temp_df], axis=0) 
                #if - else end  하루단위로 new_df에 추가됨===================================================================================   
            # for문 end====================================================================================================================
            new_dataframe.reset_index(drop=True,inplace=True)
            #새해 첫날 드랍
            new_dataframe.drop( new_dataframe.loc[new_dataframe['날짜']==20230102][:60].index, inplace=True)
            new_dataframe.drop( new_dataframe.loc[new_dataframe['날짜']==20240102][:60].index, inplace=True)
            #저장
            new_dataframe.reset_index(drop=True,inplace=True)
            new_dataframe=new_dataframe.astype({'날짜':np.uint32,
                                                '시간':np.uint16,
                                                '시가':np.uint32,
                                                '고가':np.uint32,
                                                '저가':np.uint32,
                                                '종가':np.uint32,
                                                '거래량':np.uint32,
                                                '거래대금':np.uint64
                                                })
            
            print(f'{code} : {new_dataframe.shape} : {time.time()-codest}')
            new_dataframe.to_csv(f'./1차전처리csv/{code}_전처리_20220110_20240105.csv', index=False)
        except:
            print(f'{code} 에러가 발생했습니다.')



if __name__ == '__main__':
    stock_open_days = sc.stod()
    checkdic, split_codes =sc.snd(CORE,stock_open_days)
    start = time.time()
    procs = []
    for i in range(0,CORE,1):
        print(f'{i}멀티프로세싱 시작...')
        p = multiprocessing.Process(target=func, args=(split_codes[i],checkdic,stock_open_days))
        p.start()
        procs.append(p)

    for p in procs:
        p.join()  # 프로세스가 모두 종료될 때까지 대기

    end = time.time()
    print("전체 수행시간: %f 초" % (end - start))