package com.example.hp.gap.ToDoList;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.gap.APIinterface.APIinterface;
import com.example.hp.gap.APImanager.APIClient;
import com.example.hp.gap.APIresponse.JoinClassResponse;
import com.example.hp.gap.APIresponse.SetToDoListResponse;
import com.example.hp.gap.APIresponse.TeacherListResponse;
import com.example.hp.gap.APIresponse.TeacherResponse;
import com.example.hp.gap.APIresponse.ToDoListListResponse;
import com.example.hp.gap.APIresponse.ToDoListResponse;
import com.example.hp.gap.ClassesList.StudentClassList;
import com.example.hp.gap.ClassesList.TeacherClassList;
import com.example.hp.gap.R;
import com.example.hp.gap.SharedPreferences.PreferenceManager;
import com.example.hp.gap.adapterFiles.AdapterTeacherClassList;
import com.example.hp.gap.adapterFiles.AdapterToDoList;
import com.example.hp.gap.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToDOListActivity extends AppCompatActivity {
    PreferenceManager preferenceManager;
    FloatingActionButton fab_add;
    RecyclerView rc_list;
    List<ToDoListResponse> toDoListResponses = new ArrayList<ToDoListResponse>();
    AdapterToDoList adapterToDoList;
    CommonUtil commonUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(ToDOListActivity.this);
        setTitle(preferenceManager.getRegisteredUserName() + "'s  ToDo List");
        setContentView(R.layout.activity_to_dolist);
        fab_add = findViewById(R.id.fab_add);
        preferenceManager.setCurrentActivity("ToDOListActivity");
        rc_list = findViewById(R.id.rc_list);
        rc_list.setLayoutManager(new LinearLayoutManager(this));
        commonUtil = new CommonUtil();
        getList();
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText taskEditText = new EditText(ToDOListActivity.this);
                AlertDialog dialog = new AlertDialog.Builder(ToDOListActivity.this)
                        .setTitle("Add a new task")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                setList(task);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();

            }
        });

    }


    private void setList(String task) {
        commonUtil.loading(ToDOListActivity.this);
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

        Call<SetToDoListResponse> getBrandResponseCall = apiInterface.SET_LIST("qw",  preferenceManager.getRegisteredUserId(),task);

        getBrandResponseCall.enqueue(new Callback<SetToDoListResponse>() {

            @Override
            public void onResponse(Call<SetToDoListResponse> call, Response<SetToDoListResponse> response) {
                commonUtil.cancelLoading();
                Log.e("##", response.body().getStatus().toString());
                if (response.body().getStatus() == 1) {
                    Toast.makeText(ToDOListActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    getList();
                } else {
                    Toast.makeText(ToDOListActivity.this, "Invalid Data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<SetToDoListResponse> call, Throwable t) {
                commonUtil.cancelLoading();
                Log.d("123", t.getMessage());
            }
        });
    }


    private void getList() {
        commonUtil.loading(ToDOListActivity.this);
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

        Call<ToDoListListResponse> getBrandResponseCall = apiInterface.GET_LIST("qw", preferenceManager.getRegisteredUserId());

        getBrandResponseCall.enqueue(new Callback<ToDoListListResponse>() {

            @Override
            public void onResponse(Call<ToDoListListResponse> call, Response<ToDoListListResponse> response) {
                commonUtil.cancelLoading();
                if (response.body().getSuccess() == 200) {
                    //Toast.makeText(TeacherClassList.this,Integer.toString(preferenceManager.getRegisteredUserId()), Toast.LENGTH_SHORT).show();p'
                    toDoListResponses = response.body().getItemList();
                    Log.e("##", String.valueOf(response.body().getItemList().size()));
                    adapterToDoList = new AdapterToDoList(ToDOListActivity.this,toDoListResponses);
                    adapterToDoList.notifyDataSetChanged();
                    rc_list.setAdapter(adapterToDoList);


                } else {
                    Toast.makeText(ToDOListActivity.this, "Invalid Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ToDoListListResponse> call, Throwable t) {
                commonUtil.cancelLoading();
                Toast.makeText(ToDOListActivity.this, "Cannot connect!", Toast.LENGTH_SHORT).show();
                Log.d("123", t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(preferenceManager.getPanel()==1){
            Intent i=new Intent(ToDOListActivity.this,TeacherClassList.class);
            startActivity(i);
            ToDOListActivity.this.finish();
        }
        if(preferenceManager.getPanel()==0){
            Intent i=new Intent(ToDOListActivity.this,StudentClassList.class);
            startActivity(i);
            ToDOListActivity.this.finish();
        }
        super.onBackPressed();
    }
}
