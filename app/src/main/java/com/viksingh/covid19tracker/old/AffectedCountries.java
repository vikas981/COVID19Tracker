package com.viksingh.covid19tracker.old;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;
import com.viksingh.covid19tracker.R;
import com.viksingh.covid19tracker.adapter.CountryAdapter;
import com.viksingh.covid19tracker.model.Country;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AffectedCountries extends AppCompatActivity {
    public static List<Country> countryModelList = new ArrayList();
    protected CountryAdapter countryAdapter;
    protected Country country;
    protected ListView listView;
    private EditText search;
    protected SimpleArcLoader simpleArcLoader;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(countryModelList.size()>0){
            Toast.makeText(AffectedCountries.this,"Clearing Previous Data", Toast.LENGTH_SHORT).show();
          countryModelList.clear();
        }
        setContentView(R.layout.activity_affected_countries);
        search = (EditText) findViewById(R.id.search_bar);
        listView = (ListView) findViewById(R.id.listMode);
        simpleArcLoader = (SimpleArcLoader) findViewById(R.id.loader);
        getSupportActionBar().setTitle((CharSequence) "Country List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        fetchData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                AffectedCountries affectedCountries = AffectedCountries.this;
                affectedCountries.startActivity(new Intent(affectedCountries.getApplicationContext(), CountryDetail.class).putExtra("position", position));
            }
        });
        this.search.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                countryAdapter.getFilter().filter(s);
                countryAdapter.notifyDataSetChanged();
            }

            public void afterTextChanged(Editable s) {

            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {
        simpleArcLoader.start();
        Volley.newRequestQueue(this).add(new StringRequest(0, "https://corona.lmao.ninja/v2/countries/", new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    System.out.println(jsonArray.length());
                    int i = 0;
                    while (i<jsonArray.length()) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String countryName = jsonObject.getString("country");
                        String cases = jsonObject.getString("cases");
                        String todayCases = jsonObject.getString("todayCases");
                        String deaths = jsonObject.getString("deaths");
                        String todayDeaths = jsonObject.getString("todayDeaths");
                        String recovered = jsonObject.getString("recovered");
                        String active = jsonObject.getString("active");
                        String critical = jsonObject.getString("critical");
                        String flagUrl = jsonObject.getJSONObject("countryInfo").getString("flag");
                        country = new Country(active,cases,countryName,critical,deaths,flagUrl,recovered,todayCases,todayDeaths);
                        AffectedCountries.countryModelList.add(country);
                        i++;
                    }

                    countryAdapter = new CountryAdapter(AffectedCountries.this, AffectedCountries.countryModelList);
                    listView.setAdapter(countryAdapter);
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                } catch (JSONException e) {
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                AffectedCountries.this.simpleArcLoader.stop();
                AffectedCountries.this.simpleArcLoader.setVisibility(View.GONE);
                Toast.makeText(AffectedCountries.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }));
    }
}