package com.example.asus.im.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.asus.im.data.AddFriendItem
import com.example.asus.im.widget.AddFriendListItemView

/**
 * Created by ckh.
 * date : 2019/1/8 10:26
 * desc :
 */
class AddFriendListAdapter(val context: Context, val addFriendListItems: MutableList<AddFriendItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AddFriendListAdapterViewHolder(AddFriendListItemView(context))
    }

    override fun getItemCount(): Int {
        return addFriendListItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val addFriendListItemView = holder.itemView as AddFriendListItemView
        addFriendListItemView.bindView(addFriendListItems[position])
    }
    class AddFriendListAdapterViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {}
}