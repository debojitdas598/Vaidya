package com.example.vaidya.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.vaidya.R
import com.example.vaidya.databinding.FragmentUserBinding
import com.google.firebase.auth.FirebaseAuth


class UserFragment : Fragment() {

    private lateinit var binding :  FragmentUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val currentUser = FirebaseAuth.getInstance().currentUser

            if (currentUser != null) {
                val email = currentUser.email
                // Now you can use the email address as needed
                println("Current user email: $email")
            } else {
                // User is not signed in
                println("No user signed in")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUser = FirebaseAuth.getInstance().currentUser
        val email = currentUser?.email ?: "No user signed in"
        binding.email.text = email
    }

}