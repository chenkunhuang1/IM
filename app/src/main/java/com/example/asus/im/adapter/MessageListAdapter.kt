package com.example.asus.im.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.asus.im.widget.ReceiveMessageItemView
import com.example.asus.im.widget.SendMessageItemView
import com.hyphenate.chat.EMMessage
import com.hyphenate.util.DateUtils

/**
 * Created by ckh.
 * date : 2019/1/8 16:18
 * desc :
 */
class MessageListAdapter(val context: Context,val message : List<EMMessage> ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        val ITEM_TYPE_SEND_MESSAGE= 0
        val ITEM_TYPE_RECEIVE_MESSAGE = 1
    }

    override fun getItemViewType(position: Int): Int {
        if (message[position].direct() == EMMessage.Direct.SEND){
            return ITEM_TYPE_SEND_MESSAGE
        }else {
            return ITEM_TYPE_RECEIVE_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_SEND_MESSAGE){
            SendMessageViewHolder(SendMessageItemView(context))
        }else ReceiverMessageViewHolder(ReceiveMessageItemView(context))
    }

    override fun getItemCount(): Int = message.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val showTimeStamp = isShowTimeStamp(position)
        if (getItemViewType(position) == ITEM_TYPE_SEND_MESSAGE){
            val sendMessageItemView = holder?.itemView as SendMessageItemView
            sendMessageItemView.bindView(message[position],showTimeStamp)
        }else{
            val receiveMessageItemView = holder?.itemView as ReceiveMessageItemView
            receiveMessageItemView.bindView(message[position],showTimeStamp)
        }

    }

    private fun isShowTimeStamp(position: Int): Boolean {
        var showTimeStamp = true
        if (position > 0){
            showTimeStamp = !DateUtils.isCloseEnough(message[position].msgTime , message[position - 1].msgTime)
        }
        return showTimeStamp

    }

    class SendMessageViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }

    class ReceiverMessageViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }
}