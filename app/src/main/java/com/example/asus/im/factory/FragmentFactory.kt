package com.example.asus.im.factory

import android.support.v4.app.Fragment
import com.example.asus.im.R
import com.example.asus.im.ui.fragment.ContactFragment
import com.example.asus.im.ui.fragment.ConversationFragment
import com.example.asus.im.ui.fragment.DynamicFragment


/**
 * Created by ckh.
 * date : 2019/1/7 18:03
 * desc :
 */
class FragmentFactory private constructor(){
    val conversation by lazy {
        ConversationFragment()
    }

    val contact by lazy {
        ContactFragment()
    }

    val dynamic by lazy {
        DynamicFragment()
    }

    companion object {
        val instance = FragmentFactory()
    }

    fun getFragment(tabId: Int): Fragment? {
        when(tabId)  {
            R.id.tab_conversation -> return conversation
            R.id.tab_contacts -> return contact
            R.id.tab_dynamic -> return dynamic
        }
        return null;
    }
}