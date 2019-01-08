package com.example.asus.im.contract

/**
 * Created by ckh.
 * date : 2019/1/7 15:52
 * desc :
 */
interface RegisterContract{
    interface Presenter : BasePresent{
        fun regiester(userName:String,password:String,confirmPassword:String)
    }

    interface View {
        fun onUserNameError()
        fun onPasswordError()
        fun onConfirmPasswordError()
        fun onStartRegister()
        fun onRegisterSuccess()
        fun onRegisterFailed()
        fun onUserExist()
    }
}
