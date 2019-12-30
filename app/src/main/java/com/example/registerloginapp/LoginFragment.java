package com.example.registerloginapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginFragment extends Fragment implements View.OnClickListener{

    private  View view;
    private  EditText email, password;
    private  Button loginButton;
    private  TextView forgotPassword, anyAccount;
    private static FragmentManager fragmentManager;
    private Button btn_ru;

    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,
                              Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews();
        setListeners();
        btn_ru = (Button) view.findViewById(R.id.button_ru);

        btn_ru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MainActivity().setAppLocale("ru");
            }
        });
        return view;
    }

    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();

        email = (EditText) view.findViewById(R.id.login_email);
        password = (EditText) view.findViewById(R.id.login_password);
        loginButton = (Button) view.findViewById(R.id.login_button);
        forgotPassword = (TextView) view.findViewById(R.id.login_forget_password);
        anyAccount = (TextView) view.findViewById(R.id.login_any_account);

        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);
            forgotPassword.setTextColor(csl);
            anyAccount.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        anyAccount.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                if (checkValidation());
                break;

            case R.id.login_forget_password:
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.frameContainer,
                                new ResetPasswordFragment(),
                                Utils.ResetPasswordFragment).commit();
                break;

            case R.id.login_any_account:
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.frameContainer, new RegisterFragment(),
                                Utils.RegisterFragment).commit();
                break;
          // case R.id.button_ru:
           //  new MainActivity().setAppLocale("ru") ;

        }
    }


    private boolean checkValidation() {
        boolean valid = true;
        String getEmailId = email.getText().toString();
        String getPassword = password.getText().toString();

        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Enter both fields");
            valid = false;        }
        else if (!m.find())  {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Email is Incorrect");
            valid = false;     }
        else if (getPassword.length() < 6) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Password must be at least 6 symbols");
             valid = false; }
        else
            Toast.makeText(getActivity(), "Successful Login", Toast.LENGTH_SHORT)
                    .show();
        return valid;
    }

    public void getArguments(Bundle bundle) {

        String emaill = bundle.getString("Email");

        TextView emaillText = (TextView) view.findViewById(R.id.login_any_account);
        emaillText.setText(emaill);
    }

}

