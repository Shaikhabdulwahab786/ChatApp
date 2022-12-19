package com.example.mychatapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var editUsername: EditText
    private lateinit var passId: EditText
    private lateinit var Loginbtn: Button
    private lateinit var Signbtn:  Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
         supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        editUsername= findViewById(R.id.editUsername)
        passId= findViewById(R.id.passId)
        Loginbtn= findViewById(R.id.Loginbtn)
        Signbtn= findViewById(R.id.Signbtn)

        Signbtn.setOnClickListener {
            val intent =  Intent(this,Singnup::class.java)
            startActivity(intent)

        }
        Loginbtn.setOnClickListener {
            val email = editUsername.text.toString()
            val password = passId.text.toString()

            login(email,password)
        }
    }

    private fun login(email: String, password: String) {
        //Login methode

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // FOR LOGIN
                    val intent = Intent(this@Login,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                    Log.i("cheking","i am inside login")

                } else {
                    Toast.makeText(this@Login,"User Does Not Exist",Toast.LENGTH_SHORT).show()
                    Log.i("cheking"," failed  login")
                }
            }

    }
}