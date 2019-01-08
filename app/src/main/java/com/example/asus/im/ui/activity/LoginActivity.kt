package com.example.asus.im.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import com.example.asus.im.R
import com.example.asus.im.contract.LoginContract
import com.example.asus.im.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by ckh.
 * date : 2019/1/7 12:34
 * desc :
 */
class LoginActivity : BaseActivity(),LoginContract.View{

    val presenter = LoginPresenter(this)
    override fun init() {
        super.init()
        newUser.setOnClickListener { startActivity<RegisterActivity>() }
        login.setOnClickListener {
            login()
            password.setOnEditorActionListener { v, actionId, event ->
                login()//通过软键盘登录
                true
            }
        }
    }

    fun login(){
        //隐藏软键盘
        hideSoftKeyboard()
        if(hasWriteExternalStoragePermission()){
            val userName = userName.text.trim().toString()
            val password = password.text.trim().toString()
            presenter.login(userName,password)
        }else{
            applyWriteExternalStoragePermission()
        }

    }

    private fun applyWriteExternalStoragePermission() {
        val permission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(this,permission,0)
    }

    //检查是否有写磁盘的权限
    private fun hasWriteExternalStoragePermission(): Boolean {
        val result = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //用户同意权限
            login()
        }else{
            toast(R.string.permission_denied)
        }
    }

    override fun onUserNameError() {
        userName.error = getString(R.string.user_name_error)
    }

    override fun onPassWordError() {
        password.error = getString(R.string.password_error)
    }

    override fun onStartLogin() {
        //弹出进度条
        showProgressDialog(getString(R.string.logging))
    }

    override fun onLoggedInSuccess() {
        //隐藏进度条
        dismissProgress()
        //进入主界面
        startActivity<MainActivity>()
        //结束当前界面
        finish()
    }

    override fun onLoggedInFailed() {
        //隐藏进度条
        dismissProgress()
        //弹出toast
        toast(R.string.login_failed)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_login
    }

}