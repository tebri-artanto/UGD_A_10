package com.example.testugd1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarMenu
import com.google.android.material.navigation.NavigationBarView


//import com.example.testugd1.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navigation: NavigationBarView = findViewById(R.id.bottom_navigation_view)

        navigation.setOnItemSelectedListener{ item->
            when(item.itemId){
                R.id.Home -> {replaceFragment(homeFragment())
                    true }
                R.id.Search -> {replaceFragment(searchFragment())
                    true}
                R.id.Profile -> {replaceFragment(profileFragment())
                    true}
                else -> {false}
            }
        }


    }

    private fun replaceFragment(fragment : Fragment){
        if (fragment != null) {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment, fragment)
                .commit()
        }

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
