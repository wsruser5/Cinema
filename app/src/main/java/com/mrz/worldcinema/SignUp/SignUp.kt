package com.mrz.worldcinema.SignUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.google.gson.GsonBuilder
import com.mrz.worldcinema.R
import com.mrz.worldcinema.SignIn.SignIn
import com.mrz.worldcinema.api.ApiRequests
import com.mrz.worldcinema.constants.Constants
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.Exception

class SignUp : AppCompatActivity() {

    var isEmail = false
    var isPassword = false
    var isPasswordRepeat = false
    var isFirstName = false
    var isSecondName = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        etIsEmpty()
        btnSignUpRegister.isEnabled = isEmail && isPassword && isPasswordRepeat && isFirstName && isSecondName

        btnSignUpRegister.setOnClickListener {
            signUp()
        }

        btnSignUpSignIn.setOnClickListener {
            signUpToSignIn()
        }
    }


    private fun etIsEmpty() {

        etSignUpEmail.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etSignUpEmail.text.toString()).matches()) {
                    etSignUpEmail.setError("Неверный e-mail")
                    isEmail = false
                } else {
                    isEmail = true
                }
                btnSignUpRegister.isEnabled = isEmail && isPassword && isPasswordRepeat && isFirstName && isSecondName

            }
        })

        etSignUpPassword.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (etSignUpPassword.getText().toString().equals("")) {
                    etSignUpPassword.error = "Заполните поле"
                    isPassword = false
                } else {
                    isPassword = true
                }
                btnSignUpRegister.isEnabled = isEmail && isPassword && isPasswordRepeat && isFirstName && isSecondName

            }
        })

        etSignUpFirstName.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (etSignUpFirstName.getText().toString().equals("")) {
                    etSignUpFirstName.error = "Заполните поле"
                    isFirstName = false
                } else {
                    isFirstName = true
                }
                btnSignUpRegister.isEnabled = isEmail && isPassword && isPasswordRepeat && isFirstName && isSecondName

            }
        })

        etSignUpSecondName.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (etSignUpSecondName.getText().toString().equals("")) {
                    etSignUpSecondName.error = "Заполните поле"
                    isSecondName = false
                } else {
                    isSecondName = true
                }
                btnSignUpRegister.isEnabled = isEmail && isPassword && isPasswordRepeat && isFirstName && isSecondName

            }
        })

        etSignUpPasswordRepeat.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (etSignUpPasswordRepeat.getText().toString().equals("")) {
                    etSignUpPasswordRepeat.error = "Заполните поле"
                    isPasswordRepeat = false
                } else {
                    isPasswordRepeat = true
                }

                if (!etSignUpPasswordRepeat.getText().toString().equals(etSignUpPassword.text.toString())) {
                    etSignUpPasswordRepeat.setError("Пароли не совпадают")
                    isPasswordRepeat = false
                } else {
                    isPasswordRepeat = true
                }
                btnSignUpRegister.isEnabled = isEmail && isPassword && isPasswordRepeat && isFirstName && isSecondName

            }
        })

    }

    private fun signUp() {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val api = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.signUp(
                    etSignUpEmail.text.toString(),
                    etSignUpPassword.text.toString(),
                    etSignUpFirstName.text.toString(),
                    etSignUpSecondName.text.toString()
                )
                Log.e("Login","Response: ${response.body()}")
                signUpToSignIn()
            }
            catch(e: Exception) {
                Log.e("Login", "Error: ${e.message}")
            }
        }

    }

    private fun signUpToSignIn() {
        val intent = Intent(this, SignIn::class.java)

        startActivity(intent)
        this.overridePendingTransition(0,0)
    }
}