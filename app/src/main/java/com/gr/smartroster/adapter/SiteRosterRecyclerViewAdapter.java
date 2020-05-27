package com.gr.smartroster.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gr.smartroster.R;
import com.gr.smartroster.model.Roster;

import java.text.DateFormat;
import java.util.List;
import java.util.TimeZone;

public class SiteRosterRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<Roster> mRosterList;
    private final int VIEW_TYPE_EMPTY = 0;
    private final int VIEW_TYPE_ITEM = 1;

    public void setRosterList(List<Roster> rosterList) {
        mRosterList = rosterList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        //check the viewtype, if it is empty, return empty item view
        if (viewType  ==  VIEW_TYPE_EMPTY) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_site_roster_empty, parent, false);
            return new MyEmptyViewHolder(view);
        }

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_site_roster, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            Roster roster = mRosterList.get(position);
            DateFormat dateFormat  = DateFormat.getInstance();
            dateFormat.setTimeZone(TimeZone.getDefault());
            String startTime = dateFormat.getTimeInstance(DateFormat.SHORT).format(roster.getStartTime().toDate());
            String endTime = dateFormat.getTimeInstance(DateFormat.SHORT).format(roster.getEndTime().toDate());
            String time = "Woking Time: " +  startTime + " -- " + endTime;

            myViewHolder.tvName.setText(roster.getName());
            myViewHolder.tvRole.setText(roster.getRole());
            myViewHolder.tvTime.setText(time);
        } else if (holder instanceof MyEmptyViewHolder) {
            Log.d("Ray-Adapter", "onBindViewHolder: return empty holder");
            MyEmptyViewHolder myEmptyViewHolder = (MyEmptyViewHolder) holder;
            myEmptyViewHolder.tvEmptyItem.setText("No site roster, or the roster hasn't been polished. \nPlease connect your manager for details");
        }

    }

    @Override
    public int getItemViewType(int position) {
        //if the the size of list is 0, retrun empty code, if not return have items code.
        if (mRosterList.size() == 0) {
            Log.d("Ray-Adapter", "getItemViewType: Use VIEW_TYPE_EMPTY code");
            return VIEW_TYPE_EMPTY;
        }
        return VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        //if the list is empty, display one item to show the empty item.
        if (mRosterList.size() == 0) {
            return 1;
        }
        return mRosterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvRole;
        TextView tvTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName_SiteRoster_Item);
            tvRole = itemView.findViewById(R.id.tvRole_SiteRoster_Item);
            tvTime = itemView.findViewById(R.id.tvTime_SiteRoster_Item);
        }
    }

    public class MyEmptyViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmptyItem;
        public MyEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmptyItem = itemView.findViewById(R.id.tvEmptyItem);
        }
    }

}
