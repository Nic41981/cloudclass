# 在线教育平台后台API文档

## 目录
- [用户部分](#用户部分)
    -[1.用户注册](#用户注册)
***
## 用户部分
### 1.用户注册
URL:_http://39.107.102.246/user/register_

请求方式:_POST_

请求参数样例:
```json
{
	"name":"test",
	"password":"123456",
	"email":"test@qq.com"
}
```
成功返回样例:
```json
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
```json
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

请求参数样例:无

成功返回样例:
```json
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
## 教师个人中心-课程部分
### 1.教师课程列表
### 2.新建课程
### 3.修改课程
### 4.删除课程