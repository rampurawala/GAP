package com.example.hp.gap.ClassesList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.hp.gap.APIinterface.APIinterface;
import com.example.hp.gap.APImanager.APIClient;
import com.example.hp.gap.APIresponse.CreateClassResponse;
import com.example.hp.gap.APIresponse.JoinClassResponse;
import com.example.hp.gap.APIresponse.StudentListResponse;
import com.example.hp.gap.APIresponse.StudentResponse;
import com.example.hp.gap.CreateClass.CreateClassActivity;
import com.example.hp.gap.R;
import com.example.hp.gap.SelectOption.SelectOptionActivity;
import com.example.hp.gap.SharedPreferences.PreferenceManager;
import com.example.hp.gap.SideNavigationDrawer.NavigationDrawer;
import com.example.hp.gap.adapterFiles.AdapterStudentClassList;
import com.example.hp.gap.utils.CommonUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentClassList extends NavigationDrawer {
    RecyclerView rc_student;
    PreferenceManager preferenceManager;
    Button join_class;
   // SwipeRefreshLayout mSwipeRefreshLayout;
    CommonUtil commonUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_student_class_list);
        super.onCreate(savedInstanceState);

        setTitle("Student Classes");

        //Start for navigation drawer
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.frameDisplay); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_student_class_list, contentFrameLayout);
        //End for navigation drawer
       /* mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipenya);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
*/        rc_student = findViewById(R.id.rc_student);
        join_class = findViewById(R.id.join_class);
        preferenceManager = new PreferenceManager(this);
        commonUtil = new CommonUtil();
        preferenceManager.setCurrentActivity("StudentClassList");
        rc_student.setHasFixedSize(true);
        rc_student.setLayoutManager(new LinearLayoutManager(StudentClassList.this));

        loadData();
        join_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemDialog(StudentClassList.this);
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
        commonUtil.loading(StudentClassList.this);
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

        Call<StudentListResponse> getBrandResponseCall = apiInterface.GET_JOINCLASSROOM_RESPONSE_CALL("qw", preferenceManager.getRegisteredUserId());

        getBrandResponseCall.enqueue(new Callback<StudentListResponse>() {

            @Override
            public void onResponse(Call<StudentListResponse> call, Response<StudentListResponse> response) {
                commonUtil.cancelLoading();
                if (response.body().getSuccess() == 200) {
                    List<StudentResponse> brands = response.body().getStudentClassList();
                    // Toast.makeText(JsonMain.this, "Success Data", Toast.LENGTH_SHORT).show();
                    AdapterStudentClassList listAdapter = new AdapterStudentClassList(brands, StudentClassList.this);
                    listAdapter.notifyDataSetChanged();
                    rc_student.setAdapter(listAdapter);

                } else {
                    Toast.makeText(StudentClassList.this, "Invalid Data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<StudentListResponse> call, Throwable t) {
                commonUtil.cancelLoading();
                Toast.makeText(StudentClassList.this, "Cannot connect!", Toast.LENGTH_SHORT).show();
                Log.d("123", t.getMessage());
            }
        });
    }

    //opens dialog box to enter class code
    private void showAddItemDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Class code")
                .setMessage("Enter Classcode to join Class")
                .setView(taskEditText)
                .setPositiveButton("Join", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        joinClass(taskEditText.getText().toString());
                        String task = String.valueOf(taskEditText.getText());
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }


    private void joinClass(String Classcode) {
        commonUtil.loading(StudentClassList.this);
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

        Call<JoinClassResponse> getBrandResponseCall = apiInterface.SET_JOINCLASSROOM_RESPONSE_CALL("qw", Classcode, preferenceManager.getRegisteredUserId());

        getBrandResponseCall.enqueue(new Callback<JoinClassResponse>() {

            @Override
            public void onResponse(Call<JoinClassResponse> call, Response<JoinClassResponse> response) {
                commonUtil.cancelLoading();
                Log.e("##", response.body().getStatus().toString());
                if (response.body().getStatus() == 200) {
                    Toast.makeText(StudentClassList.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    loadData();

                }
                if (response.body().getStatus() == 202) {
                    Toast.makeText(StudentClassList.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
                if (response.body().getStatus() == 203) {
                    Toast.makeText(StudentClassList.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(StudentClassList.this, "Invalid Data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<JoinClassResponse> call, Throwable t) {
                commonUtil.cancelLoading();
                Log.d("123", t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(StudentClassList.this, SelectOptionActivity.class);
        startActivity(i);
        super.onBackPressed();
    }
}
