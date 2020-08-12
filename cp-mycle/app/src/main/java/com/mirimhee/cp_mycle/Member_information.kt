package com.mirimhee.cp_mycle

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import kotlinx.android.synthetic.main.activity_member_information.*
import kotlinx.android.synthetic.main.activity_member_information.WomanRadio
import kotlinx.android.synthetic.main.activity_member_information.manRadio
import kotlinx.android.synthetic.main.activity_member_information.radio20
import kotlinx.android.synthetic.main.activity_member_information.radio30
import kotlinx.android.synthetic.main.activity_member_information.radio40
import kotlinx.android.synthetic.main.activity_member_information.radio50
import kotlinx.android.synthetic.main.activity_register_layout.*


class Member_information : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_information)

        var NowPassword ="";
        var MemberEmail = "";

        button6.setOnClickListener {
            val intent= Intent(this,setting::class.java)
            startActivity(intent)
        }

        try{
            var SQL_DB = this.openOrCreateDatabase("memberList", Context.MODE_PRIVATE, null);
            SQL_DB.execSQL("Create table if not exists member(email VARCHAR(100) PRIMARY KEY, password VARCHAR(50),gender VARCHAR(10),age VARCHAR(10),isLogin VARCHAR(10));")
            var sel = SQL_DB.rawQuery("SELECT * from member;",null)


            if(sel.moveToFirst()){
                emaliButton.setText(sel.getString(sel.getColumnIndex("email")))
                when (sel.getString(sel.getColumnIndex("gender"))){
                    "여성 운전자" -> BtnChange(WomanRadio)
                    "남성 운전자" -> BtnChange(manRadio)
                }
                when (sel.getString(sel.getColumnIndex("age"))){
                    "20대" -> BtnChange(radio20)
                    "30대" -> BtnChange(radio30)
                    "40대" -> BtnChange(radio40)
                    "50대+" -> BtnChange(radio50)
                }
                NowPassword = sel.getString(sel.getColumnIndex("password"))
                MemberEmail= sel.getString(sel.getColumnIndex("email"))
            }
            SQL_DB.close()
        }catch (e : Exception){
            Log.e("DB 로그인 체크 에러 ",e.message)
        }

        PwChange.setText(R.string.PwChnage)
        PwChange.setOnClickListener {
            //NowPassword
            var Dia = AlertDialog.Builder(this);
            var Dia2 = AlertDialog.Builder(this);
            var Dia3 = AlertDialog.Builder(this);
            val et = EditText(this);
            et.inputType = InputType.TYPE_NUMBER_VARIATION_PASSWORD
            et.hint="현재 비밀번호를 입력해 주세요."
            Dia.setMessage("현재 비밀번호")
            Dia.setView(et)


            Dia.setPositiveButton("확인",DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->
                    //지금 패스워드 확인
                    if(et.text.toString() != NowPassword){
                        Toast.makeText(this,"비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                        dialogInterface.dismiss()
                    }else{
                        et.hint="새 비밀번호를 입력해 주세요."
                        Dia2.setMessage("새 비밀번호")
                        dialogInterface.dismiss()
                        if (et.getParent() != null) (et.getParent() as ViewGroup).removeView(
                            et
                        )
                        et.setText("")
                        Dia2.setView(et)
                        Dia2.show()
                    }
            })
            Dia.setNegativeButton("취소",DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            })
            Dia2.setPositiveButton("확인",DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->

                    if(et.text.toString().length > 5){
                        NowPassword=et.text.toString()
                        et.hint="새 비밀번호를 한번 더 입력해 주세요."
                        Dia3.setMessage("비밀번호 확인")
                        dialogInterface.dismiss()
                        if (et.getParent() != null) (et.getParent() as ViewGroup).removeView(
                            et
                        )
                        et.setText("")
                        Dia3.setView(et)
                        Dia3.show()
                    }else{
                        Toast.makeText(this,"6글자를 넘겨주세요.", Toast.LENGTH_SHORT).show()
                    }

            })
            Dia2.setNegativeButton("취소",DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            })
            Dia3.setPositiveButton("확인",DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->
                    if(et.text.toString() != NowPassword){
                        Toast.makeText(this,"비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                    }else{
                        try{
                            var SQL_DB = this.openOrCreateDatabase("memberList", Context.MODE_PRIVATE, null);
                            SQL_DB.execSQL("Create table if not exists member(email VARCHAR(100) PRIMARY KEY, password VARCHAR(50),gender VARCHAR(10),age VARCHAR(10),isLogin VARCHAR(10));")
                            SQL_DB.execSQL("UPDATE member SET password='"+et.text.toString()+"' WHERE email = '"+MemberEmail+"';")
                            SQL_DB.close()
                        }catch (e : Exception){
                            Log.e("새 비밀번호 처리 에러 ",e.message)
                        }
                    }

            })
            Dia3.setNegativeButton("취소",DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            })
            Dia.show()
        }
        LogoutBtn.setOnClickListener {
            try{
                var SQL_DB = this.openOrCreateDatabase("memberList", Context.MODE_PRIVATE, null);
                SQL_DB.execSQL("Create table if not exists member(email VARCHAR(100) PRIMARY KEY, password VARCHAR(50),gender VARCHAR(10),age VARCHAR(10),isLogin VARCHAR(10));")
                SQL_DB.execSQL("UPDATE member SET isLogin='false' WHERE email = '"+MemberEmail+"';")
                SQL_DB.close()
            }catch (e:Exception){
                Log.e("DB 로그아웃 체크 에러 ",e.message)
            }
        }

    }

    private fun BtnChange(RB: RadioButton){
        RB.background = ContextCompat.getDrawable(this,R.drawable.radio_btn_cus)
        RB.setTextColor(Color.parseColor("#303030"))
    }
}