package com.viksingh.covid19tracker.updated;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.viksingh.covid19tracker.R;
import com.viksingh.covid19tracker.util.CommonUtil;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SessionActivity extends AppCompatActivity {

    public Dashboard activity = new Dashboard();

    private TextView total,govt,pvt,today,last_update;
    private PieChart pieChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle((CharSequence) "Vaccination Sessions");
        Init();
        fetchData();
    }

    private void fetchData() {
        activity.ShowDialog(this);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String today=format.format(new Date());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(new JsonObjectRequest(0, String.format("https://api.cowin.gov.in/api/v1/reports/v2/getPublicReports?state_id=&district_id=&date=%s",today), (JSONObject) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object = response.getJSONObject("topBlock").getJSONObject("sessions");
                    String total = object.get("total").toString();
                    String govt = object.get("govt").toString();
                    String pvt = object.get("pvt").toString();
                    String today = object.get("today").toString();
                    if(today==null || today.equals(null) || today.contains("null")){
                        today="0";
                    }
                    String last_update=response.get("timestamp").toString();
                    String finalToday = today;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            SessionActivity.this.total.setText(CommonUtil.getFormatedNumber(total));
                            SessionActivity.this.govt.setText(CommonUtil.getFormatedNumber(govt));
                            SessionActivity.this.pvt.setText(CommonUtil.getFormatedNumber(pvt));
                            SessionActivity.this.today.setText(finalToday);
                            SessionActivity.this.last_update.setText(last_update);
                            pieChart.addPieSlice(new PieModel("Total", (float) Integer.parseInt(total), Color.parseColor("#007afe")));
                            pieChart.addPieSlice(new PieModel("Govt", (float) Integer.parseInt(govt), Color.parseColor("#08a045")));
                            pieChart.addPieSlice(new PieModel("Pvt", (float) Integer.parseInt(pvt), Color.parseColor("#F6404F")));
                            pieChart.startAnimation();
                            activity.DismissDialog();
                        }
                    },2000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }));
    }

    private void Init() {
        total=findViewById(R.id.total_session);
        govt=findViewById(R.id.govt_session);
        pvt=findViewById(R.id.pvt_session);
        today=findViewById(R.id.today_session);
        last_update=findViewById(R.id.last_update);
        pieChart =findViewById(R.id.session_piechart);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}