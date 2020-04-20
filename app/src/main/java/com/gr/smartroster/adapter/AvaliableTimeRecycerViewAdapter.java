package com.gr.smartroster.adapter;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gr.smartroster.R;
import com.gr.smartroster.model.AvaliableTime;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class AvaliableTimeRecycerViewAdapter extends RecyclerView.Adapter<AvaliableTimeRecycerViewAdapter.MyAViewHolder> {
    List<AvaliableTime> avaliableTimesList;

    public AvaliableTimeRecycerViewAdapter(List<AvaliableTime> avaliableTimesList) {
        this.avaliableTimesList = avaliableTimesList;
    }

    @NonNull
    @Override
    public MyAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyAViewHolder viewHolder =
                new MyAViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_avaliabletime, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAViewHolder holder, int position) {
        AvaliableTime avaliableTime = avaliableTimesList.get(position);
        String date = DateFormat.getDateInstance().format(avaliableTime.getDate().toDate());
        String startTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(avaliableTime.getStartTime().toDate());
        String endTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(avaliableTime.getEndTime().toDate());
        String time = startTime + " -- " + endTime;

        holder.tvDate_avaliabletime.setText(date);
        holder.tvTime_avaliabletime.append(time);
        holder.tvCompany_avaliabletime.append(avaliableTime.getCompany());
    }

    @Override
    public int getItemCount() {
        return avaliableTimesList.size();
    }

    public class MyAViewHolder extends RecyclerView.ViewHolder  {
        TextView tvDate_avaliabletime;
        TextView tvTime_avaliabletime;
        TextView tvCompany_avaliabletime;

        public MyAViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate_avaliabletime = itemView.findViewById(R.id.tvDate_avaliabletime_item);
            tvTime_avaliabletime = itemView.findViewById(R.id.tvTime_avaliabletime_item);
            tvCompany_avaliabletime = itemView.findViewById(R.id.tvCompany_avaliabletime_itme);
        }
    }
}
