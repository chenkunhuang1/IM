package com.example.asus.im.contract

/**
 * Created by ckh.
 * date : 2019/1/7 14:06
 * desc :
 */
interface LoginContract{
    interface LoginPresenter : BasePresent{
        fun login(userName:String,passWord:String)
    }

    interface View {
        fun onUserNameError()
        fun onPassWordError()
        fun onStartLogin()
        fun onLoggedInSuccess()
        fun onLoggedInFailed()
    }
}