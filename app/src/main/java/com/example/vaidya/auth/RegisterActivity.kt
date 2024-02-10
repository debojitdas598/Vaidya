package com.example.vaidya.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.vaidya.MainActivity
import com.example.vaidya.R
import com.example.vaidya.databinding.ActivityMainBinding
import com.example.vaidya.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.btnSignUp.setOnClickListener {
            val email = binding.etSIEmail.text.toString()
            val password = binding.etSIPass.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                signUp(email, password)
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
        }


    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign-up successful, update UI accordingly
                    val user = auth.currentUser
                    // You may proceed to another activity or update UI here
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Sign-up successful", Toast.LENGTH_SHORT).show()
                } else {
                    // Sign-up failed, display a message to the user.
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}