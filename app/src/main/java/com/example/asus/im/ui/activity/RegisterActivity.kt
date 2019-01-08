package com.example.asus.im.ui.activity

import com.example.asus.im.R
import com.example.asus.im.contract.RegisterContract
import com.example.asus.im.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

/**
 * Created by ckh.
 * date : 2019/1/7 15:49
 * desc :
 */
class RegisterActivity : BaseActivity(),RegisterContract.View{
    override fun onUserExist() {
        dismissProgress()
        toast(R.string.user_already_exist)
    }

    val presenter = RegisterPresenter(this)
    override fun init() {
        super.init()
        register.setOnClickListener { register() }
        confirmPassword.setOnEditorActionListener { v, actionId, event ->
            register()
            true
        }
    }

    fun register(){
        //隐藏软件盘
        hideSoftKeyboard()
        val useName = userName.text.trim().toString()
        val password = password.text.trim().toString()
        val confirmPassword = confirmPassword.text.trim().toString()
        presenter.regiester(useName,password,confirmPassword)
    }
    override fun onUserNameError() {
        userName.error = getString(R.string.user_name_error)
    }

    override fun onPasswordError() {
        password.error = getString(R.string.password_error)
    }

    override fun onConfirmPasswordError() {
        confirmPassword.error = getString(R.string.confirm_password_error)
    }

    override fun onStartRegister() {
        showProgressDialog(getString(R.string.registering))
    }

    override fun onRegisterSuccess() {
        showProgressDialog(getString(R.string.register_success))
        toast(R.string.register_success)
        finish()
    }

    override fun onRegisterFailed() {
        dismissProgress()
        toast(R.string.register_failed)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_register
    }

}