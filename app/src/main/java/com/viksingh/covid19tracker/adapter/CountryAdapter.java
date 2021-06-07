package com.viksingh.covid19tracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.viksingh.covid19tracker.old.AffectedCountries;
import com.viksingh.covid19tracker.R;
import com.viksingh.covid19tracker.model.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends ArrayAdapter<Country> {
    private Context context;
    private List<Country> countryModelFilteredList;
    private List<Country> countryModelList;

    public CountryAdapter(Context context, List<Country> countryModelList) {
        super(context, R.layout.list_custom_item, countryModelList);
        this.context = context;
        this.countryModelList = countryModelList;
        this.countryModelFilteredList = countryModelList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item, (ViewGroup) null, true);
        ((TextView) view.findViewById(R.id.countryName)).setText(this.countryModelFilteredList.get(position).getCountry());
        Glide.with(context).load(countryModelFilteredList.get(position).getFlag()).into((ImageView) view.findViewById(R.id.flag));
        return view;
    }

    public int getCount() {
        return this.countryModelFilteredList.size();
    }

    public Country getItem(int position) {
        return this.countryModelFilteredList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public Filter getFilter() {
        return new Filter() {
            /* access modifiers changed from: protected */
            public FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = CountryAdapter.this.countryModelFilteredList.size();
                    filterResults.values = CountryAdapter.this.countryModelList;
                } else {
                    List<Country> resulModelList = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();
                    for (Country itemModel : CountryAdapter.this.countryModelList) {
                        if (itemModel.getCountry().toLowerCase().contains(searchStr)) {
                            resulModelList.add(itemModel);
                        }
                        filterResults.count = resulModelList.size();
                        filterResults.values = resulModelList;
                    }
                }
                return filterResults;
            }

            /* access modifiers changed from: protected */
            public void publishResults(CharSequence constraint, FilterResults results) {
                countryModelFilteredList = (List) results.values;
                AffectedCountries.countryModelList = (List) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
