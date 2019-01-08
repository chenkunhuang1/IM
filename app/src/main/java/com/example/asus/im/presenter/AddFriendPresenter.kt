package com.example.asus.im.presenter

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.example.asus.im.contract.AddFriendContract
import com.example.asus.im.data.AddFriendItem
import com.example.asus.im.data.db.IMDataBase
import com.hyphenate.chat.EMClient
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by ckh.
 * date : 2019/1/8 10:37
 * desc :
 */
class AddFriendPresenter(val view:AddFriendContract.View) : AddFriendContract.Presenter{
    val addFriendListItems = mutableListOf<AddFriendItem>()
    override fun search(key: String) {
        val query = BmobQuery<BmobUser>()
        query.addWhereContains("username",key)
            .addWhereNotEqualTo("username",EMClient.getInstance().currentUser)
            query.findObjects(object : FindListener<BmobUser>(){
                override fun done(p0: MutableList<BmobUser>?, p1: BmobException?) {
                    if (p1 == null){
                        addFriendListItems.clear()
                        //处理数据
                        //创建AddFriend列表
                        val allContacts = IMDataBase.instance.getAllContacts()
                        doAsync {
                            p0?.forEach {
                                //比对是否已经添加过好友
                                var isAdded = false
                                for (contact in allContacts){
                                    if (contact.name == it.username){
                                        isAdded = true
                                    }
                                }
                                val addFriendListItem = AddFriendItem(it.username,it.createdAt,isAdded)
                                addFriendListItems.add(addFriendListItem)

                            }
                            uiThread { view.onSearchSuccess() }
                        }
                    }else view.onSearchFailed()
                }
            })
    }

}