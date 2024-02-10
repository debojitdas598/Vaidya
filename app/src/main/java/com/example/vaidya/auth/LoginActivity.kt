package com.example.vaidya.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.vaidya.MainActivity
import com.example.vaidya.R
import com.example.vaidya.databinding.ActivityLoginBinding
import com.example.vaidya.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvNewHere.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.btnSignIn.setOnClickListener {
            val email = binding.etSIEmail.text.toString()
            val password = binding.etSIPass.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                signIn(email, password)
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun signIn(email: String, password: String) {
        auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign-in successful, update UI accordingly
                    val user = auth.currentUser
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    // You may proceed to another activity or update UI here
                    Toast.makeText(this, "Sign-in successful", Toast.LENGTH_SHORT).show()
                } else {
                    // Sign-in failed, display a message to the user.
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}