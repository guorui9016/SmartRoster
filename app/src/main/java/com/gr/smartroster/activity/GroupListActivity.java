package com.gr.smartroster.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.gr.smartroster.R;
import com.gr.smartroster.model.Staff;
import java.util.List;

public class GroupListActivity extends AppCompatActivity {
    private ListView groupList;
    private TextView tvGroupInfo;
    private String email;
    private List<Staff> list = null;
    private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.i("Ray - ", "handleMessage: Receive the message: what = " + msg.what + " from thread."  );
            //if only one group, jump to the home page.
            //if more than one group, display all groups in list view
            if (list.size() == 1) {
                //jump to the next page "home page"
                Staff staff = list.get(0);
                Intent intent = new Intent(GroupListActivity.this, OverViewActivity.class);
                intent.putExtra("staff", new Gson().toJson(staff));
                startActivity(intent);
            } else {
                //show the group list
                initView();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
        tvGroupInfo = findViewById(R.id.tvGroupListInfo);

        //start a thread to get data from firestore
        new Thread() {
            @Override
            public void run() {
                super.run();
                initdata();     //load data from firestore
            }
        }.start();

    }

    private void initView() {
        //init item view
        groupList = findViewById(R.id.groupList);
        groupList.setAdapter(new GourpListViewAdpter());

        groupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Jump to the home page.
                Staff staff = list.get(position);
                Intent intent = new Intent(GroupListActivity.this, OverViewActivity.class);
                intent.putExtra("staff", new Gson().toJson(staff));
                startActivity(intent);
            }
        });
    }


    private void initdata() {
        //get email from login page
        Bundle extras = this.getIntent().getExtras();
        email = extras.getString("email");
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
                            if (!list.isEmpty()) {
                                Message message = new Message();
                                message.what = 1;
                                uiHandler.sendMessage(message);
                                Log.i("Ray - ", "run: Send message to uiHandler!");
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Ray - ", "onFailure: Can not get group list from firestore. " + e );
                tvGroupInfo.setText("Can not get group list information from database, please try again later");
            }
        });
    }

    private class GourpListViewAdpter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                view = View.inflate(GroupListActivity.this, R.layout.item_group, null);
            } else {
                view = convertView;
            }

            TextView groupName = view.findViewById(R.id.tvGroupName_group);
            TextView company = view.findViewById(R.id.tvCompany_group);

            groupName.setText(list.get(position).getGroupName());
            company.append(list.get(position).getCompany());

            return view;
        }
    }
}

