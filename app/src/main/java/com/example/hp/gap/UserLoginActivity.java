package com.example.hp.gap;

import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.annotation.MediumTest;
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
import com.example.hp.gap.APIresponse.LoginResponse;
import com.example.hp.gap.APIresponse.RegistrationUserResponse;
import com.example.hp.gap.ClassesList.TeacherClassList;
import com.example.hp.gap.SelectOption.SelectOptionActivity;
import com.example.hp.gap.SharedPreferences.PreferenceManager;
import com.example.hp.gap.utils.CommonUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginActivity extends AppCompatActivity {
Button login;
EditText email,password;
TextView createAccount,forgotPassword;
PreferenceManager preferenceManager;
    private Boolean isClicked = false;
    ImageView img_LoginPassword;

    CommonUtil commonUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* setTitle("");*/
        setContentView(R.layout.activity_user_login);

      /*  getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        login=findViewById(R.id.login);
        email=findViewById(R.id.email);
        createAccount=findViewById(R.id.createAccount);
        forgotPassword=findViewById(R.id.forgotPassword);
        password=findViewById(R.id.password);
        img_LoginPassword=findViewById(R.id.img_LoginPassword);
        preferenceManager = new PreferenceManager(this);
        preferenceManager.setCurrentActivity("UserLoginActivity");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login(email.getText().toString(),password.getText().toString(),v);
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i=new Intent(UserLoginActivity.this,ForgotPassword.class);
               startActivity(i);
            }
        });
        commonUtil = new CommonUtil();
        img_LoginPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                isClicked = isClicked ? false : true;
                if (isClicked) {
                    img_LoginPassword.setImageResource(R.drawable.visible_eye);

                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                  //  Toast.makeText(UserLoginActivity.this, password.getText().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    img_LoginPassword.setImageResource(R.drawable.invisible_eye);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }


            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserLoginActivity.this, UserRegistrationActivity.class);
                startActivity(i);

            }
        });
    }

    private void login(String email, String password, final View v) {
        commonUtil.loading(UserLoginActivity.this);
        APIinterface apiInterface= APIClient.getClient().create(APIinterface.class);

        Call<LoginResponse> getBrandResponseCall=apiInterface.LOGIN_RESPONSE_CALL("qw",email,password);

        getBrandResponseCall.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                commonUtil.cancelLoading();
                if(response.body().getSuccess()==200){

                    preferenceManager.setRegisteredUserId(Integer.parseInt(response.body().getUId()));
                    preferenceManager.setRegisteredUserName(response.body().getUName());
                    preferenceManager.setRegisteredUserEmail(response.body().getUEmail());
                    preferenceManager.setRegisteredProfilePic(response.body().getProfilePic());
                   preferenceManager.setCheck(0);
                    Intent i = new Intent(UserLoginActivity.this, SelectOptionActivity.class);
                    startActivity(i);
                    UserLoginActivity.this.finish();

                }else {

                    Toast.makeText(UserLoginActivity.this, "Invalid Data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
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
