package com.viksingh.covid19tracker.old;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.leo.simplearcloader.SimpleArcLoader;
import com.viksingh.covid19tracker.About;
import com.viksingh.covid19tracker.R;
import com.viksingh.covid19tracker.util.CommonUtil;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class GlobalDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView Active;
    private TextView Affected;
    private TextView Cases;
    private TextView Critical;
    private TextView Recovered;
    private TextView TodayCases;
    private TextView TodayDeaths;
    private TextView TotalDeaths;
    private PieChart pieChart;
    private ScrollView scrollView;
    private SimpleArcLoader simpleArcLoader;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBar;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_dashboard);

        drawerLayout = (DrawerLayout) findViewById(R.id.root);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(actionBar);
        actionBar.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        navigationView.bringToFront();
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home_world);

        this.Cases = (TextView) findViewById(R.id.Cases);
        this.Recovered = (TextView) findViewById(R.id.Recovered);
        this.Critical = (TextView) findViewById(R.id.Critical);
        this.Active = (TextView) findViewById(R.id.Active);
        this.TodayCases = (TextView) findViewById(R.id.TodayCases);
        this.TotalDeaths = (TextView) findViewById(R.id.TotalDeaths);
        this.TodayDeaths = (TextView) findViewById(R.id.TodayDeaths);
        this.Affected = (TextView) findViewById(R.id.Affected);
        this.simpleArcLoader = (SimpleArcLoader) findViewById(R.id.loader);
        this.scrollView = (ScrollView) findViewById(R.id.scrollStats);
        this.pieChart = (PieChart) findViewById(R.id.piechart);
        fetchData();
    }

    /* access modifiers changed from: private */
    public void fetchData() {
        this.simpleArcLoader.start();
        Volley.newRequestQueue(this).add(new StringRequest(0, "https://corona.lmao.ninja/v2/all/", new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    Cases.setText(CommonUtil.getFormatedNumber(jsonObject.getString("cases")));
                    Recovered.setText(CommonUtil.getFormatedNumber(jsonObject.getString("recovered")));
                    Critical.setText(CommonUtil.getFormatedNumber(jsonObject.getString("critical")));
                    Active.setText(CommonUtil.getFormatedNumber(jsonObject.getString("active")));
                    TodayCases.setText(CommonUtil.getFormatedNumber(jsonObject.getString("todayCases")));
                    TotalDeaths.setText(CommonUtil.getFormatedNumber(jsonObject.getString("deaths")));
                    TodayDeaths.setText(CommonUtil.getFormatedNumber(jsonObject.getString("todayDeaths")));
                    Affected.setText(CommonUtil.getFormatedNumber(jsonObject.getString("affectedCountries")));
                    pieChart.startAnimation();
                    pieChart.addPieSlice(new PieModel("Cases", (float) Integer.parseInt(jsonObject.getString("cases")), Color.parseColor("#FFA500")));
                    pieChart.addPieSlice(new PieModel("Recovered", (float) Integer.parseInt(jsonObject.getString("recovered")), Color.parseColor("#008000")));
                    pieChart.addPieSlice(new PieModel("Deaths", (float) Integer.parseInt(jsonObject.getString("deaths")), Color.parseColor("#FF0000")));
                    pieChart.addPieSlice(new PieModel("Active Cases", (float) Integer.parseInt(jsonObject.getString("active")), Color.parseColor("#00BFFF")));
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.INVISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.INVISIBLE);
                Toast.makeText(GlobalDashboard.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void refresh(int milliseconds) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                fetchData();
            }
        }, (long) milliseconds);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        Intent intent;
        if (id==R.id.nav_home_world){
            intent = new Intent(this, GlobalDashboard.class);
            startActivity(intent);
        }

        else if(id==R.id.nav_honme_india){
            intent=new Intent(this,IndiaDashboard.class);
            startActivity(intent);
        }
        else if(id==R.id.nav_world){
            intent=new Intent(this,AffectedCountries.class);
            startActivity(intent);
        }
        else if(id==R.id.nav_india){
            intent=new Intent(this,AffectedStates.class);
            startActivity(intent);
        }
        else if(id==R.id.nav_about){
            intent=new Intent(this, About.class);
            startActivity(intent);
        }
        else if(id==R.id.nav_share){
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,"I suggest this app for you : https://i.diawi.com/gDuR6s");
            startActivity(Intent.createChooser(sharingIntent, "Share app via"));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return this.actionBar.onOptionsItemSelected(item) ? true : super.onOptionsItemSelected(item);
    }
}