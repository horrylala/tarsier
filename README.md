# Tarsier Readme

## 统一出参格式：
Result  
```  
private boolean success = false;
private String errorCode;
private String errorMessage;
private String date;
private T obj;
```  

## 测试接口(POST)
- 地址  
http://10.2.4.33:8080/book/test  

- 入参：
```  
{
	"id": "123",
	"bookName": "sfwer"
}
```  

- 出参：  
```  
{
	"success": true,
	"errorCode": null,
	"errorMessage": null,
	"date": "2017-12-14 19:54:47",
	"obj": 1
}
````  

## 测试接口(POST)：
- 地址  
http://10.2.4.23:8080/book/getNum  

- 入参  
空   

- 出参  
```    
{
	"success": true,
	"errorCode": null,
	"errorMessage": null,
	"date": "2017-12-14 19:54:47",
	"obj": 2
}
```  


保存参团接口(POST)：http://10.2.4.33:8080/users/save
入参：
{"senderNum":"123","mktId":"c40dfd11-e140-11e7-b870-000000005aad","addr":"sdfds","userName":"sdfds",
"mobile":"sdfds","weight":"123","imageUrl":"https://qq.com/sdfwe/ewrs"}

出参：
错误情况：
{"success":false,"errorCode":"marketNull","errorMessage":null,"date":"2017-12-15 14:23:25","obj":"集货拼团信息不存在，请进入拼团首页后重试！"}
正常情况：
{"success":true,"errorCode":null,"errorMessage":null,"date":"2017-12-15 14:23:25","obj":"集货旧团已过期，自动拼入新团且操作成功！"}
{"success":true,"errorCode":null,"errorMessage":null,"date":"2017-12-15 14:23:25","obj":"集货旧团已满员，自动拼入新团且操作成功！"}
{"success":true,"errorCode":null,"errorMessage":null,"date":"2017-12-15 14:23:25","obj":"参团成功！"}