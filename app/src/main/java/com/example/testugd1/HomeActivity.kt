package com.example.testugd1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController

import com.google.android.material.bottomnavigation.BottomNavigationView

//import com.example.testugd1.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    //    private lateinit var binding:
//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Initialize the bottom navigation view
        //create bottom navigation view object
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController = findNavController(R.id.frame_layout)
        bottomNavigationView.setupWithNavController(navController)


    }
}


//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        replaceFragment(Search())
//
//        binding.bottomNavigationView.setOnItemSelectedListener {
//
//            when(it.itemId) {
//                R.id.Search -> replaceFragment(Search())
//                R.id.Home -> replaceFragment(Home())
//                R.id.Profile -> replaceFragment(Profile())
//                else ->{
//                }
//            }
//            true
//        }
//    }

//    private fun replaceFragment(fragment: Fragment){
//
//        val fragmentManager = supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.frame_layout,fragment)
//        fragmentTransaction.commit()
//    }
