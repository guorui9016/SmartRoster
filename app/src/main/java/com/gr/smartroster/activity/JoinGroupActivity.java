package com.gr.smartroster.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.gr.smartroster.R;
import com.gr.smartroster.adapter.JoinGroupRecycerViewAdapter;
import com.gr.smartroster.callback.IRecyclerViewItemClickInterface;
import com.gr.smartroster.model.Group;
import com.gr.smartroster.viewmodel.JoinGroupViewModel;

import java.util.List;

public class JoinGroupActivity extends AppCompatActivity implements IRecyclerViewItemClickInterface {
    private RecyclerView joinGroupRecycleView;
    private JoinGroupViewModel mJoinGroupViewModel;
    private JoinGroupRecycerViewAdapter myViewAdapter;
    private TextView tvInfo_joinGrup;
    private SearchView svSearchGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);
        mJoinGroupViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(JoinGroupViewModel.class);
        myViewAdapter = new JoinGroupRecycerViewAdapter(this);
        joinGroupRecycleView = findViewById(R.id.rvJoinGroup);
        tvInfo_joinGrup = findViewById(R.id.tvInfo_JoinGrup);
        svSearchGroup = findViewById(R.id.svSearchGroup);
        joinGroupRecycleView.setHasFixedSize(true);
        joinGroupRecycleView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));

        mJoinGroupViewModel.getMutableLiveData().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groupList) {
                myViewAdapter.setGroupList(groupList);
                joinGroupRecycleView.setAdapter(myViewAdapter);
                if (groupList.isEmpty()) {
                    tvInfo_joinGrup.setText("No result matched");
                    tvInfo_joinGrup.setVisibility(View.VISIBLE);
                } else {
                    tvInfo_joinGrup.setVisibility(View.INVISIBLE);
                }
            }
        });

        svSearchGroup.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mJoinGroupViewModel.searchGroup(query.toLowerCase());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        //add user to the staff list of the group.
        mJoinGroupViewModel.addUser(position);
    }

    @Override
    public void onItemLongClick(int position) {

    }
}