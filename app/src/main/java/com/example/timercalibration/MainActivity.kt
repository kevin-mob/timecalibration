package com.example.timercalibration

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.timercalibration.interceptor.ServerTimeInterceptor
import com.example.timercalibration.util.TimeUtil
import okhttp3.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private val okHttpClient: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(ServerTimeInterceptor()).build()
    private val request: Request = Request.Builder().url("https://www.xcc.cn/static/img/logo.aacc5cc.png").build()
    private val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.getDefault())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun requestNetwork(view: View) {
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException) {
                Log.d(TAG, "onFailure: ${e.message}")
                System.currentTimeMillis()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call?, response: Response) {
                findViewById<TextView>(R.id.tv_hello)?.let {
                    runOnUiThread {
                        it.text = format.format(Date(TimeUtil.getCurrentTimeMillis()))
                    }
                }
            }
        })
    }

    companion object {
        const val TAG = "MainActivity"
    }
}