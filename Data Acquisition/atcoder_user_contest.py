"""
该文件用于获取ATCODER网站的用户比赛信息
用户比赛信息包括:
    handle
    contest_id
    contest_name
    rank
    diff
    new_rating
    start_time


获取的用户信息保存路径为"./csv/at_user_contest.csv"


比赛信息
    contest_id
    contest_name
    link

获取的用户信息保存路径为"./csv/at_contest.csv"
"""



import requests
from bs4 import BeautifulSoup
import pandas as pd
import math
import csv
from datetime import datetime
import pytz


# 用户比赛信息数据结构
class atcoder:
    def __init__(self,handle,contest_id):
        self.handle = handle
        self.contest_id = contest_id
        self.contest_name = ""
        self.rank = ""
        self.new_rating = ""
        self.Diff = ""          #rating变化
        self.start_time = ""    #rating更新时间


# 读取文件获取atcoder_id
def get_atcoder_id():
    handles = []
    data = pd.read_csv("./csv/at_user.csv",encoding='utf-8-sig')
    for id in data.handle:
        # 判断是否为空值
        if type(id) != str and math.isnan(id):
            continue
        atcoder_ids_list = id.split(';')
        [handles.append(atcoder_id) for atcoder_id in atcoder_ids_list]
    return handles


#获取用户比赛信息
def get_atcoder_user_contest_info(handle):
    # 每个handle比赛信息
    atcoder_contest_info = {}
    url = f"https://atcoder.jp/users/{handle}/history?contestType=algo"
    response = requests.get(url)
    if response.status_code == 200:
        soup = BeautifulSoup(response.content, 'html.parser')
        # 上次最新rating
        last_rating = '-'
        # 获取比赛位置
        rating_tag = soup.find_all('tr',attrs='text-center')
        if rating_tag:
            for tr in rating_tag:
                tds = tr.find_all('td')
                # 获取比赛id
                contest_id = tds[1].a['href'].split('/')[-1]
                # 记录每个比赛记录
                if contest_id not in contest_ids:
                    contest_ids.append(contest_id)
                # 以名字与比赛id作为主键创建对象
                atcoder_contest_info[(handle,contest_id)] = atcoder(handle,contest_id)
                # 比赛名称
                atcoder_contest_info[(handle,contest_id)].contest_name = tds[1].a.text
                # 获取排名
                atcoder_contest_info[(handle,contest_id)].rank = tds[2].text
                # 获取积分(如果为‘-’，则与上次保持一致)
                rating = tds[4].text
                if rating == '-':
                    rating = last_rating
                atcoder_contest_info[(handle, contest_id)].new_rating = rating
                last_rating = rating
                # 获取积分变化
                atcoder_contest_info[(handle,contest_id)].Diff = tds[5].text


                #获取时间
                atcoder_contest_info[(handle, contest_id)].start_time = tds[0]['data-order']

        return atcoder_contest_info
    else:
        return f"HTTP 错误: {response.status_code}"

# 存储比赛列表
contest_ids = []
# 存储用户信息列表
at_user_contests = {}
# 获取所有atcoder_id
handles = get_atcoder_id()
for handle in handles:
    atcoder_contest_info = get_atcoder_user_contest_info(handle)
    at_user_contests.update(atcoder_contest_info)


# 用户字典的键值
user_nums = list(at_user_contests.items())
#将数据类型写入csv文件
with open('./csv/at_user_contest.csv','w',newline='',encoding='utf-8-sig') as f:
    writer = csv.writer(f)
    # 先写入columns_name
    writer.writerow(["handle","update_time","contest_id","contest_name","rank","new_rating","Diff"])
    for user in user_nums:
        writer.writerow([user[1].handle,user[1].start_time,user[1].contest_id,user[1].contest_name,user[1].rank,user[1].new_rating,user[1].Diff])

print(f"共写入了{len(user_nums)}行数据")



# 查询比赛信息
# 比赛信息数据结构
class contest_info:
    def __init__(self,contest_id):
        # 主键
        self.contest_id = contest_id
        self.contest_name = ""
        self.start_time = ""
        self.end_time = ""
        self.link = ""

# 时间转换（日本UTC+9转化为UTC+8）
def Convert_Time(time_str):
    # 解析时间字符串并创建带有时区信息的时间对象
    time_obj = datetime.strptime(time_str, '%Y-%m-%d %H:%M:%S%z')

    # 转换为中国标准时间
    China_tz = pytz.timezone('Asia/Shanghai')
    China_time = time_obj.astimezone(China_tz)

    return str(China_time)

def get_atcoder_contest_info(contest_id):
    url = f"https://atcoder.jp/contests/{contest_id}/tasks"
    response = requests.get(url)
    information = contest_info(contest_id)
    if response.status_code == 200:
        soup = BeautifulSoup(response.content, 'html.parser')
        time_a = soup.find('small',attrs='contest-duration').find_all('a')
        time1 = time_a[0].text
        time2 = time_a[1].text
        information.start_time = Convert_Time(time1)
        information.end_time = Convert_Time(time2)
        information.contest_name = soup.find('a',attrs='contest-title').text
        information.link = url

        return information
    else:
        return f"HTTP 错误: {response.status_code}"


# 存储比赛信息
contests_info = {}

for contest_id in contest_ids:
    info = get_atcoder_contest_info(contest_id)
    contests_info[contest_id] = info

# 比赛字典的键值
contest_nums = list(contests_info.items())
#将数据类型写入csv文件
with open('./csv/at_contest.csv','w',newline='',encoding='utf-8-sig') as f:
    writer = csv.writer(f)
    # 先写入columns_name
    writer.writerow(["contest_id","contest_name","start_time","end_time","link"])
    for contest in contest_nums:
        writer.writerow([contest[1].contest_id,contest[1].contest_name,contest[1].start_time,contest[1].end_time,contest[1].link])

print(f"共写入了{len(contest_nums)}行数据")






