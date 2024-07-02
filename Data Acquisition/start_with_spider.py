# 导入库
from urllib.request import urlopen,Request
import bs4
from bs4 import BeautifulSoup


#用户代理，防止反爬
headers = {'User-Agent':'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36'}

# 题目属性
problemSet = "1353"
problemId = "B"

# 题目链接
url = Request(f"https://codeforces.com/problemset/problem/{problemSet}/{problemId}",headers=headers)
# 获取网页内容
html = urlopen(url).read()
# 格式化
soup = BeautifulSoup(html,'lxml')
# print(soup)


# 存储
data_dict = {}
# 找到主体内容
mainContent = soup.find_all(name="div", attrs={"class" :"problem-statement"})[0]



# Limit
# 找到题目标题、时间、和内存限制
# Title
data_dict['Title'] = f"CodeForces {problemSet} " + mainContent.find_all(name="div", attrs={"class":"title"})[0].contents[-1]
# Time Limit
data_dict['Time Limit'] = mainContent.find_all(name="div", attrs={"class":"time-limit"})[0].contents[-1]
# Memory Limit
data_dict['Memory Limit'] = mainContent.find_all(name="div", attrs={"class":"memory-limit"})[0].contents[-1]


print(data_dict)


def divTextProcess(div):
    """
    处理<div>标签中<p>的文本内容
    """
    strBuffer = ''
    # 遍历处理每个<p>标签
    for each in div.find_all("p"):
        for content in each.contents:
            # 如果不是第一个，加换行符
            if (strBuffer != ''):
                strBuffer += '\n\n'
            # 处理
            if (type(content) != bs4.element.Tag):
            # 如果是文本，添加至字符串buffer中
                strBuffer += content.replace("       ", " ").replace("$$$", "$")
            else:
            # 如果是html元素，如span等，加上粗体
                strBuffer += "**" + content.contents[0].replace("       ", " ").replace("$$$", "$") + "**"
    # 返回结果
    return strBuffer

# 处理题目描述
data_dict['Problem Description'] = divTextProcess(mainContent.find_all("div")[10])

#输入描述
div = mainContent.find_all(name="div", attrs={"class":"input-specification"})[0]
data_dict['Input'] = divTextProcess(div)

#输出描述
div = mainContent.find_all(name="div", attrs={"class":"output-specification"})[0]
data_dict['Output'] = divTextProcess(div)

#输入样例
# Input
div = mainContent.find_all(name="div", attrs={"class":"input"})[0]
data_dict['Sample Input'] = "```cpp" + div.find_all("pre")[0].contents[0] + '```'
# Onput
div = mainContent.find_all(name="div", attrs={"class":"output"})[0]
data_dict['Sample Output'] = "```cpp" + div.find_all("pre")[0].contents[0] + '```'

# 若有样例说明
if(len(mainContent.find_all(name="div", attrs={"class":"note"})) > 0):
    div = mainContent.find_all(name="div", attrs={"class":"note"})[0]
    data_dict['Note'] = divTextProcess(div)

#题目链接
data_dict['Source'] = '[' + data_dict['Title'] + ']' + '(' + url.full_url + ')'
#输出
for each in data_dict.keys():
    print('### ' + each + '\n')
    print(data_dict[each].replace("\n\n**", "**").replace("**\n\n", "**") + '\n')