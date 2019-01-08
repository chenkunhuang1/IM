package com.example.asus.im.data.db

import com.example.asus.im.extentions.toVararArray
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 * Created by ckh.
 * date : 2019/1/8 11:33
 * desc :
 */
class IMDataBase{
    companion object {
        val dataBaseHelper = DataBaseHelper()
        val instance = IMDataBase()
    }
    fun saveContact(contact : Contact){
        dataBaseHelper.use {
            insert(ContactTable.NAME,*contact.map.toVararArray())
        }
    }

    fun getAllContacts() : List<Contact>{
        return dataBaseHelper.use {
            select(ContactTable.NAME).parseList(object : MapRowParser<Contact>{
                override fun parseRow(columns: Map<String, Any?>): Contact {
                    return Contact(columns.toMutableMap())
                }
            })
        }
    }

    fun deleteContacts(){
        dataBaseHelper.use {
            delete(ContactTable.NAME,null,null)
        }
    }
}