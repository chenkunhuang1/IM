package com.example.asus.im.contract

/**
 * Created by ckh.
 * date : 2019/1/7 20:19
 * desc : 联系人契约类
 */
interface ContactContract {
    interface Presenter : BasePresent{
        fun loadContacts()
    }
    interface View {
        fun loadContactsSuccess()

        fun loadContactsFailed()
    }
}