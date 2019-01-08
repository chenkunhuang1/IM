package com.example.asus.im.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.asus.im.app.IMApplication
import org.jetbrains.anko.db.*

/**
 * Created by ckh.
 * date : 2019/1/8 11:21
 * desc :
 */
class DataBaseHelper(ctx: Context = IMApplication.instance) :
    ManagedSQLiteOpenHelper(ctx, NAME, null, VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(ContactTable.NAME,true,
            ContactTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            ContactTable.CONTACT to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(ContactTable.NAME,true)
        onCreate(db)
    }

    companion object {
        val NAME = "im.db"
        val VERSION = 1
    }


}