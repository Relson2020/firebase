package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.firebase.databinding.ActivityMainBinding
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        // firebase messaging
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if(!it.isSuccessful ){
                return@addOnCompleteListener
            }
            val token  = it.result
            Log.i("token", token)
            //Toast.makeText(this, token,Toast.LENGTH_SHORT).show()
        }

        binding.messageButton.setOnClickListener {
            val intent = Intent(this,PushNotification::class.java)
            startActivity(intent)
        }
    }

}