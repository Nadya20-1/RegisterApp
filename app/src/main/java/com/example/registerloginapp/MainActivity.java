package com.example.registerloginapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends FragmentActivity {
    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainer, new LoginFragment(),
                            Utils.LoginFragment).commit();
        }
    }

    protected void replaceLoginFragment() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.frameContainer, new LoginFragment(),
                        Utils.LoginFragment).commit();
    }

    @Override
    public void onBackPressed() {

        Fragment SignUpFragment = fragmentManager
                .findFragmentByTag(Utils.RegisterFragment);
        Fragment ForgotPasswordFragment = fragmentManager
                .findFragmentByTag(Utils.ResetPasswordFragment);

        if (SignUpFragment != null)
            replaceLoginFragment();
        else if (ForgotPasswordFragment != null)
            replaceLoginFragment();
        else
            super.onBackPressed();
    }

   // public void onClickEmail(View view) {
   //     EditText editText = (EditText) findViewById(R.id.register_email);
   //     EditText editText1 = (EditText) findViewById(R.id.login_email);
   //     editText1.setText(editText.getText());
   // }
}





