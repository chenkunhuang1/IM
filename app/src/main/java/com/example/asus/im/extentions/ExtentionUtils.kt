package com.example.asus.im.extentions

/**
 * Created by ckh.
 * date : 2019/1/7 14:29
 * desc :
 */
fun String.isValidUserName(): Boolean = this.matches(Regex("^[a-zA-Z]\\w{2,19}$"))
fun String.isValidPassWord() : Boolean = this.matches(Regex("^[0-9]{3,20}$"))

fun<K,V> MutableMap<K,V>.toVararArray() : Array<Pair<K,V>>{
    //将MutableMap 转换成Pair类型的数组
    return map {
        Pair(it.key,it.value)
    }.toTypedArray()
}