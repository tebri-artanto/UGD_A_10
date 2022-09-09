package com.example.testugd1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {
    lateinit var navigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navigation = findViewById(R.id.bottom_navigation_view)

        navigation.setOnItemSelectedListener{ item->
            when(item.itemId){
                R.id.Search -> changeFragment(searchFragment())
                R.id.Home -> changeFragment(homeFragment())
                R.id.Profile -> changeFragment(profileFragment())
            }
            true
        }

    }

    private fun changeFragment(fragment : Fragment){
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
        transaction.commit()
    }
}
