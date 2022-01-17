package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.firebase.databinding.ActivitySecondBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth

class SecondActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_second)

//        val auth = FirebaseAuth.getInstance()
//        val currentUser = auth.currentUser
//        val email = currentUser?.email
//        val emailName = currentUser?.displayName

        val emailName = intent.getStringExtra("name").toString()
        val email = intent.getStringExtra("email").toString()
        binding.emailNameTextView.text = emailName
        binding.emailTextView.text = email
        binding.logoutButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}