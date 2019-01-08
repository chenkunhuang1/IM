package com.example.asus.im.app

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.SoundPool
import cn.bmob.v3.Bmob
import com.example.asus.im.BuildConfig
import com.example.asus.im.R
import com.example.asus.im.adapter.EMMessageListenerAdapter
import com.example.asus.im.ui.activity.ChatActivity
import com.hyphenate.chat.*
import com.hyphenate.chat.a.e

/**
 * Created by ckh.
 * date : 2019/1/7 13:55
 * desc :
 */
class IMApplication : Application() {
    companion object {
        lateinit var instance : IMApplication
    }

    val soundPool = SoundPool(2,AudioManager.STREAM_MUSIC,0)

    val duan by lazy {
        soundPool.load(instance, R.raw.duan,0)
    }
    val yulu by lazy {
        soundPool.load(instance,R.raw.yulu,0)
    }

    val messageListener = object : EMMessageListenerAdapter() {
        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
            if (isForeground()){
                soundPool.play(duan,1f,1f,0,0,1f)
            }else {
                soundPool.play(yulu,1f,1f,0,0,1f)
                showNotifycation(p0)
            }
        }
    }

    private fun showNotifycation(p0: MutableList<EMMessage>?) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        p0?.forEach {
            var contentText = getString(R.string.no_text_message)
            if (it.type == EMMessage.Type.TXT){
                contentText = (it.body as EMTextMessageBody).message

            }
            val intent = Intent(this,ChatActivity::class.java)
            intent.putExtra("username",it.conversationId())


            val taskstackbuilder = TaskStackBuilder.create(this).addParentStack(ChatActivity::class.java).addNextIntent(intent)
            val pendingintent = taskstackbuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)
            val notifycation = Notification.Builder(this)
                .setContentTitle(getString(R.string.receive_new_message))
                .setContentText(contentText)
                .setLargeIcon(BitmapFactory.decodeResource(resources,R.mipmap.avatar1))
                .setSmallIcon(R.mipmap.ic_contact)
                .setContentIntent(pendingintent)
                .setAutoCancel(true)
                .notification

            notificationManager.notify(1,notifycation)

        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
//        EMOptions options = new EMOptions();
//        // 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
//        // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
//        options.setAutoTransferMessageAttachments(true);
//        // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
//        options.setAutoDownloadThumbnail(true);
//
        //初始化
        EMClient.getInstance().init(applicationContext, EMOptions())
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(BuildConfig.DEBUG)

        //第一：默认初始化
        Bmob.initialize(applicationContext, "a7c00a84e6e7d5cfe5583440edfcc5df")

        EMClient.getInstance().chatManager().addMessageListener(messageListener)
    }

    private fun isForeground():Boolean{
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (runningAppProcess in activityManager.runningAppProcesses) {
            if (runningAppProcess.processName == packageName){
                return runningAppProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
            }
        }
        return false
    }
}