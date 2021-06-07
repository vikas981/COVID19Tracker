package com.viksingh.covid19tracker.updated;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.viksingh.covid19tracker.R;
import com.viksingh.covid19tracker.util.CommonUtil;
import com.viksingh.covid19tracker.util.DateUtil;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VaccinationActivity extends AppCompatActivity {
    public Dashboard activity = new Dashboard();
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView total_vaccination,update_time;
    private TextView today_vaccination;
    private TextView male;
    private ImageView back;
    private TextView female;
    private TextView others;
    private TextView covishield;
    private TextView covaxin;
    private TextView tot_dose_1;
    private TextView tot_dose_2;
    private TextView total_doses;
    private TextView aefi;
    private TextView today_dose_one;
    private TextView today_dose_two;
    private TextView today_male;
    private TextView today_female;
    private TextView today_others;
    private TextView today_aefi;
    private TextView today_doses;
    private TextView last_update;
    private PieChart piechart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination);
        Init();
        fetchData();
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                VaccinationActivity.this.fetchData();
                VaccinationActivity.this.swipeRefreshLayout.setRefreshing(false);

            }
        });
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                    JSONObject object = response.getJSONObject("topBlock").getJSONObject("vaccination");
                    int total = Integer.parseInt(object.get("total").toString());
                    int male = Integer.parseInt(object.get("male").toString());
                    int female = Integer.parseInt(object.get("female").toString());
                    int others = Integer.parseInt(object.get("others").toString());
                    int covishield = Integer.parseInt(object.get("covishield").toString());
                    int covaxin = Integer.parseInt(object.get("covaxin").toString());
                    int today = Integer.parseInt(object.get("today").toString());
                    int tot_dose_1 = Integer.parseInt(object.get("tot_dose_1").toString());
                    int tot_dose_2 = Integer.parseInt(object.get("tot_dose_2").toString());
                    int total_doses = Integer.parseInt(object.get("total_doses").toString());
                    int aefi = Integer.parseInt(object.get("aefi").toString());
                    int today_dose_one = Integer.parseInt(object.get("today_dose_one").toString());
                    int today_dose_two = Integer.parseInt(object.get("today_dose_two").toString());
                    int today_male = Integer.parseInt(object.get("today_male").toString());
                    int today_female = Integer.parseInt(object.get("today_female").toString());
                    int today_others = Integer.parseInt(object.get("today_others").toString());
                    int today_aefi = Integer.parseInt(object.get("today_aefi").toString());


                    String last_update=response.get("timestamp").toString();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setText(last_update);
                            VaccinationActivity.this.total_vaccination.setText(CommonUtil.getFormatedNumber(total));
                            VaccinationActivity.this.male.setText(CommonUtil.getFormatedNumber(male));
                            VaccinationActivity.this.female.setText(CommonUtil.getFormatedNumber(female));
                            VaccinationActivity.this.others.setText(CommonUtil.getFormatedNumber(others));
                            VaccinationActivity.this.covishield.setText(CommonUtil.getFormatedNumber(covishield));
                            VaccinationActivity.this.covaxin.setText(CommonUtil.getFormatedNumber(covaxin));
                            VaccinationActivity.this.today_vaccination.setText("+ "+CommonUtil.getFormatedNumber(today));
                            VaccinationActivity.this.today_doses.setText("+ "+CommonUtil.getFormatedNumber(today));
                            VaccinationActivity.this.others.setText(CommonUtil.getFormatedNumber(others));
                            VaccinationActivity.this.total_vaccination.setText(CommonUtil.getFormatedNumber(total));
                            VaccinationActivity.this.tot_dose_1.setText(CommonUtil.getFormatedNumber(tot_dose_1));
                            VaccinationActivity.this.tot_dose_2.setText(CommonUtil.getFormatedNumber(tot_dose_2));
                            VaccinationActivity.this.total_doses.setText(CommonUtil.getFormatedNumber(total_doses));
                            VaccinationActivity.this.aefi.setText(CommonUtil.getFormatedNumber(aefi));
                            VaccinationActivity.this.today_dose_one.setText("+ "+CommonUtil.getFormatedNumber(today_dose_one));
                            VaccinationActivity.this.today_dose_two.setText("+ "+CommonUtil.getFormatedNumber(today_dose_two));
                            VaccinationActivity.this.today_male.setText("+ "+CommonUtil.getFormatedNumber(today_male));
                            VaccinationActivity.this.today_female.setText("+ "+CommonUtil.getFormatedNumber(today_female));
                            VaccinationActivity.this.today_others.setText("+ "+CommonUtil.getFormatedNumber(today_others));
                            VaccinationActivity.this.today_aefi.setText("+ "+CommonUtil.getFormatedNumber(today_aefi));
                            piechart.clearChart();
                            piechart.addPieSlice(new PieModel("Total", (float) total, getResources().getColor(R.color.colorPrimary)));
                            piechart.addPieSlice(new PieModel("Male", (float) male, getResources().getColor(R.color.orange_red)));
                            piechart.addPieSlice(new PieModel("Female", (float) female, getResources().getColor(R.color.pink)));
                            piechart.addPieSlice(new PieModel("others", (float) female, getResources().getColor(R.color.lime_green)));
                            piechart.startAnimation();
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


    public void setText(String updated){
        String month= DateUtil.getMonthName(updated.split("-")[1]);
        String date=updated.split("-")[2].split(" ")[0];
        String year=updated.split("-")[0];

        this.update_time.setText(String.format("Update at %s %s,%s",date,month,year));
    }


    private void Init() {
        swipeRefreshLayout=findViewById(R.id.swipe_refresh_layout);
        total_vaccination=findViewById(R.id.total_vaccination);
        today_vaccination=findViewById(R.id.today_vaccination);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        others=findViewById(R.id.others);
        covishield=findViewById(R.id.covishield);
        covaxin=findViewById(R.id.covaxin);
        tot_dose_1=findViewById(R.id.tot_dose_1);
        tot_dose_2=findViewById(R.id.tot_dose_2);
        total_doses=findViewById(R.id.total_doses);
        aefi=findViewById(R.id.aefi);
        today_dose_one=findViewById(R.id.today_dose_one);
        today_dose_two=findViewById(R.id.today_dose_two);
        today_male=findViewById(R.id.today_male);
        today_female=findViewById(R.id.today_female);
        today_others=findViewById(R.id.today_others);
        today_aefi=findViewById(R.id.today_aefi);
        today_doses=findViewById(R.id.today_doses);
        last_update=findViewById(R.id.last_update);
        piechart =findViewById(R.id.piechart_vaccine);
        back=findViewById(R.id.back);
        update_time=findViewById(R.id.updated);
    }


}