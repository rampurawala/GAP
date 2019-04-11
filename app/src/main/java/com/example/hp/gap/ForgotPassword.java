package com.example.hp.gap;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.gap.APIinterface.APIinterface;
import com.example.hp.gap.APImanager.APIClient;
import com.example.hp.gap.APIresponse.ForgotPasswordResponse;
import com.example.hp.gap.APIresponse.LoginResponse;
import com.example.hp.gap.SelectOption.SelectOptionActivity;
import com.example.hp.gap.utils.CommonUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {
    EditText forgot_email;
    Button send;
    CommonUtil commonUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Forgot Password");
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        send = findViewById(R.id.send);
        forgot_email = findViewById(R.id.forgot_email);
        commonUtil = new CommonUtil();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail(forgot_email.getText().toString(), v);
            }
        });
    }

    private void sendMail(String email, final View v) {
        commonUtil.loading(ForgotPassword.this);
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

        Call<ForgotPasswordResponse> getBrandResponseCall = apiInterface.GET_FORGOT_PASSWORD_RESPONSE_CALL("qw", email);

        getBrandResponseCall.enqueue(new Callback<ForgotPasswordResponse>() {

            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                commonUtil.cancelLoading();
                if (response.body().getSuccess() == 203) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this);
                    builder.setMessage("Mail send Successfully")
                            .setTitle("Mail Sended")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    //do things
                                    Intent i = new Intent(ForgotPassword.this, UserLoginActivity.class);
                                    startActivity(i);
                                    ForgotPassword.this.finish();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    /*Intent i = new Intent(ForgotPassword.this, UserLoginActivity.class);
                    startActivity(i);*/
                    //Toast.makeText(ForgotPassword.this, "Password send Successfully to Registered Email!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ForgotPassword.this, "Invalid Email or Enter Registered Email", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                commonUtil.cancelLoading();
                Log.d("123", t.getMessage());
                Snackbar.make(v, "Can't connect. Check if you're online.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

}
