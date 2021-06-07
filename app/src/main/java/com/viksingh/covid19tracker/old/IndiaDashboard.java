package com.viksingh.covid19tracker.old;

import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;
import com.viksingh.covid19tracker.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class IndiaDashboard extends AppCompatActivity {

    private TextView active;
    private TextView foreign;
    private TextView indian;
    private PieChart pieChart;
    private TextView recovered;
    private ScrollView scrollView;
    private SimpleArcLoader simpleArcLoader;
    private TextView totalCases;
    private TextView totalDeaths;
    private TextView unIdentified;
    private TextView update;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_india_dashboard);

        getSupportActionBar().setTitle((CharSequence) "India");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.totalCases = (TextView) findViewById(R.id.Cases);
        this.indian = (TextView) findViewById(R.id.indian);
        this.foreign = (TextView) findViewById(R.id.foreign);
        this.update = (TextView) findViewById(R.id.updated);
        this.recovered = (TextView) findViewById(R.id.recovered);
        this.totalDeaths = (TextView) findViewById(R.id.totalDeaths);
        this.unIdentified = (TextView) findViewById(R.id.unidentified);
        this.active = (TextView) findViewById(R.id.active);
        this.simpleArcLoader = (SimpleArcLoader) findViewById(R.id.loader);
        this.scrollView = (ScrollView) findViewById(R.id.scrollStats);
        this.pieChart = (PieChart) findViewById(R.id.piechart);
        fetchData();
    }

    private void fetchData() {
        this.simpleArcLoader.start();
        Volley.newRequestQueue(this).add(new StringRequest(0, "https://api.rootnet.in/covid19-in/stats/latest", new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString()).getJSONObject("data").getJSONObject("summary");
                    JSONObject object = new JSONObject(response.toString());
                    IndiaDashboard.this.totalCases.setText(jsonObject.getString("total"));
                    IndiaDashboard.this.indian.setText(jsonObject.getString("confirmedCasesIndian"));
                    IndiaDashboard.this.foreign.setText(jsonObject.getString("confirmedCasesForeign"));
                    if (object.getString("lastRefreshed").length() > 10) {
                        String[] date = object.getString("lastRefreshed").split(ExifInterface.GPS_DIRECTION_TRUE);
                        for (int i = 0; i < date.length; i++) {
                            System.out.println(date[i] + " " + i);
                        }
                        IndiaDashboard.this.update.setText(date[0]);
                    } else {
                        IndiaDashboard.this.update.setText(object.getString("lastRefreshed"));
                    }
                    IndiaDashboard.this.recovered.setText(jsonObject.getString("discharged"));
                    int activeCases = Integer.parseInt(IndiaDashboard.this.totalCases.getText().toString()) - Integer.parseInt(IndiaDashboard.this.recovered.getText().toString());
                    IndiaDashboard.this.active.setText(String.valueOf(activeCases));
                    IndiaDashboard.this.totalDeaths.setText(jsonObject.getString("deaths"));
                    IndiaDashboard.this.unIdentified.setText(jsonObject.getString("confirmedButLocationUnidentified"));
                    IndiaDashboard.this.pieChart.addPieSlice(new PieModel("Cases", (float) Integer.parseInt(IndiaDashboard.this.totalCases.getText().toString()), Color.parseColor("#FFA500")));
                    IndiaDashboard.this.pieChart.addPieSlice(new PieModel("Recovered", (float) Integer.parseInt(IndiaDashboard.this.recovered.getText().toString()), Color.parseColor("#008000")));
                    IndiaDashboard.this.pieChart.addPieSlice(new PieModel("Deaths", (float) Integer.parseInt(IndiaDashboard.this.totalDeaths.getText().toString()), Color.parseColor("#FF0000")));
                    IndiaDashboard.this.pieChart.addPieSlice(new PieModel("Active Cases", (float) activeCases, Color.parseColor("#00BFFF")));
                    IndiaDashboard.this.pieChart.startAnimation();
                    IndiaDashboard.this.simpleArcLoader.stop();
                    IndiaDashboard.this.simpleArcLoader.setVisibility(View.GONE);
                    IndiaDashboard.this.scrollView.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    IndiaDashboard.this.simpleArcLoader.stop();
                    IndiaDashboard.this.simpleArcLoader.stop();
                    IndiaDashboard.this.simpleArcLoader.setVisibility(View.GONE);
                    IndiaDashboard.this.scrollView.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                IndiaDashboard.this.simpleArcLoader.stop();
                IndiaDashboard.this.simpleArcLoader.stop();
                IndiaDashboard.this.simpleArcLoader.setVisibility(View.GONE);
                IndiaDashboard.this.scrollView.setVisibility(View.INVISIBLE);
                Toast.makeText(IndiaDashboard.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}