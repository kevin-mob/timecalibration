package com.example.timercalibration.util

import android.os.SystemClock

/**
 * 描述 :
 * 时间工具类，获取当前的服务器时间
 * SystemClock.elapsedRealtime()：手机系统开机到现在的时间间隔含睡眠时间
 * 通过网络请求获取的服务器时间计算出手机的开机时刻的时间，当本地获取时间时，将开机时刻时间+开机以来的时间即可还原为服务器时间
 * 作者 : yangjinhai
 * 版本 : V1.0
 * 创建时间 : 2021年07月23日 17:49
 */
class TimeUtil {
    companion object {
        /* 手机开机时刻的时间点 */
        private var bootStartUpTime: Long = -1

        /**
         * 获取当前的服务器时间
         * 误差范围决定于最小的网络响应时间
         * 当还没有发起任何网络请求时，返回本地时间
         */
        fun getCurrentTimeMillis(): Long {
            //开机时刻的时间+开机时长 = 当前的服务器时间
            return if (bootStartUpTime == -1L) {
                System.currentTimeMillis()
            } else {
                bootStartUpTime + SystemClock.elapsedRealtime()
            }
        }

        /**
         * 根据服务器时间更新手机开机时刻的时间
         */
        fun updateTime(lastServiceTime: Long) {
            bootStartUpTime = lastServiceTime - SystemClock.elapsedRealtime()
        }
    }
}