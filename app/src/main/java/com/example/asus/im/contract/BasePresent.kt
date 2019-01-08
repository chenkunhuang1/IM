package com.example.asus.im.contract

import android.os.Handler
import android.os.Looper

/**
 * Created by ckh.
 * date : 2019/1/7 12:23
 * desc :
 */
interface BasePresent{
    companion object {
        val handler by lazy {
            Handler(Looper.getMainLooper())
        }
    }

    fun uiThread(f : () -> Unit){
        handler.post { f() }
    }
}