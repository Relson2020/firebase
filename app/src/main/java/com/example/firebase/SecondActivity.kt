package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.firebase.databinding.ActivitySecondBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.sign

class SecondActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySecondBinding
    private lateinit var googleSignInClient : GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_second)

        val account = GoogleSignIn.getLastSignedInAccount(this)

        val emailName = account?.displayName
        val email = account?.email
        binding.emailNameTextView.text = emailName
        binding.emailTextView.text = email

        val googleSignIn = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,googleSignIn)


        binding.logoutButton.setOnClickListener {
           signOut()
        }
    }

    private fun signOut() {
        googleSignInClient.signOut().addOnSuccessListener{
            Toast.makeText(applicationContext,"Sign out Successfully",Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}