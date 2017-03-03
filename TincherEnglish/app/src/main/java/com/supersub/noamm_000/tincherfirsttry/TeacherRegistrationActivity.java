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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static com.supersub.noamm_000.tincherfirsttry.R.mipmap.simpboardready;

public class TeacherRegistrationActivity extends AppCompatActivity {
/*
    // Building 2 one-way maps in order to functionality make bidirectional map for education
    // Making the same for each enum value

    static Map<CharSequence, Teacher.Education> charsToEducation;
    static {
        charsToEducation = new HashMap<>();
        charsToEducation.put("תיכונית", Teacher.Education.High_school_Diploma);
        charsToEducation.put("תואר ראשון", Teacher.Education.First_Degree);
        charsToEducation.put("מעל תואר ראשון", Teacher.Education.Second_Degree_Or_Above);
        charsToEducation.put("ללא", Teacher.Education.None);
    }

    static Map<Teacher.Education, String> educationToString;
    static {
        educationToString = new HashMap<>();
        educationToString.put(Teacher.Education.High_school_Diploma, "תיכונית");
        educationToString.put(Teacher.Education.First_Degree, "תואר ראשון");
        educationToString.put(Teacher.Education.Second_Degree_Or_Above, "מעל תואר ראשון");
        educationToString.put(Teacher.Education.None, "ללא");
    }

    static Map<CharSequence, Teacher.AOI> charsToAOI;
    static  {
        charsToAOI = new HashMap<>();
        charsToAOI.put("השרון", Teacher.AOI.HaSharon);
        charsToAOI.put("המרכז", Teacher.AOI.HaMerkaz);
        charsToAOI.put("השפלה", Teacher.AOI.HaShfela);
        charsToAOI.put("הדרום", Teacher.AOI.HaDarom);
        charsToAOI.put("הצפון", Teacher.AOI.HaTzafon);
    }

    static Map<Teacher.AOI, String> AOIToString;
    static {
        AOIToString = new HashMap<>();
        AOIToString.put(Teacher.AOI.HaSharon, "השרון");
        AOIToString.put(Teacher.AOI.HaMerkaz, "המרכז");
        AOIToString.put(Teacher.AOI.HaShfela, "השפלה");
        AOIToString.put(Teacher.AOI.HaDarom, "הדרום");
        AOIToString.put(Teacher.AOI.HaTzafon, "הצפון");
    }

    static Map<CharSequence, Teacher.SchoolType> charsToSchoolType;
    static {
        charsToSchoolType = new HashMap<>();
        charsToSchoolType.put("ממלכתי", Teacher.SchoolType.State);
        charsToSchoolType.put("דתי", Teacher.SchoolType.Religious);
        charsToSchoolType.put("דמוקרטי", Teacher.SchoolType.Democratic);
        charsToSchoolType.put("לא משנה", Teacher.SchoolType.Nevermind);
    }
*/
    Button setAge;
    Button setEducation;
    Button setArea;
    Button setSchoolType;
    Button submit;
    TextView age;
    TextView education;
    TextView area;
    TextView schoolType;
    EditText firstName;
    EditText lastName;
    EditText userName;
    EditText password;
    EditText address;
    EditText mail;
    EditText phone;
    RadioGroup educationalDegree;
    RadioButton noEducationalDegree;
    RadioButton yesEducationalDegree;

    CharSequence Ages[] = new CharSequence[] {"18","19","20","21","22","23","24","25",
            "26","27","28","29","30","31","32","33","34","35","36","37","38","39","40",
            "41", "42","43","44","45","46","47","48","49","50","51","52","53","54","55",
            "56","57", "58","59","60","61","62", "63","64","65","66","67","68","69","70"};
    CharSequence Education[] = new CharSequence[] {"ללא", "תיכונית", "תואר ראשון", "מעל תואר ראשון"};
    CharSequence Areas[] = new CharSequence[] {"השרון", "המרכז", "השפלה", "הדרום", "הצפון"};
    CharSequence SchoolTypes[] = new CharSequence[] {"ממלכתי", "דתי", "דמוקרטי", "לא משנה"};

    //Teacher teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);

