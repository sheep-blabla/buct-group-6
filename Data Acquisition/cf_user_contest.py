"""
该文件用于获取cf的用户信息
cf_user包括:
    handle(主键）
    rating
    max_rating
    rank
    max_rank

cf_user_contest包括:
    cf_id(主键)
    contest_id(主键)
    contest_name
    rank
    old_rating
    new_rating
    rating_updatetime
获取的用户信息保存路径为"./csv/cf_users.csv"
                   "./csv/cf_user_contest.csv"
"""

import requests
import pandas as pd
from datetime import datetime
import time
import csv
# 用户数据结构
class cf_user:
    def __init__(self,handle,rating,max_rating,rank,max_rank):
        self.handle = handle
        self.rating = rating
        self.max_rating = max_rating
        self.rank = rank
        self.max_rank = max_rank
        self.contest_total = ""

# 用户比赛信息数据结构
class codeforces:
    def __init__(self,cf_id,contest_id,contest_name,rank,old_rating,new_rating,rating_updatetime):
        self.cf_id = cf_id
        self.contest_id = contest_id
        self.contest_name = contest_name
        self.rank = rank
        self.old_rating = old_rating
        self.new_rating = new_rating
        self.rating_update_time = rating_updatetime    #rating更新时间

#时间点数据结构
class time_point:
    def __init__(self,year,month,day,hour,minute):
        self.years = year
        self.months = month
        self.days = day
        self.hours = hour
        self.minutes = minute


# 读取users.csv文件，获取cf_id
def get_cf_id():
    handles = []
    data = pd.read_csv("./csv/users.csv",encoding='gbk')
    for id in data.cf_id:
        # 原数据是用;号隔开的
        cf_ids_list = id.split(';')
        [handles.append(cf_id) for cf_id in cf_ids_list]
    return handles

# 用户信息
def get_user_info(handle):
    url = f"https://codeforces.com/api/user.info?handles={handle}"
    response = requests.get(url)
    if response.status_code == 200:
        data = response.json()
        if data['status'] == 'OK':
            return data['result'][0]
        else:
            return f"错误: {data['comment']}"
    else:
        return f"HTTP 错误: {response.status_code}"

# 用户评级历史
def get_user_rating(handle):
    url = f"https://codeforces.com/api/user.rating?handle={handle}"
    response = requests.get(url)
    if response.status_code == 200:
        data = response.json()
        if data['status'] == 'OK':
            return data['result']
        else:
            return f"错误: {data['comment']}"
    else:
        return f"HTTP 错误: {response.status_code}"

#时间戳转换
def convert_time(timestamp):
    data = datetime.utcfromtimestamp(timestamp)
    year = data.year
    month = data.month
    day = data.day
    hour = data.hour
    minute = data.minute
    start_time = time_point(year,month,day,hour,minute)
    return start_time


start = time.time()
# 信息存储在字典内
cf_users = {}
cf_user_contests = {}

handle_num = 200
handle_idx = 200
# 获取所有cf_id
handles = get_cf_id()
for handle in handles[handle_num:]:
    handle_idx += 1
    # 用户信息cf_user
    user_info = get_user_info(handle)
    try:
        rating = user_info["rating"]
    except:
        continue
    max_rating = user_info["maxRating"]
    rank = user_info["rank"]
    max_rank = user_info["maxRank"]

    # 比赛信息 codeforces
    user_contest_info = get_user_rating(handle)

    if user_contest_info == 'HTTP 错误: 400':
        continue

    #遍历每一场比赛
    for contest in user_contest_info:
        contest_id = contest["contestId"]
        contest_name = contest["contestName"]
        contest_rank = contest["rank"]
        old_rating = contest["oldRating"]
        new_raitng = contest["newRating"]
        rating_update_time = convert_time(contest["ratingUpdateTimeSeconds"])
        # cf_user_contest有两个主键，所以用双键值索引
        cf_user_contests[(handle,contest_id)] = codeforces(handle,contest_id,contest_name,contest_rank,old_rating,new_raitng,rating_update_time)

    cf_users[handle] = cf_user(handle, rating, max_rating, rank, max_rank)
    # 比赛总数
    cf_users[handle].contest_total = len(user_contest_info)


# 用户字典的键值,cf_users.csv文件写入
num = 0
#将数据类型写入csv文件
with open('./csv/cf_user.csv','a',newline="",encoding='gbk') as f:
    writer = csv.writer(f)
    # 先写入columns_name
    # writer.writerow(["handle","rating","max_rating","rank","max_rank","contest_total"])
    for cf_user in cf_users.items():
        handle = cf_user[1].handle
        rating = cf_user[1].rating
        max_rating = cf_user[1].max_rating
        rank = cf_user[1].rank
        max_rank = cf_user[1].max_rank
        contest_total = cf_user[1].contest_total
        writer.writerow([handle, rating, max_rating, rank, max_rank, contest_total])
        num += 1
# 结束时间
end1 = time.time()
print(f"耗时{end1-start}s")
print(f"共写入了{num}行数据")


# 用户比赛字典的键值,cf_user_contest.csv文件写入
num = 0
#将数据类型写入csv文件
with open('./csv/cf_user_contest.csv','a',newline="",encoding='gbk') as f:
    writer = csv.writer(f)
    # 先写入columns_name
    # writer.writerow(["handle","contest_id","contest_name","old_rating","new_rating","rank"])

    for user_contest in cf_user_contests.items():
        handle = user_contest[1].cf_id
        contest_id = user_contest[1].contest_id
        contest_name = user_contest[1].contest_name
        rank = cf_user[1].rank
        old_rating = user_contest[1].old_rating
        new_rating = user_contest[1].new_rating
        writer.writerow([handle, contest_id, contest_name, old_rating, new_rating, rank])
        num += 1
# 结束时间
end2 = time.time()
print(f"耗时{end2-end1}s")
print(f"共写入了{num}行数据")
print(f"handle_idx:{handle_idx}")