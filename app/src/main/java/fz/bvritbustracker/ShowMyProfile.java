package fz.bvritbustracker;

import android.app.ProgressDialog;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowMyProfile extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    StudentModel studentModel = new StudentModel();
    FacultyModel facultyModel = new FacultyModel();
    String uid;
    TextView name, email, dept,phone;
    boolean accountFound = false;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Profile");
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        dept = findViewById(R.id.dept);
        //Log.d("UID MY",FirebaseAuth.getInstance().getCurrentUser().getUid());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference().child("Profile").child("Student")
             .addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 progressDialog.show();
                 for (DataSnapshot d: dataSnapshot.getChildren()){
                       StudentModel ss = d.getValue(StudentModel.class);

                           if (ss.getUid().equals(uid)){
                               studentModel = ss;
                               fillProfile2(studentModel);
                               accountFound = true;
                               progressDialog.dismiss();
                               break;
                           }

                 }
             }
             @Override
             public void onCancelled(DatabaseError databaseError) {
                 if (!accountFound){
                     Toast.makeText(getApplicationContext(), "Something gone wrong, please contact admin", Toast.LENGTH_SHORT).show();
                 }
             }
        });

        if (!accountFound) {//TextUtils.isEmpty(studentModel.getUid())){
            progressDialog.show();
            FirebaseDatabase.getInstance().getReference().child("Profile").child("Faculty")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                FacultyModel sd = d.getValue(FacultyModel.class);
                                if (sd.getUid().equals(FirebaseAuth.getInstance().getUid())) {
                                    facultyModel = sd;
                                    accountFound = true;
                                    fillProfile(facultyModel);
                                    progressDialog.dismiss();
                                    break;

                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            if (!accountFound){
                                Toast.makeText(getApplicationContext(), "Something gone wrong, please contact admin", Toast.LENGTH_SHORT).show();
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

    private void fillProfile2(StudentModel studentModel) {
        name.setText("Name: " + studentModel.getName());
        phone.setText("Phone: " + studentModel.getPhone());
        email.setText("Email: " + studentModel.getEmail());
        dept.setText("Department: "+ studentModel.getBranch());
    }

    private void fillProfile(FacultyModel facultyModel) {
        name.setText("Name: " + facultyModel.getName());
        phone.setText("Phone: " + facultyModel.getPhone());
        email.setText("Email: " + facultyModel.getEmail());
        dept.setText("Department: "+ facultyModel.getDept());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
