package com.mirimhee.cp_mycle

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register_layout.*
import java.lang.Exception
import java.util.regex.Matcher
import java.util.regex.Pattern


class registerLayout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_layout)
        var isPwd : Boolean =false;
        var isEmail : Boolean=false;
        var DB : SQLiteDatabase ;


        var VALID_EMAIL_ADDRESS_REGEX : Pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        backbutton.setOnClickListener {
            val intent= Intent(this,setting::class.java)
            startActivity(intent)
        }

        PasswordText.addTextChangedListener( object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(PasswordText.length() <= 5){
                    Toast.makeText(this@registerLayout,"6글자 이상으로 적어주세요.",Toast.LENGTH_SHORT).show()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(PasswordText.length() > 5){
                    isPwd=true
                    nextCheck(isPwd,isEmail)
                }else{
                    isPwd=false
                    again_Button.setVisibility(View.GONE);
                }
            }

        })
        emailText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                var email = emailText.getText().toString().trim()
                var mat:Matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email)
                if(mat.find()){
                    isEmail=true;
                    nextCheck(isPwd,isEmail)
                }else{
                    isEmail=false;
                    again_Button.setVisibility(View.GONE);
                    Toast.makeText(this@registerLayout,"이메일 형식으로 적어주세요.",Toast.LENGTH_SHORT).show()
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //TODO("Not yet implemented")
            }

        })

        again_Button.setOnClickListener {
            try{
                DB = this.openOrCreateDatabase("memberList", Context.MODE_PRIVATE, null);
                DB.execSQL("Create table if not exists member(email VARCHAR(100), password VARCHAR(50));")

                DB.execSQL("insert into member(email,password) values('"+
                        emailText.getText().toString()+"','"+PasswordText.getText().toString()+"');")

                DB.close()
            }catch (e : Exception){
                Log.e("DB insert 에러 ",e.message)
            }

        }
        getDBtest.setOnClickListener {
            try {
                var SQL_DB = this.openOrCreateDatabase("memberList", Context.MODE_PRIVATE, null);
                var sel = SQL_DB.rawQuery("SELECT * from member",null)

                if(sel.moveToFirst()){
                    Toast.makeText(applicationContext,sel.getString(sel.getColumnIndex("email")),Toast.LENGTH_LONG).show()
                }
                SQL_DB.close()
            }catch (e : Exception){
                Log.e("DB select 에러 ",e.message)
            }
        }
    }
    private fun nextCheck(one: Boolean,two: Boolean){
        if(one && two){
            again_Button.setVisibility(View.VISIBLE);
        }
    }



}


