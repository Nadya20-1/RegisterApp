package com.example.registerloginapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterFragment extends Fragment implements OnClickListener {
    private  View view;
    private  EditText fullName, email, mobileNumber, password;
    private  TextView signUp;
    private  Button signUpButton;

    private EditText emailR;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                              Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_register, container, false);
        initViews();
        setListeners();
        return view;

    }

    private void initViews() {
        fullName = (EditText) view.findViewById(R.id.register_name);
        email = (EditText) view.findViewById(R.id.register_email);
        mobileNumber = (EditText) view.findViewById(R.id.register_number);
        password = (EditText) view.findViewById(R.id.register_password);
        signUpButton = (Button) view.findViewById(R.id.register_button);
        signUp = (TextView) view.findViewById(R.id.register_any_account);

        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);
            signUp.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    private void setListeners() {
        signUpButton.setOnClickListener(this);
        signUp.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

       // emailR = (EditText)getActivity().findViewById(R.id.register_email);
        //emailL = (EditText)getActivity().findViewById(R.id.login_email);

        //signUpButton.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button:
                if (checkValidation() == true) { new MainActivity().replaceLoginFragment();
                    emailR = (EditText) view.findViewById(R.id.register_email);
                    String emailr = emailR.getText().toString();

                    Bundle bundle = new Bundle();
                    bundle.putString("Email",emailr);


                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    LoginFragment loginFragment = new LoginFragment();
                    loginFragment.setArguments(bundle);

                    fragmentTransaction.replace(R.id.frameContainer,loginFragment);
                    fragmentTransaction.commit();

                }
                break;

            case R.id.register_any_account:
                new MainActivity().replaceLoginFragment();
                break;
        }
    }


    private boolean checkValidation() {
        boolean valid = true;
        String getFullName = fullName.getText().toString();
        String getEmailId = email.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
        String getPassword = password.getText().toString();

        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getPassword.equals("") || getPassword.length() == 0)
        {
            new CustomToast().Show_Toast(getActivity(), view,
                    "All fields are required");
        valid = false; }
        else if (!m.find()) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Email is Incorrect");
            valid = false; }
        else if (getPassword.length() < 6) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Password must be at least 6 symbols");
            valid = false; }
        else
            Toast.makeText(getActivity(), "Registration Completed Successfully", Toast.LENGTH_SHORT)
                    .show();
        return valid;
    }
}