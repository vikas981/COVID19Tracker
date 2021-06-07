package com.viksingh.covid19tracker.updated;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.viksingh.covid19tracker.old.CountryDetail;
import com.viksingh.covid19tracker.R;
import com.viksingh.covid19tracker.util.CommonUtil;
import com.viksingh.covid19tracker.util.Constants;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

public class WorldData extends AppCompatActivity {
    public Dashboard activity = new Dashboard();
    public int int_active_new;
    LinearLayout lin_countrywise;
    PieChart pieChart;
    String str_active;
    String str_confirmed;
    String str_confirmed_new;
    String str_death;
    String str_death_new;
    String str_recovered;
    String str_recovered_new;
    String str_tests;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView tv_active;
    TextView tv_active_new;
    TextView tv_confirmed;
    TextView tv_confirmed_new;
    TextView tv_death;
    TextView tv_death_new;
    TextView tv_recovered;
    TextView tv_recovered_new;
    TextView tv_tests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle((CharSequence) "Covid-19 Tracker (World)");
        Init();
        FetchWorldData();
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                WorldData.this.FetchWorldData();
                WorldData.this.swipeRefreshLayout.setRefreshing(false);
            }
        });
        this.lin_countrywise.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WorldData.this.startActivity(new Intent(WorldData.this, CountryWiseDataActivity.class));
            }
        });
    }

    private void Init() {
        this.tv_confirmed = (TextView) findViewById(R.id.activity_world_data_confirmed_textView);
        this.tv_confirmed_new = (TextView) findViewById(R.id.activity_world_data_confirmed_new_textView);
        this.tv_active = (TextView) findViewById(R.id.activity_world_data_active_textView);
        this.tv_active_new = (TextView) findViewById(R.id.activity_world_data_active_new_textView);
        this.tv_recovered = (TextView) findViewById(R.id.activity_world_data_recovered_textView);
        this.tv_recovered_new = (TextView) findViewById(R.id.activity_world_data_recovered_new_textView);
        this.tv_death = (TextView) findViewById(R.id.activity_world_data_death_textView);
        this.tv_death_new = (TextView) findViewById(R.id.activity_world_data_death_new_textView);
        this.tv_tests = (TextView) findViewById(R.id.activity_world_data_tests_textView);
        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_world_data_swipe_refresh_layout);
        this.pieChart = (PieChart) findViewById(R.id.activity_world_data_piechart);
        this.lin_countrywise = (LinearLayout) findViewById(R.id.activity_world_data_countrywise_lin);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void FetchWorldData() {
        activity.ShowDialog(this);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        this.pieChart.clearChart();
        requestQueue.add(new JsonObjectRequest(0, "https://corona.lmao.ninja/v2/all", (JSONObject) null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                try {
                    str_confirmed = response.getString(Constants.COUNTRY_CONFIRMED);
                    str_confirmed_new = response.getString(Constants.COUNTRY_NEW_CONFIRMED);
                    str_active = response.getString("active");
                    str_recovered = response.getString(Constants.COUNTRY_RECOVERED);
                    str_recovered_new = response.getString("todayRecovered");
                    str_death = response.getString(Constants.COUNTRY_DECEASED);
                    str_death_new = response.getString(Constants.COUNTRY_NEW_DECEASED);
                    str_tests = response.getString(Constants.COUNTRY_TESTS);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            tv_confirmed.setText(NumberFormat.getInstance().format((long) Integer.parseInt(WorldData.this.str_confirmed)));
                            tv_confirmed_new.setText("+" + NumberFormat.getInstance().format((long) Integer.parseInt(WorldData.this.str_confirmed_new)));
                            tv_active.setText(NumberFormat.getInstance().format((long) Integer.parseInt(WorldData.this.str_active)));
                            int_active_new = Integer.parseInt(WorldData.this.str_confirmed_new) - (Integer.parseInt(WorldData.this.str_recovered_new) + Integer.parseInt(WorldData.this.str_death_new));
                            tv_active_new.setText("+" + NumberFormat.getInstance().format((long)int_active_new).replace("-",""));
                            tv_recovered.setText(NumberFormat.getInstance().format((long) Integer.parseInt(WorldData.this.str_recovered)));
                            tv_recovered_new.setText("+" + NumberFormat.getInstance().format((long) Integer.parseInt(WorldData.this.str_recovered_new)));
                            tv_death.setText(NumberFormat.getInstance().format((long) Integer.parseInt(WorldData.this.str_death)));
                            tv_death_new.setText("+" + NumberFormat.getInstance().format((long) Integer.parseInt(WorldData.this.str_death_new)));
                            System.out.println("Test :"+str_tests);
                            if(str_tests.equals(null) || str_tests.isEmpty()){
                                tv_tests.setText("0");
                            }else{
                                tv_tests.setText(CommonUtil.getFormatedNumber(str_tests));
                            }
                            pieChart.addPieSlice(new PieModel("Active", (float) Integer.parseInt(WorldData.this.str_active), Color.parseColor("#007afe")));
                            pieChart.addPieSlice(new PieModel("Recovered", (float) Integer.parseInt(WorldData.this.str_recovered), Color.parseColor("#08a045")));
                            pieChart.addPieSlice(new PieModel("Deceased", (float) Integer.parseInt(WorldData.this.str_death), Color.parseColor("#F6404F")));
                            pieChart.startAnimation();
                            activity.DismissDialog();
                        }
                    }, 1000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
    }
}