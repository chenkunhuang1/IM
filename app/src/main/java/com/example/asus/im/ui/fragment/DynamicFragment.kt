package com.example.asus.im.ui.fragment

import com.example.asus.im.R
import com.example.asus.im.adapter.EMCallBackAdapter
import com.example.asus.im.ui.activity.LoginActivity
import com.hyphenate.chat.EMClient
import kotlinx.android.synthetic.main.fragment_dynamic.*
import kotlinx.android.synthetic.main.header.*
import com.hyphenate.EMCallBack
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


/**
 * Created by ckh.
 * date : 2019/1/7 17:53
 * desc :
 */
class DynamicFragment : BaseFragment(){
    override fun getLayoutResId(): Int {
        return R.layout.fragment_dynamic
    }

    override fun init() {
        super.init()
        headerTitle.text = getString(R.string.dynamic)

        val logoutString = String.format(getString(R.string.logout),EMClient.getInstance().currentUser)
        logout.text = logoutString
        logout.setOnClickListener { logout() }
    }

    fun logout() {
        EMClient.getInstance().logout(true, object : EMCallBackAdapter() {

            override fun onSuccess() {
                context?.runOnUiThread {
                    toast(R.string.logout_success)
                    context?.startActivity<LoginActivity>()
                    activity?.finish()
                }

            }

            override fun onError(p0: Int, p1: String?) {
                //切换到主线程
                context?.runOnUiThread { toast(R.string.logout_failed) }
            }
        })
    }

}