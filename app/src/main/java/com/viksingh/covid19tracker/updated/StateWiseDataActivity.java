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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.viksingh.covid19tracker.R;
import com.viksingh.covid19tracker.adapter.StateWiseAdapter;
import com.viksingh.covid19tracker.model.StateWiseModel;
import com.viksingh.covid19tracker.util.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StateWiseDataActivity extends AppCompatActivity {
    /* access modifiers changed from: private */
    public Dashboard activity = new Dashboard();
    private EditText et_search;
    private RecyclerView rv_state_wise;
    /* access modifiers changed from: private */
    public StateWiseAdapter stateWiseAdapter;
    /* access modifiers changed from: private */
    public ArrayList<StateWiseModel> stateWiseModelArrayList;
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
    public String str_lastupdatedate;
    /* access modifiers changed from: private */
    public String str_recovered;
    /* access modifiers changed from: private */
    public String str_recovered_new;
    /* access modifiers changed from: private */
    public String str_state;
    /* access modifiers changed from: private */
    public SwipeRefreshLayout swipeRefreshLayout;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_state_wise_data);
        getSupportActionBar().setTitle((CharSequence) "Select State");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Init();
        FetchStateWiseData();
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                StateWiseDataActivity.this.FetchStateWiseData();
                StateWiseDataActivity.this.swipeRefreshLayout.setRefreshing(false);
            }
        });
        this.et_search.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                StateWiseDataActivity.this.Filter(s.toString());
            }
        });
    }

    /* access modifiers changed from: private */
    public void Filter(String text) {
        ArrayList<StateWiseModel> filteredList = new ArrayList<>();
        Iterator<StateWiseModel> it = this.stateWiseModelArrayList.iterator();
        while (it.hasNext()) {
            StateWiseModel item = it.next();
            if (item.getState().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        this.stateWiseAdapter.filterList(filteredList, text);
    }

    /* access modifiers changed from: private */
    public void FetchStateWiseData() {
        this.activity.ShowDialog(this);
        Volley.newRequestQueue(this).add(new JsonObjectRequest(0, "https://api.covid19india.org/data.json", (JSONObject) null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("statewise");
                    StateWiseDataActivity.this.stateWiseModelArrayList.clear();
                    for (int i = 1; i < jsonArray.length(); i++) {
                        JSONObject statewise = jsonArray.getJSONObject(i);
                        str_state = statewise.getString("state");
                        str_confirmed = statewise.getString("confirmed");
                        str_confirmed_new = statewise.getString("deltaconfirmed");
                        str_active = statewise.getString("active");
                        str_death = statewise.getString(Constants.COUNTRY_DECEASED);
                        str_death_new = statewise.getString("deltadeaths");
                        str_recovered = statewise.getString(Constants.COUNTRY_RECOVERED);
                        str_recovered_new = statewise.getString("deltarecovered");
                        str_lastupdatedate = statewise.getString("lastupdatedtime");
                        StateWiseDataActivity.this.stateWiseModelArrayList.add(new StateWiseModel(StateWiseDataActivity.this.str_state, StateWiseDataActivity.this.str_confirmed, StateWiseDataActivity.this.str_confirmed_new, StateWiseDataActivity.this.str_active, StateWiseDataActivity.this.str_death, StateWiseDataActivity.this.str_death_new, StateWiseDataActivity.this.str_recovered, StateWiseDataActivity.this.str_recovered_new, StateWiseDataActivity.this.str_lastupdatedate));
                    }
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            StateWiseDataActivity.this.stateWiseAdapter.notifyDataSetChanged();
                            StateWiseDataActivity.this.activity.DismissDialog();
                        }
                    }, 1000);
                } catch (JSONException e) {
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
        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_state_wise_swipe_refresh_layout);
        this.et_search = (EditText) findViewById(R.id.activity_state_wise_search_editText);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_state_wise_recyclerview);
        this.rv_state_wise = recyclerView;
        recyclerView.setHasFixedSize(true);
        this.rv_state_wise.setLayoutManager(new LinearLayoutManager(this));
        this.stateWiseModelArrayList = new ArrayList<>();
        StateWiseAdapter stateWiseAdapter2 = new StateWiseAdapter(this, this.stateWiseModelArrayList);
        this.stateWiseAdapter = stateWiseAdapter2;
        this.rv_state_wise.setAdapter(stateWiseAdapter2);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
