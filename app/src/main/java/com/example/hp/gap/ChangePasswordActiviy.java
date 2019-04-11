package com.example.hp.gap;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hp.gap.APIinterface.APIinterface;
import com.example.hp.gap.APImanager.APIClient;
import com.example.hp.gap.APIresponse.ChangePasswordResponse;
import com.example.hp.gap.APIresponse.RegistrationUserResponse;
import com.example.hp.gap.SharedPreferences.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActiviy extends AppCompatActivity {
    EditText confirm_password, new_password;
    Button change;
    PreferenceManager preferenceManager;
    ImageView img_newPassword, img_confirmPassword;
    private Boolean isClicked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Change Password");
        setContentView(R.layout.activity_change_password_activiy);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        confirm_password = findViewById(R.id.confirm_password);
        new_password = findViewById(R.id.new_password);
        change = findViewById(R.id.done);
        img_newPassword=findViewById(R.id.img_newPassword);
        img_confirmPassword=findViewById(R.id.img_confirmPassword);
        preferenceManager = new PreferenceManager(this);
        preferenceManager.setCurrentActivity("ChangePasswordActiviy");
        img_newPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isClicked = isClicked ? false : true;
                if (isClicked) {
                    img_newPassword.setImageResource(R.drawable.visible_eye);

                    new_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                   // Toast.makeText(ChangePasswordActiviy.this, new_password.getText().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    img_newPassword.setImageResource(R.drawable.invisible_eye);
                    new_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }

            }
        });
        img_confirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isClicked = isClicked ? false : true;
                if (isClicked) {
                    img_confirmPassword.setImageResource(R.drawable.visible_eye);

                    confirm_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    //Toast.makeText(ChangePasswordActiviy.this, confirm_password.getText().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    img_confirmPassword.setImageResource(R.drawable.invisible_eye);
                    confirm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new_password.getText().toString().isEmpty()) {
                    new_password.setError("Enter new Password");
                } else if (new_password.length() < 8) {
                    new_password.setError(" Password length must be equal to or more than 8");
                } else if (confirm_password.getText().toString().isEmpty()) {
                    confirm_password.setError("Enter new Password");
                } else if (!new_password.getText().toString().equals(confirm_password.getText().toString())) {
                    confirm_password.setError("New Password and Confirm password must be same!");
                } else {
                    updatePassword(new_password.getText().toString(), v);
                }

            }
        });
    }


    private void updatePassword(String password, final View v) {
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

        Call<ChangePasswordResponse> getBrandResponseCall = apiInterface.GET_CHANGE_PASSWORD_RESPONSE_CALL("qw", preferenceManager.getRegisteredUserId(), password);

        getBrandResponseCall.enqueue(new Callback<ChangePasswordResponse>() {

            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                if (response.body().getSuccess() == 200) {
                    Intent i = new Intent(ChangePasswordActiviy.this, UserLoginActivity.class);
                    startActivity(i);
                    ChangePasswordActiviy.this.finish();
                } else {
                    Toast.makeText(ChangePasswordActiviy.this, "Invalid Data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                Log.d("123", t.getMessage());
                Snackbar.make(v, "Can't connect. Check if you're online.", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });
    }


}
