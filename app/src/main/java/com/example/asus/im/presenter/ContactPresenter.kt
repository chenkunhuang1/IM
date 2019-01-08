package com.example.asus.im.presenter

import com.example.asus.im.contract.ContactContract
import com.example.asus.im.data.ContactListItem
import com.example.asus.im.data.db.Contact
import com.example.asus.im.data.db.IMDataBase
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


/**
 * Created by ckh.
 * date : 2019/1/7 20:26
 * desc :
 */
class ContactPresenter(val view:ContactContract.View) : ContactContract.Presenter{
    val contactListItems = mutableListOf<ContactListItem>()
    override fun loadContacts() {
        doAsync {
            //再次加载数据，先清空集合
            contactListItems.clear()
            //清空数据库
            IMDataBase.instance.deleteContacts()
            try {
                val usernames = EMClient.getInstance().contactManager().allContactsFromServer
                uiThread {
                    view.loadContactsSuccess()
                    //根据首字符排序
                    usernames.sortBy { it[0] }
                    usernames.forEachIndexed { index, s ->
                        val showFirstLetter = index == 0 || s[0] != usernames[index - 1][0]
                        val contactListItem = ContactListItem(s,s[0].toUpperCase(),showFirstLetter)
                        contactListItems.add(contactListItem)

                        val contact = Contact(mutableMapOf("name" to s))
                        IMDataBase.instance.saveContact(contact)
                    }

                }
            }catch (e:HyphenateException){
                uiThread {
                    view.loadContactsFailed()
                }
            }
        }


    }

}