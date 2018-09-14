package fz.bvritbustracker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class EmailLogin extends AppCompatActivity {

    private Button swi_signup, login,forgot;
    private EditText em_si,pas_si;
    private ProgressDialog prog;

    private NetworkInfo info;
    private ConnectivityManager connectivityManager;
    boolean isNetworkactive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        prog = new ProgressDialog(this);
        prog.setMessage("Please wait...");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Login");

        connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        info = connectivityManager.getActiveNetworkInfo();
        isNetworkactive = info!=null && info.isConnectedOrConnecting();
        if (!isNetworkactive){
            Toast.makeText(getApplicationContext(),"Internet not active",Toast.LENGTH_SHORT).show();
        }

        swi_signup = findViewById(R.id.switchTosignup);
        login = findViewById(R.id.login_button);
        em_si = findViewById(R.id.email_ln);
        pas_si = findViewById(R.id.paswd_ln);
        forgot = findViewById(R.id.forgotPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogin();
            }
        });

        swi_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmailLogin.this,EmailSignup.class));
                finish();
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(em_si.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Email required",Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseAuth.getInstance().sendPasswordResetEmail(em_si.getText().toString());
                Toast.makeText(getApplicationContext(),"Check your email inbox for password reset link",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home ){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    private void onLogin() {
        if (!isNetworkactive){
            Toast.makeText(getApplicationContext(),"Internet not active",Toast.LENGTH_SHORT).show();
            return;
        }
        prog.show();

        String em = em_si.getText().toString();
        String pwd = pas_si.getText().toString();
        String type = "login";
       // Login_Async login_async = new Login_Async(this);
        //login_async.execute(type,em,pwd);
        if (TextUtils.isEmpty(em)){
            em_si.setError("Required!");
            return;
        }
        if (TextUtils.isEmpty(pwd)){
            pas_si.setError("Required!");
            return;
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(em,pwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            startActivity(new Intent(EmailLogin.this,Home.class));
                            prog.dismiss();
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_SHORT).show();
                            prog.dismiss();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EmailLogin.this,MainActivity.class));
        finish();
    }
}

