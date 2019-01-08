package com.example.asus.im.ui.activity

import android.os.Handler
import com.example.asus.im.R
import com.example.asus.im.contract.SplashContract
import com.example.asus.im.presenter.SplashPresenter
import org.jetbrains.anko.startActivity

/**
 * Created by ckh.
 * date : 2019/1/7 12:08
 * desc :
 */
class SplashActivity : BaseActivity(),SplashContract.View{

    val presenter = SplashPresenter(this)
    companion object {
        val DELAY  = 2000L
    }
    val handler by lazy {
        Handler()
    }

    override fun init() {
        super.init()
        presenter.checkLoginStatus()
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_splash
    }
    //延迟两秒,进入主界面
    override fun onNotLoggedin() {
        handler.postDelayed({
            startActivity<LoginActivity>()
            finish()
        }, DELAY)
    }
    //跳转到主界面
    override fun inLoggedin() {
        startActivity<MainActivity>()
        finish()
    }

}