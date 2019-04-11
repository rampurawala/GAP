package com.example.hp.gap.ClassesList;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.hp.gap.APIinterface.APIinterface;
import com.example.hp.gap.APImanager.APIClient;
import com.example.hp.gap.APIresponse.TeacherListResponse;
import com.example.hp.gap.APIresponse.TeacherResponse;
import com.example.hp.gap.CreateClass.CreateClassActivity;
import com.example.hp.gap.MainActivity;
import com.example.hp.gap.R;
import com.example.hp.gap.SelectOption.SelectOptionActivity;
import com.example.hp.gap.SharedPreferences.PreferenceManager;
import com.example.hp.gap.SideNavigationDrawer.NavigationDrawer;
import com.example.hp.gap.adapterFiles.AdapterTeacherClassList;
import com.example.hp.gap.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherClassList extends NavigationDrawer {
    RecyclerView rc_teacher;
    PreferenceManager preferenceManager;
    AdapterTeacherClassList listAdapter;
    //SwipeRefreshLayout mSwipeRefreshLayout;
    Button btn_create;
    List<TeacherResponse> teacherResponses = new ArrayList<TeacherResponse>();
    CommonUtil commonUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_teacher_class_list);
        super.onCreate(savedInstanceState);


        setTitle("Teacher Classes");

        //Start for navigation drawer
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.frameDisplay); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_teacher_class_list, contentFrameLayout);
        //End for navigation drawer
        // mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipenya);
        //  mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        rc_teacher = findViewById(R.id.rc_teacher);
        btn_create = findViewById(R.id.btn_create);
        preferenceManager = new PreferenceManager(this);
        preferenceManager.setCurrentActivity("TeacherClassList");
        commonUtil = new CommonUtil();
        rc_teacher.setHasFixedSize(true);
//        LinearLayoutManager llm = new LinearLayoutManager(this);
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        rc_teacher.setLayoutManager(llm);
        rc_teacher.setLayoutManager(new LinearLayoutManager(this));
        loadData();
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TeacherClassList.this, CreateClassActivity.class);
                startActivity(i);

            }
        });

     /*   mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // do something
                        loadData();
                        //Toast.makeText(TeacherClassList.this, "do something", Toast.LENGTH_LONG).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });*/
    }

    private void loadData() {
        commonUtil.loading(TeacherClassList.this);
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

        Call<TeacherListResponse> getBrandResponseCall = apiInterface.GET_CLASSROOM_RESPONSE_CALL("qw", preferenceManager.getRegisteredUserId());

        getBrandResponseCall.enqueue(new Callback<TeacherListResponse>() {

            @Override
            public void onResponse(Call<TeacherListResponse> call, Response<TeacherListResponse> response) {
                commonUtil.cancelLoading();
                if (response.body().getSuccess() == 200) {
                    //Toast.makeText(TeacherClassList.this,Integer.toString(preferenceManager.getRegisteredUserId()), Toast.LENGTH_SHORT).show();p'
                    teacherResponses = response.body().getTeacherClassList();
                    Log.e("##", String.valueOf(response.body().getTeacherClassList().size()));
                    listAdapter = new AdapterTeacherClassList(teacherResponses, TeacherClassList.this);
                    listAdapter.notifyDataSetChanged();
                    rc_teacher.setAdapter(listAdapter);


                } else {
                    Toast.makeText(TeacherClassList.this, "Invalid Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TeacherListResponse> call, Throwable t) {
                commonUtil.cancelLoading();
                Toast.makeText(TeacherClassList.this, "Cannot connect!", Toast.LENGTH_SHORT).show();
                Log.d("123", t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(TeacherClassList.this, SelectOptionActivity.class);
        startActivity(i);
        super.onBackPressed();
    }
}
