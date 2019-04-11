package com.example.hp.gap.adapterFiles;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.gap.APIinterface.APIinterface;
import com.example.hp.gap.APImanager.APIClient;
import com.example.hp.gap.APIresponse.ClassStudentResponse;
import com.example.hp.gap.APIresponse.DeleteClassResponse;
import com.example.hp.gap.APIresponse.ToDoListResponse;
import com.example.hp.gap.ClassesList.TeacherClassList;
import com.example.hp.gap.GetterSetter.ClassGtSt;
import com.example.hp.gap.R;
import com.example.hp.gap.ToDoList.ToDOListActivity;
import com.example.hp.gap.utils.CommonUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterToDoList extends RecyclerView.Adapter<AdapterToDoList.ItemViewHolder> {

    Context context;
    List<ToDoListResponse> itemModels;
    CommonUtil commonUtil;
    public AdapterToDoList(Context context, List<ToDoListResponse> itemModels) {
        this.context = context;
        this.itemModels = itemModels;
        commonUtil=new CommonUtil();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_todo_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {

        holder.item.setText(itemModels.get(position).getListName());
        holder.delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteList(Integer.parseInt(itemModels.get(position).getTodoListId()));
            }
        });
        holder.created_date.setText("Created On :: "+itemModels.get(position).getListDate());
       // Picasso.with((context)).load(itemModels.get(position).getUProfilePic()).placeholder(R.drawable.person_default_theme).into(holder.img);

    }

    @Override
    public int getItemCount() { if (itemModels == null)
        return 0;
    else
        return itemModels.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView item,created_date;
        ImageButton delete_item;

        public ItemViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            delete_item = itemView.findViewById(R.id.delete_item);
            created_date=itemView.findViewById(R.id.created_date);

        }


    }



    private void deleteList(int list_id) {
        commonUtil.loading(context);
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

        Call<DeleteClassResponse> getBrandResponseCall = apiInterface.DELETE_LIST("qw",list_id);

        getBrandResponseCall.enqueue(new Callback<DeleteClassResponse>() {

            @Override
            public void onResponse(Call<DeleteClassResponse> call, Response<DeleteClassResponse> response) {
                commonUtil.cancelLoading();
                if (response.body().getSuccess() == 200) {
                    Intent i = new Intent(context, ToDOListActivity.class);
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
