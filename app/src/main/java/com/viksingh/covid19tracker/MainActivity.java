package com.viksingh.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.viksingh.covid19tracker.old.CountryData;
import com.viksingh.covid19tracker.updated.Dashboard;
import com.viksingh.covid19tracker.updated.VaccinationDashboard;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toast backPressToast;
    private boolean doubleBackToExitPressedOnce = false;
    private static final int REQUEST_PHONE_CALL = 1;
    ImageView flags;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ImageView menu_icon;
    Spinner spinner;
    Button stat;
    private Button call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        spinner = findViewById(R.id.spinner);
        menu_icon=findViewById(R.id.menu);

        flags = findViewById(R.id.flag);
        stat = findViewById(R.id.stat);
        call=findViewById(R.id.call);


        /*------------------------------Tool Bar---------------------------------------------*/

        navigationDrawer();

        /*------------------------------Navigation Bar---------------------------------------------*/


        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                CountryData.countryNames));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                flags.setImageResource(CountryData.countryFlag[spinner.getSelectedItemPosition()]);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, Dashboard.class);
                startActivity(i);

            }
        });

        call.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        });
    }

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayout.isDrawerVisible(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    public void makePhoneCall(){

        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
        }else {
            Intent callIntent =new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:104"));
            startActivity(callIntent);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_PHONE_CALL){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }else {
                Toast.makeText(this,"Permission DENIED",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        Intent intent;
        int id=item.getItemId();
        if(id==R.id.nav_home){
            intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        if(id==R.id.nav_stats){
            intent=new Intent(this,Dashboard.class);
            startActivity(intent);
        }
        if(id==R.id.nav_vaccine){
            intent=new Intent(this, VaccinationDashboard.class);
            startActivity(intent);
        }
        return  true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void onBackPressed() {
        if (this.doubleBackToExitPressedOnce) {
            this.backPressToast.cancel();
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast makeText = Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT);
        this.backPressToast = makeText;
        makeText.show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                boolean unused = MainActivity.this.doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
