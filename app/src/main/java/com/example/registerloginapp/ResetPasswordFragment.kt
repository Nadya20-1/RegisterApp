package com.example.registerloginapp

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_resetpassword.*
import java.util.*
import java.util.regex.Pattern

class ResetPasswordFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_resetpassword, container, false)
        initViews()
        setListeners()
        return v
    }

    private fun initViews() {
        loginButton = v!!.findViewById(R.id.confirm_button)
        anyAccount = v!!.findViewById(R.id.confirm_any_account)
        @SuppressLint("ResourceType") val xrp = resources.getXml(R.drawable.text_selector)
        try {
            val csl = ColorStateList.createFromXml(resources,
                    xrp)
            anyAccount!!.setTextColor(csl)
        } catch (ignored: Exception) {
        }
    }

    private fun setListeners() {
        loginButton!!.setOnClickListener(this)
        anyAccount!!.setOnClickListener(this)
    }

    override fun onResume() {
        Objects.requireNonNull(activity)!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onResume()
    }

    override fun onClick(vr: View) {
        when (vr.id) {
            R.id.confirm_button -> if (checkValidation()) MainActivity().replaceLoginFragment()
            R.id.confirm_any_account -> MainActivity().replaceRegisterFragment()
        }
    }

    private fun checkValidation(): Boolean {
        var valid = true
        val getConfirmPassword = confirm_reset_password.text.toString()
        val getEmailId =  confirm_email.text.toString()
        val getPassword = confirm_password.text.toString()
        val p = Pattern.compile(Utils.regEx)
        val m = p.matcher(getEmailId)
        if (getEmailId == "" || getEmailId.length == 0 || getPassword == "" || getPassword.length == 0 || getConfirmPassword == "" || getConfirmPassword.length == 0) {
            CustomToast().showtoast(Objects.requireNonNull(activity)!!, v!!, R.string.error_field_required)
            valid = false
        } else if (!m.find()) {
            CustomToast().showtoast(Objects.requireNonNull(activity)!!, v!!, R.string.error_email)
            valid = false
        } else if (getConfirmPassword.length < 6) {
            CustomToast().showtoast(Objects.requireNonNull(activity)!!, v!!, R.string.error_password)
            valid = false
        } else if (getConfirmPassword != getPassword) {
            CustomToast().showtoast(Objects.requireNonNull(activity)!!, v!!, R.string.error_reset)
            valid = false
        } else Toast.makeText(activity, R.string.reset_text, Toast.LENGTH_SHORT)
                .show()
        return valid
    }
    companion object {
        private var v: View? = null
        private var loginButton: Button? = null
        private var anyAccount: TextView? = null
    }
}