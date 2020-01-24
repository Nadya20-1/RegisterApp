package com.example.registerloginapp

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import java.util.*

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Companion.fragmentManager = supportFragmentManager
        if (savedInstanceState == null) {
            Companion.fragmentManager!!
                    .beginTransaction()
                    .replace(R.id.frameContainer, LoginFragment(),
                            Utils.LoginFragment).commit()
        }
        val btn_ru = findViewById<TextView>(R.id.button_rus)
        btn_ru.setOnClickListener {
            recreate()
            setAppLocale("ru")
        }
        val btn_en = findViewById<TextView>(R.id.button_eng)
        btn_en.setOnClickListener {
            recreate()
            setAppLocale("en")
        }
    }

    internal fun replaceLoginFragment() {
        Companion.fragmentManager
                ?.beginTransaction()
                ?.replace(R.id.frameContainer, LoginFragment(),
                        Utils.LoginFragment)?.commit()
    }

    internal fun replaceRegisterFragment() {
        Companion.fragmentManager
                ?.beginTransaction()
                ?.replace(R.id.frameContainer, RegisterFragment(),
                        Utils.RegisterFragment)?.commit()
    }

    override fun onBackPressed() {
        val SignUpFragment = Companion.fragmentManager
                ?.findFragmentByTag(Utils.RegisterFragment)
        val ForgotPasswordFragment = Companion.fragmentManager
                ?.findFragmentByTag(Utils.ResetPasswordFragment)
        if (SignUpFragment != null) replaceLoginFragment() else if (ForgotPasswordFragment != null) replaceRegisterFragment() else super.onBackPressed()
    }

    fun setAppLocale(localeCode: String) {
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        if (Build.VERSION.SDK_INT >= 19) {
            conf.setLocale(Locale(localeCode.toLowerCase()))
        } else {
            conf.locale = Locale(localeCode.toLowerCase())
        }
        res.updateConfiguration(conf, dm)
    }

    override fun recreate() {
        val intent = intent
        finish()
        startActivity(intent)
        super.recreate()
    }

    companion object {
        private var fragmentManager: FragmentManager? = null
    }
}