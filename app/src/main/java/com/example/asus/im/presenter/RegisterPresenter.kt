package com.example.asus.im.presenter

import android.support.annotation.UiThread
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.example.asus.im.contract.RegisterContract
import com.example.asus.im.extentions.isValidPassWord
import com.example.asus.im.extentions.isValidUserName
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.a.e
import com.hyphenate.exceptions.HyphenateException
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by ckh.
 * date : 2019/1/7 16:05
 * desc :
 */
class RegisterPresenter(val view : RegisterContract.View) : RegisterContract.Presenter{
    override fun regiester(userName: String, password: String, confirmPassword: String) {
        if (userName.isValidUserName()){
            if (password.isValidPassWord()){
                if (password.equals(confirmPassword)){
                    view.onStartRegister()
                    //开始注册
                    registerBmob(userName,password)
                }else view.onConfirmPasswordError()
            }else view.onPasswordError()
        }else view.onUserNameError()
    }

    private fun registerBmob(userName: String, password: String) {
        val user = BmobUser()
        user.username = userName
        user.setPassword(password)
        user.signUp<BmobUser>(object : SaveListener<BmobUser>(){
            override fun done(s: BmobUser?, e: BmobException?) {
                if (e == null){
                    //注册成功
                    //view.onRegisterSuccess()
                    //注册到环信
                    registerEaseMob(userName,password)
                }else{
                    if (e.errorCode == 202) view.onUserExist()
                    //注册失败
                    else view.onRegisterFailed()
                }
            }

        })
    }

    private fun registerEaseMob(userName: String, password: String) {
        doAsync {
            try {
                //注册失败会抛出HyphenateException
                EMClient.getInstance().createAccount(userName, password)//同步方法
                //注册成功
                uiThread { view.onRegisterSuccess() }
            }catch (e : HyphenateException){
                uiThread { view.onRegisterFailed() }
            }

        }

    }

}