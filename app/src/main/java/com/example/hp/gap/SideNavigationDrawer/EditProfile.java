package com.example.hp.gap.SideNavigationDrawer;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.gap.APIinterface.APIinterface;
import com.example.hp.gap.APImanager.APIClient;
import com.example.hp.gap.APIresponse.ChangeClassCode_Response;
import com.example.hp.gap.APIresponse.ChangePasswordResponse;
import com.example.hp.gap.AboutClassInfo.ClassAbout;
import com.example.hp.gap.ChangePasswordActiviy;
import com.example.hp.gap.ClassesList.StudentClassList;
import com.example.hp.gap.ClassesList.TeacherClassList;
import com.example.hp.gap.R;
import com.example.hp.gap.SharedPreferences.PreferenceManager;
import com.example.hp.gap.btmNavigation.BtmNavigationActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {
    EditText name, email;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        preferenceManager = new PreferenceManager(EditProfile.this);
        name = findViewById(R.id.update_email);
        email = findViewById(R.id.update_name);
        name.setText(preferenceManager.getRegisteredUserName());
        email.setText(preferenceManager.getRegisteredUserEmail());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.update_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.done) {
            updateProfile(name.getText().toString(), email.getText().toString());
        }

        return super.onOptionsItemSelected(item);
    }


    private void updateProfile(final String name, final String email) {
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

        Call<ChangePasswordResponse> getBrandResponseCall = apiInterface.UPDATE_PROFILE("qw", name, email, preferenceManager.getRegisteredUserId());

        getBrandResponseCall.enqueue(new Callback<ChangePasswordResponse>() {

            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                if (response.body().getSuccess() == 200) {
                    preferenceManager.setRegisteredUserName(name);
                    preferenceManager.setRegisteredUserEmail(email);
                    if(preferenceManager.getPanel()==1){
                        Intent i=new Intent(EditProfile.this, TeacherClassList.class);
                        startActivity(i);
                    }
                    if(preferenceManager.getPanel()==0){
                        Intent i=new Intent(EditProfile.this, StudentClassList.class);
                        startActivity(i);
                    }
                } else {
                    Toast.makeText(EditProfile.this, "Invalid Data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                Log.d("123", t.getMessage());
             //   Snackbar.make(v, "Can't connect. Check if you're online.", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });
    }

}
