package com.example.asus.im.contract

import com.hyphenate.chat.EMMessage

/**
 * Created by ckh.
 * date : 2019/1/8 15:46
 * desc :
 */
interface ChatContract{
    interface Presenter : BasePresent{
        fun onSendMessage(contact : String,message:String)
        fun addMessage(username: String, p0: MutableList<EMMessage>?)
        fun loadMessages(username: String)
        fun loadMoreMessage(username: String)
    }

    interface View{
        fun onMoreMessageLoaded(size: Int)

        fun onStartSendMessage()
        fun onSendMessageFailed()
        fun onSendMessageSuccess()
        fun onMessageLoaded()
    }
}