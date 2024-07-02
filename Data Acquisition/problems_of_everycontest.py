import requests

#获取比赛题目信息
def get_contest_problems(contest_ids):
    problems = []
    for contest_id in contest_ids:
        url = f"https://codeforces.com/api/contest.standings?contestId={contest_id}&from=1&count=1"
        response = requests.get(url)
        if response.status_code == 200:
            data = response.json()
            if data['status'] == 'OK':
                problems.append(data['result']['problems'])
            else:
                problems.append({contest_id:'error'})
        else:
            problems.append({contest_id:'error'})


#读取文件获取比赛id
def get_id():
    with open('./information/contest_id.txt','r') as f:
        contest_ids = list(f.read()[1:-2].split())
    return contest_ids

#获取比赛id
contest_ids = get_id()

#根据id获取比赛题目
problems = get_contest_problems(contest_ids)

#根据比赛获取题目序列
contest_problems_id = {}

for problem in problems:
    # 比赛不存在，创建列表
    if problem['contestId'] not in contest_problems_id:
        contest_problems_id[problem['contestId']] = []

    contest_problems_id[problem['contestId']].append(problem['index'])
    # print(f"题目: {problem['name']}, ID: {problem['contestId']}-{problem['index']}")

#将比赛id对应的题目序列写入文件
with open('./information/problems_of_everycontest.txt','w') as f:
    for item in contest_problems_id:
        f.write(str(item)+'\n')
