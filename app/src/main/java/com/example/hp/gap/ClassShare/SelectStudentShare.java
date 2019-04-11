package com.example.hp.gap.ClassShare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.gap.APIinterface.APIinterface;
import com.example.hp.gap.APImanager.APIClient;
import com.example.hp.gap.APIresponse.ClassStudentListResponse;
import com.example.hp.gap.APIresponse.ClassStudentResponse;
import com.example.hp.gap.R;
import com.example.hp.gap.SharedPreferences.PreferenceManager;
import com.example.hp.gap.adapterFiles.AdapterStudentClassShare;
import com.example.hp.gap.adapterFiles.AdapterStudentPeople;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectStudentShare extends Activity {
    PreferenceManager preferenceManager;
    ListView lstStudent;
    SharedPreferences sharedpreferences,namePreferrences;
    public static final String MyPREFERENCES = "MyUserChoice";
    public static final String NAMEPREFERENCES = "NameChoice";
    ArrayList<String> selectedItems = new ArrayList<String>();
    Button select, clear;
    String savename="",msg;
    List<ClassStudentResponse> classStudentResponses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Select Students");
        setContentView(R.layout.activity_select_student_share);
        preferenceManager = new PreferenceManager(this);
        lstStudent = findViewById(R.id.lstStudent);

        preferenceManager.setActivity("SelectStudentShare");
        loadStudent(preferenceManager.getClassId());

        Intent i=getIntent();
        msg=i.getStringExtra("msg");


        /*ClearSelections();*/

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        namePreferrences = getSharedPreferences(NAMEPREFERENCES, Context.MODE_PRIVATE);
       /* if(sharedpreferences.contains(MyPREFERENCES)){
            LoadSelections();

        }*/

        select = findViewById(R.id.select);
        clear = findViewById(R.id.clear);


        select.setOnClickListener(new Button.OnClickListener() {


            @Override
            public void onClick(View v) {
                ArrayList<String> selectedArray = new ArrayList<>();
                String[] selectedArrayString=new String[lstStudent.getCount()];
                String selected = "";
                int cntChoice = lstStudent.getCount();

                SparseBooleanArray sparseBooleanArray = lstStudent.getCheckedItemPositions();
                if(lstStudent.getCheckedItemCount()<1){
                    preferenceManager.setSEND_STATUS("200");
                    ClearSelections();
                }else {
                    for (int i = 0; i < cntChoice; i++) {

                        if (sparseBooleanArray.get(i)) {
                            selected += classStudentResponses.get(i).getUId() + "\n";
                            selectedArray.add(classStudentResponses.get(i).getUName());
                            selectedArrayString[i] = classStudentResponses.get(i).getUName();
                            System.out.println("Checking list while adding:" + classStudentResponses.get(i).getUId().toString());
                            SaveSelections();
                        }

                    }
                }
                Intent i = new Intent(SelectStudentShare.this, ClassShare.class);
                i.putExtra("selectedArrayString", selectedArrayString);
                i.putExtra("check","check");
                i.putStringArrayListExtra("selectedArray", selectedArray);
                i.putExtra("msg",msg);
                startActivity(i);
                SelectStudentShare.this.finish();
                Toast.makeText(SelectStudentShare.this, selected, Toast.LENGTH_LONG).show();

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                ClearSelections();

            }
        });

    }


    private void SaveSelections() {
// save the selections in the shared preference in private mode for the user

        SharedPreferences.Editor prefEditor = sharedpreferences.edit();
        SharedPreferences.Editor nameprefEditor = namePreferrences.edit();
        String savedItems = getSavedItems();
        prefEditor.putString(MyPREFERENCES.toString(), savedItems);
        nameprefEditor.putString(NAMEPREFERENCES.toString(), savename);
        prefEditor.commit();
        nameprefEditor.commit();
    }

    private String getSavedItems() {
        String savedItems="";
        savename="";
        int count = this.lstStudent.getAdapter().getCount();
        for (int i = 0; i < count; i++) {
            if (this.lstStudent.isItemChecked(i)) {
                if (savedItems.length() > 0) {
                    savedItems += "," + classStudentResponses.get(i).getUId();
                    savename += "," + classStudentResponses.get(i).getUName();

                } else {
                    savedItems += classStudentResponses.get(i).getUId();
                    savename += classStudentResponses.get(i).getUName();
                }
            }
        }
        Toast.makeText(this, savename, Toast.LENGTH_SHORT).show();
return savedItems;
    }

    /*private void LoadSelections() {
// if the selections were previously saved load them

        if (sharedpreferences.contains(MyPREFERENCES.toString())) {

            String savedItems = sharedpreferences.getString(MyPREFERENCES.toString(), "");
            selectedItems.addAll(Arrays.asList(savedItems.split(",")));

            int count = this.lstStudent.getAdapter().getCount();

            for (int i = 0; i < count; i++) {
                String currentItem = (String) lstStudent.getAdapter()
                        .getItem(i);
                if (selectedItems.contains(currentItem)) {
                    lstStudent.setItemChecked(i, true);
                    Toast.makeText(getApplicationContext(),
                            "Curren Item: " + currentItem,
                            Toast.LENGTH_LONG).show();
                } else {
                    lstStudent.setItemChecked(i, false);
                }

            }
        }
    }
*/
    private void ClearSelections() {
// user has clicked clear button so uncheck all the items
        int count = this.lstStudent.getAdapter().getCount();
        for (int i = 0; i < count; i++) {
            this.lstStudent.setItemChecked(i, false);
        }
// also clear the saved selections
        SaveSelections();


    }

    @Override
    public void onDestroy() {
        ClearSelections();
        super.onDestroy();
    }

    private void loadStudent(int classId) {
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);
        Call<ClassStudentListResponse> getBrandResponseCall = apiInterface.GET_STUDENT_LIST_RESPONSE_CALL("qw", classId);
        getBrandResponseCall.enqueue(new Callback<ClassStudentListResponse>() {
            @Override
            public void onResponse(Call<ClassStudentListResponse> call, Response<ClassStudentListResponse> response) {
                if (response.body().getSuccess() == 200) {
                    classStudentResponses = response.body().getStudentList();
                    Toast.makeText(SelectStudentShare.this, String.valueOf(classStudentResponses.size()), Toast.LENGTH_SHORT).show();
                    AdapterStudentClassShare adapter = new AdapterStudentClassShare(SelectStudentShare.this, R.layout.row_student_class_share, classStudentResponses);
                    lstStudent.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    lstStudent.setAdapter(adapter);

                } else {
                    Toast.makeText(SelectStudentShare.this, "Invalid Data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ClassStudentListResponse> call, Throwable t) {
                Toast.makeText(SelectStudentShare.this, "Cannot connect!", Toast.LENGTH_SHORT).show();
                Log.d("123", t.getMessage());
            }
        });
    }
}
