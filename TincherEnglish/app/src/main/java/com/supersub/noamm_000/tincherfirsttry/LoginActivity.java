package com.supersub.noamm_000.tincherfirsttry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class LoginActivity extends AppCompatActivity {

    EditText etUserName;
    EditText etPassword;
    RadioGroup rgUserType;
    RadioButton rbTeacher;
    RadioButton rbPrincipal;
    TextView tvTeacherReg;
    TextView tvPrincipalReg;
    Button btnRegister;
    Button btnLogin;
    Button btnFacebookLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_page);

        etUserName = (EditText) findViewById(R.id.activity_login_et_username);
        etPassword = (EditText) findViewById(R.id.activity_pr_et_password);
        rbTeacher = (RadioButton) findViewById(R.id.activity_login_rb_teacher);
        rbPrincipal = (RadioButton) findViewById(R.id.activity_login_rb_principal);



        Intent intent = getIntent();
        String userName = (String) intent.getSerializableExtra("userName");
        String password = "";
        String userType = "";
        if(userName != null) {
            password = (String) intent.getSerializableExtra("password");
            userType = (String) intent.getSerializableExtra("userType");
            etUserName.setText(userName);
            etPassword.setText(password);
            if(userType.equals("teacher")) {
                rbTeacher.setChecked(true);
            }
            else {
                rbPrincipal.setChecked(true);
            }
        }


        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        loginBtnListener();
        facebookLoginBtnListener();
        //registerBtnListener();
        teacherRegTvListener();
        principalRegTvListener();
    }
/*
    public void registerBtnListener() {
        btnRegister = (Button) findViewById(R.id.activity_login_btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.noamm_000.tincherfirsttry.RegisterActivity");
                startActivity(intent);
            }
        });
    }
*/
    public void teacherRegTvListener() {
        tvTeacherReg = (TextView) findViewById(R.id.activity_login_tv_teacherReg);
        tvTeacherReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.noamm_000.tincherfirsttry.TeacherRegistrationActivity");
                startActivity(intent);
            }
        });
    }

    public void principalRegTvListener() {
        tvPrincipalReg = (TextView) findViewById(R.id.activity_login_tv_principalReg);
        tvPrincipalReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.noamm_000.tincherfirsttry.PrincipalRegistrationActivity");
                startActivity(intent);
            }
        });
    }

    public void loginBtnListener() {
        btnLogin = (Button) findViewById(R.id.activity_login_btn_login);
        btnLogin.setBackgroundResource(R.drawable.btn_submit_selector);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //etUserName = (EditText) findViewById(R.id.activity_login_et_username);
                //etPassword = (EditText) findViewById(R.id.activity_login_et_password);
                rgUserType = (RadioGroup) findViewById(R.id.activity_login_rg_userType);
                //rbTeacher = (RadioButton) findViewById(R.id.activity_login_rb_teacher);
                String userTypeString = (rgUserType.getCheckedRadioButtonId() == rbTeacher.getId()) ? "teacher" : "principal";

                // Activating login page according to userType
                if(userTypeString.equals("principal")) {
                    PrincipalLoginAsyncTask principalLoginAsyncTask = new PrincipalLoginAsyncTask(LoginActivity.this, btnLogin);
                    principalLoginAsyncTask.execute(etUserName.getText().toString(), etPassword.getText().toString());
                }
                else if(userTypeString.equals("teacher")) {
                    TeacherLoginAsyncTask teacherLoginAsyncTask = new TeacherLoginAsyncTask(LoginActivity.this, btnLogin);
                    teacherLoginAsyncTask.execute(etUserName.getText().toString(), etPassword.getText().toString());
                }
            }
        });
    }

    public void facebookLoginBtnListener() {

        btnFacebookLogin = (Button) findViewById(R.id.activity_login_btn_flogin);
        btnFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.noamm_000.tincherfirsttry.FacebookLoginActivity");
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
