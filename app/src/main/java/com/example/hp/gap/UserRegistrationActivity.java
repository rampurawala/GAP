package com.example.hp.gap;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.gap.APIinterface.APIinterface;
import com.example.hp.gap.APImanager.APIClient;
import com.example.hp.gap.APIresponse.RegistrationUserResponse;
import com.example.hp.gap.APIresponse.StudentListResponse;
import com.example.hp.gap.APIresponse.StudentResponse;
import com.example.hp.gap.ClassesList.StudentClassList;
import com.example.hp.gap.SelectOption.SelectOptionActivity;
import com.example.hp.gap.adapterFiles.AdapterStudentClassList;
import com.example.hp.gap.utils.CommonUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegistrationActivity extends AppCompatActivity {
    Button submit;
    EditText user_name, user_email, user_password;
    TextView loginAccount;
    ImageView img_RegisterPassword;
    private Boolean isClicked = false;
CommonUtil commonUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Registration");
        setContentView(R.layout.activity_user_registration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        submit = findViewById(R.id.submit);
        user_name = findViewById(R.id.user_name);
        user_email = findViewById(R.id.user_email);
        user_password = findViewById(R.id.user_password);
        loginAccount = findViewById(R.id.loginAccount);
        img_RegisterPassword=findViewById(R.id.img_RegisterPassword);
        commonUtil=new CommonUtil();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user_name.getText().toString().isEmpty()) {
                    /*Toast.makeText(getApplicationContext(), "Name cannot be less than 4 characters!", Toast.LENGTH_LONG).show();*/
                    user_name.setError("Invalid User Name!");
                    return;
                } else if (user_email.getText().toString().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(user_email.getText().toString()).matches()) {
                    // Validation for Invalid Email Address
                    /*Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_LONG).show();*/
                    user_email.setError("Invalid Email");
                    return;
                } else if (user_password.getText().toString().isEmpty() || user_password.length() < 8) {
                    // Validation for Website Address
                    /*Toast.makeText(getApplicationContext(), "Password cannot be less than 8 characters!", Toast.LENGTH_LONG).show();*/
                    user_password.setError("Password cannot be less than 8 characters!");
                    return;
                } else {
                    insert(user_name.getText().toString(), user_email.getText().toString(), user_password.getText().toString(),v);
                }
            }
        });


        img_RegisterPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                isClicked = isClicked ? false : true;
                if (isClicked) {
                    img_RegisterPassword.setImageResource(R.drawable.visible_eye);

                    user_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                   // Toast.makeText(UserRegistrationActivity.this, user_password.getText().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    img_RegisterPassword.setImageResource(R.drawable.invisible_eye);
                    user_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }



            }
        });
        loginAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserRegistrationActivity.this, UserLoginActivity.class);
                startActivity(i);
            }
        });
    }

    private void insert(String name, String email, String password, final View v) {
        commonUtil.loading(UserRegistrationActivity.this);
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

        Call<RegistrationUserResponse> getBrandResponseCall = apiInterface.SET_USER_RESPONSE_CALL("qw", name, email, password);

        getBrandResponseCall.enqueue(new Callback<RegistrationUserResponse>() {

            @Override
            public void onResponse(Call<RegistrationUserResponse> call, Response<RegistrationUserResponse> response) {
                commonUtil.cancelLoading();
                if (response.body().getStatus() == 1) {
                    Intent i = new Intent(UserRegistrationActivity.this, UserLoginActivity.class);
                    startActivity(i);

                } else {
                    Toast.makeText(UserRegistrationActivity.this, "Invalid Data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<RegistrationUserResponse> call, Throwable t) {
                commonUtil.cancelLoading();
                Log.d("123", t.getMessage());
                Snackbar.make(v, "Can't connect. Check if you're online.", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}
