package com.gr.smartroster.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.gr.smartroster.R;
import com.gr.smartroster.adapter.GroupListViewAdapter;
import com.gr.smartroster.model.Staff;
import com.gr.smartroster.util.ConstantUtil;
import com.gr.smartroster.util.SpUtil;

import java.util.List;

public class GroupListActivity extends AppCompatActivity {
    private ListView groupList;
    private TextView tvGroupInfo;
    private String email;
    private List<Staff> list = null;

    private void EnterHomePage() {
        Staff staff = list.get(0);
        saveDataToSp(staff);
        Intent intent = new Intent(GroupListActivity.this, DashBoardActivity.class);
        startActivity(intent);
        finish();
    }

    private void EnterJoinGroupPage() {
        Intent joinIntent = new Intent(GroupListActivity.this, JoinGroupActivity.class);
        startActivity(joinIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkGroup();
        setContentView(R.layout.activity_group_list);
        tvGroupInfo = findViewById(R.id.tvGroupListInfo);
    }

    private void checkGroup() {
        //get email from login page
        email = (String) SpUtil.get(getApplicationContext(), ConstantUtil.EMAIL, "");
        Log.i("Ray - ", "initdata: -- The user Email is: " + email);
        //get the group list from firestore.
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("staffs")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                           list = task.getResult().toObjects(Staff.class);
                            Log.i("Ray - ", "onComplete: -- The list size is: " + list.size());
                            switch (list.size()) {
                                case 0:
                                    //jump to join group page
                                    EnterJoinGroupPage();
                                    break;
                                case 1:
                                    //jump to the next page "home page"
                                    EnterHomePage();
                                    break;
                                default:
                                    //show the group list
                                    initView();
                                    break;
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Ray - ", "onFailure: Can not get group list from firestore. " + e);
                        tvGroupInfo.setText("Can not get group list information from database, please try again later");
                    }
                });     //load data from firestore
    }

    private void initView() {
        //init item view
        groupList = findViewById(R.id.groupList);
        groupList.setAdapter(new GroupListViewAdapter(list, GroupListActivity.this));

        groupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Jump to the home page.
                Staff staff = list.get(position);
                saveDataToSp(staff);
                Intent intent = new Intent(GroupListActivity.this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void saveDataToSp(Staff staff) {
        Log.i("Ray - ", "saveDataToSp: Save user information");
        SpUtil.set(getApplicationContext(), ConstantUtil.GROUP_NAME, staff.getGroupName());
        SpUtil.set(getApplicationContext(), ConstantUtil.COMPANY, staff.getCompany());
        SpUtil.set(getApplicationContext(), ConstantUtil.ADMIN, staff.getAdmin());
    }
}

