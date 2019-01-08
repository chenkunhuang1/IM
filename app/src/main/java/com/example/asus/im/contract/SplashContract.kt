package com.example.asus.im.contract

/**
 * Created by ckh.
 * date : 2019/1/7 12:25
 * desc :  Splash页面的契约接口
 */
interface SplashContract{
    interface Presenter : BasePresent{
        //检查是否登录
        fun checkLoginStatus()
    }
    interface View{
        fun onNotLoggedin()//没有登录的ui处理
        fun inLoggedin() //已经登录的ui处理
    }
}