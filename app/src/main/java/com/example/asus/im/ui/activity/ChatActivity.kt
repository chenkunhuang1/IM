package com.example.asus.im.ui.activity

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.asus.im.R
import com.example.asus.im.adapter.EMMessageListenerAdapter
import com.example.asus.im.adapter.MessageListAdapter
import com.example.asus.im.contract.ChatContract
import com.example.asus.im.presenter.ChatPresenter
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.toast

/**
 * Created by ckh.
 * date : 2019/1/7 21:35
 * desc : 聊天界面
 */
class ChatActivity : BaseActivity(),ChatContract.View{
    override fun onMoreMessageLoaded(size: Int) {
        recyclerView.adapter.notifyDataSetChanged()
        recyclerView.scrollToPosition(size)
    }

    override fun onMessageLoaded() {
        recyclerView.adapter.notifyDataSetChanged()
        scrollToBottom()
    }

    lateinit var username : String
    val presenter  = ChatPresenter(this)

    val messageListener = object : EMMessageListenerAdapter(){
        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
            presenter.addMessage(username,p0)
            runOnUiThread {
                recyclerView.adapter.notifyDataSetChanged()
                scrollToBottom()}
        }
    }
    override fun onStartSendMessage() {
        //通知RecyclerView刷新
        recyclerView.adapter.notifyDataSetChanged()
    }

    override fun onSendMessageFailed() {
        toast(R.string.send_message_failed)
        recyclerView.adapter.notifyDataSetChanged()
    }

    override fun onSendMessageSuccess() {

        toast(R.string.send_message_success)
        //清空编辑框
        edit.text.clear()
        scrollToBottom()
    }

    private fun scrollToBottom() {
        recyclerView.scrollToPosition(presenter.emMessageList.size - 1)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_chat
    }

    override fun init() {
        super.init()
        initHeader()
        initEditText()
        initRecyclerView()
        EMClient.getInstance().chatManager().addMessageListener(messageListener)
        send.setOnClickListener { send() }
        presenter.loadMessages(username)
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = MessageListAdapter(context,presenter.emMessageList)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                    //如果RecyclerView是空闲状态
                    //检查是否滑动到顶部，加载更多数据
                    if (newState == RecyclerView.SCROLL_STATE_IDLE){
                        val linearLayoutManager = layoutManager as LinearLayoutManager
                        //如果第一个可见条目是0
                        if (linearLayoutManager.findFirstVisibleItemPosition() == 0){
                            //加载更多数据
                            presenter.loadMoreMessage(username)
                        }
                    }
                }
            })

        }
    }

    fun send(){
        hideSoftKeyboard()
        send.isEnabled = false
        val message = edit.text.trim().toString()
        presenter.onSendMessage(username,message)
    }

    private fun initEditText() {
        edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //如果用户输入字符不为空
                send.isEnabled = !s.isNullOrEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        edit.setOnEditorActionListener { v, actionId, event ->
            send()
            true
        }
    }

    private fun initHeader() {
        back.visibility = View.VISIBLE
        back.setOnClickListener { finish() }

        //获取聊天的用户名
        username = intent.getStringExtra("username")
        val titleString = String.format(getString(R.string.chat_title),username)
        headerTitle.text = titleString
    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().chatManager().removeMessageListener(messageListener)
    }

}