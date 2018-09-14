package fz.bvritbustracker;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcel;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

//    private Button r1j,r2j,r3j,r4j,r5j,r6j,r7j,r8j,r9j,r10j,r11j,r1n,r2n,r3n,r4n,r1m,r2m,r3m,
//            r4m,r5m,r6m,r7m,rbh1,rbh2,rbh3,rbh4,rbh5,r1b,r2b,r3b,r4b,r5b,r6b,r7b,r8b,r9b,r10b,r11b,r12b,
//            r1s,r2s,r3s,r4s,r5s,r6s,r1l,r2l,r3l,r4l,r5l,rsrd1,rsrd2,rsrd3,rbdl1,rbdl2,rodf1,rodf2,
//            rssp1,rssp2,rssp3,risp,rmdk,rgjl,rmdcl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        r1j = findViewById(R.id.r1j);
//        r2j = findViewById(R.id.r2j);
//        r3j = findViewById(R.id.r3j);
//        r4j = findViewById(R.id.r4j);
//        r5j = findViewById(R.id.r5j);
//        r6j = findViewById(R.id.r6j);
//        r7j = findViewById(R.id.r7j);
//        r8j = findViewById(R.id.r8j);
//        r9j = findViewById(R.id.r9j);
//        r10j = findViewById(R.id.r10j);
//        r11j = findViewById(R.id.r11j);
//
//        r1n = findViewById(R.id.r1n);
//        r2n = findViewById(R.id.r2n);
//        r3n = findViewById(R.id.r3n);
//        r4n = findViewById(R.id.r4n);
//
//        r1m = findViewById(R.id.r1m);
//        r2m = findViewById(R.id.r2m);
//        r3m = findViewById(R.id.r3m);
//        r4m = findViewById(R.id.r4m);
//        r5m = findViewById(R.id.r5m);
//        r6m = findViewById(R.id.r6m);
//        r7m = findViewById(R.id.r7m);
//
//        rbh1 = findViewById(R.id.rbh1);
//        rbh2 = findViewById(R.id.rbh2);
//        rbh3 = findViewById(R.id.rbh3);
//        rbh4 = findViewById(R.id.rbh4);
//        rbh5 = findViewById(R.id.rbh5);
//
//        r1b = findViewById(R.id.r1b);
//        r2b = findViewById(R.id.r2b);
//        r3b = findViewById(R.id.r3b);
//        r4b = findViewById(R.id.r4b);
//        r5b = findViewById(R.id.r5b);
//        r6b = findViewById(R.id.r6b);
//        r7b = findViewById(R.id.r7b);
//        r8b = findViewById(R.id.r8b);
//        r9b = findViewById(R.id.r9b);
//        r10b = findViewById(R.id.r10b);
//        r11b = findViewById(R.id.r11b);
//        r12b = findViewById(R.id.r12b);
//
//        r1s = findViewById(R.id.r1s);
//        r2s = findViewById(R.id.r2s);
//        r3s = findViewById(R.id.r3s);
//        r4s = findViewById(R.id.r4s);
//        r5s = findViewById(R.id.r5s);
//        r6s = findViewById(R.id.r6s);
//
//        r1l = findViewById(R.id.r1l);
//        r2l = findViewById(R.id.r2l);
//        r3l = findViewById(R.id.r3l);
//        r4l = findViewById(R.id.r4l);
//        r5l = findViewById(R.id.r5l);
//
//
//        rsrd1 = findViewById(R.id.rsrd1);
//        rsrd2 = findViewById(R.id.rsrd2);
//        rsrd3 = findViewById(R.id.rsrd3);
//
//        rbdl1 = findViewById(R.id.rbdl1);
//        rbdl2 = findViewById(R.id.rbdl2);
//
//        rodf1 = findViewById(R.id.rodf1);
//        rodf2 = findViewById(R.id.rodf2);
//
//        rssp1 = findViewById(R.id.rssp1);
//        rssp2 = findViewById(R.id.rssp2);
//        rssp3 = findViewById(R.id.rssp3);
//
//        risp = findViewById(R.id.risp);
//
//        rmdcl = findViewById(R.id.rmdcl);
//
//        rgjl = findViewById(R.id.rgjl);
//
//        rmdk = findViewById(R.id.rmdk);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Home.this,MainActivity.class));
            Toast.makeText(getApplicationContext(),"You have been logged out successfully",Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }
        if (id == R.id.action_delete) {
            FirebaseAuth.getInstance().getCurrentUser().delete();
            startActivity(new Intent(Home.this,MainActivity.class));
            Toast.makeText(getApplicationContext(),"You account has been deleted successfully",Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }
        if (id  == R.id.action_exit){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.requestLayout();
        drawer.bringToFront();

        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(Home.this, ShowMyProfile.class));
        }
        if (id == R.id.nav_report) {
            // Handle the camera action
            startActivity(new Intent(Home.this, ReportBug.class));
        }
        if (id == R.id.nav_exit) {
            // Handle the camera action
            finish();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.r1j) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR1J");
            startActivity(intent);
        }
        if (view.getId() == R.id.r2j) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR2J");
            startActivity(intent);
        }

        if (view.getId() == R.id.r3j) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR3J");
            startActivity(intent);
        }
        if (view.getId() == R.id.r4j) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR4J");
            startActivity(intent);
        }
        if (view.getId() == R.id.r5j) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR5J");
            startActivity(intent);
        }
        if (view.getId() == R.id.r6j) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR6J");
            startActivity(intent);
        }
        if (view.getId() == R.id.r7j) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR7J");
            startActivity(intent);
        }
        if (view.getId() == R.id.r8j) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR8J");
            startActivity(intent);
        }
        if (view.getId() == R.id.r9j) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR9J");
            startActivity(intent);
        }
        if (view.getId() == R.id.r10j) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR10J");
            startActivity(intent);
        }
        if (view.getId() == R.id.r11j) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR11J");
            startActivity(intent);
        }
        if (view.getId() == R.id.r1n) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR1N");
            startActivity(intent);
        }
        if (view.getId() == R.id.r2n) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR2N");
            startActivity(intent);
        }
        if (view.getId() == R.id.r3n) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR3N");
            startActivity(intent);
        }
        if (view.getId() == R.id.r4n) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR4N");
            startActivity(intent);
        }
        if (view.getId() == R.id.r1m) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR1M");
            startActivity(intent);
        }
        if (view.getId() == R.id.r2m) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR2M");
            startActivity(intent);
        }
        if (view.getId() == R.id.r3m) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR3M");
            startActivity(intent);
        }
        if (view.getId() == R.id.r4m) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR4M");
            startActivity(intent);
        }
        if (view.getId() == R.id.r5m) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR5M");
            startActivity(intent);
        }
        if (view.getId() == R.id.r6m) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR6M");
            startActivity(intent);
        }
        if (view.getId() == R.id.r7m) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR7M");
            startActivity(intent);
        }
        if (view.getId() == R.id.rbh1) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVRBH1");
            startActivity(intent);
        }
        if (view.getId() == R.id.rbh2) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVRBH2");
            startActivity(intent);
        }
        if (view.getId() == R.id.rbh3) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVRBH3");
            startActivity(intent);
        }
        if (view.getId() == R.id.rbh4) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVRBH4");
            startActivity(intent);
        }
        if (view.getId() == R.id.rbh5) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVRBH5");
            startActivity(intent);
        }

        if (view.getId() == R.id.r1b) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR1B");
            startActivity(intent);
        }
        if (view.getId() == R.id.r2b) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR2B");
            startActivity(intent);
        }

        if (view.getId() == R.id.r3b) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR3B");
            startActivity(intent);
        }

        if (view.getId() == R.id.r4b) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR4B");
            startActivity(intent);
        }

        if (view.getId() == R.id.r5b) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR5B");
            startActivity(intent);
        }

        if (view.getId() == R.id.r6b) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR6B");
            startActivity(intent);
        }

        if (view.getId() == R.id.r7b) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR7B");
            startActivity(intent);
        }

        if (view.getId() == R.id.r8b) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR8B");
            startActivity(intent);
        }
        if (view.getId() == R.id.r9b) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR9B");
            startActivity(intent);
        }
        if (view.getId() == R.id.r10b) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR10B");
            startActivity(intent);
        }
        if (view.getId() == R.id.r11b) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR11B");
            startActivity(intent);
        }
        if (view.getId() == R.id.r12b) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR12B");
            startActivity(intent);
        }
        if (view.getId() == R.id.r1s) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR1S");
            startActivity(intent);
        }
        if (view.getId() == R.id.r2s) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR2S");
            startActivity(intent);
        }
        if (view.getId() == R.id.r3s) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR3S");
            startActivity(intent);
        }
        if (view.getId() == R.id.r4s) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR4S");
            startActivity(intent);
        }
        if (view.getId() == R.id.r5s) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR5S");
            startActivity(intent);
        }
        if (view.getId() == R.id.r6s) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR6S");
            startActivity(intent);
        }
        if (view.getId() == R.id.r1l) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR1L");
            startActivity(intent);
        }
        if (view.getId() == R.id.r2l) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR2L");
            startActivity(intent);
        }
        if (view.getId() == R.id.r3l) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR3L");
            startActivity(intent);
        }
        if (view.getId() == R.id.r4l) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR4L");
            startActivity(intent);
        }
        if (view.getId() == R.id.r5l) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVR5L");
            startActivity(intent);
        }

        if (view.getId() == R.id.rsrd1) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVRSRD1");
            startActivity(intent);
        }

        if (view.getId() == R.id.rsrd2) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVRSRD2");
            startActivity(intent);
        }

        if (view.getId() == R.id.rsrd3) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVRSRD3");
            startActivity(intent);
        }
        if (view.getId() == R.id.rbdl1) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVRBDL1");
            startActivity(intent);
        }

        if (view.getId() == R.id.rodf1) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVRODF1");
            startActivity(intent);
        }
        if (view.getId() == R.id.rodf2) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVRODF2");
            startActivity(intent);
        }
        if (view.getId() == R.id.rssp1) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVRSSP1");
            startActivity(intent);
        }
        if (view.getId() == R.id.rssp2) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVRSSP2");
            startActivity(intent);
        }
        if (view.getId() == R.id.rssp3) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVRSSP3");
            startActivity(intent);
        }
        if (view.getId() == R.id.risp) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVRISP");
            startActivity(intent);
        }
        if (view.getId() == R.id.rmdk) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVRMDK");
            startActivity(intent);
        }
        if (view.getId() == R.id.rgjl) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVRGJL");
            startActivity(intent);
        }
        if (view.getId() == R.id.rmdcl) {
            Intent intent = new Intent(Home.this, MapsActivity.class);
            intent.putExtra("code", "BVRMDCL");
            startActivity(intent);
        }

    }
}
