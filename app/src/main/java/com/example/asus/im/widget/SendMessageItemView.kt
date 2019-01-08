package com.example.asus.im.widget

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.asus.im.R
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.hyphenate.util.DateUtils
import kotlinx.android.synthetic.main.view_send_message_item.view.*
import java.util.*

/**
 * Created by ckh.
 * date : 2019/1/8 15:40
 * desc :
 */
class SendMessageItemView(context: Context?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {
    fun bindView(emMessage: EMMessage, showTimeStamp: Boolean) {
        updateTimeStamp(emMessage,showTimeStamp)
        updateMessage(emMessage)
        updateProgress(emMessage)
    }

    private fun updateProgress(emMessage: EMMessage) {
        emMessage.status().let {
            when(it){
                EMMessage.Status.INPROGRESS -> {
                    sendMessageProgress.visibility = View.VISIBLE
                    sendMessageProgress.setImageResource(R.drawable.send_message_progress)
                    val animatable = sendMessageProgress.drawable as AnimationDrawable
                    animatable.start()
                }
                EMMessage.Status.SUCCESS -> {
                    sendMessageProgress.visibility = View.GONE
                }
                EMMessage.Status.FAIL -> {
                    sendMessageProgress.visibility = View.VISIBLE
                    sendMessageProgress.setImageResource(R.mipmap.msg_error)
                }
            }
        }
    }

    private fun updateMessage(emMessage: EMMessage) {
        if (emMessage.type == EMMessage.Type.TXT){
            sendMessage.text = (emMessage.body as EMTextMessageBody).message
        }else sendMessage.text = context.getString(R.string.no_text_message)
    }

    private fun updateTimeStamp(emMessage: EMMessage, showTimeStamp: Boolean) {
        if (showTimeStamp){
            timestamp.visibility = View.VISIBLE
            timestamp.text = DateUtils.getTimestampString(Date(emMessage.msgTime))
        }else timestamp.visibility = View.GONE

    }

    init {
        View.inflate(context, R.layout.view_send_message_item,this)
    }
}