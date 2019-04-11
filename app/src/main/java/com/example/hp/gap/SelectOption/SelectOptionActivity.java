package com.example.hp.gap.SelectOption;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.gap.APIinterface.APIinterface;
import com.example.hp.gap.APImanager.APIClient;
import com.example.hp.gap.APIresponse.ChangePasswordResponse;
import com.example.hp.gap.APIresponse.LoginResponse;
import com.example.hp.gap.AboutClassInfo.ClassAbout;
import com.example.hp.gap.ClassesList.StudentClassList;
import com.example.hp.gap.ClassesList.TeacherClassList;
import com.example.hp.gap.R;
import com.example.hp.gap.SharedPreferences.PreferenceManager;
import com.example.hp.gap.UserLoginActivity;
import com.example.hp.gap.btmNavFragment.StreamFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectOptionActivity extends AppCompatActivity {
    Button teacher, student;
    PreferenceManager preferenceManager;
    TextView select;
    String tokenStr, SFcm;
    private Handler mHandler;
    private Runnable mRunnable;
    private int mInterval = 300; // milliseconds
    private boolean initialState = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option);
        teacher = findViewById(R.id.teacher);
        student = findViewById(R.id.student);
        select=findViewById(R.id.select);
        mHandler = new Handler();
        preferenceManager = new PreferenceManager(this);
        FirebaseInstanceId.getInstance().getInstanceId()

                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {

                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {

                        if (!task.isSuccessful()) {

                            Log.e("firebase", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        tokenStr = task.getResult().getToken();
                        SFcm = tokenStr;

                        Log.e("firebase", tokenStr);
                        fcm(tokenStr);
                    }
                });

        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceManager.setPanel(1);
                Intent i = new Intent(SelectOptionActivity.this, TeacherClassList.class);
                startActivity(i);
                SelectOptionActivity.this.finish();
            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceManager.setPanel(0);
                Intent i = new Intent(SelectOptionActivity.this, StudentClassList.class);
                startActivity(i);
                SelectOptionActivity.this.finish();
            }
        });



        mRunnable = new Runnable() {

            @Override
            public void run() {
                // Do the task
                doTask();
            }
        };

        mHandler.postDelayed(mRunnable,mInterval);



    }


    private void fcm(String fcm) {
        //commonUtil.loading(UserLoginActivity.this);
        APIinterface apiInterface= APIClient.getClient().create(APIinterface.class);

        Call<ChangePasswordResponse> getBrandResponseCall=apiInterface.SET_FCM("qw",fcm,preferenceManager.getRegisteredUserId());

        getBrandResponseCall.enqueue(new Callback<ChangePasswordResponse>() {

            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
             //   commonUtil.cancelLoading();
                if(response.body().getSuccess()==200){



                    Toast.makeText(SelectOptionActivity.this, "FCM registered", Toast.LENGTH_SHORT).show();
                }else {

                    Toast.makeText(SelectOptionActivity.this, "Invalid Data", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
               // commonUtil.cancelLoading();
                Log.d("123", t.getMessage());
             //   Snackbar.make(v, "Can't connect. Check if you're online.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }


            // Custom method to do a task
    protected void doTask(){
        if(initialState){
            // Reverse the boolean
            initialState = false;
            // Set the TextView color to red
            select.setTextColor(Color.RED);
        }else {
            // Reverse the boolean
            initialState = true;
            // Change the TextView color to initial State
            select.setTextColor(Color.BLACK);
        }

        // Schedule the task
        mHandler.postDelayed(mRunnable,mInterval);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logoutmenu) {
            preferenceManager.setCheck(1);
            Intent i = new Intent(getApplicationContext(), UserLoginActivity.class);
            startActivity(i);
            SelectOptionActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed()
    {
       // finishAffinity();
        finish();
    }
}





