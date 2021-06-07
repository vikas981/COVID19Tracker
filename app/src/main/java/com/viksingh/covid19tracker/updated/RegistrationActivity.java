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

public class RegistrationActivity extends AppCompatActivity {
    public Dashboard activity = new Dashboard();

    private TextView total,from_18_to_45,above_45,today_reg,last_update;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle((CharSequence) "Registration");
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
                    JSONObject object = response.getJSONObject("topBlock").getJSONObject("registration");
                    String total = object.get("total").toString();
                    String from_18_to_45 = object.get("cit_18_45").toString();
                    String above_45 = object.get("cit_45_above").toString();
                    String today_reg = object.get("today").toString();
                    if(today_reg==null || today_reg.equals(null) || today_reg.contains("null")){
                        today_reg="0";
                    }
                    String last_update=response.get("timestamp").toString();
                    String finalToday = today_reg;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            RegistrationActivity.this.total.setText(CommonUtil.getFormatedNumber(total));
                            RegistrationActivity.this.from_18_to_45.setText(CommonUtil.getFormatedNumber(from_18_to_45));
                            RegistrationActivity.this.above_45.setText(CommonUtil.getFormatedNumber(above_45));
                            RegistrationActivity.this.today_reg.setText(CommonUtil.getFormatedNumber(finalToday));
                            RegistrationActivity.this.last_update.setText(last_update);
                            pieChart.addPieSlice(new PieModel("Total", (float) Integer.parseInt(total), Color.parseColor("#007afe")));
                            pieChart.addPieSlice(new PieModel("18-45", (float) Integer.parseInt(from_18_to_45), Color.parseColor("#08a045")));
                            pieChart.addPieSlice(new PieModel("45 Above", (float) Integer.parseInt(above_45), Color.parseColor("#F6404F")));
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
        total=findViewById(R.id.total_reg);
        from_18_to_45=findViewById(R.id.from_18_to_45);
        above_45=findViewById(R.id.above_45);
        today_reg=findViewById(R.id.today_reg);
        last_update=findViewById(R.id.last_update);
        pieChart =findViewById(R.id.reg_piechart);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}