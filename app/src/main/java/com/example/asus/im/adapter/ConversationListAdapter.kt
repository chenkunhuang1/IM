package com.example.asus.im.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.asus.im.ui.activity.ChatActivity
import com.example.asus.im.widget.ConversationListItemView
import com.hyphenate.chat.EMConversation
import org.jetbrains.anko.startActivity

/**
 * Created by ckh.
 * date : 2019/1/8 18:56
 * desc :
 */
class ConversationListAdapter(
    val context: Context,
    val conversations: MutableList<EMConversation>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ConversationListItemViewHolder(ConversationListItemView(context))
    }

    override fun getItemCount(): Int {
        return conversations.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val conversationListItemView = holder.itemView as ConversationListItemView
        conversationListItemView.bindView(conversations[position])
        conversationListItemView.setOnClickListener{context.startActivity<ChatActivity>(
            "username" to conversations[position].conversationId()
        )}
    }

    class ConversationListItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {}
}