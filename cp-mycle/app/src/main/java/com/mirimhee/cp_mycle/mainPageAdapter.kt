package com.mirimhee.cp_mycle

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PageAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return MyCarFragment()
            1 -> return  OurCarFragment()
            else -> return  MyCarFragment()
        }
    }

    override fun getCount(): Int {
       return 2;
    }

}