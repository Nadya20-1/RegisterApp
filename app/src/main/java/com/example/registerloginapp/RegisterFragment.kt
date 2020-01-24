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
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.*
import java.util.regex.Pattern

class RegisterFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_register, container, false)
        initViews()
        setListeners()
        return v
    }

    private fun initViews() {
        signUpButton = v!!.findViewById(R.id.register_button)
        signUp = v!!.findViewById(R.id.register_any_account)
        @SuppressLint("ResourceType") val xrp = resources.getXml(R.drawable.text_selector)
        try {
            val csl = ColorStateList.createFromXml(resources,
                    xrp)
            signUp!!.setTextColor(csl)
        } catch (ignored: Exception) {
        }
    }

    private fun setListeners() {
        signUpButton!!.setOnClickListener(this)
        signUp!!.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        // emailR = (EditText)getActivity().findViewById(R.id.register_email);
//emailL = (EditText)getActivity().findViewById(R.id.login_email);
//signUpButton.setOnClickListener(this);
    }

    override fun onResume() {
        Objects.requireNonNull(activity)!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onResume()
    }

    override fun onClick(vr: View) {
        when (vr.id) {
            R.id.register_button -> if (checkValidation()) {
                MainActivity().replaceLoginFragment()
                val emailR = v!!.findViewById<EditText>(R.id.register_email)
                val emailr = emailR.text.toString()
                val bundle = Bundle()
                bundle.putString("Email", emailr)
                val fragmentManager = Objects.requireNonNull(activity)!!.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                val loginFragment = LoginFragment()
                loginFragment.arguments = bundle
                fragmentTransaction.replace(R.id.frameContainer, loginFragment)
                fragmentTransaction.commit()
            }
            R.id.register_any_account -> MainActivity().replaceLoginFragment()
        }
    }

    private fun checkValidation(): Boolean {
        var valid = true
        val getFullName = register_name.text.toString()
        val getEmailId = register_email.text.toString()
        val getMobileNumber = register_number.text.toString()
        val getPassword = register_password.text.toString()
        val p = Pattern.compile(Utils.regEx)
        val m = p.matcher(getEmailId)
        if (getFullName == "" || getFullName.length == 0 || getEmailId == "" || getEmailId.length == 0 || getMobileNumber == "" || getMobileNumber.length == 0 || getPassword == "" || getPassword.length == 0) {
            CustomToast().showtoast(Objects.requireNonNull(activity)!!, v!!, R.string.error_field_required)
            valid = false
        } else if (!m.find()) {
            CustomToast().showtoast(Objects.requireNonNull(activity)!!, v!!, R.string.error_email)
            valid = false
        } else if (getPassword.length < 6) {
            CustomToast().showtoast(Objects.requireNonNull(activity)!!, v!!, R.string.error_password)
            valid = false
        } else Toast.makeText(activity, R.string.register_text, Toast.LENGTH_SHORT)
                .show()
        return valid
    }
    companion object {
        private var v: View? = null
        private var signUp: TextView? = null
        private var signUpButton: Button? = null
    }
}