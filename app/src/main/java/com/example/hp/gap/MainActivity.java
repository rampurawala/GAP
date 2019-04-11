package com.example.hp.gap;

import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hp.gap.btmNavFragment.ClassFragment;
import com.example.hp.gap.btmNavFragment.FilesFragment;
import com.example.hp.gap.btmNavFragment.StreamFragment;

public class MainActivity extends AppCompatActivity {
    /*FragmentManager manager;
    FragmentTransaction transaction;
    ClassFragment streamFragment;*/
    ImageView imgshare;
    SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayout bottomsheetLayout;
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipenya);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
       bottomsheetLayout=findViewById(R.id.bottomsheet);
       imgshare=findViewById(R.id.shareimg);
       bottomSheetBehavior=BottomSheetBehavior.from(bottomsheetLayout);


       // state hidden
       bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        //state collapsed
        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        //state expand
        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        imgshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show BottomSheet
                if(bottomSheetBehavior.getState()== BottomSheetBehavior.STATE_EXPANDED ){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

                }else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                }

            }
        });
        /*manager = getSupportFragmentManager();*/
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // do something
                        Toast.makeText(MainActivity.this, "do something", Toast.LENGTH_LONG).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

    }



   /* public void StreamFrag(View view) {
        streamFragment = new ClassFragment();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fl, streamFragment, "o");
        transaction.commit();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }



/* @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }*/
}
