 package com.example.mychatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.common.data.DataBufferObserverSet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

 class Singnup : AppCompatActivity() {


    private lateinit var Email: EditText
    private lateinit var Username: EditText
    private lateinit var passId: EditText
    private lateinit var Signbtn: Button
     private lateinit var mAuth: FirebaseAuth
     private lateinit var mDbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singnup)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        Username= findViewById(R.id.Username)
        passId= findViewById(R.id.passId)
        Email = findViewById(R.id.Email)
        Signbtn= findViewById(R.id.Signbtn)

        Signbtn.setOnClickListener {
            val name = Username.text.toString()
            val email = Email.text.toString()
            val password = passId.text.toString()

            signup(name,email,password)
        }

    }

     private fun signup(name:String,email:String, password:String) {
         //Signup methode

         mAuth.createUserWithEmailAndPassword(email, password)
             .addOnCompleteListener(this) { task ->
                 if (task.isSuccessful) {
                     // Sign in success, update UI with the signed-in user's information
                     val intent = Intent(this@Singnup,MainActivity::class.java)
                     finish()
                     startActivity(intent)
                     addUserToDatabase(name,email,mAuth.currentUser?.uid!!)

                 } else {
                     // If sign in fails, display a message to the user.
                      Toast.makeText(this@Singnup,"Something Wrong",Toast.LENGTH_SHORT).show()
                 }
             }
     }

     private fun addUserToDatabase(name: String, email: String, uid: String) {

           mDbRef = FirebaseDatabase.getInstance().getReference()

            mDbRef.child("User").child(uid).setValue(User(name,email,uid))
     }
 }