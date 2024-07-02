import requests

#获取比赛信息
def get_contests():
    url = "https://www.buctcoder.com/ACDC/codeforces/rank"
    response = requests.get(url)
    if response.status_code == 200:
        data = response.json()
        if data['status'] == 'OK':
            return data['result']
        else:
            return f"错误: {data['comment']}"
    else:
        return f"HTTP 错误: {response.status_code}"

#获取比赛信息
contests = get_contests()
for contest in contests[:5]:  # 打印前5个比赛
    print(f"比赛名称: {contest['name']}, ID: {contest['id']}")

#按行写入信息,因为部分题目有俄语，所以用GBK编码
with open("information/contest_information.txt", 'w', encoding ='gbk') as f:
    for i in contests:
        f.write(str(i) + '\n',)

contest_ids = [contest['id'] for contest in contests]
with open("information/contest_id.txt", 'w', encoding ='gbk') as f:
        f.write(str(contest_ids))


