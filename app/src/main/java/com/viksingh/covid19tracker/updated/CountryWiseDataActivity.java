package com.viksingh.covid19tracker.updated;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.viksingh.covid19tracker.R;
import com.viksingh.covid19tracker.adapter.CountryWiseAdapter;
import com.viksingh.covid19tracker.model.CountryWiseModel;
import com.viksingh.covid19tracker.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CountryWiseDataActivity extends AppCompatActivity {
    /* access modifiers changed from: private */
    public Dashboard activity = new Dashboard();
    /* access modifiers changed from: private */
    public CountryWiseAdapter countryWiseAdapter;
    /* access modifiers changed from: private */
    public ArrayList<CountryWiseModel> countryWiseModelArrayList;
    private EditText et_search;
    private RecyclerView rv_country_wise;
    /* access modifiers changed from: private */
    public String str_active;
    private String str_active_new;
    /* access modifiers changed from: private */
    public String str_confirmed;
    /* access modifiers changed from: private */
    public String str_confirmed_new;
    /* access modifiers changed from: private */
    public String str_country;
    /* access modifiers changed from: private */
    public String str_death;
    /* access modifiers changed from: private */
    public String str_death_new;
    /* access modifiers changed from: private */
    public String str_recovered;
    private String str_recovered_new;
    /* access modifiers changed from: private */
    public String str_tests;
    /* access modifiers changed from: private */
    public SwipeRefreshLayout swipeRefreshLayout;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_country_wise_data);
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#473F97"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle((CharSequence) "World Data (Select Country)");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Init();
        FetchCountryWiseData();
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                CountryWiseDataActivity.this.FetchCountryWiseData();
                CountryWiseDataActivity.this.swipeRefreshLayout.setRefreshing(false);
            }
        });
        this.et_search.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                CountryWiseDataActivity.this.Filter(s.toString());
            }
        });
    }

    /* access modifiers changed from: private */
    public void Filter(String text) {
        ArrayList<CountryWiseModel> filteredList = new ArrayList<>();
        Iterator<CountryWiseModel> it = this.countryWiseModelArrayList.iterator();
        while (it.hasNext()) {
            CountryWiseModel item = it.next();
            if (item.getCountry().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        this.countryWiseAdapter.filterList(filteredList, text);
    }

    /* access modifiers changed from: private */
    public void FetchCountryWiseData() {
        this.activity.ShowDialog(this);
        Volley.newRequestQueue(this).add(new JsonArrayRequest(0, "https://corona.lmao.ninja/v2/countries", (JSONArray) null, new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                try {
                    CountryWiseDataActivity.this.countryWiseModelArrayList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject countryJSONObject = response.getJSONObject(i);
                        str_country = countryJSONObject.getString(Constants.COUNTRY_NAME);
                        str_confirmed = countryJSONObject.getString(Constants.COUNTRY_CONFIRMED);
                        str_confirmed_new = countryJSONObject.getString(Constants.COUNTRY_NEW_CONFIRMED);
                        str_active = countryJSONObject.getString("active");
                        str_recovered = countryJSONObject.getString(Constants.COUNTRY_RECOVERED);
                        str_death = countryJSONObject.getString(Constants.COUNTRY_DECEASED);
                        str_death_new = countryJSONObject.getString(Constants.COUNTRY_NEW_DECEASED);
                        str_tests = countryJSONObject.getString(Constants.COUNTRY_TESTS);
                        countryWiseModelArrayList.add(new CountryWiseModel(CountryWiseDataActivity.this.str_country, CountryWiseDataActivity.this.str_confirmed, CountryWiseDataActivity.this.str_confirmed_new, CountryWiseDataActivity.this.str_active, CountryWiseDataActivity.this.str_death, CountryWiseDataActivity.this.str_death_new, CountryWiseDataActivity.this.str_recovered, CountryWiseDataActivity.this.str_tests, countryJSONObject.getJSONObject("countryInfo").getString(Constants.COUNTRY_FLAGURL)));
                    }
                    Collections.sort(CountryWiseDataActivity.this.countryWiseModelArrayList, new Comparator<CountryWiseModel>() {
                        public int compare(CountryWiseModel o1, CountryWiseModel o2) {
                            if (Integer.parseInt(o1.getConfirmed()) > Integer.parseInt(o2.getConfirmed())) {
                                return -1;
                            }
                            return 1;
                        }
                    });
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            CountryWiseDataActivity.this.countryWiseAdapter.notifyDataSetChanged();
                            CountryWiseDataActivity.this.activity.DismissDialog();
                        }
                    }, 1000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
    }

    private void Init() {
        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_country_wise_swipe_refresh_layout);
        this.et_search = (EditText) findViewById(R.id.activity_country_wise_search_editText);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_country_wise_recyclerview);
        this.rv_country_wise = recyclerView;
        recyclerView.setHasFixedSize(true);
        this.rv_country_wise.setLayoutManager(new LinearLayoutManager(this));
        this.countryWiseModelArrayList = new ArrayList<>();
        CountryWiseAdapter countryWiseAdapter2 = new CountryWiseAdapter(this, this.countryWiseModelArrayList);
        this.countryWiseAdapter = countryWiseAdapter2;
        this.rv_country_wise.setAdapter(countryWiseAdapter2);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
