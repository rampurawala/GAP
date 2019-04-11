package com.example.hp.gap.SideNavigationDrawer;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.gap.AboutClassInfo.ClassAbout;
import com.example.hp.gap.ChangePasswordActiviy;
import com.example.hp.gap.ClassShare.ClassShare;
import com.example.hp.gap.ClassShare.ImageVideoGtSt;
import com.example.hp.gap.ClassesList.StudentClassList;
import com.example.hp.gap.ClassesList.TeacherClassList;
import com.example.hp.gap.CreateClass.CreateClassActivity;
import com.example.hp.gap.R;
import com.example.hp.gap.SharedPreferences.PreferenceManager;
import com.example.hp.gap.ToDoList.ToDOListActivity;
import com.example.hp.gap.UserLoginActivity;
import com.example.hp.gap.adapterFiles.RecyclerImageAdapter;
import com.example.hp.gap.btmNavigation.BtmNavigationActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView header_userName, header_userEmail;
    ImageView profilePic;
   //  ImageButton editProfile;
    PreferenceManager preferenceManager;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        preferenceManager = new PreferenceManager(this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View v = navigationView.getHeaderView(0);
        header_userEmail = v.findViewById(R.id.header_userEmail);
        header_userName = v.findViewById(R.id.header_userName);
        profilePic = v.findViewById(R.id.profilePic);
        //  editProfile = v.findViewById(R.id.editProfile);
        url = preferenceManager.getRegisteredProfilePic();
        Picasso.with((this)).load(url).placeholder(R.drawable.person_default_theme).into(profilePic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NavigationDrawer.this, ViewProfilePic.class);
                i.putExtra("url", url);
                startActivity(i);
                NavigationDrawer.this.finish();
                /*Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);
*/
            }
        });
       /* editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(NavigationDrawer.this,EditProfile.class);
                startActivity(i);
            }
        });*/

        header_userName.setText(preferenceManager.getRegisteredUserName());
        header_userEmail.setText(preferenceManager.getRegisteredUserEmail());
        Menu menu = navigationView.getMenu();
        if (preferenceManager.getPanel() == 1) {
            MenuItem target = menu.findItem(R.id.createClass);
            target.setVisible(true);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            // When an Image is picked
            if (requestCode == 1 && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                if (data.getData() != null) {

                    Uri mImageUri = data.getData();
                    profilePic.setImageURI(mImageUri);
                    String mImageuriString = mImageUri.toString();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imageEncoded = cursor.getString(columnIndex);


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


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.classes) {
            if (preferenceManager.getPanel() == 1) {
                if (preferenceManager.getCurrentActivity().contains("TeacherClassList")) {

                } else {
                    preferenceManager.setCurrentActivity("TeacherClassList");
                    Intent i = new Intent(NavigationDrawer.this, TeacherClassList.class);
                    startActivity(i);
                    NavigationDrawer.this.finish();
                }

            }
            if (preferenceManager.getPanel() == 0) {
                if (preferenceManager.getCurrentActivity().contains("StudentClassList")) {

                } else {
                    preferenceManager.setCurrentActivity("StudentClassList");
                    Intent i = new Intent(NavigationDrawer.this, StudentClassList.class);
                    startActivity(i);
                    NavigationDrawer.this.finish();
                }

            }
        }
        if (id == R.id.change_password) {
            if (preferenceManager.getActivity().contains("ChangePasswordActiviy")) {

            } else {
                preferenceManager.setCurrentActivity("ChangePasswordActiviy");
                Intent i = new Intent(NavigationDrawer.this, ChangePasswordActiviy.class);
                startActivity(i);
                NavigationDrawer.this.finish();
            }

        }
        if (id == R.id.logOut) {
            if (preferenceManager.getCurrentActivity().equals("UserLoginActivity")) {

            } else {
                preferenceManager.setCurrentActivity("UserLoginActivity");
                preferenceManager.setCheck(1);
                Intent i = new Intent(NavigationDrawer.this, UserLoginActivity.class);
                startActivity(i);
                NavigationDrawer.this.finish();
            }
        }
        if (id == R.id.createClass) {
            if (preferenceManager.getCurrentActivity().equals("CreateClassActivity")) {

            } else {
                preferenceManager.setCurrentActivity("CreateClassActivity");
                preferenceManager.setCheck(1);
                Intent i = new Intent(NavigationDrawer.this, CreateClassActivity.class);
                startActivity(i);
                NavigationDrawer.this.finish();
            }
        }

        if (id == R.id.msgList) {
            if (preferenceManager.getCurrentActivity().equals("BtmNavigationActivity")) {

            } else {
                preferenceManager.setCurrentActivity("BtmNavigationActivity");
                preferenceManager.setCheck(1);
                Intent i = new Intent(NavigationDrawer.this, BtmNavigationActivity.class);
                startActivity(i);
            }
        }

        if (id == R.id.todolist) {
            if (preferenceManager.getCurrentActivity().equals("ToDOListActivity")) {

            } else {
                preferenceManager.setCurrentActivity("ToDOListActivity");

                Intent i = new Intent(NavigationDrawer.this, ToDOListActivity.class);
                startActivity(i);
            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
