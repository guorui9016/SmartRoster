package com.gr.smartroster.activity.fragment.roster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.gr.smartroster.R;
import com.gr.smartroster.adapter.SiteRosterRecyclerViewAdapter;
import com.gr.smartroster.model.Roster;

import java.util.Date;
import java.util.List;

public class SiteRosterFragment extends Fragment {
    private SiteRosterViewModel mSiteRosterViewModel;
    private RecyclerView mRvSiteRoster;
    private SiteRosterRecyclerViewAdapter mViewAdapter;
    private Timestamp mDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_siteroster, container, false);
        mSiteRosterViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(SiteRosterViewModel.class);
        //initial recyclerview
        mRvSiteRoster = root.findViewById(R.id.rvSiteRoster);
        mRvSiteRoster.setHasFixedSize(true);
        mRvSiteRoster.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        if (mDate==null) {
            mDate = new Timestamp(new Date(System.currentTimeMillis()));
        }
        mSiteRosterViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<List<Roster>>() {
            @Override
            public void onChanged(List<Roster> rosters) {
                mViewAdapter = new SiteRosterRecyclerViewAdapter(rosters);
                mRvSiteRoster.setAdapter(mViewAdapter);
            }
        });
        return root;
    }
}
