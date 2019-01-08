package com.example.asus.im.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.asus.im.R
import com.example.asus.im.adapter.EMCallBackAdapter
import com.example.asus.im.data.AddFriendItem
import com.hyphenate.chat.EMClient
import kotlinx.android.synthetic.main.view_add_friend_item.view.*
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast

/**
 * Created by ckh.
 * date : 2019/1/8 10:23
 * desc :   添加好友列表的item
 */
class AddFriendListItemView(context: Context?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {
    fun bindView(addFriendItem: AddFriendItem) {
        if (addFriendItem.isAdd){
            add.isEnabled = false
            add.text = context.getString(R.string.already_added)
        }else{
            add.isEnabled = true
            add.text = context.getString(R.string.add)
        }
        userName.text = addFriendItem.userName
        timestamp.text = addFriendItem.timestamp

        add.setOnClickListener { addFriend(addFriendItem.userName) }
    }

    private fun addFriend(userName: String) {
        EMClient.getInstance().contactManager().aysncAddContact(userName,null,object :EMCallBackAdapter(){
            override fun onSuccess() {
                context.runOnUiThread { toast(R.string.send_message_success) }
            }

            override fun onError(p0: Int, p1: String?) {
                context.runOnUiThread { toast(R.string.send_message_failed) }
            }
        })
    }

    init {
        View.inflate(context, R.layout.view_add_friend_item,this)
    }
}