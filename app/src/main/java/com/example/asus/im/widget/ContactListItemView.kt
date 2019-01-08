package com.example.asus.im.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.asus.im.R
import com.example.asus.im.data.ContactListItem
import kotlinx.android.synthetic.main.view_contact_item.view.*

/**
 * Created by ckh.
 * date : 2019/1/7 19:15
 * desc :
 */
class ContactListItemView(context: Context?, attrs: AttributeSet?=null) : RelativeLayout(context, attrs) {
    fun bindView(contactListItem: ContactListItem) {
        if (contactListItem.isShowFirstLetter){
            firstLetter.visibility = View.VISIBLE
            firstLetter.text = contactListItem.firstLetter.toString()
        }else firstLetter.visibility = View.GONE

        userName.text = contactListItem.userName
    }

    init {
        View.inflate(context, R.layout.view_contact_item,this)
    }
}