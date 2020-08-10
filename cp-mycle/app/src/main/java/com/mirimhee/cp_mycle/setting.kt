package com.mirimhee.cp_mycle

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.login_popup.*
import kotlinx.android.synthetic.main.login_popup.view.*

class setting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val adapter = ArrayAdapter.createFromResource(this, R.array.PlaceArray,R.layout.spinner_item)
        place_spinner.adapter = adapter
        val adapter2 = ArrayAdapter.createFromResource(this, R.array.monetary_unit,R.layout.spinner_item)
        currency_spinner.adapter = adapter2
        currency_spinner.setSelection(0)

        login_button.setOnClickListener {
            showLoginPopUp()
        }

    }
    private fun showLoginPopUp(){

        var inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = inflater.inflate(R.layout.login_popup,null)
        var alterDialog = AlertDialog.Builder(this)
            .create()

        var cancleBtn : Button = view.findViewById(R.id.cancleBtn);
        var register : TextView = view.findViewById(R.id.register);
        cancleBtn.setOnClickListener {
            alterDialog.dismiss()
        }
        register.setText(R.string.register)
        register.setOnClickListener {
            val intent= Intent(this,registerLayout::class.java)
            startActivity(intent)
        }


        alterDialog.setView(view)
        alterDialog.show()
    }
}