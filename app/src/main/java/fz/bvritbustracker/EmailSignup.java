package fz.bvritbustracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailSignup extends AppCompatActivity {

    private Button signUpNow;
    private EditText emailStudent,emailFaculty,phoneFaculty,phoneStudent,passwordFaculty,passwordStudent,name,rollnoStudent;
    private ProgressDialog prog;
    private Spinner year,branch,dept,genderStudent,genderFaculty;
    private RadioGroup switchView;
    private LinearLayout faculty,student;
    private DatabaseReference profileData;
    private boolean isViewShown = false;
    private int code = 0;

    private NetworkInfo info;
    private ConnectivityManager connectivityManager;
    boolean isNetworkactive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_signup);
        prog = new ProgressDialog(this);
        prog.setMessage("Please Wait...");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Signup");

        connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        info = connectivityManager.getActiveNetworkInfo();
        isNetworkactive = info!=null && info.isConnectedOrConnecting();
        if (!isNetworkactive){
            Toast.makeText(getApplicationContext(),"Internet not active",Toast.LENGTH_SHORT).show();
        }

        rollnoStudent = findViewById(R.id.roll_student);
        name = findViewById(R.id.name_register);
        switchView = findViewById(R.id.switchView);
        faculty = findViewById(R.id.facultyViewGroup);
        student = findViewById(R.id.studentViewGroup);
        year = findViewById(R.id.year_student);
        genderFaculty = findViewById(R.id.gender_faculty);
        genderStudent = findViewById(R.id.gender_student);
        branch = findViewById(R.id.branch_student);
        dept = findViewById(R.id.dept_faculty);
        emailFaculty = findViewById(R.id.email_faculty);
        emailStudent = findViewById(R.id.email_student);
        phoneFaculty = findViewById(R.id.mobile_faculty);
        phoneStudent = findViewById(R.id.mobile_student);
        passwordFaculty = findViewById(R.id.paswd_faculty);
        passwordStudent = findViewById(R.id.paswd_student);
        profileData = FirebaseDatabase.getInstance().getReference().child("Profile");

        switchView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

            }
        });
        switchView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.faculty){
                    faculty.setVisibility(View.VISIBLE);
                    isViewShown = true;
                    student.setVisibility(View.GONE);
                    code = 1;
                }else if (i == R.id.student){
                    student.setVisibility(View.VISIBLE);
                    isViewShown = true;
                    faculty.setVisibility(View.GONE);
                    code = 2;
                }
            }
        });
        signUpNow = findViewById(R.id.signUpMan);

        signUpNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignup();
            }
        });

    }

    private void onSignup() {

        prog.show();
        if (isViewShown && code == 2){
            if (TextUtils.isEmpty(name.getText().toString())){
                name.setError("Required!");
                return;
            }
            if (TextUtils.isEmpty(emailStudent.getText().toString())){
                emailStudent.setError("Required!");
                return;
            }
            if (TextUtils.isEmpty(phoneStudent.getText().toString())){
                phoneStudent.setError("Required!");
                return;
            }
            if (TextUtils.isEmpty(rollnoStudent.getText().toString())){
                rollnoStudent.setError("Required!");
                return;
            }
            if (year.getSelectedItemId()==0){
                Toast.makeText(getApplicationContext(),"Select year",Toast.LENGTH_SHORT).show();
                return;
            }
            if (genderStudent.getSelectedItemId()==0){
                Toast.makeText(getApplicationContext(),"Select gender",Toast.LENGTH_SHORT).show();
                return;
            }
            if (branch.getSelectedItemId()==0){
                Toast.makeText(getApplicationContext(),"Select branch",Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(passwordStudent.getText().toString())){
                passwordStudent.setError("Required!");
                return;
            }

            String passwd = passwordStudent.getText().toString();
            String passwdPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
            if (!passwd.matches(passwdPattern)){
                Toast.makeText(getApplicationContext(),"Password must contain\n" +
                        "1 Captial letter\n" +
                        "1 Small letter\n" +
                        "1 Special character\n" +
                        "1 Number\n" +
                        "and 8 characters long",Toast.LENGTH_LONG).show();
                prog.dismiss();
                return;
            }

            Pattern pattern;
            Matcher matcher;
            final String PATTERN="^[0-9a-zA-Z.]+@bvrit.ac.in$";
            pattern=Pattern.compile(PATTERN);
            String email = emailStudent.getText().toString();
            matcher=pattern.matcher(email);
            if(!matcher.matches()) {
                Toast.makeText(getApplicationContext(),"Invalid email, please use college email id only",Toast.LENGTH_LONG).show();
                prog.dismiss();
                return;
            }

            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(emailStudent.getText().toString(),passwordStudent.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                saveProfileData(task.getResult().getUser().getUid());
                                prog.dismiss();
                            }else {
                                Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_SHORT).show();
                                prog.dismiss();
                                return;
                            }
                        }
                    });

        }
        else if (isViewShown && code == 1){
            if (TextUtils.isEmpty(name.getText().toString())){
                name.setError("Required!");
                return;
            }
            if (TextUtils.isEmpty(emailFaculty.getText().toString())){
                emailFaculty.setError("Required!");
                return;
            }
            if (TextUtils.isEmpty(phoneFaculty.getText().toString())){
                phoneFaculty.setError("Required!");
                return;
            }
            if (TextUtils.isEmpty(passwordFaculty.getText().toString())){
                passwordFaculty.setError("Required!");
                return;
            }
            if (genderFaculty.getSelectedItemId()==0){
                Toast.makeText(getApplicationContext(),"Select gender", Toast.LENGTH_SHORT).show();
                return;
            }
            if (dept.getSelectedItemId()==0){
                Toast.makeText(getApplicationContext(),"Select dept", Toast.LENGTH_SHORT).show();
                return;
            }

            String passwd = passwordFaculty.getText().toString();
            String passwdPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
            if (!passwd.matches(passwdPattern)){
                Toast.makeText(getApplicationContext(),"Password must contain\n" +
                        "1 Captial letter\n" +
                        "1 Small letter\n" +
                        "1 Special character\n" +
                        "1 Number\n" +
                        "and 8 characters long",Toast.LENGTH_LONG).show();
                prog.dismiss();
                return;
            }

            Pattern pattern;
            Matcher matcher;
            final String PATTERN="^[0-9a-zA-Z.]+@bvrit.ac.in$";
            pattern=Pattern.compile(PATTERN);
            String email = emailFaculty.getText().toString();
            matcher=pattern.matcher(email);
            if(!matcher.matches()) {
                Toast.makeText(getApplicationContext(),"Invalid email, please use college email id only",Toast.LENGTH_LONG).show();
                prog.dismiss();
                return;
            }

            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(emailFaculty.getText().toString(),passwordFaculty.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                saveProfileData(task.getResult().getUser().getUid());
                                prog.dismiss();
                            }else {
                                Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_SHORT).show();
                                prog.dismiss();
                                return;
                            }
                        }
                    });
        }
        else {
            Toast.makeText(getApplicationContext(),"Select appropriate position",Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void saveProfileData(String uid) {
        if (isViewShown && code == 2) {
            profileData = FirebaseDatabase.getInstance().getReference().child("Profile").child("Student");
            StudentModel studentModel = new StudentModel();
            studentModel.setBranch(branch.getSelectedItem().toString());
            studentModel.setEmail(emailStudent.getText().toString());
            studentModel.setGender(genderStudent.getSelectedItem().toString());
            studentModel.setName(name.getText().toString());
            studentModel.setPassword(passwordStudent.getText().toString());
            studentModel.setPhone(phoneStudent.getText().toString());
            studentModel.setRollno(rollnoStudent.getText().toString());
            studentModel.setYear(year.getSelectedItem().toString());
            studentModel.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
            profileData.child(uid).setValue(studentModel)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Registration Success\n"+"Welcome "+name.getText().toString(),Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EmailSignup.this,Home.class));
                                finish();
                            }
                        }
                    });

        } else if (isViewShown && code == 1) {
            profileData = FirebaseDatabase.getInstance().getReference().child("Profile").child("Faculty");
            FacultyModel fm = new FacultyModel();
            fm.setDept(dept.getSelectedItem().toString());
            fm.setEmail(emailFaculty.getText().toString());
            fm.setGender(genderFaculty.getSelectedItem().toString());
            fm.setPassword(passwordFaculty.getText().toString());
            fm.setPhone(phoneFaculty.getText().toString());
            fm.setName(name.getText().toString());
            fm.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
            profileData.child(uid).setValue(fm)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                         Toast.makeText(getApplicationContext(),"Registration Success\n"+"Welcome "+name.getText().toString(),Toast.LENGTH_SHORT).show();
                         startActivity(new Intent(EmailSignup.this,Home.class));
                         finish();
                    }
                }
                });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home ){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EmailSignup.this,MainActivity.class));
        finish();
    }

}

