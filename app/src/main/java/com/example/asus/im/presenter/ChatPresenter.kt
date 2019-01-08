package com.example.asus.im.presenter

import com.example.asus.im.adapter.EMCallBackAdapter
import com.example.asus.im.contract.ChatContract
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


/**
 * Created by ckh.
 * date : 2019/1/8 15:50
 * desc :
 */
class ChatPresenter(val view:ChatContract.View): ChatContract.Presenter{
    companion object {
        val PAGE_SIZE = 10
    }
    override fun loadMoreMessage(username: String) {
        doAsync {
            val conversation = EMClient.getInstance().chatManager().getConversation(username)
            val startMsgId = emMessageList[0].msgId
            val messages = conversation.loadMoreMsgFromDB(startMsgId, PAGE_SIZE)
            emMessageList.addAll(0,messages)
            uiThread { view.onMoreMessageLoaded(messages.size) }
        }

    }

    override fun loadMessages(username: String) {
        doAsync {
            val conversation = EMClient.getInstance().chatManager().getConversation(username)
            emMessageList.addAll(conversation.allMessages)
            uiThread { view.onMessageLoaded() }
        }

    }

    override fun addMessage(username: String, p0: MutableList<EMMessage>?) {
        //加入当前列表
        p0?.let { emMessageList.addAll(it) }
        //更新消息为已读
        //获取跟联系人的会话 标记为已读
        val conversation = EMClient.getInstance().chatManager().getConversation(username)
        conversation.markAllMessagesAsRead()
    }

    val emMessageList = mutableListOf<EMMessage>()
    override fun onSendMessage(contact: String, message: String) {
        val emMessage = EMMessage.createTxtSendMessage(message,contact)
        emMessage.setMessageStatusCallback(object : EMCallBackAdapter(){
            override fun onSuccess() {
                uiThread {  view.onSendMessageSuccess()}
            }

            override fun onError(p0: Int, p1: String?) {
                uiThread {  view.onSendMessageFailed()}
            }
        })
        emMessageList.add(emMessage)
        view.onStartSendMessage()
        EMClient.getInstance().chatManager().sendMessage(emMessage)
    }

}