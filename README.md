# tarsier test

统一出参格式：Result
private boolean success = false;
private String errorCode;
private String errorMessage;
private String date;
private T obj;



测试接口(POST)：http://10.2.4.33:8080/book/test
入参：空
出参：{"success":true,"errorCode":null,"errorMessage":null,"date":"2017-12-14 19:54:47","obj":1}