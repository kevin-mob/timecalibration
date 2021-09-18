package com.example.timercalibration.interceptor

import com.example.timercalibration.util.TimeUtil
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.http.HttpDate

/**
 * 描述 : 获取response header中的时间，通过找到更小的网络请求响应时间来精确本地的时间工具类
 * 作者 : yangjinhai
 * 版本 : V1.0
 * 创建时间 : 2021年07月23日 17:25
 */
class ServerTimeInterceptor : Interceptor {
    /**
     * 记录服务器请求最小响应时间
     */
    var minRequestResponseTime = Long.MAX_VALUE
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val startTime = System.nanoTime()
        val response = chain.proceed(request)
        val requestResponseTime = System.nanoTime() - startTime

        val headers: Headers? = response.headers()
        calibrationTime(requestResponseTime, headers?.get("Date"))
        return response
    }

    private fun calibrationTime(responseTime: Long, serverDateStr: String?) {
        serverDateStr?.let {
            // 本次响应时间小于最小响应时间，更新时间
            if (responseTime < minRequestResponseTime) {
                HttpDate.parse(serverDateStr)?.let {
                    TimeUtil.updateTime(it.time)
                    minRequestResponseTime = responseTime
                }
            }
        }
    }
}