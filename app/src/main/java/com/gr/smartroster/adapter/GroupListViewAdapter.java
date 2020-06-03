package com.gr.smartroster.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gr.smartroster.R;
import com.gr.smartroster.model.Staff;

import java.util.List;

public class GroupListViewAdapter extends BaseAdapter {
    private List<Staff> list;
    private Context context;

    public GroupListViewAdapter(List<Staff> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            view = View.inflate(context, R.layout.item_group, null);
        } else {
            view = convertView;
        }

        TextView groupName = view.findViewById(R.id.tvGroupName_GroupList);
        TextView company = view.findViewById(R.id.tvCompany_GroupList);

        groupName.setText(list.get(position).getGroupName());
        company.append(list.get(position).getCompany());

        return view;
    }
}
