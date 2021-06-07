package com.viksingh.covid19tracker.old;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.viksingh.covid19tracker.R;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;

public class CountryDetail extends AppCompatActivity {

    private TextView Active;
    private TextView Critical;
    private TextView Recovered;
    private TextView TodayCases;
    private TextView TodayDeaths;
    private TextView TotalDeaths;
    private TextView cases;
    private TextView country;
    private BarChart mBarChart;
    private PieChart pieChart;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);
        setContentView((int) R.layout.activity_country_detail);
        getSupportActionBar().setTitle((CharSequence) "Country Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.country = (TextView) findViewById(R.id.Country);
        this.cases = (TextView) findViewById(R.id.Cases);
        this.TodayCases = (TextView) findViewById(R.id.TodayCases);
        this.TotalDeaths = (TextView) findViewById(R.id.TotalDeaths);
        this.TodayDeaths = (TextView) findViewById(R.id.TodayDeaths);
        this.Recovered = (TextView) findViewById(R.id.Recovered);
        this.Active = (TextView) findViewById(R.id.Active);
        this.Critical = (TextView) findViewById(R.id.Critical);
        this.mBarChart = (BarChart) findViewById(R.id.barchart);
        this.position = getIntent().getIntExtra("position", 0);
        this.country.setText(AffectedCountries.countryModelList.get(this.position).getCountry());
        this.cases.setText(AffectedCountries.countryModelList.get(this.position).getCases());
        this.TodayCases.setText(AffectedCountries.countryModelList.get(this.position).getTodayCases());
        this.TotalDeaths.setText(AffectedCountries.countryModelList.get(this.position).getDeaths());
        this.TodayDeaths.setText(AffectedCountries.countryModelList.get(this.position).getTodayDeaths());
        this.Recovered.setText(AffectedCountries.countryModelList.get(this.position).getRecovered());
        this.Active.setText(AffectedCountries.countryModelList.get(this.position).getActive());
        this.Critical.setText(AffectedCountries.countryModelList.get(this.position).getCritical());
        this.mBarChart.addBar(new BarModel("Cases", (float) Integer.parseInt(this.cases.getText().toString()), Color.parseColor("#FFA500")));
        this.mBarChart.addBar(new BarModel("Recovered", (float) Integer.parseInt(this.Recovered.getText().toString()), Color.parseColor("#008000")));
        this.mBarChart.addBar(new BarModel("Deaths", (float) Integer.parseInt(this.TotalDeaths.getText().toString()), Color.parseColor("#FF0000")));
        this.mBarChart.addBar(new BarModel("Active Cases", (float) Integer.parseInt(this.Active.getText().toString()), Color.parseColor("#00BFFF")));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}