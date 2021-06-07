package com.viksingh.covid19tracker.old;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.viksingh.covid19tracker.R;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

public class StateDetail extends AppCompatActivity {

    private TextView confirmed;
    private TextView dead;
    private TextView forign;
    private TextView indian;
    private BarChart mBarChart;
    private int position;
    private TextView recovered;
    private TextView state;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_detail);
        this.position = getIntent().getIntExtra("position", 0);
        getSupportActionBar().setTitle((CharSequence) AffectedStates.stateList.get(this.position).getLoc());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.state = (TextView) findViewById(R.id.state);
        this.indian = (TextView) findViewById(R.id.indian);
        this.forign = (TextView) findViewById(R.id.forign);
        this.confirmed = (TextView) findViewById(R.id.confirmed);
        this.dead = (TextView) findViewById(R.id.death);
        this.recovered = (TextView) findViewById(R.id.Recovered);
        this.indian = (TextView) findViewById(R.id.indian);
        this.forign = (TextView) findViewById(R.id.forign);
        this.mBarChart = (BarChart) findViewById(R.id.barchart);

        this.state.setText(AffectedStates.stateList.get(this.position).getLoc());
        this.indian.setText(AffectedStates.stateList.get(this.position).getConfirmedCasesIndian());
        this.forign.setText(AffectedStates.stateList.get(this.position).getConfirmedCasesForeign());
        this.confirmed.setText(AffectedStates.stateList.get(this.position).getTotalConfirmed());
        this.dead.setText(AffectedStates.stateList.get(this.position).getDeaths());
        this.recovered.setText(AffectedStates.stateList.get(this.position).getDischarged());
        int activeCases = Integer.parseInt(this.confirmed.getText().toString()) - Integer.parseInt(this.recovered.getText().toString());
        this.mBarChart.addBar(new BarModel("Confirmed", (float) Integer.parseInt(this.confirmed.getText().toString()), Color.parseColor("#FFA500")));
        this.mBarChart.addBar(new BarModel("Recovered", (float) Integer.parseInt(this.recovered.getText().toString()), Color.parseColor("#008000")));
        this.mBarChart.addBar(new BarModel("Deaths", (float) Integer.parseInt(this.dead.getText().toString()), Color.parseColor("#FF0000")));
        this.mBarChart.addBar(new BarModel("Active", (float) activeCases, Color.parseColor("#00BFFF")));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}