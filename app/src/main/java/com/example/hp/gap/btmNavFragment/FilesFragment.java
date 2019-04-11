package com.example.hp.gap.btmNavFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hp.gap.APIinterface.APIinterface;
import com.example.hp.gap.APImanager.APIClient;
import com.example.hp.gap.APIresponse.ClassTeacherListResponse;
import com.example.hp.gap.APIresponse.ClassTeacherResponse;
import com.example.hp.gap.APIresponse.FileListAll;
import com.example.hp.gap.APIresponse.FileListAllListResponse;
import com.example.hp.gap.GetterSetter.ClassGtSt;
import com.example.hp.gap.GetterSetter.StreamGtSt;
import com.example.hp.gap.R;
import com.example.hp.gap.SharedPreferences.PreferenceManager;
import com.example.hp.gap.adapterFiles.AdapterClassFragment;
import com.example.hp.gap.adapterFiles.AdapterFileFragment;
import com.example.hp.gap.adapterFiles.AdapterTeacherPeople;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilesFragment extends Fragment {
    RecyclerView rc;
    PreferenceManager preferenceManager;

    public FilesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       // getActivity().setTitle(preferenceManager.getClassName());
        View view = inflater.inflate(R.layout.fragment_files, container, false);
        rc = (RecyclerView) view.findViewById(R.id.rc);
        preferenceManager = new PreferenceManager(getContext());
        rc.setLayoutManager(new LinearLayoutManager(this.getActivity()));
       /* ArrayList<ClassGtSt> itemModals=new ArrayList<ClassGtSt>();
        ClassGtSt modal[] = {
                new ClassGtSt(R.drawable.avtar, "1234", "9:52"),
                new ClassGtSt(R.drawable.avtar, "1234", "10:00"),
                new ClassGtSt(R.drawable.avtar, "1234", "05:08"),
                new ClassGtSt(R.drawable.avtar, "1234", "11:52"),
                new ClassGtSt(R.drawable.avtar, "1234", "07:08")
        };
        for (int i = 0; i < modal.length; i++) {
            itemModals.add(modal[i]);
        }*/
       /* AdapterClassFragment adapter = new AdapterClassFragment(this.getActivity(), itemModals);
        rc.setAdapter(adapter);*/

        loadFilesAll();
        return view;
    }


    private void loadFilesAll() {

        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

        Call<FileListAllListResponse> getBrandResponseCall = apiInterface.GET_FILE_LIST_ALL_RESPONSE_CALL("qw", preferenceManager.getClassId(), preferenceManager.getRegisteredUserId());

        getBrandResponseCall.enqueue(new Callback<FileListAllListResponse>() {

            @Override
            public void onResponse(Call<FileListAllListResponse> call, Response<FileListAllListResponse> response) {

                if (response.body().getSuccess() == 200) {

                    List<FileListAll> FileResponses = response.body().getFileListAll();
                    AdapterFileFragment adapter = new AdapterFileFragment(getActivity(), FileResponses);
                    rc.setAdapter(adapter);

                } else {
                    Toast.makeText(getContext(), "Invalid Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FileListAllListResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Cannot connect!", Toast.LENGTH_SHORT).show();
                Log.d("123", t.getMessage());
            }
        });
    }


}


