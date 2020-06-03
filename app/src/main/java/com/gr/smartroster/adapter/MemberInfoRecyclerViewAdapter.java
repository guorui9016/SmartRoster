package com.gr.smartroster.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.gr.smartroster.R;
import com.gr.smartroster.model.Staff;

import java.util.ArrayList;
import java.util.List;

public class MemberInfoRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<Staff> mStaffList = new ArrayList<>();
    private final int VIEW_TYPE_EMPTY = 0;
    private final int VIEW_TYPE_ITEM = 1;

    public void setStaffList(List<Staff> staffList) {
        this.mStaffList = staffList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_EMPTY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false);
            return new MyEmptyViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_info_list, parent,false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyEmptyViewHolder) {
            ((MyEmptyViewHolder) holder).tvEmptyItem.setText("No member in the group right now, please add staff frist");
        } else {
            MyViewHolder myViewHolder = (MyViewHolder)holder;
            Staff staff = mStaffList.get(position);
            myViewHolder.tvName_GroupInfo.setText(mStaffList.get(position).getEmail());
            if (staff.getRoles()==null) {
                myViewHolder.tvRoles_GroupInfo.setText("No Roles");
            } else {
                myViewHolder.tvRoles_GroupInfo.setText(mStaffList.get(position).getRoles().toString());
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        //if the the size of list is 0, retrun empty code, if not return have items code.
        if (mStaffList.size() == 0) {
            Log.d("Ray-Adapter", "getItemViewType: Use VIEW_TYPE_EMPTY code");
            return VIEW_TYPE_EMPTY;
        }
        return VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        if (mStaffList.size() == 0) {
            return 1;
        }
        return mStaffList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName_GroupInfo,tvRoles_GroupInfo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName_GroupInfo = itemView.findViewById(R.id.tvName_GroupInfo);
            tvRoles_GroupInfo = itemView.findViewById(R.id.tvRoles_GroupInfo);
        }
    }

    private class MyEmptyViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmptyItem;
        public MyEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmptyItem = itemView.findViewById(R.id.tvEmptyItem);
        }
    }
}
