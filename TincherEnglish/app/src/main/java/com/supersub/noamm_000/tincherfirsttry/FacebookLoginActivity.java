package com.supersub.noamm_000.tincherfirsttry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;

public class FacebookLoginActivity extends AppCompatActivity {

    LoginButton fLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initial Facebook SDK for enable the use of all the methods
        FacebookSdk.sdkInitialize(getApplicationContext());
        CallbackManager cbm  = CallbackManager.Factory.create();

        setContentView(R.layout.activity_facebook_login);
    }
}
