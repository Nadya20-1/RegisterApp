@file:Suppress("DEPRECATION")

package com.example.registerloginapp

import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_resetpassword.*
import java.util.*


class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewpager.adapter = PagerAdapter(supportFragmentManager)
        viewpager.setPagingEnabled(false)

        val option = arrayOf(getText(R.string.select),getText(R.string.button_en),getText(R.string.button_ru))
        val adapter  = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,option)
        language_spinner.adapter = adapter

        language_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
           override fun onNothingSelected(parent: AdapterView<*>?) {}
           override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               if (position == 1) {
                   recreate()
                   setAppLocale(Locale.ENGLISH)
               }
               if (position == 2) {
                   recreate()
                   setAppLocale(Locale.forLanguageTag("Ru"))
               }
           }
        }
    }

    private fun replaceLoginFragment() {
        viewpager.adapter = PagerAdapter(supportFragmentManager)
        viewpager.currentItem = 0
    }

    private fun replaceRegisterFragment() {
        viewpager.adapter = PagerAdapter(supportFragmentManager)
        viewpager.currentItem = 1
    }

    private fun replaceResetPasswordFragment() {
        viewpager.adapter = PagerAdapter(supportFragmentManager)
        viewpager.currentItem = 2
    }

    override fun onBackPressed() {
        if (viewpager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewpager.currentItem = viewpager.currentItem - 1
        }
    }

    @Suppress("DEPRECATION")
    private fun setAppLocale(localeCode: Locale) {
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        if (Build.VERSION.SDK_INT >= 23) {
            conf.setLocale(Locale(localeCode.toString()))
        } else {
            conf.locale = Locale(localeCode.toString())
        }
        res.updateConfiguration(conf, dm)
    }

    fun onClick(vr: View) {
        when (vr.id) {
            //Login
            R.id.login_button -> if (checkLoginEmailValidation() and checkLoginPasswordValidation()) {
                login()
              replaceLoginFragment()
            }
            R.id.login_forget_password -> replaceResetPasswordFragment()
            R.id.login_any_account -> replaceRegisterFragment()
            //Register
            R.id.register_button -> if (checkRegisterEmailValidation() and checkRegisterPasswordValidation() and checkRegisterNameValidation() and checkRegisterMobileValidation()) {
                Toast.makeText(this, R.string.register_text, Toast.LENGTH_SHORT).show()
                val setText = register_email.text.toString()
                val bundle = Bundle()
                bundle.putString("Email", setText)
                replaceLoginFragment()
                val loginFragment = LoginFragment()
                loginFragment.arguments = bundle
                loginFragment.arguments?.let { val result = loginFragment.arguments!!.getString("Email")
                    login_email.setText(result.toString()) } ?: login_email.setText("")
            }
            R.id.register_any_account -> replaceLoginFragment()
            //ResetPassword
            R.id.confirm_button -> if (checkResetPasswordEmailValidation() and checkConfirmPasswordValidation() and  checkResetPasswordValidation()) {
                Toast.makeText(this, R.string.reset_text, Toast.LENGTH_SHORT).show()
                replaceLoginFragment()   }
            R.id.confirm_any_account -> replaceRegisterFragment()
            //Switcher
            R.id.switch_btn -> if (switch_btn.isChecked) {viewpager.setPagingEnabled(true)} else {viewpager.setPagingEnabled(false)}
        }
    }

   @Suppress("DEPRECATION")
   private fun login() {
        login_button.isEnabled = false
        val progressDialog = ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog)
        progressDialog.apply {
           isIndeterminate = true
           setMessage(getText(R.string.authenticating))
           show()
        }
        Handler().postDelayed(
                {
                    onLoginSuccess()
                    progressDialog.dismiss()
                }, 3000)
    }

    private fun onLoginSuccess() {
        Toast.makeText(this, R.string.login_text, Toast.LENGTH_SHORT).show()
        replaceLoginFragment()
    }

    private fun checkLoginEmailValidation(): Boolean {
        var valid = true
        val getEmailId = login_email.text.toString()
        if ( getEmailId.isEmpty() ) {
            login_email_input.error = getText(R.string.error_field_required)
            valid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(getEmailId).matches() ) {
            login_email_input.error = getText(R.string.error_email)
            valid = false
        } else
            login_email_input.error = null
        return valid
    }

    private fun checkLoginPasswordValidation(): Boolean {
        var valid = true
        val getPassword = login_password.text.toString()
        when {
            getPassword.isEmpty() -> {
                login_password_input.error = getText(R.string.error_field_required)
                valid = false
            }
            getPassword.length < 6 -> {
                login_password_input.error = getText(R.string.error_password)
                valid = false
            }
            else -> login_password_input.error = null
        }
        return valid
    }

    private fun checkResetPasswordEmailValidation(): Boolean {
        var valid = true
        val getEmailId =  confirm_email.text.toString()
        if ( getEmailId.isEmpty() ) {
            reset_email_input.error = getText(R.string.error_field_required)
            valid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(getEmailId).matches() ) {
            reset_email_input.error = getText(R.string.error_email)
            valid = false
        } else
            reset_email_input.error = null
        return valid
    }

    private fun checkResetPasswordValidation(): Boolean {
        var valid = true
        val getConfirmPassword = confirm_reset_password.text.toString()
        val getPassword = confirm_password.text.toString()
        when {
            getPassword.isEmpty() -> {
                reset_password_input.error = getText(R.string.error_field_required)
                valid = false
            }
            getPassword.length < 6 -> {
                reset_password_input.error = getText(R.string.error_password)
                valid = false
            }
            getConfirmPassword != getPassword -> {
                reset_password_input.error = getText(R.string.error_reset)
                valid = false
            }
            else -> reset_password_input.error = null
        }
        return valid
    }

    private fun checkConfirmPasswordValidation(): Boolean {
        var valid = true
        val getConfirmPassword = confirm_reset_password.text.toString()
        val getPassword = confirm_password.text.toString()
        when {
            getConfirmPassword.isEmpty() -> {
                reset_confirm_input.error = getText(R.string.error_field_required)
                valid = false
            }
            getConfirmPassword.length < 6 -> {
                reset_confirm_input.error = getText(R.string.error_password)
                valid = false
            }
            getConfirmPassword != getPassword -> {
                reset_confirm_input.error = getText(R.string.error_reset)
                valid = false
            }
            else -> reset_confirm_input.error = null
        }
        return valid
    }

    private fun checkRegisterEmailValidation(): Boolean {
        var valid = true
        val getEmailId = register_email.text.toString()
        if ( getEmailId.isEmpty() ) {
            register_email_input.error = getText(R.string.error_field_required)
            valid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(getEmailId).matches() ) {
            register_email_input.error = getText(R.string.error_email)
            valid = false
        } else
            register_email_input.error = null
        return valid
    }

    private fun checkRegisterPasswordValidation(): Boolean {
        var valid = true
        val getPassword = register_password.text.toString()
        when {
            getPassword.isEmpty() -> {
                register_password_input.error = getText(R.string.error_field_required)
                valid = false
            }
            getPassword.length < 6 -> {
                register_password_input.error = getText(R.string.error_password)
                valid = false
            }
            else -> register_password_input.error = null
        }
        return valid
    }

    private fun checkRegisterNameValidation(): Boolean {
        var valid = true
        val getFullName = register_name.text.toString()
        if ( getFullName.isEmpty()) {
            register_username_input.error = getText(R.string.error_field_required)
            valid = false
        } else
           register_username_input.error = null
        return valid
    }

    private fun checkRegisterMobileValidation(): Boolean {
        var valid = true
        val getMobileNumber = register_number.text.toString()
        if ( getMobileNumber.isEmpty() ) {
            register_number_input.error = getText(R.string.error_field_required)
            valid = false
        } else
           register_number_input.error = null
        return valid
    }

    override fun recreate() {
        val intent = intent
        finish()
        startActivity(intent)
        super.recreate()
    }
}