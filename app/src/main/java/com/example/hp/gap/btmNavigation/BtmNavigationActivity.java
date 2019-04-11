package com.example.hp.gap.btmNavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.gap.AboutClassInfo.ClassAbout;
import com.example.hp.gap.ClassesList.StudentClassList;
import com.example.hp.gap.ClassesList.TeacherClassList;
import com.example.hp.gap.R;
import com.example.hp.gap.SharedPreferences.PreferenceManager;
import com.example.hp.gap.SideNavigationDrawer.NavigationDrawer;
import com.example.hp.gap.btmNavFragment.ClassFragment;
import com.example.hp.gap.btmNavFragment.FilesFragment;
import com.example.hp.gap.btmNavFragment.PeopleFragment;
import com.example.hp.gap.btmNavFragment.StreamFragment;
import com.squareup.picasso.Picasso;

public class BtmNavigationActivity extends NavigationDrawer {

    private TextView mTextMessage;
    FragmentManager manager;
    String className;
    int classInt;
    FragmentTransaction transaction;
    /* FragmentTransaction transaction;*/
    ClassFragment classFragment;
    StreamFragment streamFragment;
    PreferenceManager preferenceManager;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_stream:

                    // set Fragmentclass Arguments
                    fragment = new StreamFragment();
                    break;

                case R.id.navigation_classwork:
                    fragment = new FilesFragment();
                    break;
                case R.id.navigation_people:
                    fragment = new PeopleFragment();
                    break;

            }
            transaction = manager.beginTransaction();
            transaction.replace(R.id.f1, fragment).commit();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_btm_navigation);
        super.onCreate(savedInstanceState);

        //Start for navigation drawer
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.frameDisplay); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_btm_navigation, contentFrameLayout);
        //End for navigation drawer

        mTextMessage = (TextView) findViewById(R.id.message);
        Intent i = getIntent();
        preferenceManager = new PreferenceManager(this);
        preferenceManager.setActivity("BtmNavigationActivity");
        preferenceManager.setCurrentActivity("BtmNavigationActivity");
       /* className = i.getStringExtra("cname");
        classInt = i.getIntExtra("c_id", 1);*/
        Toast.makeText(this, preferenceManager.getClassName() + ", " + Integer.toString(preferenceManager.getClassId()), Toast.LENGTH_SHORT).show();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        manager = getSupportFragmentManager();
       /* Bundle bundle = new Bundle();
        bundle.putString("cname", className);
        bundle.putInt("c_id", classInt);
*/
        // set Fragmentclass Arguments

        StreamFragment streamFragment = new StreamFragment();
        // streamFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.f1, streamFragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.info) {
            Intent i = new Intent(getApplicationContext(), ClassAbout.class);
            startActivity(i);
        }
        if (item.getItemId() == R.id.refresh) {
            StreamFragment streamFragment = new StreamFragment();
            // streamFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.f1, streamFragment)
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(preferenceManager.getPanel()==1){
            Intent i=new Intent(BtmNavigationActivity.this, TeacherClassList.class);
            startActivity(i);
        }
        if(preferenceManager.getPanel()==0){
            Intent i=new Intent(BtmNavigationActivity.this, StudentClassList.class);
            startActivity(i);
        }
        super.onBackPressed();
    }
}
