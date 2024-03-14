def timeset(): #490일 다있는경우 timeset 새해도 <-이거쓰고 dropna씀
    TIMESET=set()
    h,m,t=9,1,''
    while True:
        if m<10:
            t=f'{h}0{m}'
        else: 
            t=f'{h}{m}'
        
        # print(t)
        m= (m+1)%60
        if m==0:
            h+=1
        if t=='1521':
            break
        TIMESET.add(t)
    TIMESET.add('1530')
    return TIMESET

def timeset_su(): #수능일 timeset
    TIMESET_SU=set()
    h,m,t=10,1,''
    while True:
        if m<10:
            t=f'{h}0{m}'
        else: 
            t=f'{h}{m}'
        
        # print(t)
        m= (m+1)%60
        if m==0:
            h+=1
        if t=='1621':
            break
        TIMESET_SU.add(t)
    TIMESET_SU.add('1630')
    return TIMESET_SU

