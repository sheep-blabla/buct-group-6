import requests
from datetime import datetime

# 定义数据结构
##################################################
# 比赛
class contest:
    def __init__(self,name,contest_date):
        # 比赛名称(字符串类型)(主键)
        contest_name = name
        # 比赛时间(字符串类型)
        contest_time = contest_name


# 用户基本信息
class user:
    def __init__(self):
        #学号(字符串类型)(主键)
        stuNO = ""
        #姓名(字符串类型)
        stuName = ""
        #班级(字符串类型)
        classname = ""
        #性别(字符串类型)
        gender = ""
        #cf_id(因为一个人有多个账号,所以使用字符串列表)(外键)
        cf_id = []

class cf_user:
    def __init__(self):
        #账号名(主键)
        id_name = ""
        #最大ratiing(int类型)
        max_rating = ""
        #当前rating
        latest_rating = ""
        # 比赛(字符串列表)
        contests = []
        #rating change(字典类型，  contest:[old_rating,new_rating])
        rating_change = {}
        #排名(int)
        rank = ""

#################################################



def get_user_info(codeforce_id):
    url = f"https://codeforces.com/api/user.info?handles={codeforce_id}"
    response = requests.get(url)
    if response.status_code == 200:
        data = response.json()
        if data['status'] == 'OK':
            return data['result']
        else:
            return f"错误: {data['comment']}"
    else:
        return f"HTTP 错误: {response.status_code}"

#获取用户rating
def get_user_rating(codeforce_id):
    url = f"https://codeforces.com/api/user.rating?handle={codeforce_id}"
    response = requests.get(url)
    if response.status_code == 200:
        data = response.json()
        if data['status'] == 'OK':
            return data['result']
        else:
            return f"错误: {data['comment']}"
    else:
        return f"HTTP 错误: {response.status_code}"

#获取用户做题数据
def get_user_submissions(handle, count=10):
    url = f"https://codeforces.com/api/user.status?handle={handle}&form=1&count={count}"
    response = requests.get(url)
    if response.status_code == 200:
        data = response.json()
        if data['status'] == 'OK':
            return data['result']
        else:
            return f"错误: {data['comment']}"
    else:
        return f"HTTP 错误: {response.status_code}"

#时间转换
def convert_timestamp_to_datetime(timestamp):
    return datetime.utcfromtimestamp(timestamp).strftime('%Y-%m-%d %H:%M:%S')

#输出rating随时间变化
def print_rating_changes_by_date(rating_changes):
    for change in rating_changes:
        contest_name = change['contestName']
        old_rating = change['oldRating']
        new_rating = change['newRating']
        rating_change = new_rating - old_rating
        contest_date = convert_timestamp_to_datetime(change['ratingUpdateTimeSeconds'])
        print(f"日期: {contest_date}")
        print(f"比赛: {contest_name}")
        print(f"旧评级: {old_rating}, 新评级: {new_rating}, 变化: {rating_change}\n")

# 读取用户名
codeforce_ids = []
with open('./information/codeforce.txt','r') as f:
    lines = f.readlines()
    for line in lines:
        codeforce_ids.append(line.strip('\n'))

for codeforce_id in codeforce_ids:
    user_info = get_user_info(codeforce_id)
    print(user_info)

    user_rating = get_user_rating(codeforce_id)
    print_rating_changes_by_date(user_rating)

    submissions = get_user_submissions(codeforce_id)
    #AC数
    AC = 0
    #未通过数
    WA = 0

    if isinstance(submissions, str):
        print(submissions)  # 打印错误信息
    else:
        print(f"{codeforce_id} 的最近 {len(submissions)} 次提交：")
        for submission in submissions:
            problem = submission['problem']
            submission_time = convert_timestamp_to_datetime(submission['creationTimeSeconds'])
            print(f"题目: {problem['name']} (ID: {problem['contestId']}-{problem['index']})")
            print(f"提交时间: {submission_time}")
            print(f"结果: {submission['verdict']}\n")
            if submission['verdict'] == 'OK':
                AC += 1
            else:
                WA += 1
        print(f"最近{len(submissions)}次提交,共AC {AC}次,未通过{WA}次")
    break