package com.example.firebase

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import android.R.attr.data
import android.R.attr.id
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.lang.Error


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var fireBaseInstance : FirebaseDatabase
    private lateinit var fireBaseDataBaseReference : DatabaseReference
    private lateinit var childReference: DatabaseReference
    private lateinit var googleSignIn : GoogleSignInOptions
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var task : Task<GoogleSignInAccount>
    private lateinit var account : GoogleSignInAccount
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        fireBaseInstance = FirebaseDatabase.getInstance()
        Firebase.database.setPersistenceEnabled(true)

        fireBaseDataBaseReference = fireBaseInstance.reference
        childReference = fireBaseDataBaseReference.child("message")

        childReference.keepSynced(true)

        requestGoogleSignIn()
        binding.messageButton.setOnClickListener {
            childReference.setValue(binding.editTextView2.text.toString())
        }
        binding.googleButton.setOnClickListener {
            signIn()
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
          //  val account = task.getResult(ApiException::class.java)
            Toast.makeText(this,"hey",Toast.LENGTH_SHORT).show()
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            account = task.getResult(ApiException::class.java)
            val intent = Intent(this,SecondActivity::class.java)
            intent.putExtra("name",account.displayName)
            intent.putExtra("email",account.email)
            startActivity(intent)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
        }

    }


//    private fun handleSignInResult(idToken: String?) {
//        val credential = GoogleAuthProvider.getCredential(idToken,null)
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this){
//                if (it.isSuccessful){
//                    Toast.makeText(this,"successfull",Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this,SecondActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//                else{
//                    Toast.makeText(this,"Failure",Toast.LENGTH_SHORT).show()
//                }
//            }
//    }




//    override fun onStart() {
//        super.onStart()
//        val googleSignInCheck = GoogleSignIn.getLastSignedInAccount(this)
//        if (googleSignInCheck != null){
//            val intent = Intent(this,SecondActivity::class.java)
//            intent.putExtra("name",googleSignInCheck.displayName)
//            intent.putExtra("email",googleSignInCheck.email)
//            startActivity(intent)
//        }
//    }
}