package com.viksingh.covid19tracker.updated;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.viksingh.covid19tracker.R;
import com.viksingh.covid19tracker.old.StateDetail;
import com.viksingh.covid19tracker.util.CommonUtil;
import com.viksingh.covid19tracker.util.Constants;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Dashboard extends AppCompatActivity {

    public int int_active_new;
    public LinearLayout lin_state_data;
    public LinearLayout lin_world_data;
    public PieChart pieChart;
    public ProgressDialog progressDialog;
    public String str_active;
    public String str_active_new;
    public String str_confirmed;
    public String str_confirmed_new;
    public String str_death;
    public String str_death_new;
    public String str_last_update_time;
    public String str_recovered;
    public String str_recovered_new;
    public String str_tests;
    public String str_tests_new;
    public SwipeRefreshLayout swipeRefreshLayout;
    public TextView tv_active;
    public TextView tv_active_new;
    public TextView tv_confirmed;
    public TextView tv_confirmed_new;
    public TextView tv_date;
    public TextView tv_death;
    public TextView tv_death_new;
    public TextView tv_recovered;
    public TextView tv_recovered_new;
    public TextView tv_tests;
    public TextView tv_tests_new;
    public TextView tv_time;
    public String version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#473F97"));
        getSupportActionBar().setTitle((CharSequence) "Covid-19 Tracker (India)");
        Init();
        FetchData();
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                Dashboard.this.FetchData();
                Dashboard.this.swipeRefreshLayout.setRefreshing(false);
            }
        });
        this.lin_state_data.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Dashboard.this.startActivity(new Intent(Dashboard.this, StateWiseDataActivity.class));
            }
        });
        this.lin_world_data.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Dashboard.this.startActivity(new Intent(Dashboard.this, WorldData.class));
            }
        });
    }


    public void FetchData() {
        ShowDialog(this);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        this.pieChart.clearChart();
        requestQueue.add(new JsonObjectRequest(0, "https://api.covid19india.org/data.json", (JSONObject) null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                try {
                    JSONArray all_state_jsonArray = response.getJSONArray("statewise");
                    JSONArray testData_jsonArray = response.getJSONArray("tested");
                    JSONObject data_india = all_state_jsonArray.getJSONObject(0);
                    JSONObject test_data_india = testData_jsonArray.getJSONObject(testData_jsonArray.length() - 1);
                    str_confirmed = data_india.getString("confirmed");
                    str_confirmed_new = data_india.getString("deltaconfirmed");
                    str_active = data_india.getString("active");
                    str_recovered = data_india.getString(Constants.COUNTRY_RECOVERED);
                    str_recovered_new = data_india.getString("deltarecovered");
                    str_death = data_india.getString(Constants.COUNTRY_DECEASED);
                    str_death_new = data_india.getString("deltadeaths");
                    str_last_update_time = data_india.getString("lastupdatedtime");
                    str_tests = test_data_india.getString("totalsamplestested");
                    str_tests_new = test_data_india.getString("samplereportedtoday");

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            tv_confirmed.setText(NumberFormat.getInstance().format((long) Integer.parseInt(str_confirmed)));
                            tv_confirmed_new.setText("+" + NumberFormat.getInstance().format((long) Integer.parseInt(str_confirmed_new)));
                            tv_active.setText(NumberFormat.getInstance().format((long) Integer.parseInt(str_active)));
                            int_active_new = Integer.parseInt(str_confirmed_new) - (Integer.parseInt(str_recovered_new) + Integer.parseInt(str_death_new));
                            tv_active_new.setText("+" + NumberFormat.getInstance().format((long) int_active_new));
                            tv_recovered.setText(NumberFormat.getInstance().format((long) Integer.parseInt(str_recovered)));
                            tv_recovered_new.setText("+" + NumberFormat.getInstance().format((long) Integer.parseInt(str_recovered_new)));
                            tv_death.setText(NumberFormat.getInstance().format((long) Integer.parseInt(str_death)));
                            tv_death_new.setText("+" + NumberFormat.getInstance().format((long) Integer.parseInt(str_death_new)));

                            if(str_tests.equals(null) || str_tests.isEmpty()){
                                tv_tests.setText("0");
                            }else{
                                tv_tests.setText(NumberFormat.getInstance().format((long) Integer.parseInt(str_tests)));
                            }

                            if(str_tests_new.equals(null) || str_tests_new.isEmpty()){
                                tv_tests_new.setText("+0");
                            }else{
                                tv_tests_new.setText("+" + CommonUtil.getFormatedNumber(str_tests_new));
                            }

                            tv_date.setText(FormatDate(str_last_update_time, 1));
                            tv_time.setText(FormatDate(str_last_update_time, 2));
                            pieChart.addPieSlice(new PieModel("Active", (float) Integer.parseInt(str_active), Color.parseColor("#007afe")));
                            pieChart.addPieSlice(new PieModel("Recovered", (float) Integer.parseInt(str_recovered), Color.parseColor("#08a045")));
                            pieChart.addPieSlice(new PieModel("Deceased", (float) Integer.parseInt(str_death), Color.parseColor("#F6404F")));
                            pieChart.startAnimation();
                            DismissDialog();
                        }
                    }, 1000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }));
    }

    public void ShowDialog(Context context) {
        ProgressDialog progress = new ProgressDialog(context);
        this.progressDialog = progress;
        progress.show();
        this.progressDialog.setContentView(R.layout.progress_dialog);
        this.progressDialog.setCanceledOnTouchOutside(false);
        this.progressDialog.getWindow().setBackgroundDrawableResource(R.color.cardview_shadow_end_color);

    }

    public void DismissDialog() {
        this.progressDialog.dismiss();
    }

    public String FormatDate(String date, int testCase) {
        try {
            Date mDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US).parse(date);
            if (testCase == 0) {
                return new SimpleDateFormat("dd MMM yyyy, hh:mm a").format(mDate);
            }
            if (testCase == 1) {
                return new SimpleDateFormat("dd MMM yyyy").format(mDate);
            }
            if (testCase == 2) {
                return new SimpleDateFormat("hh:mm a").format(mDate);
            }
            Log.d("error", "Wrong input! Choose from 0 to 2");
            return "Error";
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    private void Init() {
        this.tv_confirmed = (TextView) findViewById(R.id.activity_main_confirmed_textview);
        this.tv_confirmed_new = (TextView) findViewById(R.id.activity_main_confirmed_new_textview);
        this.tv_active = (TextView) findViewById(R.id.activity_main_active_textview);
        this.tv_active_new = (TextView) findViewById(R.id.activity_main_active_new_textview);
        this.tv_recovered = (TextView) findViewById(R.id.activity_main_recovered_textview);
        this.tv_recovered_new = (TextView) findViewById(R.id.activity_main_recovered_new_textview);
        this.tv_death = (TextView) findViewById(R.id.activity_main_death_textview);
        this.tv_death_new = (TextView) findViewById(R.id.activity_main_death_new_textview);
        this.tv_tests = (TextView) findViewById(R.id.activity_main_samples_textview);
        this.tv_tests_new = (TextView) findViewById(R.id.activity_main_samples_new_textview);
        this.tv_date = (TextView) findViewById(R.id.activity_main_date_textview);
        this.tv_time = (TextView) findViewById(R.id.activity_main_time_textview);
        this.pieChart = (PieChart) findViewById(R.id.activity_main_piechart);
        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        this.lin_state_data = (LinearLayout) findViewById(R.id.activity_main_statewise_lin);
        this.lin_world_data = (LinearLayout) findViewById(R.id.activity_main_world_data_lin);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}