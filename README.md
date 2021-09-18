# Timer Calibration

### 作用
不依赖客户端时钟获取精确的时间，防止用户手动更改本地时间。
### 用法
将ServerTimeInterceptor拦截器加入OkHttp中，当有网络请求产生后，调用TimeUtil.getCurrentTimeMillis()即可获取时间。
### 原理
https://www.jianshu.com/writer#/notebooks/17583395/notes/92920314