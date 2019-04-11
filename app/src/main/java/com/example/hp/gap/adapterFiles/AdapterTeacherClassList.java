package com.example.hp.gap.adapterFiles;

import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.gap.APIinterface.APIinterface;
import com.example.hp.gap.APImanager.APIClient;
import com.example.hp.gap.APIresponse.DeleteClassResponse;
import com.example.hp.gap.APIresponse.StudentEnrollClass;
import com.example.hp.gap.APIresponse.TeacherResponse;
import com.example.hp.gap.ClassesList.StudentClassList;
import com.example.hp.gap.ClassesList.TeacherClassList;
import com.example.hp.gap.R;
import com.example.hp.gap.SharedPreferences.PreferenceManager;
import com.example.hp.gap.btmNavigation.BtmNavigationActivity;
import com.example.hp.gap.utils.CommonUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterTeacherClassList extends RecyclerView.Adapter<AdapterTeacherClassList.ItemViewHolder> {
    List<TeacherResponse> android;
    Context context;
    PreferenceManager preferenceManager;
CommonUtil commonUtil;

    public AdapterTeacherClassList(List<TeacherResponse> android, Context context) {
        this.android = android;
        this.context = context;
        preferenceManager=new PreferenceManager(context);
        commonUtil=new CommonUtil();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_teacher_class_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.count.setText(android.get(position).getCount()+"  Student");
        holder.class_name.setText(android.get(position).getCName());
        holder.teacher_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.teacher_menu);
                //inflating menu from xml resource
                popup.inflate(R.menu.option_menu_teacher);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.deleteClass:
                                //handle menu1 click
                                DeleteClass(Integer.parseInt(android.get(position).getCId()));
                                break;

                           /* case R.id.menu2:
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
        holder.teacher_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, BtmNavigationActivity.class);
               preferenceManager.setClassId(Integer.parseInt(android.get(position).getCId()));
               preferenceManager.setClassName(android.get(position).getCName());
               preferenceManager.setCCode(android.get(position).getCCode());
               preferenceManager.setCSubject(android.get(position).getCSubject());
                /*i.putExtra("cname",android.get(position).getCName());
                i.putExtra("c_id",android.get(position).getCId());*/
                context.startActivity(i);
                //Toast.makeText(context, android.get(position).getCName().toString(), Toast.LENGTH_SHORT).show();
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
        TextView count, class_name;
        LinearLayout teacher_list;
        Button teacher_menu;

        public ItemViewHolder(View itemView) {
            super(itemView);
            count = itemView.findViewById(R.id.count);
            class_name = itemView.findViewById(R.id.class_name);
            teacher_list = itemView.findViewById(R.id.teacher_list);
            teacher_menu=itemView.findViewById(R.id.teacher_menu);
        }


    }

    private void DeleteClass(int c_id) {
        commonUtil.loading(context);
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

        Call<DeleteClassResponse> getBrandResponseCall = apiInterface.DELETE_CLASSROOM_RESPONSE_CALL("qw", c_id);

        getBrandResponseCall.enqueue(new Callback<DeleteClassResponse>() {

            @Override
            public void onResponse(Call<DeleteClassResponse> call, Response<DeleteClassResponse> response) {
                commonUtil.cancelLoading();
                if (response.body().getSuccess() == 200) {
                    Intent i = new Intent(context, TeacherClassList.class);
                    context.startActivity(i);

                } else {
                    Toast.makeText(context, "Invalid Data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<DeleteClassResponse> call, Throwable t) {
                commonUtil.cancelLoading();
                Log.d("123", t.getMessage());
            }
        });
    }
}
