package com.example.asus.im.presenter

import com.example.asus.im.adapter.EMCallBackAdapter
import com.example.asus.im.contract.LoginContract
import com.example.asus.im.extentions.isValidPassWord
import com.example.asus.im.extentions.isValidUserName
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient

/**
 * Created by ckh.
 * date : 2019/1/7 14:35
 * desc :
 */
class LoginPresenter(val view:LoginContract.View) : LoginContract.LoginPresenter{
    override fun login(userName: String, passWord: String) {
        if (userName.isValidUserName()){//用户名合法
            if (passWord.isValidPassWord()){//用户名合法
                view.onStartLogin()//开始登录
                loginEaseMob(userName,passWord)//登录到环信
            }else view.onPassWordError()
        }else view.onUserNameError()
    }

    private fun loginEaseMob(userName: String, passWord: String) {
        EMClient.getInstance().login(userName,passWord,object : EMCallBackAdapter(){
            override fun onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups()
                EMClient.getInstance().chatManager().loadAllConversations()
                //在主线程通知View层
                uiThread { view.onLoggedInSuccess() }
            }

            override fun onError(p0: Int, p1: String?) {
                uiThread { view.onLoggedInFailed() }
            }
        })
    }

}