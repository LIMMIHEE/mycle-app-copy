package com.mirimhee.cp_mycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_setting.*

class setting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val adapter = ArrayAdapter.createFromResource(this, R.array.PlaceArray,R.layout.spinner_item)
        place_spinner.adapter = adapter
        val adapter2 = ArrayAdapter.createFromResource(this, R.array.monetary_unit,R.layout.spinner_item)
        currency_spinner.adapter = adapter2
        currency_spinner.setSelection(0)

    }
}