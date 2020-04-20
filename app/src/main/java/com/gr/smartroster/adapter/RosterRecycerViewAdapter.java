package com.gr.smartroster.adapter;


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

public class RosterRecycerViewAdapter extends RecyclerView.Adapter<RosterRecycerViewAdapter.MyViewHolder> {
    private List<Roster> rosterList;

    public RosterRecycerViewAdapter(List<Roster> rosterList) {
        this.rosterList = rosterList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder =
                new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_roster, parent,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Roster roster = rosterList.get(position);
        String date = DateFormat.getDateInstance().format(roster.getDate().toDate());
        String startTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(roster.getStartTime().toDate());
        String endTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(roster.getEndTime().toDate());
        String Time = startTime + " -- " + endTime;
        holder.tvDate_roster.setText(date);
        holder.tvTime_roster.append(Time);
        holder.tvRole_roster.append(roster.getRole());
        holder.tvCompany_roster.append(roster.getCompany());
    }

    @Override
    public int getItemCount() {
        return rosterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDate_roster;
        public TextView tvTime_roster;
        public TextView tvCompany_roster;
        public TextView tvRole_roster;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate_roster = itemView.findViewById(R.id.tvDate_roster_item);
            tvTime_roster = itemView.findViewById(R.id.tvTime_roster_item);
            tvCompany_roster = itemView.findViewById(R.id.tvCompany_roster_item);
            tvRole_roster = itemView.findViewById(R.id.tvRole_roster_item);
        }
    }
}