package com.viksingh.covid19tracker.updated;

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
import com.viksingh.covid19tracker.adapter.DistrictWiseAdapter;
import com.viksingh.covid19tracker.model.DistrictWiseModel;
import com.viksingh.covid19tracker.util.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DistrictWiseDataActivity extends AppCompatActivity {
    /* access modifiers changed from: private */
    public Dashboard activity = new Dashboard();
    /* access modifiers changed from: private */
    public DistrictWiseAdapter districtWiseAdapter;
    /* access modifiers changed from: private */
    public ArrayList<DistrictWiseModel> districtWiseModelArrayList;
    private EditText et_search;
    private RecyclerView rv_district_wise;
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
    /* access modifiers changed from: private */
    public String str_district;
    /* access modifiers changed from: private */
    public String str_recovered;
    /* access modifiers changed from: private */
    public String str_recovered_new;
    /* access modifiers changed from: private */
    public String str_state_name;
    /* access modifiers changed from: private */
    public SwipeRefreshLayout swipeRefreshLayout;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_district_wise_data);
        GetIntent();
        getSupportActionBar().setTitle((CharSequence) "Region/District");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Init();
        FetchDistrictWiseData();
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                DistrictWiseDataActivity.this.FetchDistrictWiseData();
                DistrictWiseDataActivity.this.swipeRefreshLayout.setRefreshing(false);
            }
        });
        this.et_search.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                DistrictWiseDataActivity.this.Filter(s.toString());
            }
        });
    }

    /* access modifiers changed from: private */
    public void Filter(String s) {
        ArrayList<DistrictWiseModel> filteredList = new ArrayList<>();
        Iterator<DistrictWiseModel> it = this.districtWiseModelArrayList.iterator();
        while (it.hasNext()) {
            DistrictWiseModel item = it.next();
            if (item.getDistrict().toLowerCase().contains(s.toLowerCase())) {
                filteredList.add(item);
            }
        }
        this.districtWiseAdapter.filterList(filteredList, s);
    }

    /* access modifiers changed from: private */
    public void FetchDistrictWiseData() {
        this.activity.ShowDialog(this);
        Volley.newRequestQueue(this).add(new JsonArrayRequest(0, "https://api.covid19india.org/v2/state_district_wise.json", (JSONArray) null, new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                int flag = 0;
                DistrictWiseDataActivity.this.districtWiseModelArrayList.clear();
                int i = 1;
                while (true) {
                    if (i >= response.length()) {
                        JSONArray jSONArray = response;
                        break;
                    }
                    try {
                        JSONObject jsonObjectState = response.getJSONObject(i);
                        if (DistrictWiseDataActivity.this.str_state_name.toLowerCase().equals(jsonObjectState.getString("state").toLowerCase())) {
                            JSONArray jsonArrayDistrict = jsonObjectState.getJSONArray("districtData");
                            for (int j = 0; j < jsonArrayDistrict.length(); j++) {
                                JSONObject jsonObjectDistrict = jsonArrayDistrict.getJSONObject(j);
                                str_district = jsonObjectDistrict.getString("district");
                                str_confirmed = jsonObjectDistrict.getString("confirmed");
                                str_active = jsonObjectDistrict.getString("active");
                                str_death = jsonObjectDistrict.getString("deceased");
                                str_recovered = jsonObjectDistrict.getString(Constants.COUNTRY_RECOVERED);
                                JSONObject jsonObjectDistNew = jsonObjectDistrict.getJSONObject("delta");
                                str_confirmed_new = jsonObjectDistNew.getString("confirmed");
                                str_recovered_new = jsonObjectDistNew.getString(Constants.COUNTRY_RECOVERED);
                                str_death_new = jsonObjectDistNew.getString("deceased");
                                DistrictWiseDataActivity.this.districtWiseModelArrayList.add(new DistrictWiseModel(DistrictWiseDataActivity.this.str_district, DistrictWiseDataActivity.this.str_confirmed, DistrictWiseDataActivity.this.str_active, DistrictWiseDataActivity.this.str_recovered, DistrictWiseDataActivity.this.str_death, DistrictWiseDataActivity.this.str_confirmed_new, DistrictWiseDataActivity.this.str_recovered_new, DistrictWiseDataActivity.this.str_death_new));
                            }
                            flag = 1;
                        }
                        if (flag == 1) {
                            break;
                        }
                        i++;
                    } catch (JSONException e) {
                        e = e;
                        e.printStackTrace();
                    }
                }
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        DistrictWiseDataActivity.this.districtWiseAdapter.notifyDataSetChanged();
                        DistrictWiseDataActivity.this.activity.DismissDialog();
                    }
                }, 1000);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }));
    }

    private void Init() {
        this.rv_district_wise = (RecyclerView) findViewById(R.id.activity_district_wise_recyclerview);
        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_district_wise_swipe_refresh_layout);
        this.et_search = (EditText) findViewById(R.id.activity_district_wise_search_editText);
        this.rv_district_wise.setHasFixedSize(true);
        this.rv_district_wise.setLayoutManager(new LinearLayoutManager(this));
        this.districtWiseModelArrayList = new ArrayList<>();
        DistrictWiseAdapter districtWiseAdapter2 = new DistrictWiseAdapter(this, this.districtWiseModelArrayList);
        this.districtWiseAdapter = districtWiseAdapter2;
        this.rv_district_wise.setAdapter(districtWiseAdapter2);
    }

    private void GetIntent() {
        this.str_state_name = getIntent().getStringExtra(Constants.STATE_NAME);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
