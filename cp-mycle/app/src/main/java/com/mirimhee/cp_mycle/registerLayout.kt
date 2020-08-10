package com.mirimhee.cp_mycle

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_register_layout.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
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

        again_Button.setOnClickListener {

            reqRegister(emailText.getText().toString(),PasswordButton.getText().toString())
        }

    }
    private fun nextCheck(one: Boolean,two: Boolean){
        if(one && two){
            again_Button.setVisibility(View.VISIBLE);
        }
    }
//    private  fun reqRegister(id : String, pw : String){
//        var url = "http://211.177.22.26:3000"
//        //JSONTask().execute(url)
//        var json = JSONObject();
//        Toast.makeText(applicationContext,"회원가입 시도",Toast.LENGTH_SHORT).show()
//        try {
//            json.put("id", id)
//            json.put("pw",pw)
//            var jsonTest = json.toString()
//            var requestQueue = Volley.newRequestQueue(this)
//            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, json, Response.Listener { response ->
//
//            },
//             Response.ErrorListener { error -> error.printStackTrace() })
//
//        }catch (e : Exception){
//            Toast.makeText(applicationContext,"회원가입 시도 실패",Toast.LENGTH_SHORT).show()
//        }
//    }
    private fun reqLogin(id : String, pw : String){
        var url = "http://211.177.22.26:3000";

        var json = JSONObject();
        try {
            json.put("id", id)
            json.put("pw",pw)
            var jsonTest = json.toString()
            var requestQueue = Volley.newRequestQueue(this)
            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, json, Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response.toString())
                        val resultId = jsonObject.getString("approve_id")
                        val resultPassword = jsonObject.getString("approve_pw")
                        if ((resultId == "OK") and (resultPassword == "OK")) {
                            Toast.makeText(applicationContext,"로그인 성공",Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(getApplicationContext(),"로그인 실패",Toast.LENGTH_SHORT).show();
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error -> error.printStackTrace() })
            jsonObjectRequest.setRetryPolicy(DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT))
            requestQueue.add(jsonObjectRequest)
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    inner class JSONTask : AsyncTask<String?, String?, String?>() {
        protected Override fun doInBackground(vararg urls: String): String? {
            var con: HttpURLConnection? = null
            var reader: BufferedReader? = null
            try {
                val url = URL(urls[0])
                con = url.openConnection() as HttpURLConnection
                con.connect()
                val stream: InputStream = con!!.inputStream
                reader = BufferedReader(InputStreamReader(stream))
                val buffer = StringBuffer()
                var line: String? = ""
                while (reader.readLine().also { line = it } != null) buffer.append(line)
                return buffer.toString()
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                con!!.disconnect()
                try {
                    reader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return null
        }

        override fun onPostExecute(s: String?) {
            super.onPostExecute(s)
        }

        public override fun doInBackground(vararg params: String?): String? {
            TODO("Not yet implemented")
        }
    }


}


