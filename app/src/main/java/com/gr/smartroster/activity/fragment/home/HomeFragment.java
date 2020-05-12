package com.gr.smartroster.activity.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.gr.smartroster.R;
import com.gr.smartroster.adapter.RosterRecycerViewAdapter;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private LayoutAnimationController layoutAnimationController;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.rvRosterList);
        initRecycView();
        homeViewModel.getRosterLiveDataList().observe(getViewLifecycleOwner(), rosterList ->{
            //set adapter
            RosterRecycerViewAdapter rosterRecycerViewAdapter = new RosterRecycerViewAdapter(rosterList);
            recyclerView.setAdapter(rosterRecycerViewAdapter);
            recyclerView.setLayoutAnimation(layoutAnimationController);
        });
        return root;
    }

    private void initRecycView() {
        layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_item_from_left);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

}
