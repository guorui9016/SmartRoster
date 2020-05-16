package com.gr.smartroster.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.gr.smartroster.R;
import com.gr.smartroster.callback.IRecyclerViewItemClickLister;
import com.gr.smartroster.model.Group;

import java.util.List;

public class JoinGroupRecycerViewAdapter extends RecyclerView.Adapter<JoinGroupRecycerViewAdapter.JoinGroupViewHolder> {
    private List<Group> groupList;
    private IRecyclerViewItemClickLister mItemClickInterface;

    public JoinGroupRecycerViewAdapter(IRecyclerViewItemClickLister itemClickInterface) {
        mItemClickInterface = itemClickInterface;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
/*        notifyDataSetChanged();*/
    }

    @NonNull
    @Override
    public JoinGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        JoinGroupViewHolder joinGroupViewHolder =
                new JoinGroupViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_joingrouplist, parent, false));
        return joinGroupViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull JoinGroupViewHolder holder, int position) {

        holder.tvGroupName.setText(groupList.get(position).getGroupName());
        holder.tvManagerName.setText("Manager: "+groupList.get(position).getManager());
        holder.tvCompanyName.setText("Company: "+groupList.get(position).getCompany());
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public class JoinGroupViewHolder extends RecyclerView.ViewHolder{
        TextView tvGroupName;
        TextView tvCompanyName;
        TextView tvManagerName;

        public JoinGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGroupName = itemView.findViewById(R.id.tvGroupname_JoinGroup);
            tvCompanyName = itemView.findViewById(R.id.tvCompany_JoinGroup);
            tvManagerName = itemView.findViewById(R.id.tvManagerName_JoinGroup);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickInterface.onItemClick(getAdapterPosition());
                    Log.i("Ray", "onClick: " + getAdapterPosition() + " item has been click.");
                }
            });
        }
    }
}
