package com.mirimhee.cp_mycle.ui.bulletin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mirimhee.cp_mycle.R
import com.mirimhee.cp_mycle.ui.dashboard.DashboardViewModel

class bulletinFragment: Fragment() {

    private lateinit var BulletinViewModel: bulletinViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        BulletinViewModel =
            ViewModelProviders.of(this).get(bulletinViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_bulletin_board, container, false)
        return root
    }
}