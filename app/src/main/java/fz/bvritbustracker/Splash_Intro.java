package fz.bvritbustracker;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class Splash_Intro extends AppCompatActivity {

    private ImageView imageView;
    private Animation animation;
    int n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__intro);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        imageView = (ImageView)findViewById(R.id.splashlogo);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        imageView.setAnimation(animation);
        n=0;

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
