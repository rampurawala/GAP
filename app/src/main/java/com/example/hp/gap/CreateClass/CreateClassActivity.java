package com.example.hp.gap.CreateClass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.gap.APIinterface.APIinterface;
import com.example.hp.gap.APImanager.APIClient;
import com.example.hp.gap.APIresponse.CreateClassResponse;
import com.example.hp.gap.APIresponse.RegistrationUserResponse;
import com.example.hp.gap.ClassesList.TeacherClassList;
import com.example.hp.gap.R;
import com.example.hp.gap.SharedPreferences.PreferenceManager;
import com.example.hp.gap.UserLoginActivity;
import com.example.hp.gap.UserRegistrationActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateClassActivity extends AppCompatActivity {
    EditText class_name, subject;
    Button create;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set title
        setTitle("Create Class");
        setContentView(R.layout.activity_create_class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //end set title


        class_name = findViewById(R.id.class_name);
        subject = findViewById(R.id.subject);
        create = findViewById(R.id.btnCreate);
        preferenceManager = new PreferenceManager(this);
        preferenceManager.setCurrentActivity("CreateClassActivity");
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subject.getText().toString().isEmpty()) {
                    subject.setError("Class name is required");
                } else {
                    createClass(class_name.getText().toString(), subject.getText().toString());
                }
            }
        });

    }


    private void createClass(String Classname, String subject) {
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

        Call<CreateClassResponse> getBrandResponseCall = apiInterface.SET_CLASSROOM_RESPONSE_CALL("qw", preferenceManager.getRegisteredUserId(), Classname, subject);

        getBrandResponseCall.enqueue(new Callback<CreateClassResponse>() {

            @Override
            public void onResponse(Call<CreateClassResponse> call, Response<CreateClassResponse> response) {
                if (response.body().getStatus() == 200) {
                    Toast.makeText(CreateClassActivity.this, response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(CreateClassActivity.this, TeacherClassList.class);
                    startActivity(i);
                    CreateClassActivity.this.finish();

                } else {
                    Toast.makeText(CreateClassActivity.this, "Invalid Data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<CreateClassResponse> call, Throwable t) {
                Log.d("123", t.getMessage());
            }
        });
    }

}
