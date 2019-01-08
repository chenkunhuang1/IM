package com.example.asus.im.ui.activity

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager

/**
 * Created by ckh.
 * date : 2019/1/7 11:55
 * desc : Activity 基类的抽取
 */

abstract class BaseActivity : AppCompatActivity(){
    val progressDialog by lazy {
        ProgressDialog(this)
    }


    val inputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        init()
    }

    open fun init() {
        //初始化一些资源  子类可以复写该方法
    }

    //返回布局文件的资源Id  子类必须实现
    abstract fun getLayoutResId(): Int

    fun showProgressDialog(message:String){
        progressDialog.setMessage(message)
        progressDialog.show()
    }
    fun dismissProgress(){
        progressDialog.dismiss()
    }

    fun hideSoftKeyboard(){
        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken,0)
    }
}
