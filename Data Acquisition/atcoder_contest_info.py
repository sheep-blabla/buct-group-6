"""
该文件用于获取atcoder网站的比赛信息
    学号
    姓名
    班级
    codeforces_id
    atcoder_id

获取的用户信息保存路径为"./csv/atcoder.csv"
"""

import requests
from bs4 import BeautifulSoup






contest_id = "abc300"  # 示例比赛 ID
problems = get_atcoder_contest_problems(contest_id)

if isinstance(problems, str):
    print(problems)  # 打印错误信息
else:
    for problem in problems:
        print(f"题目: {problem['name']}")
        print(f"链接: {problem['link']}\n")
