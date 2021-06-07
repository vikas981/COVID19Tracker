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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;
import com.viksingh.covid19tracker.R;
import com.viksingh.covid19tracker.adapter.StateAdapter;
import com.viksingh.covid19tracker.model.State;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AffectedStates extends AppCompatActivity {

    public static List<State> stateList = new ArrayList();
    private ListView listView;
    private EditText search;
    private SimpleArcLoader simpleArcLoader;
    private StateAdapter stateAdapter;
    private State stateModel;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(stateList.size()>0){
            Toast.makeText(AffectedStates.this,"Clearing Previous Data", Toast.LENGTH_SHORT).show();
            stateList.clear();
        }
        setContentView((int) R.layout.activity_affected_states);
        this.search = (EditText) findViewById(R.id.search_bar);
        this.listView = (ListView) findViewById(R.id.listMode);
        this.simpleArcLoader = (SimpleArcLoader) findViewById(R.id.loader);
        getSupportActionBar().setTitle((CharSequence) "State List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        fetchData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                AffectedStates affectedStates = AffectedStates.this;
                affectedStates.startActivity(new Intent(affectedStates.getApplicationContext(), StateDetail.class).putExtra("position", position));
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                stateAdapter.getFilter().filter(s);
                stateAdapter.notifyDataSetChanged();
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
        this.simpleArcLoader.start();
        Volley.newRequestQueue(this).add(new JsonObjectRequest(0, "https://api.rootnet.in/covid19-in/stats/latest", (JSONObject) null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONObject("data").getJSONArray("regional");
                    int i = 0;
                    while (i< jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String stateName = object.getString("loc");
                        String indian = object.getString("confirmedCasesIndian");
                        String foreign = object.getString("confirmedCasesForeign");
                        String confirmed = object.getString("totalConfirmed");
                        String dead = object.getString("deaths");
                        String recovered = object.getString("discharged");
                        stateModel = new State(stateName,indian,foreign,confirmed,dead,recovered);
                        AffectedStates.stateList.add(stateModel);
                        i++;
                    }
                    stateAdapter = new StateAdapter(AffectedStates.this, stateList);
                    listView.setAdapter(stateAdapter);
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    AffectedStates.this.simpleArcLoader.stop();
                    AffectedStates.this.simpleArcLoader.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                AffectedStates.this.simpleArcLoader.stop();
                AffectedStates.this.simpleArcLoader.setVisibility(View.GONE);
            }
        }));
    }
}