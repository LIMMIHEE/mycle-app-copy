package com.mirimhee.cp_mycle

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard,R.id.navigation_bulletin_board, R.id.navigation_notifications,R.id.navigation_info))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val adapter = ViewPagerAdapter(frag)
//        val garage_selection : TabLayout = findViewById(R.id.garage_selection);
//        val CarViewPager : ViewPager2 = findViewById(R.id.CarViewPager);
//        val tabLayoutText = arrayOf("내 차고","모두의 차고");
//        TabLayoutMediator(garage_selection,CarViewPager){tab,pos->
//            tab.text = tabLayoutText[pos]
//        }.attach()
//        viewPager.adapter = PageAdapter(supportFragmentManager)


    }
    private inner class ViewPagerAdapter(fa:FragmentActivity):FragmentStateAdapter(fa){
        override fun getItemCount():Int {
            val PAGE_CNT = 0
            return PAGE_CNT
        }

        override fun createFragment(position: Int): Fragment {
            return  when(position){
                0 -> return MyCarFragment()
                1 -> return  OurCarFragment()
                else -> return  MyCarFragment()
            }
        }

    }

}

