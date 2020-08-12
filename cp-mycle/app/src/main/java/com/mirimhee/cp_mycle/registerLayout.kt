package com.mirimhee.cp_mycle

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_register_layout.*
import java.lang.Exception
import java.util.regex.Matcher
import java.util.regex.Pattern


class registerLayout : AppCompatActivity() {
    var isGender : Boolean = false;
    var isAge : Boolean = false;
    lateinit var GenderText : String
    lateinit var AgeText : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_layout)
        var isPwd : Boolean =false;
        var isEmail : Boolean=false;
        var Basic_information : Boolean = false;


        ageRange.setVisibility(View.GONE);
        textG.setVisibility(View.GONE);
        GenderRadioG.setVisibility(View.GONE);
        textView17.setVisibility(View.GONE);

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

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

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
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

        again_Button.setOnClickListener {
            if (!Basic_information){
                try{
                    var SQL_DB = this.openOrCreateDatabase("memberList", Context.MODE_PRIVATE, null);
                    SQL_DB.execSQL("Create table if not exists member(email VARCHAR(100) PRIMARY KEY, password VARCHAR(50),gender VARCHAR(10),age VARCHAR(10),isLogin VARCHAR(10));")
                    SQL_DB.execSQL("insert into member(email,password,isLogin) values('"+
                            emailText.getText().toString()+"','"+PasswordText.getText().toString()+"','true');")
                    SQL_DB.close()
                }catch (e : Exception){
                    Log.e("DB insert 에러 ",e.message)
                }
                basic_information()
                Basic_information=true
            }else{
                try{
                    var SQL_DB = this.openOrCreateDatabase("memberList", Context.MODE_PRIVATE, null);
                    SQL_DB.execSQL("Create table if not exists member(email VARCHAR(100) PRIMARY KEY, password VARCHAR(50),gender VARCHAR(10),age VARCHAR(10),isLogin VARCHAR(10));")
                    SQL_DB.execSQL("UPDATE member SET gender='"+GenderText+"',age='"+AgeText+"' WHERE email = '"+emailText.getText().toString()+"';")
                    SQL_DB.close()
                }catch (e : Exception){
                    Log.e("DB insert 에러 ",e.message)
                }

                val intent= Intent(this,setting::class.java)
                startActivity(intent)
            }

        }
        getDBtest.setOnClickListener {
            try {
                var SQL_DB = this.openOrCreateDatabase("memberList", Context.MODE_PRIVATE, null);
                var sel = SQL_DB.rawQuery("SELECT * from member;",null)

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
    private fun basic_information(){
        again_Button.setVisibility(View.GONE);
        backbutton.setVisibility(View.GONE);
        textView15.setVisibility(View.GONE);
        emailText.setVisibility(View.GONE); PasswordText.setVisibility(View.GONE);
        getDBtest.setVisibility(View.GONE);

        textView13.setText("기본 정보")
        ageRange.setVisibility(View.VISIBLE);
        textG.setVisibility(View.VISIBLE);
        GenderRadioG.setVisibility(View.VISIBLE);
        textView17.setVisibility(View.VISIBLE);



        ageRange.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.radio20 ->allsetBack(radio20,"Age")
                R.id.radio30 ->allsetBack(radio30,"Age")
                R.id.radio40 ->allsetBack(radio40,"Age")
                R.id.radio50 ->allsetBack(radio50,"Age")
            }
            if(isAge && isGender){
                again_Button.setVisibility(View.VISIBLE);
            }

        }

        GenderRadioG.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.WomanRadio ->allsetBack(WomanRadio,"Gender")
                R.id.manRadio ->allsetBack(manRadio,"Gender")
            }
            if(isAge && isGender){
                again_Button.setVisibility(View.VISIBLE);
            }
        }


    }


    fun allsetBack(RB: RadioButton, what : String) {
        if (what=="Gender"){
            WomanRadio.background = ContextCompat.getDrawable(this,R.drawable.fragment_info_round_edge)
            manRadio.background = ContextCompat.getDrawable(this,R.drawable.fragment_info_round_edge)

            RB.background = ContextCompat.getDrawable(this,R.drawable.radio_btn_cus)
            RB.setTextColor(Color.parseColor("#303030"))
            GenderText = RB.text.toString()
            isGender=true;
        }else{
            radio20.background = ContextCompat.getDrawable(this,R.drawable.fragment_info_round_edge)
            radio30.background = ContextCompat.getDrawable(this,R.drawable.fragment_info_round_edge)
            radio40.background = ContextCompat.getDrawable(this,R.drawable.fragment_info_round_edge)
            radio50.background = ContextCompat.getDrawable(this,R.drawable.fragment_info_round_edge)
            RB.setTextColor(Color.parseColor("#303030"))
            RB.background = ContextCompat.getDrawable(this,R.drawable.radio_btn_cus)
            AgeText = RB.text.toString()
            isAge = true;
        }
    }
}


