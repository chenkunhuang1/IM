package com.example.asus.im.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.asus.im.R
import com.example.asus.im.adapter.ContactListAdapter
import com.example.asus.im.adapter.EMContactListenerAdapter
import com.example.asus.im.contract.ContactContract
import com.example.asus.im.presenter.ContactPresenter
import com.example.asus.im.ui.activity.AddFriendActivity
import com.example.asus.im.widget.SlideBar
import com.hyphenate.chat.EMClient
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.view_contact_item.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

/**
 * Created by ckh.
 * date : 2019/1/7 17:53
 * desc :
 */
class ContactFragment : BaseFragment(),ContactContract.View{

    val presenter = ContactPresenter(this)
    val contactListener = object : EMContactListenerAdapter(){
        override fun onContactDeleted(p0: String?) {
            //重新加载数据
            presenter.loadContacts()
        }

        override fun onContactAdded(p0: String?) {
            //重新获取联系人数据
            presenter.loadContacts()
        }
    }
    override fun getLayoutResId(): Int {
        return R.layout.fragment_contacts
    }

    override fun init() {
        super.init()
        initHeader()

        initSwipeRefreshLayout()
        initRecyclerView()

        EMClient.getInstance().contactManager().setContactListener(contactListener)

        initSlideBar()
        presenter.loadContacts()
    }

    private fun initSlideBar() {
        slideBar.onSectionChangeListener = object : SlideBar.OnSectionChangeListener {
            override fun onSectionChange(firstLetter: String) {
                section.visibility = View.VISIBLE
                section.text = firstLetter
                recyclerView.smoothScrollToPosition(getPosition(firstLetter))
            }

            override fun onSlideFinish() {
                section.visibility = View.GONE

            }

        }
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ContactListAdapter(context, presenter.contactListItems)

        }
    }

    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.apply {
            setColorSchemeResources(R.color.qq_blue)
            isRefreshing = true
            swipeRefreshLayout.setOnRefreshListener {
                presenter.loadContacts()
            }
        }
    }

    private fun initHeader() {
        headerTitle.text = getString(R.string.contact)

        add.visibility = View.VISIBLE

        add.setOnClickListener { startActivity<AddFriendActivity>() }
    }

    private fun getPosition(firstLetter:String): Int =
        presenter.contactListItems.binarySearch {
            contactListItem -> contactListItem.firstLetter.minus(firstLetter[0])
        }


    override fun loadContactsSuccess() {
        swipeRefreshLayout.isRefreshing = false
        recyclerView.adapter.notifyDataSetChanged()
    }

    override fun loadContactsFailed() {
        swipeRefreshLayout.isRefreshing = false
        toast(R.string.load_contacts_failed)
    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().contactManager().removeContactListener(contactListener)
    }
}