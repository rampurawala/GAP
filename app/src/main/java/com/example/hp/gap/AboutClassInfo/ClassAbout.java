package com.example.hp.gap.AboutClassInfo;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.gap.APIinterface.APIinterface;
import com.example.hp.gap.APImanager.APIClient;
import com.example.hp.gap.APIresponse.ChangeClassCode_Response;
import com.example.hp.gap.APIresponse.ChangePasswordResponse;
import com.example.hp.gap.ChangePasswordActiviy;
import com.example.hp.gap.R;
import com.example.hp.gap.SharedPreferences.PreferenceManager;
import com.example.hp.gap.UserLoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassAbout extends AppCompatActivity {
TextView cc,sn,tn,cn;
LinearLayout cc_view,tn_view;
Button chnge_code;
PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Class Info");
        setContentView(R.layout.activity_class_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cc=findViewById(R.id.cc);
        cc_view=findViewById(R.id.cc_view);
        tn=findViewById(R.id.tn);
        tn_view=findViewById(R.id.tn_view);
        sn=findViewById(R.id.sn);
        cn=findViewById(R.id.cn);
        chnge_code=findViewById(R.id.chngcode);
        preferenceManager=new PreferenceManager(this);

        if(preferenceManager.getPanel()==1){
            cc_view.setVisibility(View.VISIBLE);
            cc.setText(preferenceManager.getCCode());
            cn.setText(preferenceManager.getClassName());
            sn.setText(preferenceManager.getCSubject());
            chnge_code.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                            //creating a popup menu
                            PopupMenu popup = new PopupMenu(ClassAbout.this, chnge_code);
                            //inflating menu from xml resource
                            popup.inflate(R.menu.change_code);
                            //adding click listener
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    switch (item.getItemId()) {
                                        case R.id.changeCode:
                                            //handle menu1 click
                                            updateCode(preferenceManager.getClassId(),v);
                                            break;

                           /* case R.id.menu2:
                                //handle menu2 click
                                break;
                            case R.id.menu3:
                                //handle menu3 click
                                break;*/
                                    }
                                    return false;
                                }
                            });
                            //displaying the popup
                            popup.show();

                }
            });
        }

        if(preferenceManager.getPanel()==0){
            tn_view.setVisibility(View.VISIBLE);
            tn.setText(preferenceManager.getCTeacherName());
            cn.setText(preferenceManager.getClassName());
            sn.setText(preferenceManager.getCSubject());
        }
    }







    private void updateCode(int classid, final View v) {
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

        Call<ChangeClassCode_Response> getBrandResponseCall = apiInterface.SET_CLASSCODE_RESPONSE_CALL("qw", classid);

        getBrandResponseCall.enqueue(new Callback<ChangeClassCode_Response>() {

            @Override
            public void onResponse(Call<ChangeClassCode_Response> call, Response<ChangeClassCode_Response> response) {
                if (response.body().getSuccess() == 200) {

                    cc.setText(response.body().getCCode());
                    preferenceManager.setCCode(response.body().getCCode());

                } else {
                    Toast.makeText(ClassAbout.this, "Invalid Data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ChangeClassCode_Response> call, Throwable t) {
                Log.d("123", t.getMessage());
                Snackbar.make(v, "Can't connect. Check if you're online.", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });
    }


}
