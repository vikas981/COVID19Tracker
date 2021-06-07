package com.viksingh.covid19tracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.viksingh.covid19tracker.R;
import com.viksingh.covid19tracker.model.DistrictWiseModel;
import com.viksingh.covid19tracker.updated.EachDistrictDataActivity;
import com.viksingh.covid19tracker.util.Constants;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DistrictWiseAdapter extends RecyclerView.Adapter<DistrictWiseAdapter.MyViewHolder> {
    /* access modifiers changed from: private */
    public ArrayList<DistrictWiseModel> districtWiseModelArrayList;
    /* access modifiers changed from: private */
    public Context mContext;

    /* renamed from: sb */
    private SpannableStringBuilder f3sb;
    private String searchText = "";

    public DistrictWiseAdapter(Context mContext2, ArrayList<DistrictWiseModel> districtWiseModelArrayList2) {
        this.mContext = mContext2;
        this.districtWiseModelArrayList = districtWiseModelArrayList2;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.layout_state_wise, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, final int position) {
        DistrictWiseModel currentItem = this.districtWiseModelArrayList.get(position);
        String districtName = currentItem.getDistrict();
        holder.tv_districtTotalCases.setText(NumberFormat.getInstance().format((long) Integer.parseInt(currentItem.getConfirmed())));
        if (this.searchText.length() > 0) {
            int indexOf = districtName.indexOf(this.searchText);
            this.f3sb = new SpannableStringBuilder(districtName);
            Matcher match = Pattern.compile(this.searchText.toLowerCase()).matcher(districtName.toLowerCase());
            while (match.find()) {
                this.f3sb.setSpan(new ForegroundColorSpan(Color.rgb(52, 195, 235)), match.start(), match.end(), 18);
            }
            holder.tv_districtName.setText(this.f3sb);
        } else {
            holder.tv_districtName.setText(districtName);
        }
        holder.lin_district_wise.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DistrictWiseModel clickedItem = (DistrictWiseModel) DistrictWiseAdapter.this.districtWiseModelArrayList.get(position);
                Intent perDistrictIntent = new Intent(DistrictWiseAdapter.this.mContext, EachDistrictDataActivity.class);
                perDistrictIntent.putExtra(Constants.DISTRICT_NAME, clickedItem.getDistrict());
                perDistrictIntent.putExtra(Constants.DISTRICT_CONFIRMED, clickedItem.getConfirmed());
                perDistrictIntent.putExtra(Constants.DISTRICT_CONFIRMED_NEW, clickedItem.getNewConfirmed());
                perDistrictIntent.putExtra(Constants.DISTRICT_ACTIVE, clickedItem.getActive());
                perDistrictIntent.putExtra(Constants.DISTRICT_DEATH, clickedItem.getDeceased());
                perDistrictIntent.putExtra(Constants.DISTRICT_DEATH_NEW, clickedItem.getNewDeceased());
                perDistrictIntent.putExtra(Constants.DISTRICT_RECOVERED, clickedItem.getRecovered());
                perDistrictIntent.putExtra(Constants.DISTRICT_RECOVERED_NEW, clickedItem.getNewRecovered());
                DistrictWiseAdapter.this.mContext.startActivity(perDistrictIntent);
            }
        });
    }

    public int getItemCount() {
        ArrayList<DistrictWiseModel> arrayList = this.districtWiseModelArrayList;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public void filterList(ArrayList<DistrictWiseModel> filteredList, String search) {
        this.districtWiseModelArrayList = filteredList;
        this.searchText = search;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout lin_district_wise;
        TextView tv_districtName;
        TextView tv_districtTotalCases;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.tv_districtName = (TextView) itemView.findViewById(R.id.statewise_layout_name_textview);
            this.tv_districtTotalCases = (TextView) itemView.findViewById(R.id.statewise_layout_confirmed_textview);
            this.lin_district_wise = (LinearLayout) itemView.findViewById(R.id.layout_state_wise_lin);
        }
    }
}
