package com.viksingh.covid19tracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.viksingh.covid19tracker.old.AffectedStates;
import com.viksingh.covid19tracker.R;
import com.viksingh.covid19tracker.model.State;

import java.util.ArrayList;
import java.util.List;

public class StateAdapter extends ArrayAdapter<State> {
    private Context context;
    private List<State> stateFilteredList;
    private List<State> stateList;

    public StateAdapter(Context context, List<State> stateList) {
        super(context, R.layout.list_custom_item, stateList);
        this.context = context;
        this.stateList = stateList;
        this.stateFilteredList = stateList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item, (ViewGroup) null, true);
        System.out.println(stateList.size());
        System.out.println(position);
        ((TextView) view.findViewById(R.id.countryName)).setText(stateFilteredList.get(position).getLoc());
        ((ImageView) view.findViewById(R.id.flag)).setImageResource(R.drawable.akshardham);
        return view;
    }

    public int getCount() {
        return this.stateFilteredList.size();
    }

    public State getItem(int position) {
        return this.stateFilteredList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public Filter getFilter() {
        return new Filter() {
            public FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = stateFilteredList.size();
                    filterResults.values = stateList;
                } else {
                    List<State> resultList = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();


                    for (State item : stateList) {
                        if (item.getLoc().toLowerCase().contains(searchStr)) {
                            resultList.add(item);
                        }
                        filterResults.count = resultList.size();
                        filterResults.values = resultList;

                    }
                }
                return filterResults;
            }
            protected void publishResults(CharSequence constraint, FilterResults results) {
                stateFilteredList = (List) results.values;
                AffectedStates.stateList = (List) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
