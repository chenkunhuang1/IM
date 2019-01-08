package com.example.asus.im.ui.activity

import android.support.v7.widget.LinearLayoutManager
import com.example.asus.im.R
import com.example.asus.im.adapter.AddFriendListAdapter
import com.example.asus.im.contract.AddFriendContract
import com.example.asus.im.presenter.AddFriendPresenter
import kotlinx.android.synthetic.main.activity_add_friend.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.toast

/**
 * Created by ckh.
 * date : 2019/1/8 10:17
 * desc :
 */
class AddFriendActivity : BaseActivity(),AddFriendContract.View{

    val presenter = AddFriendPresenter(this)
    override fun onSearchSuccess() {
        dismissProgress()
        toast(R.string.search_success)
        recyclerView.adapter.notifyDataSetChanged()
    }

    override fun onSearchFailed() {
        dismissProgress()
        toast(R.string.search_failed)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_add_friend
    }

    override fun init() {
        super.init()
        headerTitle.text = getString(R.string.add_friend)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = AddFriendListAdapter(context,presenter.addFriendListItems)
        }

        search.setOnClickListener { search() }
        userName.setOnEditorActionListener { v, actionId, event ->
            search()
            true
        }
    }
    fun search(){
        hideSoftKeyboard()
        showProgressDialog(getString(R.string.searching))
        val key = userName.text.trim().toString()
        presenter.search(key)
    }

}