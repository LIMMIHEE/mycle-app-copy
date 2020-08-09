package com.mirimhee.cp_mycle

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.core.content.res.ComplexColorCompat.inflate
import androidx.core.graphics.drawable.DrawableCompat.inflate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View.inflate
import android.widget.Button
import androidx.fragment.app.FragmentManager


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard,R.id.navigation_bulletin_board, R.id.navigation_info,R.id.navigation_notifications))

        navView.setupWithNavController(navController)

//        val settingButton : Button = findViewById(R.id.settingButton);
//        settingButton.setOnClickListener{
//            startActivity(Intent(this,setting::class.java))
//            finish()
//        }

    }
    /*
    fun changeGarage(  ){
        val fragment :Fragment ;

        when(view.id){
            R.id.mycar ->return LayoutInflater.inflate(R.layout.fragment_my_car, container, false)
            R.id.ourcar ->return LayoutInflater.inflate(R.layout.fragment_our_car, container, false)
            else ->return LayoutInflater.inflate(R.layout.fragment_my_car, container, false)
        }

        val fragmentmanager : FragmentManager;
        fragmentmanager.getFragment()
    }

     */

//    fun onSettingButtonClick(){
//        startActivity(Intent(this,setting::class.java))
//        finish()
//    }
}

