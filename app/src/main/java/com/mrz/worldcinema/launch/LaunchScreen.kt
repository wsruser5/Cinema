package com.mrz.worldcinema.launch

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.mrz.worldcinema.R
import com.mrz.worldcinema.SignIn.SignIn
import com.mrz.worldcinema.SignUp.SignUp

class LaunchScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_screen)

        val hasVisitPref = getSharedPreferences(
            "hasVisit", Context.MODE_PRIVATE
        )
        Handler().postDelayed({
            val hasVisit = hasVisitPref.getBoolean("hasVisit", false)
            if(!hasVisit) {
                val editSharedPref = hasVisitPref.edit()
                editSharedPref.putBoolean("hasVisit", true)
                editSharedPref.commit()

                startActivity(Intent(this, SignUp::class.java))
            } else {
                startActivity(Intent(this, SignIn::class.java))
            }
        }, 3000)
    }
}