"""
该文件用于获取BUCTOJ网站的用户信息
用户信息包括:
    contest_id
    contest_name
    contest_time
    contest_link
获取的用户信息保存路径为"./csv/buctoj-contest.csv"
"""

import requests
import csv
from bs4 import BeautifulSoup

# 比赛基本信息数据结构
class contest_info:
    def __init__(self,contest_id):
        self.contest_id = contest_id
        self.contest_name = ""
        self.contest_time = ""
        self.contest_link = ""


#获取用户信息
def get_buctoj_contest_info(index):
    contests = {}
    url = f"https://www.buctcoder.com/contest.php?page={index}"
    response = requests.get(url)
    if response.status_code == 200:
        soup = BeautifulSoup(response.content, 'html.parser')
        tds = soup.find_all('tr')
        for index in range(1,len(tds)):
            # 比赛id
            id = eval(tds[index].find('td').text)
            # 比赛姓名
            name = tds[index].find('a').text
            # 比赛时间
            time = tds[index].find_all('span')[1].text
            # 比赛链接
            link = f"https://www.buctcoder.com/contest.php?cid={id}"
            contests[id] = contest_info(id)
            contests[id].contest_name = name
            contests[id].contest_time = time
            contests[id].contest_link = link
        

        return  contests
    else:
        return f"HTTP 错误: {response.status_code}"


# 爬取页数
pages = 40
#比赛信息
contests = {}

for page in range(1,pages):
    contest = get_buctoj_contest_info(page)
    contests.update(contest)

# 比赛字典的键值
contests_nums = list(contests.items())
#将数据类型写入csv文件
with open('./csv/buctoj_contests.csv','w',newline='',encoding='utf-8-sig') as f:
    writer = csv.writer(f)
    # 先写入columns_name
    writer.writerow(["contest_id","contest_name","end_time","link"])
    for contest in contests_nums:
        writer.writerow([contest[1].contest_id,contest[1].contest_name,contest[1].contest_time,contest[1].contest_link])

print(f"共写入了{len(contests_nums)}行数据")
