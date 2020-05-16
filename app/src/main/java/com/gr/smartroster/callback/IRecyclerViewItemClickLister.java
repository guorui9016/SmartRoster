package com.gr.smartroster.callback;

import com.gr.smartroster.model.AvaliableTime;

public interface IRecyclerViewItemClickLister {
    void onItemClick(int position);
    void onItemLongClick(int position);
}
