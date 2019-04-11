package com.example.hp.gap.adapterFiles;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.gap.APIresponse.ClassStudentResponse;
import com.example.hp.gap.APIresponse.ClassTeacherResponse;
import com.example.hp.gap.GetterSetter.PeopleGtSt;
import com.example.hp.gap.GetterSetter.StreamGtSt;
import com.example.hp.gap.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterPeopleFragment extends RecyclerView.Adapter<AdapterPeopleFragment.ItemViewHolder> {
    Context context;
    List<ClassStudentResponse> itemModels;
    List<ClassTeacherResponse> classTeacherResponses;

    public AdapterPeopleFragment(Context context, List<ClassStudentResponse> itemModels) {
        this.context = context;
        this.itemModels = itemModels;
    }
    public AdapterPeopleFragment(Context context, List<ClassTeacherResponse> itemModels,int flag) {
        this.context = context;
        this.classTeacherResponses = itemModels;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_people, parent, false);
        return new AdapterPeopleFragment.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        holder.name.setText(itemModels.get(position).getUName());
        Picasso.with((context)).load(R.mipmap.ic_launcher).placeholder(R.drawable.person_default_theme).into(holder.img);

    }

    @Override
    public int getItemCount() { if (itemModels == null)
        return 0;
    else
        return itemModels.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView img;

        public ItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            img = itemView.findViewById(R.id.img);


        }


    }


}
