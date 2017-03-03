package com.supersub.noamm_000.tincherfirsttry;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TeacherPersonalArea extends AppCompatActivity {

    TextView first;
    TextView last;
    TextView age;
    TextView education;
    TextView aoi;
    TextView schoolType;
    TextView address;
    TextView mail;
    TextView phone;
    TextView phoneHolder;
    TextView viewsNumber;
    TextView viewsNumberHolder;
    Button btnGetPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_personal_area);
        Intent intent =  getIntent();
        String userType = (String) intent.getSerializableExtra("userType");
        Teacher cTeacher = (userType.equals("Teacher"))
                ? (Teacher) intent.getSerializableExtra("currentTeacher")
                : (Teacher)  intent.getSerializableExtra("selectedTeacher");



        first = (TextView) findViewById(R.id.activity_tpa_tv_firstname);
        last = (TextView) findViewById(R.id.activity_tpa_tv_lastname);
        age = (TextView) findViewById(R.id.activity_tpa_tv_age);
        education = (TextView) findViewById(R.id.activity_tpa_tv_education);
        aoi = (TextView) findViewById(R.id.activity_tpa_tv_aoi);
        schoolType = (TextView) findViewById(R.id.activity_tpa_tv_schoolType);
        address = (TextView) findViewById(R.id.activity_tpa_tv_address);
        mail = (TextView) findViewById(R.id.activity_tpa_tv_mail);
        phone = (TextView) findViewById(R.id.activity_tpa_tv_phone);
        phoneHolder = (TextView) findViewById(R.id.activity_tpa_tv_phoneHolder);
        viewsNumber = (TextView) findViewById(R.id.activity_tpa_tv_views);
        viewsNumberHolder = (TextView) findViewById(R.id.activity_tpa_tv_viewsHolder);
        btnGetPhone = (Button) findViewById(R.id.activity_tpa_btn_viewPhone);


        first.setText(cTeacher.getFirstName());
        last.setText(cTeacher.getLastName());
        age.setText(Integer.toString(cTeacher.getAge()));
        education.setText(MapsForEnums.educationToString.get(cTeacher.getEducation()));
        aoi.setText(MapsForEnums.aoiToString.get(cTeacher.getAoi()));
        schoolType.setText(MapsForEnums.schoolTypeToString.get(cTeacher.getSchoolType()));
        address.setText(cTeacher.getAddress());
        mail.setText(cTeacher.getMail());

        userTypeBasedTunning(userType, cTeacher);


    }
    public void userTypeBasedTunning(String userType, final Teacher cTeacher) {
        if(userType.equals("Teacher")) {
            phone.setText(cTeacher.getPhone());
            viewsNumber.setText(Integer.toString(cTeacher.getViewsNum()));
            btnGetPhone.setVisibility(View.GONE);
        }
        else if(userType.equals("Principal")) {
            phoneHolder.setVisibility(View.INVISIBLE);
            viewsNumberHolder.setVisibility(View.INVISIBLE);
            btnGetPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    phoneHolder.setVisibility(View.VISIBLE);
                    phone.setText("0" + cTeacher.getPhone());
                    phone.setTextColor(Color.CYAN);
                    UpdateViewsNumAsyncTask updateViewsNumAsyncTask = new UpdateViewsNumAsyncTask(TeacherPersonalArea.this);
                    updateViewsNumAsyncTask.execute(cTeacher);
                }
            });
        }
    }


}
