import copy
import csv
import requests
from datetime import datetime
import time

"""
该文件用于获取cf比赛信息以及题目信息
比赛信息包括:
    id
    name
    start_time
    duration_time
    end_time
    
获取的用户信息保存路径为"./csv/contests_info.csv"

"""
#比赛信息数据结构
class contests:
    def __init__(self,id,name,start_time,duration_time,end_time,problems):
        #主键
        self.id = id
        self.contest_name = name
        self.start_time = start_time
        self.duration_time = duration_time
        self.end_time = end_time
        self.contest_problems = problems
# 比赛题目信息
class contest_problems:
    def __init__(self,index,problem_name,tags,link):
        self.index = index
        self.problem_name = problem_name
        self.tags = tags
        self.link = link

#时间点数据结构
class time_point:
    def __init__(self,year,month,day,hour,minute):
        self.years = year
        self.months = month
        self.days = day
        self.hours = hour
        self.minutes = minute

#时间段节点
class time_period:
    def __init__(self,hour,minute):
        self.hours = hour
        self.minutes = minute




#获取比赛题目信息
def get_contest_problems(contest_id):
    problems = {}
    url = f"https://codeforces.com/api/contest.standings?contestId={contest_id}&from=1&count=1"
    response = requests.get(url)
    if response.status_code == 200:
        data = response.json()
        if data['status'] == 'OK':
            num = len(data['result']['problems'])
            for i in range(num):
                index = data['result']['problems'][i]['index']
                name = data['result']['problems'][i]['name']
                tags = data['result']['problems'][i]['tags']
                link = f'https://codeforces.com/contest/{contest_id}/problem/{index}'
                problems[index] = contest_problems(index,name,tags,link)
            return problems
        else:
            return f"错误: {data['comment']}"
    else:
        return f"HTTP 错误: {response.status_code}"


#获取比赛信息
def get_contests():
    url = "https://codeforces.com/api/contest.list"
    response = requests.get(url)
    if response.status_code == 200:
        data = response.json()
        if data['status'] == 'OK':
            return data['result']
        else:
            return f"错误: {data['comment']}"
    else:
        return f"HTTP 错误: {response.status_code}"


#数据溢出处理
##判断是否为闰年,返回值为每个月的天数
def is_leap_year(year):
    list_common = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    list_leap = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    if year%100 == 0:
        if year%400 == 0:
            return list_leap
        else:
            return list_common
    else:
        if year%4 == 0:
            return list_leap
        else:
            return list_common

##日期溢出处理
def overflow_solving(end_time):
    #每月天数
    month_list = is_leap_year(end_time.years)
    #超过60分钟
    while end_time.minutes >= 60:
        end_time.minutes -= 60
        end_time.hours += 1
    # 超过24小时
    while end_time.hours >= 24:
        end_time.hours -= 24
        end_time.days += 1

    # 超过月份天数
    while end_time.days > month_list[end_time.months-1]:
        end_time.days -= month_list[end_time.months-1]
        end_time.months += 1

    # 超过12月
    while end_time.months > 12 :
        end_time.years += 1
        end_time.months -= 11

    return end_time

#时间戳转换
def get_start_time(timestamp):
    data = datetime.utcfromtimestamp(timestamp)
    year = data.year
    month = data.month
    day = data.day
    hour = data.hour
    minute = data.minute
    start_time = time_point(year,month,day,hour,minute)
    return start_time
#持续时间
def get_duration(seconds):
    hours = seconds // 3600
    minutes = (seconds % 3600) // 60
    duration_time = time_period(hours,minutes)
    return duration_time
#计算结束时间
def get_end_time(start_time,duration_time):
    end_time = copy.copy(start_time)
    end_time.hours += duration_time.hours
    end_time.minutes +=duration_time.minutes
    end_time = overflow_solving(end_time)
    return end_time

contest_index = 1005
# 开始时间
start = time.time()
#比赛信息字典
contests_info = {}
#获取比赛信息
contest_list = get_contests()
for contest in contest_list[contest_index:contest_index+5]:
    contest_index += 1
    id = contest['id']
    name = contest['name']
    # 获取每个比赛题目的信息
    problems = get_contest_problems(id)

    # 比赛时间
    start_time = get_start_time(contest['startTimeSeconds'])
    duration_time = get_duration(contest['durationSeconds'])
    end_time = get_end_time(start_time,duration_time)



    contests_info[id] = contests(id,name,start_time,duration_time,end_time,problems)


# 用户字典的键值
contest_nums = contests_info.items()
num = 0
#将数据类型写入csv文件
with open('./csv/contests_info.csv','a',newline="",encoding='utf-8-sig') as f:
    writer = csv.writer(f)
    # 先写入columns_name
    # writer.writerow(["contest_id", "contest_name", "index","problem_name","tags","start_time","end_time","link"])
    for contest in contest_nums:
        # 题目信息不为空
        if contest[1].contest_problems != 'HTTP 错误: 400':
            start_time = f'{contest[1].start_time.years}' + '.' + f'{contest[1].start_time.months}' + '.' + f'{contest[1].start_time.days}' + ' ' + f'{contest[1].start_time.hours}' + ':' + f'{contest[1].start_time.minutes}'
            end_time = f'{contest[1].end_time.years}' + '.' + f'{contest[1].end_time.months}' + '.' + f'{contest[1].end_time.days}' + ' ' + f'{contest[1].end_time.hours}' + ':' + f'{contest[1].end_time.minutes}'
            id = contest[1].id
            name = contest[1].contest_name
            problems = contest[1].contest_problems
            for problem in problems:
                index = problems[problem].index
                problem_name = problems[problem].problem_name
                problem_tags = problems[problem].tags
                problem_link = problems[problem].link
                writer.writerow([id,name,index,problem_name,problem_tags,start_time,end_time,problem_link])
                num += 1

# 结束时间
end = time.time()
print(f"耗时{end-start}s")
print(f"共写入了{num}行数据")
print(f"contest_index:{contest_index}")


