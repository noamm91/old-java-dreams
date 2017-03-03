package com.supersub.noamm_000.tincherfirsttry;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PrincipalMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_main);
        initTable();
    }


    public void initTable() {

        //StateListDrawable drawable = new StateListDrawable();
        //drawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(Color.CYAN));
        //drawable.addState(new int[]{}, new ColorDrawable(Color.TRANSPARENT));

        final Intent intent = getIntent();
        String numOfTeachersAsString = (String) intent.getSerializableExtra("numOfTeachers");
        final Teacher[] teachers = (Teacher[]) intent.getSerializableExtra("allTeachers");
        //Toast.makeText(PrincipalMainActivity.this, Integer.toString(teachers.length), Toast.LENGTH_LONG).show();
        int numOfTeachers = Integer.parseInt(numOfTeachersAsString);
        //Toast.makeText(PrincipalMainActivity.this,numOfTeachersAsString,Toast.LENGTH_LONG).show();

        // Initial the content table layout and a row in this table
        TableLayout tableContent = (TableLayout) findViewById(R.id.activity_main_table_main);
        final TableRow tr = new TableRow(this);

        // Initial the header table layout and the header row in this table
        //TableLayout tableHeader = (TableLayout) findViewById(R.id.activity_main_table_header);
        //TableRow trHeader = new TableRow(this);

        TextView tvFirstname = new TextView(this);
        tvFirstname.setText("שם פרטי");
        tvFirstname.setTextColor(Color.WHITE);
        tvFirstname.setPadding(80,0,0,15);
        tvFirstname.setTextSize(18);
        tvFirstname.setGravity(Gravity.RIGHT);
        tvFirstname.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        tr.addView(tvFirstname);
        //trHeader.addView(tvFirstname);

        TextView tvLastname = new TextView(this);
        tvLastname.setText("שם משפחה");
        tvLastname.setTextColor(Color.WHITE);
        tvLastname.setPadding(100,0,0,0);
        tvLastname.setTextSize(18);
        tvLastname.setGravity(Gravity.RIGHT);
        tvLastname.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        tr.addView(tvLastname);
        //trHeader.addView(tvLastname);

        TextView tvAge = new TextView(this);
        tvAge.setText("גיל");
        tvAge.setTextColor(Color.WHITE);
        tvAge.setPadding(50,0,0,0);
        tvAge.setTextSize(18);
        tvAge.setGravity(Gravity.RIGHT);
        tvAge.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        tr.addView(tvAge);
        //trHeader.addView(tvAge);


        TextView tvEducation = new TextView(this);
        tvEducation.setText("השכלה");
        tvEducation.setTextColor(Color.WHITE);
        tvEducation.setPadding(100,0,0,0);
        tvEducation.setTextSize(18);
        tvEducation.setGravity(Gravity.RIGHT);
        //tvEducation.setWidth(ActionBar.LayoutParams.WRAP_CONTENT);
        tvEducation.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        tr.addView(tvEducation);
        //trHeader.addView(tvEducation);

        TextView tvAoi = new TextView(this);
        tvAoi.setText("איזור עניין");
        tvAoi.setTextColor(Color.WHITE);
        tvAoi.setPadding(80,0,0,0);
        tvAoi.setTextSize(18);
        tvAoi.setGravity(Gravity.RIGHT);
        tvAoi.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        tr.addView(tvAoi);
        //trHeader.addView(tvAoi);

        tableContent.addView(tr);
        //tableHeader.addView(trHeader);

        for(int i = 0; i < teachers.length; i++) {

            StateListDrawable drawable = new StateListDrawable();
            drawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(Color.CYAN));
            drawable.addState(new int[]{}, new ColorDrawable(Color.TRANSPARENT));

            TableRow trTeacher = new TableRow(this);
            //trTeacher.setBackgroundDrawable(drawable);

            TextView tvTeacherFirstname = new TextView(this);
            TextView tvTeacherLastname = new TextView(this);
            TextView tvTeacherAge = new TextView(this);
            TextView tvTeacherEducation = new TextView(this);
            TextView tvTeacherAoi = new TextView(this);

            tvTeacherFirstname.setText(teachers[i].getFirstName());
            tvTeacherFirstname.setTextColor(Color.WHITE);
            tvTeacherFirstname.setPadding(100,0,0,25);
            tvTeacherFirstname.setGravity(Gravity.RIGHT);
            tvTeacherFirstname.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            //tvTeacherFirstname.setBackgroundResource(R.drawable.shape_et);
            trTeacher.addView(tvTeacherFirstname);

            tvTeacherLastname.setText(teachers[i].getLastName());
            tvTeacherLastname.setTextColor(Color.WHITE);
            tvTeacherLastname.setPadding(100,0,0,25);
            tvTeacherLastname.setGravity(Gravity.RIGHT);
            tvTeacherLastname.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            //tvTeacherLastname.setBackgroundResource(R.drawable.shape_et);
            trTeacher.addView(tvTeacherLastname);

            tvTeacherAge.setText(Integer.toString(teachers[i].getAge()));
            tvTeacherAge.setTextColor(Color.WHITE);
            tvTeacherAge.setPadding(100,0,0,25);
            tvTeacherAge.setGravity(Gravity.RIGHT);
            tvTeacherAge.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            trTeacher.addView(tvTeacherAge);

            tvTeacherEducation.setText(MapsForEnums.educationToString.get(teachers[i].getEducation()));
            tvTeacherEducation.setTextColor(Color.WHITE);
            tvTeacherEducation.setPadding(100,0,0,25);
            tvTeacherEducation.setGravity(Gravity.RIGHT);
            tvTeacherEducation.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            trTeacher.addView(tvTeacherEducation);

            tvTeacherAoi.setText(MapsForEnums.aoiToString.get(teachers[i].getAoi()));
            tvTeacherAoi.setTextColor(Color.WHITE);
            tvTeacherAoi.setPadding(100,0,0,25);
            tvTeacherAoi.setGravity(Gravity.RIGHT);
            tvTeacherAoi.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            trTeacher.addView(tvTeacherAoi);


            trTeacher.setBackgroundResource(R.drawable.tr_selector);
            //trTeacher.setBackgroundDrawable(drawable);
            trTeacher.setClickable(true);

            //trTeacher.setFocusable(true);
            //trTeacher.setFocusableInTouchMode(true);

            final int finalI = i;
            trTeacher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TableRow trCurrent = (TableRow) view;

                    //trCurrent.setBackgroundDrawable(drawable);

                    //trCurrent.setBackgroundColor(Color.CYAN);
                    // Get data of selected teacher and build from it a teacher object
                    String first = ((TextView) trCurrent.getChildAt(0)).getText().toString(); // First name
                    String last = ((TextView) trCurrent.getChildAt(1)).getText().toString(); // Last name
                    int age = Integer.parseInt(((TextView) trCurrent.getChildAt(2)).getText().toString()); // Age
                    Teacher.Education education = MapsForEnums.stringToEducation.get(((TextView) trCurrent.getChildAt(3)).getText().toString()); // Education
                    Teacher.AOI aoi = MapsForEnums.stringToAOI.get(((TextView) trCurrent.getChildAt(4)).getText().toString()); // School Type

                    //Teacher selectedTeacher = new Teacher(first, last, age, education, aoi);
                    Teacher selectedTeacher = teachers[finalI];
                    // Create Intent and fill it with Teacher object and userType
                    String userType = "Principal";
                    Intent intent1 = new Intent("com.example.noamm_000.tincherfirsttry.TeacherPersonalArea");
                    intent1.putExtra("userType", userType);
                    intent1.putExtra("selectedTeacher", selectedTeacher);
                    startActivity(intent1);
                }
            });

            tableContent.addView(trTeacher);
        }
    }


}
