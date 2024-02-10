package com.example.vaidya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.vaidya.databinding.ActivityMainBinding
import com.example.vaidya.fragments.DoctorFragment
import com.example.vaidya.fragments.HomeFragment
import com.example.vaidya.fragments.ServicesFragment
import com.example.vaidya.fragments.UserFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setUpTabBar()

    }

    private fun setUpTabBar() {

        binding.bottomNav.setOnItemSelectedListener {
            when(it){
                R.id.home -> {
                    setFragment(HomeFragment(),R.id.home)
                }
               R.id.doctorbutton -> {
                    setFragment(DoctorFragment(), R.id.doctorbutton)
                }
                R.id.servicesbutton -> {
                    setFragment(ServicesFragment(), R.id.servicesbutton)
                }
                else -> {
                    setFragment(UserFragment(), R.id.userbutton)
                }
            }
        }
    }

    private fun setFragment(fragment: Fragment, orders: Int) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.flFragment.id, fragment)
        binding.bottomNav.setItemSelected(orders,true)
        fragmentTransaction.commit()
    }
}