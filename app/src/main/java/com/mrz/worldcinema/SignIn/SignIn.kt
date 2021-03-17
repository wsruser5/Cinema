package com.mrz.worldcinema.SignIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.mrz.worldcinema.MainScreen.MainScreen
import com.mrz.worldcinema.R
import com.mrz.worldcinema.SignUp.SignUp
import com.mrz.worldcinema.api.ApiRequests
import com.mrz.worldcinema.constants.Constants
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class SignIn : AppCompatActivity() {

    var isEmail = false
    var isPassword = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        etIsEmpty()

        btnSignInLogin.isEnabled = isEmail && isPassword
        btnSignInRegister.setOnClickListener { signInToSignUp() }
        btnSignInLogin.setOnClickListener {
            signIn()
        }
    }

    private fun signInToSignUp() {
        val intent = Intent(this, SignUp::class.java)
        startActivity(intent)
        this.overridePendingTransition(0,0)

    }

    private fun signInToMain() {
        val intent = Intent(this, MainScreen::class.java)
        startActivity(intent)
        this.overridePendingTransition(0,0)
    }

    private fun etIsEmpty() {

        etSignInEmail.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etSignInEmail.text.toString()).matches()) {
                    etSignInEmail.setError("Неверный e-mail")
                    isEmail = false
                } else {
                    isEmail = true
                }

                if (etSignInEmail.getText().toString().equals("")) {
                    etSignInEmail.setError("Введите e-mail")
                    isEmail = false
                } else {
                    isEmail = true
                }
                btnSignInLogin.isEnabled = isEmail && isPassword
            }
        })

        etSignInPassword.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (etSignInPassword.getText().toString().equals("")) {
                    etSignInPassword.setError("Введите пароль")
                    isPassword = false
                } else {
                    isPassword = true
                }
                btnSignInLogin.isEnabled = isEmail && isPassword
            }
        })
    }

    private fun signIn(){
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val api = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.signIn(etSignInEmail.text.toString(), etSignInPassword.text.toString())
                withContext(Dispatchers.Main) {
                    val token = response.body()?.token
                    if (token != null) {
                        val intent = Intent(this@SignIn, MainScreen::class.java)
                        intent.putExtra("token", token)
                        startActivity(intent)
                        this@SignIn.overridePendingTransition(0,0)
                    } else {
                        val toast = Toast.makeText(this@SignIn, "Неавторизованный доступ", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            catch (e: java.lang.Exception){
                val toastSign = Toast.makeText(this@SignIn, "Упс, что-то пошло не так", Toast.LENGTH_SHORT).show()
                Log.e("Main", "Error: ${e.message}")
            }
        }
    }
}