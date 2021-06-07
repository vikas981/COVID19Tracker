package com.viksingh.covid19tracker.updated;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.viksingh.covid19tracker.R;
import com.viksingh.covid19tracker.util.Constants;

import java.text.NumberFormat;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class EachDistrictDataActivity extends AppCompatActivity {
    /* access modifiers changed from: private */
    public Dashboard activity = new Dashboard();
    /* access modifiers changed from: private */
    public PieChart pieChart;
    /* access modifiers changed from: private */
    public String str_active;
    private String str_active_new;
    /* access modifiers changed from: private */
    public String str_confirmed;
    /* access modifiers changed from: private */
    public String str_confirmed_new;
    /* access modifiers changed from: private */
    public String str_death;
    /* access modifiers changed from: private */
    public String str_death_new;
    private String str_districtName;
    /* access modifiers changed from: private */
    public String str_recovered;
    /* access modifiers changed from: private */
    public String str_recovered_new;
    /* access modifiers changed from: private */
    public TextView tv_active;
    /* access modifiers changed from: private */
    public TextView tv_active_new;
    /* access modifiers changed from: private */
    public TextView tv_confirmed;
    /* access modifiers changed from: private */
    public TextView tv_confirmed_new;
    /* access modifiers changed from: private */
    public TextView tv_death;
    /* access modifiers changed from: private */
    public TextView tv_death_new;
    /* access modifiers changed from: private */
    public TextView tv_recovered;
    /* access modifiers changed from: private */
    public TextView tv_recovered_new;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_each_district_data);
        GetIntent();
        getSupportActionBar().setTitle((CharSequence) this.str_districtName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Init();
        LoadDistrictData();
    }

    private void LoadDistrictData() {
        this.activity.ShowDialog(this);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                EachDistrictDataActivity.this.tv_confirmed.setText(NumberFormat.getInstance().format((long) Integer.parseInt(EachDistrictDataActivity.this.str_confirmed)));
                TextView access$300 = EachDistrictDataActivity.this.tv_confirmed_new;
                access$300.setText("+" + NumberFormat.getInstance().format((long) Integer.parseInt(EachDistrictDataActivity.this.str_confirmed_new)));
                EachDistrictDataActivity.this.tv_active.setText(NumberFormat.getInstance().format((long) Integer.parseInt(EachDistrictDataActivity.this.str_active)));
                int int_active_new = Integer.parseInt(EachDistrictDataActivity.this.str_confirmed_new) - (Integer.parseInt(EachDistrictDataActivity.this.str_recovered_new) + Integer.parseInt(EachDistrictDataActivity.this.str_death_new));
                TextView access$800 = EachDistrictDataActivity.this.tv_active_new;
                StringBuilder sb = new StringBuilder();
                sb.append("+");
                sb.append(NumberFormat.getInstance().format(int_active_new < 0 ? 0 : (long) int_active_new));
                access$800.setText(sb.toString());
                EachDistrictDataActivity.this.tv_death.setText(NumberFormat.getInstance().format((long) Integer.parseInt(EachDistrictDataActivity.this.str_death)));
                TextView access$1100 = EachDistrictDataActivity.this.tv_death_new;
                access$1100.setText("+" + NumberFormat.getInstance().format((long) Integer.parseInt(EachDistrictDataActivity.this.str_death_new)));
                EachDistrictDataActivity.this.tv_recovered.setText(NumberFormat.getInstance().format((long) Integer.parseInt(EachDistrictDataActivity.this.str_recovered)));
                TextView access$1400 = EachDistrictDataActivity.this.tv_recovered_new;
                access$1400.setText("+" + NumberFormat.getInstance().format((long) Integer.parseInt(EachDistrictDataActivity.this.str_recovered_new)));
                EachDistrictDataActivity.this.pieChart.addPieSlice(new PieModel("Active", (float) Integer.parseInt(EachDistrictDataActivity.this.str_active), Color.parseColor("#007afe")));
                EachDistrictDataActivity.this.pieChart.addPieSlice(new PieModel("Recovered", (float) Integer.parseInt(EachDistrictDataActivity.this.str_recovered), Color.parseColor("#08a045")));
                EachDistrictDataActivity.this.pieChart.addPieSlice(new PieModel("Deceased", (float) Integer.parseInt(EachDistrictDataActivity.this.str_death), Color.parseColor("#F6404F")));
                EachDistrictDataActivity.this.pieChart.startAnimation();
                EachDistrictDataActivity.this.activity.DismissDialog();
            }
        }, 1000);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void GetIntent() {
        Intent intent = getIntent();
        this.str_districtName = intent.getStringExtra(Constants.DISTRICT_NAME);
        this.str_confirmed = intent.getStringExtra(Constants.DISTRICT_CONFIRMED);
        this.str_confirmed_new = intent.getStringExtra(Constants.DISTRICT_CONFIRMED_NEW);
        this.str_active = intent.getStringExtra(Constants.DISTRICT_ACTIVE);
        this.str_death = intent.getStringExtra(Constants.DISTRICT_DEATH);
        this.str_death_new = intent.getStringExtra(Constants.DISTRICT_DEATH_NEW);
        this.str_recovered = intent.getStringExtra(Constants.DISTRICT_RECOVERED);
        this.str_recovered_new = intent.getStringExtra(Constants.DISTRICT_RECOVERED_NEW);
    }

    private void Init() {
        this.tv_confirmed = (TextView) findViewById(R.id.activity_each_district_confirmed_textView);
        this.tv_confirmed_new = (TextView) findViewById(R.id.activity_each_district_confirmed_new_textView);
        this.tv_active = (TextView) findViewById(R.id.activity_each_district_active_textView);
        this.tv_active_new = (TextView) findViewById(R.id.activity_each_district_active_new_textView);
        this.tv_recovered = (TextView) findViewById(R.id.activity_each_district_recovered_textView);
        this.tv_recovered_new = (TextView) findViewById(R.id.activity_each_district_recovered_new_textView);
        this.tv_death = (TextView) findViewById(R.id.activity_each_district_death_textView);
        this.tv_death_new = (TextView) findViewById(R.id.activity_each_district_death_new_textView);
        this.pieChart = (PieChart) findViewById(R.id.activity_each_district_piechart);
    }
}
