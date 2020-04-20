package com.gr.smartroster.activity.ui.avaliabletime;

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
import com.gr.smartroster.adapter.AvaliableTimeRecycerViewAdapter;

public class AvaliableTimeFragment extends Fragment {

    private AvaliabletTimeViewModel avaliabletTimeViewModel;
    private RecyclerView recyclerView;
    private LayoutAnimationController animationController;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        avaliabletTimeViewModel = ViewModelProviders.of(this).get(AvaliabletTimeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_avaliable, container, false);
        recyclerView = root.findViewById(R.id.rvAvaliableTimeList);
        initRecycleView();

        avaliabletTimeViewModel.getAvaliableTimeLiveDataList().observe(getViewLifecycleOwner(), avaliableTimes ->{
            AvaliableTimeRecycerViewAdapter viewAdapter = new AvaliableTimeRecycerViewAdapter(avaliableTimes);
            recyclerView.setAdapter(viewAdapter);
            recyclerView.setLayoutAnimation(animationController);
        });
        return root;
    }

    private void initRecycleView() {
        animationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_item_from_left);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }
}
