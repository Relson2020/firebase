package com.example.firebase

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.databinding.DataBindingUtil
import com.example.firebase.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.android.gms.common.api.ApiException
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var fireBaseInstance : FirebaseDatabase
    private lateinit var fireBaseDataBaseReference : DatabaseReference
    private lateinit var childReference: DatabaseReference
    private lateinit var googleSignIn : GoogleSignInOptions
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var task : Task<GoogleSignInAccount>
    private lateinit var account : GoogleSignInAccount
    private lateinit var callBackManager : CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        // dataBase
        fireBaseInstance = FirebaseDatabase.getInstance()
        Firebase.database.setPersistenceEnabled(true)
        fireBaseDataBaseReference = fireBaseInstance.reference
        childReference = fireBaseDataBaseReference.child("message")
        childReference.keepSynced(true)

        binding.messageButton.setOnClickListener {
            childReference.setValue(binding.editTextView2.text.toString())
        }

        // firebase messaging
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if(!it.isSuccessful ){
                return@addOnCompleteListener
            }
            val token  = it.result
            Log.i("token", token)
            //Toast.makeText(this, token,Toast.LENGTH_SHORT).show()
        }

        //
        requestGoogleSignIn()
        binding.googleButton.setOnClickListener {
            signIn()
        }


        callBackManager = CallbackManager.Factory.create()
        binding.facebookLoginButton.setPermissions(listOf("email"))
        binding.facebookLoginButton.registerCallback(callBackManager , object : FacebookCallback<LoginResult>{
            override fun onCancel() {

            }

            override fun onError(error: FacebookException) {

            }

            override fun onSuccess(result: LoginResult) {

            }
        })

    }


    private fun requestGoogleSignIn(){
        googleSignIn = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,googleSignIn)
    }
    private fun signIn() {
        val intent = googleSignInClient.signInIntent
        resultLauncher.launch(intent)

    }

    private var resultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)

        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            account = task.getResult(ApiException::class.java)
            Toast.makeText(this,"hey",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,SecondActivity::class.java))
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
        }

    }

}