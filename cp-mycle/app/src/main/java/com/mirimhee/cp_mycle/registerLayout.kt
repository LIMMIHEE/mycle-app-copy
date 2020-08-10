package com.mirimhee.cp_mycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_register_layout.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class registerLayout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_layout)
        var isPwd : Boolean =false;
        var isEmail : Boolean=false;

        var VALID_EMAIL_ADDRESS_REGEX : Pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        backbutton.setOnClickListener {
            val intent= Intent(this,setting::class.java)
            startActivity(intent)
        }

        PasswordButton.addTextChangedListener( object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(PasswordButton.length() <= 5){
                    Toast.makeText(this@registerLayout,"6글자 이상으로 적어주세요.",Toast.LENGTH_SHORT).show()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(PasswordButton.length() > 5){
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


    }
    private fun nextCheck(one: Boolean,two: Boolean){
        if(one && two){
            again_Button.setVisibility(View.VISIBLE);
        }
    }
}