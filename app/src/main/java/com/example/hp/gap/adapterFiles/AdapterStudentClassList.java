package com.example.hp.gap.adapterFiles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.gap.APIinterface.APIinterface;
import com.example.hp.gap.APImanager.APIClient;
import com.example.hp.gap.APIresponse.RegistrationUserResponse;
import com.example.hp.gap.APIresponse.StudentEnrollClass;
import com.example.hp.gap.APIresponse.StudentResponse;
import com.example.hp.gap.ClassesList.StudentClassList;
import com.example.hp.gap.MainActivity;
import com.example.hp.gap.R;
import com.example.hp.gap.SharedPreferences.PreferenceManager;
import com.example.hp.gap.UserLoginActivity;
import com.example.hp.gap.UserRegistrationActivity;
import com.example.hp.gap.btmNavFragment.StreamFragment;
import com.example.hp.gap.btmNavigation.BtmNavigationActivity;
import com.example.hp.gap.utils.CommonUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterStudentClassList extends RecyclerView.Adapter<AdapterStudentClassList.ItemViewHolder> {
    List<StudentResponse> android;
    Context context;
    PreferenceManager preferenceManager;
    CommonUtil commonUtil;

    public AdapterStudentClassList(List<StudentResponse> android, Context context) {
        this.android = android;
        this.context = context;
        preferenceManager = new PreferenceManager(context);
    commonUtil=new CommonUtil();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_student_class_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder,final int position) {
        holder.teachername.setText(android.get(position).getUName());
        holder.class_name.setText(android.get(position).getCName());

        holder.student_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.student_menu);
                //inflating menu from xml resource
                popup.inflate(R.menu.option_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.enroll:
                               /* int c_id = Integer.parseInt(android.get(position).getCId());*/
                                enrollFromClass(Integer.parseInt(android.get(position).getCId()));
                                break;
                            /*case R.id.menu2:
                                //handle menu2 click
                                break;
                            case R.id.menu3:
                                //handle menu3 click
                                break;*/
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();


            }
        });
        holder.student_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                 Intent i = new Intent(context, BtmNavigationActivity.class);
               /* i.putExtra("cname",android.get(position).getCName());
                i.putExtra("c_id",android.get(position).getCId());*/
                preferenceManager.setClassId(Integer.parseInt(android.get(position).getCId()));
                preferenceManager.setClassName(android.get(position).getCName());
                preferenceManager.setCSubject(android.get(position).getCSubject());
                preferenceManager.setCTeacherName(android.get(position).getUName());
               context.startActivity(i);


                //  Toast.makeText(context, android.get(position).getCName().toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        if (android == null)
            return 0;
        else
            return android.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView teachername, class_name;
        LinearLayout student_list;
        Button student_menu;

        public ItemViewHolder(View itemView) {
            super(itemView);
            teachername = itemView.findViewById(R.id.teacher_name);
            class_name = itemView.findViewById(R.id.class_name);
            student_list = itemView.findViewById(R.id.student_list);
            student_menu = itemView.findViewById(R.id.student_menu);
        }

    }

    private void enrollFromClass(int c_id) {
        commonUtil.loading(context);
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

        Call<StudentEnrollClass> getBrandResponseCall = apiInterface.ENROLL_JOINCLASSROOM_RESPONSE_CALL("qw", preferenceManager.getRegisteredUserId(), c_id);

        getBrandResponseCall.enqueue(new Callback<StudentEnrollClass>() {

            @Override
            public void onResponse(Call<StudentEnrollClass> call, Response<StudentEnrollClass> response) {
                commonUtil.cancelLoading();
                if (response.body().getSuccess() == 200) {
                    Intent i = new Intent(context, StudentClassList.class);
                    context.startActivity(i);

                } else {
                    Toast.makeText(context, "Invalid Data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<StudentEnrollClass> call, Throwable t) {
                commonUtil.cancelLoading();
                Log.d("123", t.getMessage());
            }
        });
    }


}
