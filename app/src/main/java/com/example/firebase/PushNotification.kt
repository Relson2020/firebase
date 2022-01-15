package com.example.firebase

import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotification : FirebaseMessagingService() {

//    override fun onNewToken(p0: String) {
//        super.onNewToken(p0)
//        Log.i("log","hey token $p0 ")
//        Toast.makeText(applicationContext,"toast",Toast.LENGTH_SHORT).show()
//    }
//
////    override fun onMessageReceived(message: RemoteMessage) {
////        super.onMessageReceived(message)
////        Toast.makeText(applicationContext,"${message.from}",Toast.LENGTH_SHORT).show()
////        Toast.makeText(applicationContext,"${message.data}",Toast.LENGTH_SHORT).show()
////    }
}