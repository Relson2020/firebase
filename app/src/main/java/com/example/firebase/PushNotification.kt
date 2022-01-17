package com.example.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotification : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.i("log","hey token $p0 ")
        Toast.makeText(applicationContext,"toast",Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {
        val title = message.notification?.title.toString()
        val data = message.notification?.body.toString()
        val notificationChannelName = "Channel"

        val notificationChannel = NotificationChannel(notificationChannelName,"channelId",NotificationManager.IMPORTANCE_HIGH)
        val notificationSystemService = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationSystemService.createNotificationChannel(notificationChannel)
        val notificationBuilder = Notification.Builder(this,notificationChannelName)
            .setContentTitle(title)
            .setContentText(data)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_launcher_background)
        notificationSystemService.notify(0,notificationBuilder.build())

        super.onMessageReceived(message)
    }
}