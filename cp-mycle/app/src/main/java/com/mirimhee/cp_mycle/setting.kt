package com.mirimhee.cp_mycle

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register_layout.*
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.login_popup.*
import kotlinx.android.synthetic.main.login_popup.view.*
import java.lang.Exception

class setting : AppCompatActivity() {
    var isLogin = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val adapter = ArrayAdapter.createFromResource(this, R.array.PlaceArray,R.layout.spinner_item)
        place_spinner.adapter = adapter
        val adapter2 = ArrayAdapter.createFromResource(this, R.array.monetary_unit,R.layout.spinner_item)
        currency_spinner.adapter = adapter2
        currency_spinner.setSelection(0)

        LoginCheck()
        if(isLogin){
            login_button.setText("회원 정보")
        }

        login_button.setOnClickListener {
            if(isLogin) {
                val intent= Intent(this,Member_information::class.java)
                startActivity(intent)
            }
            else showLoginPopUp()
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

    private fun LoginCheck(){
        try{
            var SQL_DB = this.openOrCreateDatabase("memberList", Context.MODE_PRIVATE, null);
            SQL_DB.execSQL("Create table if not exists member(email VARCHAR(100) PRIMARY KEY, password VARCHAR(50),gender VARCHAR(10),age VARCHAR(10),isLogin VARCHAR(10));")
            var sel = SQL_DB.rawQuery("SELECT isLogin from member;",null)

            if(sel.moveToFirst()){
                    isLogin = true;
            }else{
                Log.e("DB 로그인 체크 에러 ","true 인 고객 없음")
            }
            SQL_DB.close()
        }catch (e : Exception){
            Log.e("DB 로그인 체크 에러 ",e.message)
        }
    }
}