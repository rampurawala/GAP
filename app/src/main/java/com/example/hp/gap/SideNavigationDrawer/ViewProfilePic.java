package com.example.hp.gap.SideNavigationDrawer;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hp.gap.APIinterface.APIinterface;
import com.example.hp.gap.APImanager.APIClient;
import com.example.hp.gap.APIresponse.ServerImageResponse;
import com.example.hp.gap.R;
import com.example.hp.gap.SharedPreferences.PreferenceManager;
import com.example.hp.gap.btmNavFragment.StreamFragment;
import com.example.hp.gap.btmNavigation.BtmNavigationActivity;
import com.example.hp.gap.utils.CommonUtil;
import com.example.hp.gap.utils.TouchImageView;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProfilePic extends AppCompatActivity {
    TouchImageView tv;

    PreferenceManager preferenceManager;
ImageButton done;
    String imageEncoded;
    String mImageuriString;
    CommonUtil commonUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Profile Pic");
        setContentView(R.layout.activity_view_profile_pic);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // fabbtn=findViewById(R.id.fabbtn);
        preferenceManager = new PreferenceManager(this);
        Intent i = getIntent();
        commonUtil=new CommonUtil();
        tv = findViewById(R.id.frag_imageview);
       done=findViewById(R.id.done);

        Picasso.with((this)).load(i.getStringExtra("url")).placeholder(R.drawable.person_default_theme).into(tv);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file=new File(imageEncoded);
                if((file.length()/1024)/1024 > 1){
                    Toast.makeText(ViewProfilePic.this, "file has size > 1 mb..size should be les than 1 mb", Toast.LENGTH_SHORT).show();
                }else {
                    uploadImage(imageEncoded);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_profilepic, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if (id == R.id.edit_pic) {
            //fabbtn.setVisibility(View.VISIBLE);
            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, 1);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            // When an Image is picked
            if (requestCode == 1 && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data
                //   menu.getItem(2).setEnabled(true);
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                if (data.getData() != null) {

                    Uri mImageUri = data.getData();


                    mImageuriString = mImageUri.toString();
                    Picasso.with((this)).load(mImageuriString).placeholder(R.drawable.person_default_theme).into(tv);

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);
                //uploadImage(imageEncoded);

                    cursor.close();

                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private void uploadImage(String mediaPath) {
        commonUtil.loading(ViewProfilePic.this);
        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(mediaPath);
        // Parsing any Media type file
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file1", file.getName(), requestBody1);
        RequestBody id =
                RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(preferenceManager.getRegisteredUserId()));

        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);
        Call<ServerImageResponse> call = apiInterface.uploadProfileFileImage(fileToUpload1, id);
        call.enqueue(new Callback<ServerImageResponse>() {
            @Override
            public void onResponse(Call<ServerImageResponse> call, Response<ServerImageResponse> response) {
                commonUtil.cancelLoading();
                ServerImageResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        preferenceManager.setRegisteredProfilePic(mImageuriString);
                        Intent i = new Intent(ViewProfilePic.this, BtmNavigationActivity.class);
                        startActivity(i);
                        ViewProfilePic.this.finish();
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }

            }

            @Override
            public void onFailure(Call<ServerImageResponse> call, Throwable t) {
                commonUtil.cancelLoading();
                Log.d("123", t.getMessage());
                Toast.makeText(ViewProfilePic.this,"Connection time out!!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, BtmNavigationActivity.class);
        startActivity(i);
        super.onBackPressed();
    }
}
