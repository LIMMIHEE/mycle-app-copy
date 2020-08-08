package com.mirimhee.cp_mycle.ui.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mirimhee.cp_mycle.R
import com.mirimhee.cp_mycle.setting
import com.mirimhee.cp_mycle.ui.dashboard.DashboardViewModel
import kotlinx.android.synthetic.main.fragment_info.*

class infoFragment : Fragment() {

    private lateinit var InfoViewModel: infoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_info, container, false)
        val settingButton : Button = root.findViewById(R.id.settingButton)
        settingButton.setOnClickListener{
            val intent= Intent(activity,setting::class.java)
            startActivity(intent)
        }
        val driver_recom : Button = root.findViewById(R.id.driver_recom)
        driver_recom.setOnClickListener {
            val intent= Intent(Intent.ACTION_VIEW,Uri.parse("https://www.shinhancard.com/pconts/html/benefit/event/1197452_2239.html"))
            startActivity(intent)
        }

        InfoViewModel =
            ViewModelProviders.of(this).get(infoViewModel::class.java)
        return root
    }
}