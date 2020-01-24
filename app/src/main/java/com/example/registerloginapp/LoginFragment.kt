package com.example.registerloginapp

import android.annotation.SuppressLint
import android.content.Intent
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
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.*
import java.util.regex.Pattern

class LoginFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_login, container, false)
        initViews()
        setListeners()
        val text = v!!.findViewById<EditText>(R.id.login_email)
        if (arguments != null) {
            val result = arguments!!.getString("Email")
            text.setText(result.toString())
        } else {
            text.setText("")
        }
        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initViews() {
        Companion.fragmentManager = Objects.requireNonNull(activity)!!.supportFragmentManager
        loginButton = v!!.findViewById(R.id.login_button)
        forgotPassword = v!!.findViewById(R.id.login_forget_password)
        anyAccount = v!!.findViewById(R.id.login_any_account)
        @SuppressLint("ResourceType") val xrp = resources.getXml(R.drawable.text_selector)
        try {
            val csl = ColorStateList.createFromXml(resources,
                    xrp)
            forgotPassword!!.setTextColor(csl)
            anyAccount!!.setTextColor(csl)
        } catch (ignored: Exception) {
        }
    }

    private fun setListeners() {
        loginButton!!.setOnClickListener(this)
        forgotPassword!!.setOnClickListener(this)
        anyAccount!!.setOnClickListener(this)
    }

    override fun onResume() {
        Objects.requireNonNull(activity)!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onResume()
    }

    override fun onClick(vr: View) {
        when (vr.id) {
            R.id.login_button -> if (checkValidation()) {
                Companion.fragmentManager
                        ?.beginTransaction()
                        ?.replace(R.id.frameContainer,
                                LoginFragment(),
                                Utils.LoginFragment)?.commit()
            }
            R.id.login_forget_password -> Companion.fragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.frameContainer,
                            ResetPasswordFragment(),
                            Utils.ResetPasswordFragment)?.commit()
            R.id.login_any_account -> Companion.fragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.frameContainer, RegisterFragment(),
                            Utils.RegisterFragment)?.commit()
        }
    }

    private fun checkValidation(): Boolean {
        var valid = true
        val getEmailId = login_email.text.toString()
        val getPassword = login_password.text.toString()
        val p = Pattern.compile(Utils.regEx)
        val m = p.matcher(getEmailId)
        if (getEmailId == "" || getEmailId.length == 0 || getPassword == "" || getPassword.length == 0) {
            CustomToast().showtoast(Objects.requireNonNull(activity)!!, v!!, R.string.login_field)
            valid = false
        } else if (!m.find()) {
            CustomToast().showtoast(Objects.requireNonNull(activity)!!, v!!, R.string.error_email)
            valid = false
        } else if (getPassword.length < 6) {
            CustomToast().showtoast(Objects.requireNonNull(activity)!!, v!!, R.string.error_password)
            valid = false
        } else Toast.makeText(activity, R.string.login_text, Toast.LENGTH_SHORT)
                .show()
        return valid
    }

    companion object {
        private var fragmentManager: FragmentManager? = null
        private var v: View? = null
        private var loginButton: Button? = null
        private var forgotPassword: TextView? = null
        private var anyAccount: TextView? = null
    }
}