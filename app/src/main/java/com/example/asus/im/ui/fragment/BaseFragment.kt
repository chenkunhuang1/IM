package com.example.asus.im.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by ckh.
 * date : 2019/1/7 12:01
 * desc :  BaseFragment的抽取
 */
abstract class BaseFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResId(),null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
    }

    open fun init() {
        //初始化Fragment中的一些公共的方法，子类可以复写
    }

    abstract fun getLayoutResId(): Int
}