"""
该文件用于获取ATCODER网站的用户信息
用户信息包括:
    handle
    rating
    max_rating


获取的用户信息保存路径为"./csv/at_user.csv"
"""



import requests
from bs4 import BeautifulSoup
import pandas as pd
import math
import csv

class users:
    def __init__(self,handle):
        self.handle = handle
        self.rating = ""
        self.max_rating = ""


# 读取文件获取atcoder_id
def get_atcoder_id():
    handles = []
    data = pd.read_csv("./csv/users.csv",encoding='utf-8-sig')
    for id in data.at_id:
        # 判断是否为空值
        if type(id) != str and math.isnan(id):
            continue
        atcoder_ids_list = id.split(';')
        [handles.append(atcoder_id) for atcoder_id in atcoder_ids_list]
    return handles

#获取用户信息
def get_atcoder_user_info(handle):
    url = f"https://atcoder.jp/users/{handle}"
    response = requests.get(url)
    if response.status_code == 200:
        soup = BeautifulSoup(response.content, 'html.parser')
        # 创建user_info
        user_info = users(handle)
        # 获取 Rating 位置
        rating_tag = soup.find('th', text='Rating')
        if rating_tag:
            user_info.rating = int(rating_tag.find_next_sibling('td').text.split('\n')[0])

        # 获取 Highest Rating
        highest_rating_tag = soup.find('th', text='Highest Rating')
        if highest_rating_tag:
            user_info.max_rating = int(highest_rating_tag.find_next_sibling('td').text.split('\n')[0])

        return user_info
    else:
        return f"HTTP 错误: {response.status_code}"




user_info = {}
# 获取所有atcoder_id
i = 0
handles = get_atcoder_id()
for user in handles:
    user_info[user] = get_atcoder_user_info(user)

# 用户字典的键值
user_nums = list(user_info.items())
#将数据类型写入csv文件
with open('./csv/at_user.csv','w',newline='',encoding='utf-8-sig') as f:
    writer = csv.writer(f,quoting=csv.QUOTE_NONE)
    # 先写入columns_name
    writer.writerow(["handle","rating","max_rating"])
    for user in user_nums:
        writer.writerow([user[1].handle,user[1].rating,user[1].max_rating])

print(f"共写入了{len(user_info)}行数据")
