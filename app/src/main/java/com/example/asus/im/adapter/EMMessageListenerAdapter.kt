package com.example.asus.im.adapter

import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMMessage

/**
 * Created by ckh.
 * date : 2019/1/8 17:00
 * desc :
 */
open class EMMessageListenerAdapter : EMMessageListener{
    override fun onMessageRecalled(p0: MutableList<EMMessage>?) {
    }

    override fun onMessageChanged(p0: EMMessage?, p1: Any?) {
    }

    override fun onCmdMessageReceived(p0: MutableList<EMMessage>?) {
    }

    override fun onMessageReceived(p0: MutableList<EMMessage>?) {
    }

    override fun onMessageDelivered(p0: MutableList<EMMessage>?) {
    }

    override fun onMessageRead(p0: MutableList<EMMessage>?) {
    }

}