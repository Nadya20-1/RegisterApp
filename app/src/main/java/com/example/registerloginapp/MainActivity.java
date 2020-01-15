package com.example.registerloginapp;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;


import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import java.util.Locale;


public class MainActivity extends FragmentActivity {
    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        setAppLocale("en");

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

    protected void replaceRegisterFragment() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.frameContainer, new RegisterFragment(),
                        Utils.RegisterFragment).commit();
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
            replaceRegisterFragment();
        else
            super.onBackPressed();
    }


     public void setAppLocale(String localeCode)
   {
       Resources res = getResources();
       DisplayMetrics dm = res.getDisplayMetrics();
       Configuration conf = res.getConfiguration();
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
           conf.setLocale(new Locale(localeCode.toLowerCase()));
       }
       else {conf.locale = new Locale(localeCode.toLowerCase());}
       res.updateConfiguration(conf,dm);
   }

    @Override
    public void recreate() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        super.recreate();
    }

}





