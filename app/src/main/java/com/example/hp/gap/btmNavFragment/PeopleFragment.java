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
import com.example.hp.gap.APIresponse.ClassStudentListResponse;
import com.example.hp.gap.APIresponse.ClassStudentResponse;
import com.example.hp.gap.APIresponse.ClassTeacherListResponse;
import com.example.hp.gap.APIresponse.ClassTeacherResponse;
import com.example.hp.gap.APIresponse.TeacherMessageList;
import com.example.hp.gap.ClassShare.ClassShare;
import com.example.hp.gap.GetterSetter.PeopleGtSt;
import com.example.hp.gap.GetterSetter.StreamGtSt;
import com.example.hp.gap.R;
import com.example.hp.gap.SharedPreferences.PreferenceManager;
import com.example.hp.gap.adapterFiles.AdapterPeopleFragment;
import com.example.hp.gap.adapterFiles.AdapterStreamFragment;
import com.example.hp.gap.adapterFiles.AdapterStudentPeople;
import com.example.hp.gap.adapterFiles.AdapterTeacherPeople;
import com.example.hp.gap.adapterFiles.SpinnerStudentListAdapter;
import com.example.hp.gap.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PeopleFragment extends Fragment {

    RecyclerView rcTeacher, rcStudent;
    PreferenceManager preferenceManager;
CommonUtil commonUtil;
    public PeopleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        preferenceManager = new PreferenceManager(getContext());
        getActivity().setTitle(preferenceManager.getClassName()+"People List");
        View view = inflater.inflate(R.layout.fragment_people, container, false);
        rcTeacher = view.findViewById(R.id.teacher);
        rcStudent = view.findViewById(R.id.student);
       commonUtil=new CommonUtil();
        /* rcTeacher.setHasFixedSize(true);
        rcStudent.setHasFixedSize(true);*/

        preferenceManager = new PreferenceManager(getContext());
        rcTeacher.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rcStudent.setLayoutManager(new LinearLayoutManager(this.getActivity()));
       /* ArrayList<PeopleGtSt> itemModalt = new ArrayList<PeopleGtSt>();
        ArrayList<PeopleGtSt> itemModals = new ArrayList<PeopleGtSt>();
        PeopleGtSt modal[] = {
                new PeopleGtSt("Jameela", "1", R.drawable.avtar),
                new PeopleGtSt("Ankita", "0", R.drawable.avtar),
                new PeopleGtSt("Rupal", "1", R.drawable.avtar),
                new PeopleGtSt("James", "1", R.drawable.avtar),
                new PeopleGtSt("Trupti", "1", R.drawable.avtar)
        };
        for (int i = 0; i < modal.length; i++) {
            if (modal[i].getEvent().equals("0")) {
                itemModalt.add(modal[i]);
            } else if (modal[i].getEvent().equals("1")) {
                itemModals.add(modal[i]);
            }

        }*/
        // int classId = getArguments().getInt("c_id");
        loadTeacher(preferenceManager.getClassId());
        loadStudent(preferenceManager.getClassId());
        Toast.makeText(getContext(), String.valueOf(preferenceManager.getClassId()), Toast.LENGTH_SHORT).show();
       /* AdapterPeopleFragment adapters = new AdapterPeopleFragment(this.getActivity(), itemModals);
        AdapterPeopleFragment adaptert = new AdapterPeopleFragment(this.getActivity(), itemModalt);
        rcStudent.setAdapter(adapters);
        rcTeacher.setAdapter(adaptert);
*/
        return view;
    }

    private void loadTeacher(int classsId) {
        commonUtil.loading(getContext());
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

        Call<ClassTeacherListResponse> getBrandResponseCall = apiInterface.GET_TEACHER_LIST_RESPONSE_CALL("qw", classsId);

        getBrandResponseCall.enqueue(new Callback<ClassTeacherListResponse>() {

            @Override
            public void onResponse(Call<ClassTeacherListResponse> call, Response<ClassTeacherListResponse> response) {
                commonUtil.cancelLoading();
                if (response.body().getSuccess() == 200) {

                    List<ClassTeacherResponse> classTeacherResponses = response.body().getClassTeacher();
                    AdapterTeacherPeople adapter = new AdapterTeacherPeople(getActivity(), classTeacherResponses);
                    rcTeacher.setAdapter(adapter);

                } else {
                    Toast.makeText(getContext(), "Invalid Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ClassTeacherListResponse> call, Throwable t) {
                commonUtil.cancelLoading();
                Log.d("123", t.getMessage());
            }
        });
    }


    private void loadStudent(int classId) {
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);
        Call<ClassStudentListResponse> getBrandResponseCall = apiInterface.GET_STUDENT_LIST_RESPONSE_CALL("qw", classId);
        getBrandResponseCall.enqueue(new Callback<ClassStudentListResponse>() {
            @Override
            public void onResponse(Call<ClassStudentListResponse> call, Response<ClassStudentListResponse> response) {
                if (response.body().getSuccess() == 200) {
                    List<ClassStudentResponse> classStudentResponses = response.body().getStudentList();
                    if (classStudentResponses.isEmpty()) {
                        Toast.makeText(getContext(), "No Student Yet", Toast.LENGTH_SHORT).show();
                    } else {
                        AdapterStudentPeople adapter = new AdapterStudentPeople(getActivity(), classStudentResponses);
                        rcStudent.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(getContext(), "Invalid Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ClassStudentListResponse> call, Throwable t) {
                Log.d("123", t.getMessage());
            }
        });
    }


}
