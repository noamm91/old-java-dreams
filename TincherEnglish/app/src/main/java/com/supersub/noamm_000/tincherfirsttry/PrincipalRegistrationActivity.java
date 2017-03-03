package com.supersub.noamm_000.tincherfirsttry;

import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import static com.supersub.noamm_000.tincherfirsttry.R.mipmap.simpboardready;

public class PrincipalRegistrationActivity extends AppCompatActivity {

    static Map<CharSequence, Principal.SchoolCity> charsToSchoolsCities;
    static {
        charsToSchoolsCities = new HashMap<>();
        charsToSchoolsCities.put("רעננה", Principal.SchoolCity.Raanana);
        charsToSchoolsCities.put("הרצליה", Principal.SchoolCity.Herzeliya);
        charsToSchoolsCities.put("בית שאן", Principal.SchoolCity.Beit_Shaan);
    }
    static Map<CharSequence, Principal.SchoolName> charsToSchoolsNames;
    static {
        charsToSchoolsNames = new HashMap<>();
        charsToSchoolsNames.put("דקל", Principal.SchoolName.Dekel);
        charsToSchoolsNames.put("אוסטרובסקי", Principal.SchoolName.Ostrovski);
        charsToSchoolsNames.put("תיכון חדש", Principal.SchoolName.Tichon_Hadash);
    }
    CharSequence[] schoolsCities = new CharSequence[] {"רעננה", "הרצליה", "בית שאן"};
    CharSequence[] schoolsNames = new CharSequence[] {"דקל", "אוסטרובסקי", "תיכון חדש"};

    //String schoolCity;
    //String schoolName;

    TextView tvSchoolNameValue;
    TextView tvSchoolCityValue;
    EditText etFirstName;
    EditText etLastName;
    EditText etUserName;
    EditText etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_registration);

        // Adjusting the background programmatically
        getWindow().setBackgroundDrawable(ContextCompat.getDrawable(PrincipalRegistrationActivity.this, simpboardready));
        //getWindow().setBackgroundDrawable(getResources().getDrawable(simpboardready));
        // Making a soft raise when keyboard pops up and scroll view is defined
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        schoolCityBtnListener();
        schoolNameBtnListener();
        submitBtnListener();
    }
    public void schoolCityBtnListener() {
        final Button btnSchoolCity = (Button) findViewById(R.id.activity_pr_btn_schoolCity);
        btnSchoolCity.setBackgroundResource(R.drawable.btn_choose_selector);
        tvSchoolCityValue = (TextView) findViewById(R.id.activity_pr_tv_schoolCityValue);
        btnSchoolCity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalRegistrationActivity.this);
                builder.setTitle("בחר עיר בה נמצא בית הספר");
                builder.setItems(schoolsCities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        tvSchoolCityValue.setText(schoolsCities[i]);
                        //schoolCity = (String) schoolsCities[i];
                        //btnSchoolCity.setBackgroundColor(Color.CYAN);
                        //btnSchoolCity.setText(schoolsCities[i]);
                    }
                });
                builder.show();
            }
        });
    }
    public void schoolNameBtnListener() {
        final Button btnSchoolName = (Button) findViewById(R.id.activity_pr_btn_schoolName);
        btnSchoolName.setBackgroundResource(R.drawable.btn_choose_selector);
        tvSchoolNameValue = (TextView) findViewById(R.id.activity_pr_tv_schoolNameValue);

        btnSchoolName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalRegistrationActivity.this);
                builder.setTitle("בחר שם בית הספר");
                builder.setItems(schoolsNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tvSchoolNameValue.setText(schoolsNames[i]);
                        //schoolName = (String ) schoolsNames[i];
                        //btnSchoolName.setBackgroundColor(Color.CYAN);
                        //btnSchoolName.setText(schoolsNames[i]);

                    }
                });
                builder.show();
            }
        });
    }
    public void submitBtnListener() {
        Button btnSubmit = (Button) findViewById(R.id.activity_pr_btn_submit);
        btnSubmit.setBackgroundResource(R.drawable.btn_submit_selector);
        etFirstName = (EditText) findViewById(R.id.activity_pr_et_firstName);
        etLastName = (EditText) findViewById(R.id.activity_pr_et_lastName);
        etUserName = (EditText) findViewById(R.id.activity_pr_et_userName);
        etPassword = (EditText) findViewById(R.id.activity_pr_et_password);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pFirstname = etFirstName.getText().toString();
                String pLastname = etLastName.getText().toString();
                String pUsername = etUserName.getText().toString();
                String pPassword = etPassword.getText().toString();


                Principal.SchoolCity pSchoolCity = charsToSchoolsCities.get(tvSchoolCityValue.getText());
                Principal.SchoolName pSchoolName = charsToSchoolsNames.get(tvSchoolNameValue.getText());
                if(pFirstname.equals("") || pLastname.equals("") || pUsername.equals("") ||
                        pPassword.equals("") || (pSchoolCity == null) || (pSchoolName == null)) {

                    Toast.makeText(PrincipalRegistrationActivity.this, "Please fill all fields and submit again", Toast.LENGTH_LONG).show();

                }
                else {
                    Principal newPrincipal = new Principal(pFirstname, pLastname,
                            pUsername, pPassword, pSchoolCity, pSchoolName);
                    PrincipalRegistrationAsyncTask principalRegistrationAsyncTask = new PrincipalRegistrationAsyncTask(PrincipalRegistrationActivity.this);
                    principalRegistrationAsyncTask.execute(newPrincipal);
                }
            }
        });
    }
}
