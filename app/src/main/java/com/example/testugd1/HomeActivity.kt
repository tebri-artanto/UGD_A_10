package com.example.testugd1

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.testugd1.databinding.ActivityHomeBinding
import me.ibrahimsn.lib.SmoothBottomBar


class HomeActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    lateinit var navigation: SmoothBottomBar
    lateinit var navigation2: BottomNavigationView
    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding: ActivityHomeBinding
    lateinit var bundle: Bundle
    var key = "idKey"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
       // val akunId = getBundle()
//        setContentView(R.layout.activity_home)
//        navController = findNavController(R.id.frame_layout)
//        setupActionBarWithNavController(navController)
//        setupSmoothBottomMenu()
        navigation2 = findViewById(R.id.bottom_navigation_view)
        changeFragment(searchFragment())
        navigation2.setOnItemSelectedListener{ item->
            when(item.itemId){
                R.id.Search -> changeFragment(searchFragment())
                R.id.Home -> changeFragment(HomeFragment())
                R.id.Profile -> changeFragment(profileFragment())
            }
            true
        }
//
   }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_navigation_menu,menu)
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.Search -> changeFragment(searchFragment())
//               R.id.Home -> changeFragment(HomeFragment())
//               R.id.Profile -> changeFragment(profileFragment(akunLogin = akunId))
//        }
//        return super.onOptionsItemSelected(item)
//    }
//    private fun setupSmoothBottomMenu() {
//        val popupMenu = PopupMenu(this, null)
//        popupMenu.inflate(R.menu.bottom_navigation_menu)
//        val menu = popupMenu.menu
//        binding.bottomBar.setupWithNavController(menu,navController)
//    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun changeFragment(fragment : Fragment){
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
        transaction.commit()
    }
    fun getBundle(): String {
        bundle = intent.getBundleExtra("idKey")!!
        var idAkun : String = bundle.getString("idKey")!!
        return idAkun
    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp() || super.onSupportNavigateUp()
//    }
}
