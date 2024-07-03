"""
该文件用于获取cf的用户信息
用户信息包括:
    handle
    rating
    max_rating
    rank
    max_rank

获取的用户信息保存路径为"./csv/users.csv"
"""

import requests

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

handle = "tourist"  # 示例用户
user_info = get_user_info(handle)
print(user_info)

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

handle = "tourist"  # 示例用户
user_rating = get_user_rating(handle)
for rating in user_rating[:5]:  # 打印前5个评级变化
    print(f"比赛: {rating['contestName']}, 变化: {rating['newRating']}")