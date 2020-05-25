package com.gr.smartroster.activity.fragment.roster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.gr.smartroster.R;
import com.gr.smartroster.adapter.RosterRecycerViewAdapter;
import com.gr.smartroster.callback.IRecyclerViewItemClickLister;

public class MyRosterFragment extends Fragment implements IRecyclerViewItemClickLister {

    private MyRosterViewModel mMyRosterViewModel;
    private RecyclerView mRecyclerView;
    private LayoutAnimationController mLayoutAnimationController;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMyRosterViewModel = ViewModelProviders.of(this).get(MyRosterViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_roster, container, false);
        mRecyclerView = root.findViewById(R.id.rvRosterList);
        initRecycView();
        mMyRosterViewModel.getRosterLiveDataList().observe(getViewLifecycleOwner(), rosterList ->{
            //set adapter
            RosterRecycerViewAdapter rosterRecycerViewAdapter = new RosterRecycerViewAdapter(rosterList, this);
            mRecyclerView.setAdapter(rosterRecycerViewAdapter);
            mRecyclerView.setLayoutAnimation(mLayoutAnimationController);
        });
        return root;
    }

    private void initRecycView() {
        mLayoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_item_from_left);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(), "Roster item has been clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(int position) {
    }
}
