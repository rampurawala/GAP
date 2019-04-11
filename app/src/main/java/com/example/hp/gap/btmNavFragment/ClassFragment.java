package com.example.hp.gap.btmNavFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.gap.GetterSetter.ClassGtSt;
import com.example.hp.gap.GetterSetter.StreamGtSt;
import com.example.hp.gap.R;
import com.example.hp.gap.adapterFiles.AdapterClassFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassFragment extends Fragment {
    RecyclerView rc;

    public ClassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_class, container, false);

        rc=(RecyclerView)view.findViewById(R.id.rc_class);
        rc.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        ArrayList<ClassGtSt> itemModals=new ArrayList<ClassGtSt>();
        ClassGtSt modal[] = {
                new ClassGtSt(R.drawable.avtar, "1234", "9:52"),
                new ClassGtSt(R.drawable.avtar, "1234", "10:00"),
                new ClassGtSt(R.drawable.avtar, "1234", "05:08"),
                new ClassGtSt(R.drawable.avtar, "1234", "11:52"),
                new ClassGtSt(R.drawable.avtar, "1234", "07:08")
        };
        for (int i = 0; i < modal.length; i++) {
            itemModals.add(modal[i]);
        }
        AdapterClassFragment adapter = new AdapterClassFragment(this.getActivity(), itemModals);
        rc.setAdapter(adapter);
        return view;
    }

}
