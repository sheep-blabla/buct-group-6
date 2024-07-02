"""
该文件用于获取ACDC网站的用户信息
用户信息包括:
    学号
    姓名
    班级
    codeforces_id
    atcoder_id

获取的用户信息保存路径为"./csv/users.csv"
"""
import requests
import csv

# 用户基本信息数据结构
class user:
    def __init__(self,stuNo,name,classname):
        #学号(字符串类型)(主键)
        self.stuNO = stuNo
        #姓名(字符串类型)
        self.stuName = name
        #班级(字符串类型)
        self.classname = classname
        #cf_id(因为一个人有多个账号,所以使用字符串列表)(外键)
        self.cf_id = []
        #at_id
        self.at_id = []
#用户信息
users = {}

#codeforces账号api
url_cf = 'https://buctoj.com/ACDC/api/codeforces/rank'
#atcoder账号api
url_at = 'https://buctoj.com/ACDC/api/atcoder/rank'

#获取队员codeforces的数据
response = requests.get(url_cf)
if response.status_code == 200:
    students_cf = response.json()['result']
#获取队员atcoder的数据
response = requests.get(url_at)
if response.status_code == 200:
    students_at = response.json()['result']

for student in students_cf:
    #学号
    stuNo = student['stuNo']
    #不在列表内
    if stuNo not in users:
        name = student['realname']
        class_name = student['classname']
        cf_id = student['codeforcesId']
        users[stuNo] = user(stuNo,name,class_name)
        users[stuNo].cf_id.append(cf_id)
    #否则添加cf子id
    else:
        cf_id = student['codeforcesId']
        if cf_id not in users[stuNo].cf_id:
            users[stuNo].cf_id.append(cf_id)

for student in students_at:
    #学号
    stuNo = student['stuNo']
    #只在原有数据结构上添加属性，不添加成员
    if stuNo in users:
        at_id = student['atcoderId']
        users[stuNo].at_id.append(at_id)


# 用户字典的键值
user_nums = users.items()
#将数据类型写入csv文件
with open('./csv/users.csv','w',newline='',encoding='gbk') as f:
    writer = csv.writer(f)
    # 先写入columns_name
    writer.writerow(["stuNo", "name", "class_name","cf_id","at_id"])
    for user in user_nums:
        writer.writerow([user[1].stuNO,user[1].stuName,user[1].classname,user[1].cf_id,user[1].at_id])

print(f"共写入了{len(users)}行数据")