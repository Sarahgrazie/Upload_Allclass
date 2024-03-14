import numpy as np 
import pandas as pd 
import os, glob
from datetime import datetime,timedelta

from exchange_calendars import get_calendar #주식개장일 라이브러리
from datetime import datetime

def stod(): #stock_open_days
    from exchange_calendars import get_calendar #주식개장일 라이브러리
    print('stock_open_days 생성중.....')
    XKRX = get_calendar("XKRX")
    days = pd.date_range(start='20220110', end='20240105')
    stock_open_days = [date.strftime('%Y%m%d') for date in days if XKRX.is_session(date)] #위기간동안 개장일 490일
    return stock_open_days

def snd(CORE,stock_open_days): #seach null day
    print('결측일 찾는중...')
    #1. 주식코드 전체 불러오기
    stock_code_df=pd.read_csv('./stockcode1.2.csv') #1519종목
    stock_code_df['종목코드']=stock_code_df['종목코드'].apply(lambda x: str(x).zfill(6)) 

    #2. 20220110 거래가 없는 코드들 확인인하기
    path_ = r'C:\Education\teamproject\stock\save_data1' #220110~220128
    all_files_ = glob.glob(os.path.join(path_, "*.csv"))

    #종목마다 거래 결측일을 빼서 딕셔너리(code : [결측일])에 담아라
    checkdic={}
    for fileadress in all_files_:
        code=fileadress.split('\\')[-1].split('_')[0][1:] #종목코드만 분리
        #df1 : 20220110~20220128
        df1 = pd.read_csv(f'./save_data1/A{code}_20220110_20220128.csv')
        #df2 : 20220203~20240105
        df2 = pd.read_csv(f'./save_data2/A{code}_20220203_20240105.csv')
        #합치기
        df=pd.concat([df1,df2], ignore_index=True)
        #결측일 확인
        intersection= set(stock_open_days) - set(df['날짜'].values.astype(str))
        _temp = list(map(int,list(intersection))) #리스트로 변환
        _temp.sort()
        if len(intersection)>0:
            checkdic[code]=_temp #결측일이 있는 데이터만 딕셔너리에 저장

    # 20220110일이 결측일인 종목들 확인 - 첫줄을 직접 채워 줘야 함
    except_code=[]
    for key in checkdic.keys():
        if checkdic[key][0]==20220110:
            except_code.append(key)

    #3 20220110 결측인 주식코드 제외하기 drop
    dropindex=stock_code_df.loc[stock_code_df['종목코드'].isin(except_code)].index
    stock_code_df.drop(dropindex, inplace=True) #1500개 남음
    codes = stock_code_df['종목코드'].values
    step=int(len(codes)/CORE)
    split_codes=[codes[i:i+step] for i in range(0, len(codes), step)]
    
    return checkdic, split_codes 