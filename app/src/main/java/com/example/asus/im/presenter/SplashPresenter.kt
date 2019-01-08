package com.example.asus.im.presenter

import com.example.asus.im.contract.SplashContract
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions

/**
 * Created by ckh.
 * date : 2019/1/7 12:46
 * desc :  SplashPresenter界面
 */
class SplashPresenter(val view : SplashContract.View) : SplashContract.Presenter{
    override fun checkLoginStatus() {
        if (isLogged()) view.inLoggedin() else view.onNotLoggedin()
    }
    //是否登录到环信的服务器
    private fun isLogged(): Boolean = EMClient.getInstance().isConnected && EMClient.getInstance().isLoggedInBefore

}