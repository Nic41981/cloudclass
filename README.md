# 在线教育平台后台API文档
***
## 目录
- [用户部分](#用户部分)
    1. [用户注册](#1用户注册)
    2. [用户登录](#2用户登录)
    3. [自动登录](#3自动登录)
    4. [退出登录](#4退出登录)
- [教师个人中心-课程部分](#教师个人中心-课程部分)
    1. [教师课程列表](#1教师课程列表)
    2. [教师课程下拉列表](#2教师课程下拉列表)
    3. [新建课程](#3新建课程)
    4. [修改课程](#4修改课程)
    5. [删除课程](#5删除课程)
    5. [创建公告](#6创建公告)
- [教师个人中心-章节部分](#教师个人中心-章节部分)
    1. [教师章节列表](#1教师章节列表)
    2. [新建章节](#2新建章节)
    3. [修改章节](#3修改章节)
    4. [删除章节](#4删除章节)
- [教师个人中心-统计部分](#教师个人中心-统计部分)
    1. [选课情况](#1选课情况)
    2. [成绩分析](#2成绩分析)
- [期末考试部分](#期末考试部分)
    1. [创建期末试卷](#1创建期末试卷)
    2. [获取期末试卷](#2获取期末试卷)
    3. [提交期末试卷](#3提交期末试卷)
    4. [查询期末成绩](#4查询期末成绩)
- [章节测试部分](#章节测试部分)
    1. [创建章节试卷](#1创建章节试卷)
    2. [获取章节试卷](#2获取章节试卷)
    3. [提交章节试卷](#3提交章节试卷)
    4. [查询章节成绩](#4查询章节成绩)
- [文件部分](#文件部分)
    1. [课程封面上传](#1课程封面上传)
    2. [章节视频上传](#2章节视频上传)
    3. [获取文件信息](#3获取文件信息)
    4. [文件静态资源](#4文件静态资源)
- [学生个人中心-课程部分](#学生个人中心-课程部分)
    1. [学生课程列表](#1学生课程列表)
    2. [学生课程下拉列表](#2学生课程下拉列表)
    3. [选课](#3选课)
    4. [退课](#4退课)
- [下拉列表部分](#下拉列表部分)
    1. [课程下拉列表](#1课程下拉列表)
    2. [章节下拉列表](#2章节下拉列表)
    3. [考试下拉列表](#3考试下拉列表)
- [通用部分](#首页课程分类列表)
    1. [首页课程分类列表](#1首页课程分类列表)
    2. [章节列表](#2章节列表)
    3. [公告列表](#3公告列表)
    4. [轮播图ID列表](#4轮播图ID列表)
***
## 用户部分
### 1.用户注册
URL:_http://39.107.102.246/user/register_

请求方式:_POST_

请求参数样例:
```json5
{
	"name":"test",
	"password":"123456",
	"email":"test@qq.com"
}
```
成功返回样例:
```json5
{
    "status": 0,
    "msg": "注册成功"
}
```
失败信息:

|status|msg|
|:----:|---|
|-1|用户名已存在|
|-1|该邮箱已注册|
|-3|缺少参数|
### 2.用户登录
URL:_http://39.107.102.246/user/login_

请求方式:_POST_

请求参数样例:
```json5
{
	"name":"test",
	"password":"123456",
	"autoLogin":true
}
//autoLogin参数可选,默认为false
```
成功返回样例:
```json5
{
    "status": 0,
    "msg": "登录成功",
    "data": {
        "name": "test",
        "identity": 0,
        "email": "test@qq.com"
    }
}
```
失败信息:

|status|msg|
|:----:|---|
|-2|用户名或密码错误|
|-3|缺少参数|
### 3.自动登录
URL:_http://39.107.102.246/user/login/auto_

请求方式:_POST_

请求参数样例:_无_

成功返回样例:
```json5
{
    "status": 0,
    "msg": "登陆成功",
    "data": {
        "name": "test",
        "identity": 0,
        "email": "test@qq.com"
    }
}
```
失败信息:

|status|msg|
|:----:|---|
|-2|登录失败|
|-3|缺少参数|
### 4.退出登录
URL:_http://39.107.102.246/user/logout_

请求方式:_POST_

请求参数样例:_无_

成功返回样例:
```json5
{
    "status": 0,
    "msg": "退出登录成功"
}
```
失败信息:_无_
## 教师个人中心-课程部分
### 1.教师课程列表
URL:_http://39.107.102.246/teacher/course/list_

请求方式:_GET_

请求参数样例:无

成功返回样例:
```json5
{
    "status": 0,
    "msg": "查询成功",
    "data": [
        {
            "id": "81f88387543743328b30d7b299f33c01",
            "name": "API开发",
            "image": "0478f16dfa5946a887ecb3b6610d8074",
            "teacher": "李老师",
            "tag": "web;API;开发",
            "finalExam": "d4daf49c80cb4e548b9711720c319bcb"
        }
    ]
}
```
失败信息:

|status|msg|
|:----:|---|
|-2|用户未登录|
|-2|权限不足|
### 2.教师课程下拉列表
URL:_http://39.107.102.246/teacher/course/spinner_

请求方法:_GET_

请求参数样例:_无_

返回成功样例:
```json5
{
    "status": 0,
    "msg": "查询成功",
    "data": [
        {
            "id": "81f88387543743328b30d7b299f33c01",
            "name": "API开发"
        },
        {
            "id": "096702b7aab44441815622f4788df63b",
            "name": "API开发"
        }
    ]
}
```
失败信息:

|status|msg|
|:----:|---|
|-2|用户未登录|
|-2|权限不足|
### 3.新建课程
URL:_http://39.107.102.246/teacher/course_

请求方法:_POST_

请求参数样例:
```json5
{
	"name":"API开发",
	"tag":"web;API;开发"
}
```
成功返回样例:
```json5
{
    "status": 0,
    "msg": "创建成功"
}
```
失败信息:

|status|msg|
|:----:|---|
|-1|创建失败|
|-2|用户未登录|
|-2|权限不足|
|-3|缺少参数|
### 4.修改课程
URL:_http://39.107.102.246/teacher/course/{课程ID}_

请求方法:_PUT_

请求参数样例:
```json5
{
	"name":"API开发",
	"tag":"web;API;开发"
}
//所有参数均为可选参数,但request body必须要有JSON结构
```
成功返回样例:
```json5
{
    "status": 0,
    "msg": "更新成功"
}
```
失败信息:

|status|msg|
|:----:|---|
|-1|更新失败|
|-2|用户未登录|
|-2|权限不足|
|-3|缺少参数|
|-4|课程不存在|
### 5.删除课程
URL:_http://39.107.102.246/teacher/course/{课程ID}_

请求方法:_DELETE_

请求参数样例:_无_

返回成功样例:
```json5
{
    "status": 0,
    "msg": "删除成功"
}
```
失败信息:

|status|msg|
|:----:|---|
|-1|删除失败|
|-2|用户未登录|
|-2|权限不足|
|-4|课程不存在|
### 6.创建公告
URL:_localhost:8080/teacher/course/notice_

请求方法:_POST_

请求参数样例:
```json5
{
	"course":"81f88387543743328b30d7b299f33c01",
	"content":"公告"
}
```

返回成功样例:
```json5
{
    "status": 0,
    "msg": "创建成功"
}
```
失败信息:

|status|msg|
|:----:|---|
|-1|创建失败|
|-2|用户未登录|
|-2|权限不足|
|-3|缺少参数|
|-4|课程不存在|
## 教师个人中心-章节部分
### 1.教师章节列表
详见[章节列表](#2章节列表)
### 2.创建章节
URL:_http://39.107.102.246/teacher/chapter_

请求方法:_POST_

请求参数样例:
```json5
{
	"num":1,
	"course":"82ca0f2515624098b269341ea15db3a4",
	"name":"API第一课",
	"info":"API第一课"
}
//num参数可选,默认追加章节
```

返回成功样例:
```json5
{
    "status": 0,
    "msg": "创建成功"
}
```
失败信息:

|status|msg|
|:----:|---|
|-1|创建失败|
|-2|用户未登录|
|-2|权限不足|
|-3|缺少参数|
|-4|课程不存在|
### 3.修改章节
URL:_http://39.107.102.246/teacher/chapter/{章节ID}_

请求方法:_PUT_

请求参数样例:
```json5
{
	"name":"API第一课",
	"info":"API第一课"
}
//所有参数均为可选参数,但request body必须要有JSON结构
```

返回成功样例:
```json5
{
    "status": 0,
    "msg": "更新成功"
}
```

失败信息:

|status|msg|
|:----:|---|
|-1|更新失败|
|-2|用户未登录|
|-2|权限不足|
|-3|缺少参数|
|-4|章节不存在|
|-4|课程不存在|
### 4.删除章节
URL:_http://39.107.102.246/teacher/chapter/{章节ID}_

请求方法:_DELETE_

请求参数样例:_无_

返回成功样例:
```json5
{
    "status": 0,
    "msg": "删除成功"
}
```

失败信息:

|status|msg|
|:----:|---|
|-1|删除失败|
|-2|用户未登录|
|-2|权限不足|
|-4|章节不存在|
|-4|课程不存在|
## 教师个人中心-统计部分
### 1.选课情况
URL:_http://39.107.102.246/teacher/statistics/student_

请求方法:_GET_

请求参数样例:_?course=82ca0f2515624098b269341ea15db3a4_

返回成功样例:
```json5
{
    "status": 0,
    "msg": "无人参加该课程"
}
```
```json5
{
    "status": 0,
    "msg": "查询成功",
    "data": {
        "total": 4,
        "nameList": [
            "nic",
            "15699999999",
            "15610492887",
            "redefault"
        ]
    }
}
```
失败信息:

|status|msg|
|:----:|---|
|-2|用户未登录|
|-2|权限不足|
|-3|缺少参数|
|-4|课程不存在|
### 2.成绩分析
URL:_http://39.107.102.246/statistics/score_

请求方法:_GET_

请求参数样例:_?exam=a0f94e7f7e9945738f3e7e52d665a1db_

返回成功样例:
```json5
{
    "status": 0,
    "msg": "无人参加该测试"
}
```
```json5
{
    "status": 0,
    "msg": "查询成功",
    "data": {
        "total": 4,
        "passNum": 2,
        "passNumPercent": "50",
        "scoreList": [
            {
                "name": "test",
                "score": 20
            },
            {
                "name": "test",
                "score": 20
            },
            {
                "name": "test",
                "score": 100
            },
            {
                "name": "test",
                "score": 100
            }
        ],
        "perfectPercent": "50",
        "excellentPercent": "0",
        "goodPercent": "0",
        "passPercent": "0",
        "failPercent": "50"
    }
}
```
失败信息:

|status|msg|
|:----:|---|
|-2|用户未登录|
|-2|权限不足|
|-3|缺少参数|
|-4|课程不存在|
|-4|章节不存在|
|-4|考试不存在|
## 期末考试部分
### 1.创建期末试卷
URL:_http://39.107.102.246/exam/final_

请求方法:_POST_

请求参数样例:
```json5
{
	"name":"API开发期末考试",
	"course":"81f88387543743328b30d7b299f33c01",
	"startTime":"2019-04-21 07:00:00",
	"stopTime":"2019-04-21 09:00:00",
	"choiceScoreWeight":1,
	"choiceList":[
		{
			"question":"一天有多少小时?",
			"optionList":[
				"5小时",
				"10小时",
				"20小时",
				"24小时"
				],
			"answer":"24小时"
		},
		{
			"question":"一小时有多少分钟?",
			"optionList":[
				"10分钟",
				"30分钟",
				"60分钟",
				"90分钟"
				],
			"answer":"60分钟"
		}
		],
	"judgementScoreWeight":1,
	"judgementList":[
		{
			"question":"青岛工学院是双一流大学.",
			"answer":"false"
		},
		{
			"question":"BTA指的是百度,腾讯和阿里爸爸.",
			"answer":"true"
		}
		]
}
//考试开始时间必须在当前时间之前,在结束时间之后,时长在5分钟至24小时之间
```
返回成功样例:
```json5
{
    "status": 0,
    "msg": "创建成功"
}
```
失败信息:

|status|msg|
|:----:|---|
|-1|创建失败|
|-2|用户未登录|
|-2|权限不足|
|-3|缺少参数|
|-4|考试时间错误|
|-4|课程不存在|
|-4|有效题目不足|
### 2.获取期末试卷
URL:_http://39.107.102.246/exam/final/{考试ID}_

请求方法:_GET_

请求参数样例:_无_

返回成功样例:
```json5
{
    "status": 0,
    "msg": "查询成功",
    "data": {
        "name": "API开发期末考试",
        "choiceList": [
            {
                "id": 1,
                "question": "一天有多少小时?",
                "score": 5,
                "optionList": [
                    "10小时",
                    "20小时",
                    "5小时",
                    "24小时"
                ]
            },
            {
                "id": 2,
                "question": "一小时有多少分钟?",
                "score": 5,
                "optionList": [
                    "90分钟",
                    "60分钟",
                    "10分钟",
                    "30分钟"
                ]
            }
        ],
        "judgementList": [
            {
                "id": 4,
                "question": "BTA指的是百度,腾讯和阿里爸爸.",
                "score": 5
            },
            {
                "id": 3,
                "question": "青岛工学院是双一流大学.",
                "score": 5
            }
        ],
        "startTime": "2019-04-20 07:00:00",
        "stopTime": "2019-04-20 09:00:00"
    }
}
```
失败信息:

|status|msg|
|:----:|---|
|-2|用户未登录|
|-2|未参加学习|
|-4|考试不存在|
|-4|课程不存在|
|-4|不在考试时间|
### 3.提交期末试卷
URL:_http://39.107.102.246/exam/final/{考试ID}_

请求方法:_POST_

请求参数样例:
```json5
{
	"choiceList":[
		{
			"id":1,
			"answer":"24小时"
		},
		{
			"id":2,
			"answer":"60分钟"
		}
		],
	"judgementList":[
		{
			"id":3,
			"answer":false
		},
		{
			"id":4,
			"answer":true
		}
		]
}
```
返回成功样例:
```json5
{
    "status": 0,
    "msg": "提交成功",
    "data": {
        "exam": "d4daf49c80cb4e548b9711720c319bcb",
        "score": 20
    }
}
```
失败信息:

|status|msg|
|:----:|---|
|-1|提交失败|
|-2|用户未登录|
|-2|未参加学习|
|-4|考试不存在|
|-4|课程不存在|
|-4|不在考试时间|
|-4|不可重复提交|
### 4.查询期末成绩
URL:_39.107.102.246/exam/final/score/{考试ID}_

请求方法:_GET_

请求参数样例:_无_

返回成功样例:
```json5
{
    "status": 0,
    "msg": "暂无成绩"
}
```
```json5
{
    "status": 0,
    "msg": "查询成功",
    "data": {
        "exam": "d4daf49c80cb4e548b9711720c319bcb",
        "score": 20
    }
}
```
失败信息:

|status|msg|
|:----:|---|
|-2|用户未登录|
|-2|未参加学习|
|-4|考试不存在|
|-4|课程不存在|
## 章节测试部分
### 1.创建章节试卷
URL:_http://39.107.102.246/exam/chapter_

请求方法:_POST_

请求参数样例:
```json5
{
	"chapter":"6c87779f2dcb4588a7fbe3bb42d925b5",
	"name":"第一章测试",
	"choiceScoreWeight":1,
	"choiceList":[
		{
			"question":"一天有多少小时?",
			"optionList":[
				"5小时",
				"10小时",
				"20小时",
				"24小时"
				],
			"answer":"24小时"
		},
		{
			"question":"一小时有多少分钟?",
			"score":5,
			"optionList":[
				"10分钟",
				"30分钟",
				"60分钟",
				"90分钟"
				],
			"answer":"60分钟"
		}
		],
	"judgementScoreWeight":1,
	"judgementList":[
		{
			"question":"青岛工学院是双一流大学.",
			"score":5,
			"answer":"false"
		},
		{
			"question":"BTA指的是百度,腾讯和阿里爸爸.",
			"score":5,
			"answer":"true"
		}
		]
}
```
返回成功样例:
```json5
{
    "status": 0,
    "msg": "创建成功"
}
```
失败信息:

|status|msg|
|:----:|---|
|-1|创建失败|
|-2|用户未登录|
|-2|权限不足|
|-3|缺少参数|
|-4|课程不存在|
|-4|章节不存在|
|-4|有效题目不足|
### 2.获取章节试卷
URL:_http://39.107.102.246/exam/chapter/{考试ID}_

请求方法:_GET_

请求参数样例:_无_

返回成功样例:
```json5
{
    "status": 0,
    "msg": "查询成功",
    "data": {
        "name": "第一章测试",
        "choiceList": [
            {
                "id": 9,
                "question": "一天有多少小时?",
                "score": 5,
                "optionList": [
                    "20小时",
                    "5小时",
                    "24小时",
                    "10小时"
                ]
            },
            {
                "id": 10,
                "question": "一小时有多少分钟?",
                "score": 5,
                "optionList": [
                    "60分钟",
                    "10分钟",
                    "90分钟",
                    "30分钟"
                ]
            }
        ],
        "judgementList": [
            {
                "id": 12,
                "question": "BTA指的是百度,腾讯和阿里爸爸.",
                "score": 5
            },
            {
                "id": 11,
                "question": "青岛工学院是双一流大学.",
                "score": 5
            }
        ]
    }
}
```
失败信息:

|status|msg|
|:----:|---|
|-2|用户未登录|
|-2|未参加学习|
|-4|考试不存在|
|-4|课程不存在|
|-4|章节不存在|
### 3.提交章节试卷
URL:_http://39.107.102.246/exam/final/{考试ID}_

请求方法:_POST_

请求参数样例:
```json5
{
	"choiceList":[
		{
			"id":1,
			"answer":"24小时"
		},
		{
			"id":2,
			"answer":"60分钟"
		}
		],
	"judgementList":[
		{
			"id":3,
			"answer":false
		},
		{
			"id":4,
			"answer":true
		}
		]
}
```
返回成功样例:
```json5
{
    "status": 0,
    "msg": "提交成功",
    "data": {
        "exam": "0ae6c94c1e1f4507832405d50ff065b0",
        "score": 20
    }
}
```
失败信息:

|status|msg|
|:----:|---|
|-1|提交失败|
|-2|用户未登录|
|-2|未参加学习|
|-4|考试不存在|
|-4|课程不存在|
|-4|章节不存在|
### 4.查询章节成绩
URL:_39.107.102.246/exam/chapter/score/{考试ID}_

请求方法:_GET_

请求参数样例:_无_

返回成功样例:
```json5
{
    "status": 0,
    "msg": "查询成功",
    "data": {
        "exam": "0ae6c94c1e1f4507832405d50ff065b0",
        "score": 20
    }
}
```
失败信息:

|status|msg|
|:----:|---|
|-2|用户未登录|
|-2|未参加学习|
|-4|考试不存在|
|-4|课程不存在|
|-4|章节不存在|
## 文件部分
### 1.课程封面上传
URL:_http://39.107.102.246/upload/course/image_

请求方法:_POST(multipart/form-data)_

请求参数样例:

|参数名|参数类型|参数值|
|:----:|:-----:|-----|
|course|text|81f88387543743328b30d7b299f33c01|
|image|file|image.jpg|
返回成功样例:
```json5
{
    "status": 0,
    "msg": "上传成功"
}
```
失败信息:

|status|msg|
|:----:|---|
|-1|上传失败|
|-2|用户未登录|
|-2|权限不足|
|-3|缺少参数|
|-4|课程不存在|
|-4|不支持的文件类型|
### 2.章节视频上传
URL:_http://39.107.102.246/upload/chapter/video_

请求方法:_POST(multipart/form-data)_

请求参数样例:

|参数名|参数类型|参数值|
|:----:|:-----:|-----|
|chapter|text|81f88387543743328b30d7b299f33c01|
|video|file|video.mp4|
返回成功样例:
```json5
{
    "status": 0,
    "msg": "上传成功"
}
```
失败信息:

|status|msg|
|:----:|---|
|-1|上传失败|
|-2|用户未登录|
|-2|权限不足|
|-3|缺少参数|
|-4|课程不存在|
|-4|章节不存在|
|-4|不支持的文件类型|
### 3.获取文件信息
URL:_http://39.107.102.246/files/info_

请求方法:_GET_

请求参数样例:_?file=0478f16dfa5946a887ecb3b6610d8074_

返回成功样例:
```json5
{
    "status": 0,
    "msg": "查询成功",
    "data": {
        "id": "0478f16dfa5946a887ecb3b6610d8074",
        "mappingPath": "/files/image/course/81f88387543743328b30d7b299f33c01/",
        "realName": "程序员",
        "suffix": "png"
    }
}
```
失败信息:

|status|msg|
|:----:|---|
|-3|缺少参数|
|-4|文件不存在|
### 4.文件静态资源
URL:_http://39.107.102.246{文件mappingPath}{文件ID}.{文件后缀名}_

请求方法:_GET_

请求参数样例:_无_

返回成功样例:_文件资源_

失败信息:_无_
## 学生个人中心-课程部分
### 1.学生课程列表
URL:_http://39.107.102.246/student/course/list_

请求方式:_GET_

请求参数样例:无

成功返回样例:
```json5
{
    "status": 0,
    "msg": "查询成功",
    "data": [
        {
            "id": "81f88387543743328b30d7b299f33c01",
            "name": "API开发",
            "image": "0478f16dfa5946a887ecb3b6610d8074",
            "teacher": "李老师",
            "tag": "web;API;开发",
            "finalExam": "a0f94e7f7e9945738f3e7e52d665a1db"
        }
    ]
}
```
失败信息:

|status|msg|
|:----:|---|
|-2|用户未登录|
### 2.学生课程下拉列表
URL:_http://39.107.102.246/student/course/spinner_

请求方法:_GET_

请求参数样例:_无_

返回成功样例:
```json5
{
    "status": 0,
    "msg": "查询成功",
    "data": [
        {
            "id": "81f88387543743328b30d7b299f33c01",
            "name": "API开发"
        }
    ]
}
```
失败信息:

|status|msg|
|:----:|---|
|-2|用户未登录|
### 3.选课
URL:_http://39.107.102.246/student/course/{课程ID}_

请求参数:_POST_

请求参数样例:_无_

返回成功样例:
```json5
{
    "status": 0,
    "msg": "选课成功"
}
```
失败信息:

|status|msg|
|:----:|---|
|-1|选课失败|
|-1|已经参加该课程|
|-2|用户未登录|
|-4|课程不存在|
### 4.退课
URL:_http://39.107.102.246/student/course/{课程ID}_

请求参数:_DELETE_

请求参数样例:_无_

返回成功样例:
```json5
{
    "status": 0,
    "msg": "退课成功"
}
```
失败信息:

|status|msg|
|:----:|---|
|-1|退课失败|
|-1|未参加该课程|
|-2|用户未登录|
|-4|课程不存在|
## 下拉列表部分
### 1.课程下拉列表
URL:_http://39.107.102.246/spinner/course_

请求方法:_GET_

请求参数样例:_无_

返回成功样例:
```json5
{
    "status": 0,
    "msg": "查询成功",
    "data": [
        {
            "id": "1",
            "name": "111"
        },
        {
            "id": "81f88387543743328b30d7b299f33c01",
            "name": "API开发"
        },
        {
            "id": "096702b7aab44441815622f4788df63b",
            "name": "API开发"
        }
    ]
}
```
失败信息:_无_
### 2.章节下拉列表
URL:_http://39.107.102.246/spinner/chapter_

请求方法:_GET_

请求参数样例:_?course=81f88387543743328b30d7b299f33c01_

返回成功样例:
```json5
{
    "status": 0,
    "msg": "查询成功",
    "data": [
        {
            "id": "6c87779f2dcb4588a7fbe3bb42d925b5",
            "num": 1,
            "name": "API第一课"
        }
    ]
}
```
失败信息:

|status|msg|
|:----:|---|
|-3|缺少参数|
### 3.考试下拉列表
URL:_http://39.107.102.246/spinner/exam_

请求方法:_GET_

请求参数样例:_?course=81f88387543743328b30d7b299f33c01_

返回成功样例:
```json5
{
    "status": 0,
    "msg": "查询成功",
    "data": [
        {
            "id": "6c87779f2dcb4588a7fbe3bb42d925b5",
            "num": 1,
            "name": "API第一课"
        }
    ]
}
```
失败信息:

|status|msg|
|:----:|---|
|-3|缺少参数|
## 通用部分
### 1.首页课程分类列表
URL:_http://39.107.102.246/course/tag/{标签名}_

请求方法:_GET_

请求参数样例:_无_

返回成功样例:
```json5
{
    "status": 0,
    "msg": "查询成功",
    "data": [
        {
            "id": "81f88387543743328b30d7b299f33c01",
            "name": "API开发",
            "image": "0478f16dfa5946a887ecb3b6610d8074",
            "teacher": "李老师",
            "tag": "web;API;开发",
            "finalExam": "a0f94e7f7e9945738f3e7e52d665a1db"
        },
        {
            "id": "096702b7aab44441815622f4788df63b",
            "name": "API开发",
            "image": null,
            "teacher": "李老师",
            "tag": "web;API;开发",
            "finalExam": null
        }
    ]
}
```

失败信息:_无_
### 2.章节列表
URL:_http://39.107.102.246/ccourse/chapter/list_

请求方法:_GET_

请求参数样例:_?course=81f88387543743328b30d7b299f33c01_

返回成功样例:
```json5
{
    "status": 0,
    "msg": "查询成功",
    "data": {
        "chapterList": [
            {
                "id": "6c87779f2dcb4588a7fbe3bb42d925b5",
                "num": 1,
                "name": "API第一课",
                "info": "API第一课",
                "video": "71143e27752c41059e852e7aa004c029",
                "chapterExam": "6e02e9532a1f419bb834397fbf845e0a"
            }
        ],
        "finalExam": "a0f94e7f7e9945738f3e7e52d665a1db"
    }
}
```
失败信息:

|status|msg|
|:----:|---|
|-3|缺少参数|
|-4|课程不存在|
|-4|未参加学习|
### 3.公告列表
URL:_http://39.107.102.246/course/notice_

请求方法:_GET_

请求参数样例:_?course=81f88387543743328b30d7b299f33c01_

返回成功样例:
```json5
{
    "status": 0,
    "msg": "查询成功",
    "data": [
        {
            "content": "公告",
            "createTime": "2019-04-22 22:18:20"
        }
    ]
}
```
错误信息:_无_
### 4.轮播图ID列表
URL:_http://39.107.102.246/rotationPicture/list_

请求方法:_Get_

请求参数样例:_?tag=index_

返回成功样例:
```json5
{
    "status": 0,
    "msg": "查询成功",
    "data": [
        {
            "id": "rotation1",
            "mappingPath": "/files/image/rotation/",
            "realName": "rotation_picture_1",
            "suffix": "jpg"
        },
        {
            "id": "rotation2",
            "mappingPath": "/files/image/rotation/",
            "realName": "rotation_picture_2",
            "suffix": "jpg"
        },
        {
            "id": "rotation3",
            "mappingPath": "/files/image/rotation/",
            "realName": "rotation_picture_3",
            "suffix": "jpg"
        }
    ]
}
```
失败信息:_无_
