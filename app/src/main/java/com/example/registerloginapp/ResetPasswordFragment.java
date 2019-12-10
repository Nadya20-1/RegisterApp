package com.example.registerloginapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ResetPasswordFragment extends Fragment implements View.OnClickListener {

    private static View view;
    private static EditText email, password,confirmPassword;
    private static Button loginButton;
    private static TextView  anyAccount;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_resetpassword, container, false);
        initViews();
        setListeners();
        return view;
    }

    private void initViews() {

        email = (EditText) view.findViewById(R.id.confirm_email);
        password = (EditText) view.findViewById(R.id.confirm_password);
        loginButton = (Button) view.findViewById(R.id.confirm_button);
        confirmPassword = (EditText) view.findViewById(R.id.confirm_reset_password);
        anyAccount = (TextView) view.findViewById(R.id.confirm_any_account);

        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);
            anyAccount.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    private void setListeners() {
        loginButton.setOnClickListener(this);
        anyAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_button:
                if (checkValidation() == true) new MainActivity().replaceLoginFragment();
                break;

            case R.id.confirm_any_account:
                new MainActivity().replaceLoginFragment();
                break;
        }
    }

    private boolean checkValidation() {
        boolean valid = true;
        String getConfirmPassword = confirmPassword.getText().toString();
        String getEmailId = email.getText().toString();
        String getPassword = password.getText().toString();

        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        if (    getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0) {

            new CustomToast().Show_Toast(getActivity(), view,
                    "All fields are required");
        valid = false; }
        else if (!m.find()) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Email is Incorrect");
            valid = false;
        }
        else if (getConfirmPassword.length() < 6) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Password must be at least 6 symbols");
            valid = false;
        }
        else if (!getConfirmPassword.equals(getPassword)) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Both password doesn't match");
        valid = false; }
        else
            Toast.makeText(getActivity(), "Please Activate Your Account", Toast.LENGTH_SHORT)
                    .show();
        return valid;
    }
}
