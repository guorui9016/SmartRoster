package com.gr.smartroster.activity.ui.avaliabletime;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gr.smartroster.R;
import com.gr.smartroster.activity.AddTimeActivity;
import com.gr.smartroster.adapter.AvaliableTimeRecycerViewAdapter;
import com.gr.smartroster.model.AvaliableTime;

import java.util.List;

public class AvaliableTimeFragment extends Fragment {

    private AvaliabletTimeViewModel avaliabletTimeViewModel;
    private RecyclerView recyclerView;
    private LayoutAnimationController animationController;
    private FloatingActionButton fabAddAvaliableTime;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        avaliabletTimeViewModel = ViewModelProviders.of(this).get(AvaliabletTimeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_avaliable, container, false);
        recyclerView = root.findViewById(R.id.rv_avaliable_time_list);
        fabAddAvaliableTime = root.findViewById(R.id.fab_add_avaliable_time);
        initRecycleView();
        AvaliableTimeRecycerViewAdapter adapter = new AvaliableTimeRecycerViewAdapter();
        avaliabletTimeViewModel.getAvaliableTimeLiveDataList().observe(getViewLifecycleOwner(), new Observer<List<AvaliableTime>>() {
            @Override
            public void onChanged(List<AvaliableTime> avaliableTimes) {
                adapter.setAvaliableTimesList(avaliableTimes);
                recyclerView.setAdapter(adapter);
            }
        });

        fabAddAvaliableTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(), AddTimeActivity.class);
                startActivity(intent);
            }
        });

/*        avaliabletTimeViewModel.getAvaliableTimeLiveDataList().observe(getViewLifecycleOwner(), avaliableTimes ->{
            AvaliableTimeRecycerViewAdapter viewAdapter = new AvaliableTimeRecycerViewAdapter(avaliableTimes);
            recyclerView.setAdapter(viewAdapter);
            recyclerView.setLayoutAnimation(animationController);
        });*/
        return root;
    }

    private void initRecycleView() {

        animationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_item_from_left);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }
}
