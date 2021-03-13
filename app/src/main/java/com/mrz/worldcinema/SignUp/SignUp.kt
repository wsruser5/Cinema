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
import java.lang.Exception

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        etIsEmpty()

        btnSignUpRegister.setOnClickListener {
            signUp()
            signUpToSignIn()
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
                }

            }
        })

        etSignUpPassword.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (etSignUpPassword.getText().toString().equals("")) {
                    etSignUpPassword.error = "Заполните поле"
                }
                if (!etSignUpPasswordRepeat.getText().toString().equals(etSignUpPasswordRepeat.text.toString())) {
                    etSignUpPasswordRepeat.setError("Пароли не совпадают")
                }

            }
        })

        etSignUpFirstName.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (etSignUpFirstName.getText().toString().equals("")) {
                    etSignUpFirstName.error = "Заполните поле"
                }

            }
        })

        etSignUpSecondName.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (etSignUpSecondName.getText().toString().equals("")) {
                    etSignUpSecondName.error = "Заполните поле"
                }

            }
        })

        etSignUpPasswordRepeat.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (etSignUpPasswordRepeat.getText().toString().equals("")) {
                    etSignUpPasswordRepeat.error = "Заполните поле"
                }
            }
        })

    }

    private fun signUp() {
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
                val response = api.signUp(
                    etSignUpEmail.text.toString(),
                    etSignUpPassword.text.toString(),
                    etSignUpFirstName.text.toString(),
                    etSignUpSecondName.text.toString()
                )
                Log.e("Login","Response: ${response.body()}")
            }
            catch(e: Exception) {
                Log.e("Login", "Error: ${e.message}")
            }
        }

    }

    private fun signUpToSignIn() {
        val intent = Intent(this, SignIn::class.java)

        startActivity(intent)
    }
}