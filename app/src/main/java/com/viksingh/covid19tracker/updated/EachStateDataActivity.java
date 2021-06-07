package com.viksingh.covid19tracker.updated;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.viksingh.covid19tracker.R;
import com.viksingh.covid19tracker.util.Constants;

import java.text.NumberFormat;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class EachStateDataActivity extends AppCompatActivity {
    public Dashboard activity = new Dashboard();
    private LinearLayout lin_district;
    private PieChart pieChart;
    private String str_active;
    private String str_active_new;
    private String str_confirmed;
    private String str_confirmed_new;
    private String str_death;
    private String str_death_new;
    private String str_lastupdatedate;
    private String str_recovered;
    private String str_recovered_new;
    private String str_stateName;
    private TextView tv_active;
    private TextView tv_active_new;
    private TextView tv_confirmed;
    private TextView tv_confirmed_new;
    private TextView tv_death;
    private TextView tv_death_new;
    private TextView tv_dist;
    private TextView tv_lastupdatedate;
    private TextView tv_recovered;
    private TextView tv_recovered_new;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_each_state_data);
        GetIntent();
        getSupportActionBar().setTitle((CharSequence) this.str_stateName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Init();
        LoadStateData();
        this.lin_district.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(EachStateDataActivity.this, DistrictWiseDataActivity.class);
                intent.putExtra(Constants.STATE_NAME, EachStateDataActivity.this.str_stateName);
                EachStateDataActivity.this.startActivity(intent);
            }
        });
    }

    private void LoadStateData() {
        this.activity.ShowDialog(this);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                EachStateDataActivity.this.tv_confirmed.setText(NumberFormat.getInstance().format((long) Integer.parseInt(EachStateDataActivity.this.str_confirmed)));
                TextView access$400 = EachStateDataActivity.this.tv_confirmed_new;
                access$400.setText("+" + NumberFormat.getInstance().format((long) Integer.parseInt(EachStateDataActivity.this.str_confirmed_new)));
                EachStateDataActivity.this.tv_active.setText(NumberFormat.getInstance().format((long) Integer.parseInt(EachStateDataActivity.this.str_active)));
                int int_active_new = Integer.parseInt(EachStateDataActivity.this.str_confirmed_new) - (Integer.parseInt(EachStateDataActivity.this.str_recovered_new) + Integer.parseInt(EachStateDataActivity.this.str_death_new));
                TextView access$900 = EachStateDataActivity.this.tv_active_new;
                StringBuilder sb = new StringBuilder();
                sb.append("+");
                sb.append(NumberFormat.getInstance().format(int_active_new < 0 ? 0 : (long) int_active_new));
                access$900.setText(sb.toString());
                EachStateDataActivity.this.tv_death.setText(NumberFormat.getInstance().format((long) Integer.parseInt(EachStateDataActivity.this.str_death)));
                TextView access$1200 = EachStateDataActivity.this.tv_death_new;
                access$1200.setText("+" + NumberFormat.getInstance().format((long) Integer.parseInt(EachStateDataActivity.this.str_death_new)));
                EachStateDataActivity.this.tv_recovered.setText(NumberFormat.getInstance().format((long) Integer.parseInt(EachStateDataActivity.this.str_recovered)));
                TextView access$1500 = EachStateDataActivity.this.tv_recovered_new;
                access$1500.setText("+" + NumberFormat.getInstance().format((long) Integer.parseInt(EachStateDataActivity.this.str_recovered_new)));
                EachStateDataActivity.this.tv_lastupdatedate.setText(EachStateDataActivity.this.activity.FormatDate(EachStateDataActivity.this.str_lastupdatedate, 0));
                TextView access$1900 = EachStateDataActivity.this.tv_dist;
                access$1900.setText("District data of " + EachStateDataActivity.this.str_stateName);
                EachStateDataActivity.this.pieChart.addPieSlice(new PieModel("Active", (float) Integer.parseInt(EachStateDataActivity.this.str_active), Color.parseColor("#007afe")));
                EachStateDataActivity.this.pieChart.addPieSlice(new PieModel("Recovered", (float) Integer.parseInt(EachStateDataActivity.this.str_recovered), Color.parseColor("#08a045")));
                EachStateDataActivity.this.pieChart.addPieSlice(new PieModel("Deceased", (float) Integer.parseInt(EachStateDataActivity.this.str_death), Color.parseColor("#F6404F")));
                EachStateDataActivity.this.pieChart.startAnimation();
                EachStateDataActivity.this.activity.DismissDialog();
            }
        }, 1000);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void Init() {
        this.tv_confirmed = (TextView) findViewById(R.id.activity_each_state_confirmed_textView);
        this.tv_confirmed_new = (TextView) findViewById(R.id.activity_each_state_confirmed_new_textView);
        this.tv_active = (TextView) findViewById(R.id.activity_each_state_active_textView);
        this.tv_active_new = (TextView) findViewById(R.id.activity_each_state_active_new_textView);
        this.tv_recovered = (TextView) findViewById(R.id.activity_each_state_recovered_textView);
        this.tv_recovered_new = (TextView) findViewById(R.id.activity_each_state_recovered_new_textView);
        this.tv_death = (TextView) findViewById(R.id.activity_each_state_death_textView);
        this.tv_death_new = (TextView) findViewById(R.id.activity_each_state_death_new_textView);
        this.tv_lastupdatedate = (TextView) findViewById(R.id.activity_each_state_lastupdate_textView);
        this.tv_dist = (TextView) findViewById(R.id.activity_each_state_district_data_title);
        this.pieChart = (PieChart) findViewById(R.id.activity_each_state_piechart);
        this.lin_district = (LinearLayout) findViewById(R.id.activity_each_state_lin);
    }

    private void GetIntent() {
        Intent intent = getIntent();
        this.str_stateName = intent.getStringExtra(Constants.STATE_NAME);
        this.str_confirmed = intent.getStringExtra(Constants.STATE_CONFIRMED);
        this.str_confirmed_new = intent.getStringExtra(Constants.STATE_CONFIRMED_NEW);
        this.str_active = intent.getStringExtra(Constants.STATE_ACTIVE);
        this.str_death = intent.getStringExtra(Constants.STATE_DEATH);
        this.str_death_new = intent.getStringExtra(Constants.STATE_DEATH_NEW);
        this.str_recovered = intent.getStringExtra(Constants.STATE_RECOVERED);
        this.str_recovered_new = intent.getStringExtra(Constants.STATE_RECOVERED_NEW);
        this.str_lastupdatedate = intent.getStringExtra(Constants.STATE_LAST_UPDATE);
    }
}
