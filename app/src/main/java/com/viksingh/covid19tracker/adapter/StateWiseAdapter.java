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
import com.viksingh.covid19tracker.model.StateWiseModel;
import com.viksingh.covid19tracker.updated.EachStateDataActivity;
import com.viksingh.covid19tracker.util.Constants;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StateWiseAdapter extends RecyclerView.Adapter<StateWiseAdapter.ViewHolder> {
    /* access modifiers changed from: private */
    public Context mContext;

    /* renamed from: sb */
    private SpannableStringBuilder f4sb;
    private String searchText = "";
    /* access modifiers changed from: private */
    public ArrayList<StateWiseModel> stateWiseModelArrayList;

    public StateWiseAdapter(Context mContext2, ArrayList<StateWiseModel> arrayList) {
        this.mContext = mContext2;
        this.stateWiseModelArrayList = arrayList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.layout_state_wise, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        StateWiseModel currentItem = this.stateWiseModelArrayList.get(position);
        String stateName = currentItem.getState();
        holder.tv_stateTotalCases.setText(NumberFormat.getInstance().format((long) Integer.parseInt(currentItem.getConfirmed())));
        if (this.searchText.length() > 0) {
            this.f4sb = new SpannableStringBuilder(stateName);
            Matcher match = Pattern.compile(this.searchText.toLowerCase()).matcher(stateName.toLowerCase());
            while (match.find()) {
                this.f4sb.setSpan(new ForegroundColorSpan(Color.rgb(52, 195, 235)), match.start(), match.end(), 17);
            }
            holder.tv_stateName.setText(this.f4sb);
        } else {
            holder.tv_stateName.setText(stateName);
        }
        holder.lin_state_wise.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StateWiseModel clickedItem = (StateWiseModel) StateWiseAdapter.this.stateWiseModelArrayList.get(position);
                Intent perStateIntent = new Intent(StateWiseAdapter.this.mContext, EachStateDataActivity.class);
                perStateIntent.putExtra(Constants.STATE_NAME, clickedItem.getState());
                perStateIntent.putExtra(Constants.STATE_CONFIRMED, clickedItem.getConfirmed());
                perStateIntent.putExtra(Constants.STATE_CONFIRMED_NEW, clickedItem.getConfirmed_new());
                perStateIntent.putExtra(Constants.STATE_ACTIVE, clickedItem.getActive());
                perStateIntent.putExtra(Constants.STATE_DEATH, clickedItem.getDeath());
                perStateIntent.putExtra(Constants.STATE_DEATH_NEW, clickedItem.getDeath_new());
                perStateIntent.putExtra(Constants.STATE_RECOVERED, clickedItem.getRecovered());
                perStateIntent.putExtra(Constants.STATE_RECOVERED_NEW, clickedItem.getRecovered_new());
                perStateIntent.putExtra(Constants.STATE_LAST_UPDATE, clickedItem.getLastupdate());
                StateWiseAdapter.this.mContext.startActivity(perStateIntent);
            }
        });
    }

    public void filterList(ArrayList<StateWiseModel> filteredList, String text) {
        this.stateWiseModelArrayList = filteredList;
        this.searchText = text;
        notifyDataSetChanged();
    }

    public int getItemCount() {
        ArrayList<StateWiseModel> arrayList = this.stateWiseModelArrayList;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout lin_state_wise;
        TextView tv_stateName;
        TextView tv_stateTotalCases;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_stateName = (TextView) itemView.findViewById(R.id.statewise_layout_name_textview);
            this.tv_stateTotalCases = (TextView) itemView.findViewById(R.id.statewise_layout_confirmed_textview);
            this.lin_state_wise = (LinearLayout) itemView.findViewById(R.id.layout_state_wise_lin);
        }
    }
}
