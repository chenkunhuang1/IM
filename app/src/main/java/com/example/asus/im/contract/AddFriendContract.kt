package com.example.asus.im.contract

/**
 * Created by ckh.
 * date : 2019/1/8 10:34
 * desc :
 */
interface AddFriendContract{
    interface Presenter : BasePresent{
        fun search(key : String)
    }
    interface View{
        fun onSearchSuccess()
        fun onSearchFailed()
    }
}