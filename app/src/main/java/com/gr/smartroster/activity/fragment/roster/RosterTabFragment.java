package com.gr.smartroster.activity.fragment.roster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.gr.smartroster.R;
import com.gr.smartroster.adapter.RosterPageViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class RosterTabFragment extends Fragment {
    TabLayout mTabLayout;
    ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_roster_tab, container, false);
        //find items
        mTabLayout = root.findViewById(R.id.tabLayout);
        mViewPager = root.findViewById(R.id.vpRosterPageView);
        //initial UI
        initUI();
        return root;
    }

    private void initUI() {
        //init view pager adapter
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new MyRosterFragment());
        fragmentList.add(new SiteRosterFragment());
        List<String> titleList = new ArrayList<>();
        titleList.add("My Roster");
        titleList.add("Site Roster");
        RosterPageViewAdapter pageViewAdapter = new RosterPageViewAdapter(getParentFragmentManager(),fragmentList, titleList);
        mViewPager.setAdapter(pageViewAdapter);
        //link the layout and viewpager
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