        // Adjusting the background programmatically
        getWindow().setBackgroundDrawable(ContextCompat.getDrawable(TeacherRegistrationActivity.this, simpboardready));
        //getWindow().setBackgroundDrawable(getResources().getDrawable(simpboardready));
        // Making a soft raise when keyboard pops up and scroll view is defined
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);



        onAgeBtnClickListener();
        onEducationBtnClickListener();
        onAreaBtnClickListener();
        onSchoolTypeBtnClickListener();
        onSubmitBtnClickListener();
    }

    public void onAgeBtnClickListener() {
        setAge = (Button) findViewById(R.id.setAgeBtn);
        setAge.setBackgroundResource(R.drawable.btn_choose_selector);
        age = (TextView) findViewById(R.id.activity_tr_tv_ageByValue);
        // Open a dialog for choosing age when age btn being clicked
        setAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TeacherRegistrationActivity.this);
                builder.setTitle("בחר גיל");
                builder.setItems(Ages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //TextView tv22 = (TextView) findViewById(R.id.activity_tr_tv_ageByValue);
                        age.setText(Ages[i]);
                        //setAge.setText(Ages[i]);
                        //age.setText(Ages[i]);
                        //setAge.setBackgroundColor(Color.CYAN);
                    }
                });
                builder.show();
            }
        });

    }

    public void onEducationBtnClickListener() {
        setEducation = (Button) findViewById(R.id.setEducationBtn);
        setEducation.setBackgroundResource(R.drawable.btn_choose_selector);
        education = (TextView) findViewById(R.id.activity_tr_tv_educationValue);
        // Open a dialog for choosing education when education btn being clicked
        setEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TeacherRegistrationActivity.this);
                builder.setTitle("בחר השכלה");
                builder.setItems(Education, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        education.setText(Education[i]);
                        //setEducation.setText(Education[i]);
                        //setEducation.setBackgroundColor(Color.CYAN);
                    }
                });
                builder.show();
            }
        });
    }

    public void onAreaBtnClickListener() {
        setArea = (Button) findViewById(R.id.workAreakBtn);
        setArea.setBackgroundResource(R.drawable.btn_choose_selector);
        area = (TextView) findViewById(R.id.activity_tr_tv_aoiValue);
        // Open a dialog for choosing area when area btn being clicked
        setArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TeacherRegistrationActivity.this);
                builder.setTitle("בחר איזור מבוקש");
                builder.setItems(Areas, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        area.setText(Areas[i]);
                        //setArea.setText(Areas[i]);
                        //setArea.setBackgroundColor(Color.CYAN);
                    }
                });
                builder.show();
            }
        });
    }

    public void onSchoolTypeBtnClickListener() {
        setSchoolType = (Button) findViewById(R.id.schoolTypeBtn);
        setSchoolType.setBackgroundResource(R.drawable.btn_choose_selector);
        schoolType = (TextView) findViewById(R.id.activity_tr_tv_schoolTypeValue);
        // Open a dialog for choosing schoolype when schoolType btn being clicked
        setSchoolType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TeacherRegistrationActivity.this);
                builder.setTitle("בחר אופי בית ספר מבוקש");
                builder.setItems(SchoolTypes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        schoolType.setText(SchoolTypes[i]);
                        //setSchoolType.setText(SchoolTypes[i]);
                        //setSchoolType.setBackgroundColor(Color.CYAN);
                    }
                });
                builder.show();
            }
        });
    }
    // Creating new teacher object constructed of all data being chosen earlier
    public void onSubmitBtnClickListener() {
        submit = (Button) findViewById(R.id.submitBtn);
        submit.setBackgroundResource(R.drawable.btn_submit_selector);
        firstName = (EditText) findViewById(R.id.activity_tr_et_firstName);
        lastName = (EditText) findViewById(R.id.activity_tr_et_lastName);
        userName = (EditText) findViewById(R.id.activity_tr_et_userName);
        password = (EditText) findViewById(R.id.activity_tr_et_password);
        educationalDegree = (RadioGroup) findViewById(R.id.rg_EducationalDegree);
        noEducationalDegree = (RadioButton) findViewById(R.id.noRb);
        yesEducationalDegree = (RadioButton) findViewById(R.id.yesRb);
        address = (EditText) findViewById(R.id.AddressEt);
        mail = (EditText) findViewById(R.id.mailEt);
        phone = (EditText) findViewById(R.id.phoneEt);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tAge;
                try {
                     tAge = Integer.parseInt(age.getText().toString());
                } catch (NumberFormatException e) {
                    tAge = 0;
                }
                boolean tIsEducation = (educationalDegree.getCheckedRadioButtonId() == yesEducationalDegree.getId());
                Teacher.Education tEducation = MapsForEnums.stringToEducation.get(education.getText().toString());
                Teacher.AOI tAOI = MapsForEnums.stringToAOI.get(area.getText().toString());
                Teacher.SchoolType tSchoolType = MapsForEnums.stringToSchoolType.get(schoolType.getText().toString());
                String tAddress = address.getText().toString();
                String tMail = mail.getText().toString();
                String tPhone = phone.getText().toString();

                /*
                // Getting Person object that transferred from RegisterActivity
                Intent i = getIntent();
                Person newPerson = (Person) i.getSerializableExtra("newPerson");
                String tFirstname = newPerson.getFirstName();
                String tLastname = newPerson.getLastName();
                String tUsername = newPerson.getUserName();
                String tPassword = newPerson.getPassword();
                */

                String tFirstname = firstName.getText().toString();
                String tLastname = lastName.getText().toString();
                String tUsername = userName.getText().toString();
                String tPassword = password.getText().toString();


                // Validate all fields have data (no emepty fields) before creation of teacher object
                if(tFirstname.equals("") || tLastname.equals("") || tUsername.equals("") ||
                        tPassword.equals("") || (tEducation == null) || (tAOI == null) ||
                        (tSchoolType == null) || tAddress.equals("") || tMail.equals("")) {

                    Toast.makeText(TeacherRegistrationActivity.this, "Please fill all fields and submit again", Toast.LENGTH_LONG).show();

                }
                else {
                    // Creating a Teacher object by using the person data with the new data
                    Teacher newTeacher = new Teacher(tFirstname, tLastname, tUsername, tPassword,
                                                tAge, tEducation, tAOI, tSchoolType, tIsEducation, tAddress, tMail, tPhone, 0);

                    //String type = "register";
                    TeacherRegistrationAsyncTask teacherRegistrationAsyncTask = new TeacherRegistrationAsyncTask(TeacherRegistrationActivity.this);
                    teacherRegistrationAsyncTask.execute(newTeacher);
                }
            }
        });
    }

}
