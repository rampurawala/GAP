package com.example.hp.gap.btmNavFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.gap.APIinterface.APIinterface;
import com.example.hp.gap.APImanager.APIClient;
import com.example.hp.gap.APIresponse.MessageList;
import com.example.hp.gap.APIresponse.TeacherListResponse;
import com.example.hp.gap.APIresponse.TeacherMessageList;
import com.example.hp.gap.AboutClassInfo.ClassAbout;
import com.example.hp.gap.ClassShare.ClassShare;
import com.example.hp.gap.ClassesList.TeacherClassList;
import com.example.hp.gap.GetterSetter.StreamGtSt;
import com.example.hp.gap.R;
import com.example.hp.gap.SharedPreferences.PreferenceManager;
import com.example.hp.gap.adapterFiles.AdapterStreamFragment;
import com.example.hp.gap.adapterFiles.AdapterTeacherClassList;
import com.example.hp.gap.utils.CommonUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class StreamFragment extends Fragment /*implements TabLayout.OnTabSelectedListener*/ {
    FragmentManager manager;
    FragmentTransaction transaction;
    TabLayout tab;
    ViewPager view;
    String classname;
    PreferenceManager preferenceManager;
    int classsId;
    TextView classname1, classSubject;
    FilesFragment filesFragment;
    RecyclerView rc;
    TextView share;
    ImageView share_class_pic;
    CommonUtil commonUtil;
    List<MessageList> MessageResponses = new ArrayList<MessageList>();


    public StreamFragment() {

    }

    /*public static StreamFragment newInstance(String cname, int c_id) {
        Bundle bundle = new Bundle();
        bundle.putString("name", cname);
        bundle.putInt("class_id", c_id);

        StreamFragment fragment = new StreamFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            classname = bundle.getString("name");
            classsId = bundle.getInt("class_id");
        }
    }
*/

/*

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view1 = inflater.inflate(R.layout.fragment_stream, container, false);
        manager = getFragmentManager();
        tab = view1.findViewById(R.id.tab);
        view = view1.findViewById(R.id.viewpager);
        view.setAdapter(new StreamFragment.adapter(manager,tab.getTabCount()));
        tab.setOnTabSelectedListener(this);
        view.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));

        return view1;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public class adapter extends FragmentPagerAdapter {
        int mNumOfTabs;
        public adapter(FragmentManager fm,int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment frg = null;
            if (position == 0) {
               frg = new FilesFragment();

            }
            if (position == 1) {
                frg = new FilesFragment();

            }
            return frg;
        }

        @Override
        public int getCount() {
            return  mNumOfTabs;
        }
    }
*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //set title bar
        getActivity().setTitle("");


        View view1 = inflater.inflate(R.layout.fragment_stream, container, false);
        rc = view1.findViewById(R.id.stream);
        rc.setHasFixedSize(true);

        share = view1.findViewById(R.id.share);
        share_class_pic=view1.findViewById(R.id.share_class_pic);
        preferenceManager = new PreferenceManager(this.getActivity());

        classname1 = view1.findViewById(R.id.tv_classname);
        classSubject = view1.findViewById(R.id.tv_subjectname);
        commonUtil=new CommonUtil();
        /* readBundle(getArguments());*/
       /* classname = getArguments().getString("cname");
        classsId = getArguments().getInt("c_id");*/
        classname1.setText(preferenceManager.getClassName());
        classSubject.setText(preferenceManager.getCSubject());
        Picasso.with((getContext())).load(preferenceManager.getRegisteredProfilePic()).placeholder(R.drawable.person_default_theme).into(share_class_pic);

        rc.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        //for oncreate option menu
        setHasOptionsMenu(true);

       /* if (preferenceManager.getPanel() == 1) {
            loadMessageList();
        }*/


        loadMessageList();


        /*ArrayList<StreamGtSt> itemModals = new ArrayList<StreamGtSt>();
        StreamGtSt modal[] = {
                new StreamGtSt(R.drawable.avtar, "1234", "9:52", "hii"),
                new StreamGtSt(R.drawable.avtar, "1234", "10:00", "hello"),
                new StreamGtSt(R.drawable.avtar, "1234", "05:08", ""),
                new StreamGtSt(R.drawable.avtar, "1234", "11:52", ""),
                new StreamGtSt(R.drawable.avtar, "1234", "07:08", "hey Message  Assignment name or message sender name ")
        };
        for (int i = 0; i < modal.length; i++) {
            itemModals.add(modal[i]);
        }
        AdapterStreamFragment adapter = new AdapterStreamFragment(this.getActivity(), itemModals);
        rc.setAdapter(adapter);
*/
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceManager.setSEND_STATUS("200");
                Intent i = new Intent(getContext(), ClassShare.class);
                startActivity(i);
            }
        });
        return view1;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.streamactionbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }



    private void loadMessageList() {
        commonUtil.loading(getContext());
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

        Call<TeacherMessageList> getBrandResponseCall = apiInterface.GET_MESSAGE_LIST_RESPONSE_CALL("qw", preferenceManager.getClassId(),preferenceManager.getRegisteredUserId());

        getBrandResponseCall.enqueue(new Callback<TeacherMessageList>() {

            @Override
            public void onResponse(Call<TeacherMessageList> call, Response<TeacherMessageList> response) {
                commonUtil.cancelLoading();
                if (response.body().getSuccess() == 200) {

                    MessageResponses = response.body().getMessageList();
                    Log.e("##", String.valueOf(response.body().getMessageList().size()));
                    AdapterStreamFragment listAdapter = new AdapterStreamFragment(getActivity(), MessageResponses);
                    rc.setAdapter(listAdapter);


                } else {
                    Toast.makeText(getContext(), "Invalid Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TeacherMessageList> call, Throwable t) {
                commonUtil.cancelLoading();
                Toast.makeText(getActivity(), "Cannot connect!", Toast.LENGTH_SHORT).show();
                Log.d("123", t.getMessage());
            }
        });
    }


}
