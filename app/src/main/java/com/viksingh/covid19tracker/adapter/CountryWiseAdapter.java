package com.viksingh.covid19tracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.viksingh.covid19tracker.R;
import com.viksingh.covid19tracker.model.CountryWiseModel;
import com.viksingh.covid19tracker.updated.EachCountryDataActivity;
import com.viksingh.covid19tracker.util.Constants;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountryWiseAdapter extends RecyclerView.Adapter<CountryWiseAdapter.MyViewHolder> {
    /* access modifiers changed from: private */
    public ArrayList<CountryWiseModel> countryWiseModelArrayList;
    /* access modifiers changed from: private */
    public Context mContext;

    /* renamed from: sb */
    private SpannableStringBuilder f2sb;
    private String searchText = "";

    public CountryWiseAdapter(Context mContext2, ArrayList<CountryWiseModel> countryWiseModelArrayList2) {
        this.mContext = mContext2;
        this.countryWiseModelArrayList = countryWiseModelArrayList2;
    }

    public void filterList(ArrayList<CountryWiseModel> filteredList, String text) {
        this.countryWiseModelArrayList = filteredList;
        this.searchText = text;
        notifyDataSetChanged();
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.layout_country_wise, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyViewHolder myViewHolder = holder;
        final int i = position;
        CountryWiseModel currentItem = this.countryWiseModelArrayList.get(i);
        String countryName = currentItem.getCountry();
        String countryTotal = currentItem.getConfirmed();
        String countryFlag = currentItem.getFlag();
        String countryRank = String.valueOf(i + 1);
        int countryTotalInt = Integer.parseInt(countryTotal);
        Log.d("country rank", countryRank);
        TextView access$000 = holder.tv_rankTextView;
        access$000.setText(countryRank + ".");
        Log.d("country total cases int", String.valueOf(countryTotalInt));
        holder.tv_countryTotalCases.setText(NumberFormat.getInstance().format((long) countryTotalInt));
        if (this.searchText.length() > 0) {
            int indexOf = countryName.indexOf(this.searchText);
            this.f2sb = new SpannableStringBuilder(countryName);
            Matcher match = Pattern.compile(this.searchText.toLowerCase()).matcher(countryName.toLowerCase());
            while (match.find()) {
                this.f2sb.setSpan(new ForegroundColorSpan(Color.rgb(52, 195, 235)), match.start(), match.end(), 18);
                currentItem = currentItem;
            }
            holder.tv_countryName.setText(this.f2sb);
        } else {
            holder.tv_countryName.setText(countryName);
        }
        ((RequestBuilder) Glide.with(this.mContext).load(countryFlag).diskCacheStrategy(DiskCacheStrategy.DATA)).into(myViewHolder.iv_flagImage);
        myViewHolder.lin_country.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CountryWiseModel clickedItem = (CountryWiseModel) CountryWiseAdapter.this.countryWiseModelArrayList.get(i);
                Intent perCountryIntent = new Intent(CountryWiseAdapter.this.mContext, EachCountryDataActivity.class);
                perCountryIntent.putExtra(Constants.COUNTRY_NAME, clickedItem.getCountry());
                perCountryIntent.putExtra(Constants.COUNTRY_CONFIRMED, clickedItem.getConfirmed());
                perCountryIntent.putExtra("active", clickedItem.getActive());
                perCountryIntent.putExtra(Constants.COUNTRY_RECOVERED, clickedItem.getRecovered());
                perCountryIntent.putExtra(Constants.COUNTRY_DECEASED, clickedItem.getDeceased());
                perCountryIntent.putExtra(Constants.COUNTRY_NEW_CONFIRMED, clickedItem.getNewConfirmed());
                perCountryIntent.putExtra(Constants.COUNTRY_NEW_DECEASED, clickedItem.getNewDeceased());
                perCountryIntent.putExtra(Constants.COUNTRY_TESTS, clickedItem.getTests());
                perCountryIntent.putExtra(Constants.COUNTRY_FLAGURL, clickedItem.getFlag());
                CountryWiseAdapter.this.mContext.startActivity(perCountryIntent);
            }
        });
    }

    public int getItemCount() {
        ArrayList<CountryWiseModel> arrayList = this.countryWiseModelArrayList;
        if (arrayList == null || arrayList.isEmpty()) {
            return 0;
        }
        return this.countryWiseModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_flagImage;
        LinearLayout lin_country;
        /* access modifiers changed from: private */
        public TextView tv_countryName;
        /* access modifiers changed from: private */
        public TextView tv_countryTotalCases;
        /* access modifiers changed from: private */
        public TextView tv_rankTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.tv_countryName = (TextView) itemView.findViewById(R.id.layout_country_wise_country_name_textview);
            this.tv_countryTotalCases = (TextView) itemView.findViewById(R.id.layout_country_wise_confirmed_textview);
            this.iv_flagImage = (ImageView) itemView.findViewById(R.id.layout_country_wise_flag_imageview);
            this.tv_rankTextView = (TextView) itemView.findViewById(R.id.layout_country_wise_country_rank);
            this.lin_country = (LinearLayout) itemView.findViewById(R.id.layout_country_wise_lin);
        }
    }
}
