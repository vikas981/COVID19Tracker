package com.viksingh.covid19tracker.updated;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.viksingh.covid19tracker.R;

public class VaccinationDashboard extends AppCompatActivity {

    private LinearLayout sites,sessions,vaccination,registration;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_dashboard);

        Init();

        this.sites.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VaccinationDashboard.this.startActivity(new Intent(VaccinationDashboard.this, SitesActivity.class));
            }
        });
        this.sessions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VaccinationDashboard.this.startActivity(new Intent(VaccinationDashboard.this, SessionActivity.class));
            }
        });

        this.vaccination.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VaccinationDashboard.this.startActivity(new Intent(VaccinationDashboard.this, VaccinationActivity.class));
            }
        });
        this.registration.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VaccinationDashboard.this.startActivity(new Intent(VaccinationDashboard.this, RegistrationActivity.class));
            }
        });

        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void Init(){
        sites=findViewById(R.id.sites);
        sessions=findViewById(R.id.sessions);
        vaccination=findViewById(R.id.vaccination);
        registration=findViewById(R.id.registration);
        backButton=findViewById(R.id.back);
    }


}