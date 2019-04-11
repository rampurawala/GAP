package com.example.hp.gap.adapterFiles;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.gap.APIresponse.ClassStudentResponse;
import com.example.hp.gap.APIresponse.ClassTeacherResponse;
import com.example.hp.gap.R;
import com.example.hp.gap.SharedPreferences.PreferenceManager;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterTeacherPeople extends RecyclerView.Adapter<AdapterTeacherPeople.ItemViewHolder> {
    Context context;
    List<ClassTeacherResponse> itemModels;
PreferenceManager preferenceManager;

    public AdapterTeacherPeople(Context context, List<ClassTeacherResponse> itemModels) {
        this.context = context;
        this.itemModels = itemModels;
        preferenceManager=new PreferenceManager(context);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_people, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        if(preferenceManager.getPanel()==0) {
            holder.gmail.setVisibility(View.VISIBLE);
            holder.gmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + itemModels.get(position).getUEmail()));
                        context.startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        //TODO smth
                    }


                }
            });
        }
        holder.name.setText(itemModels.get(position).getUName());
        Picasso.with((context)).load(itemModels.get(position).getUProfilePic()).placeholder(R.drawable.person_default_theme).into(holder.img);

    }

    @Override
    public int getItemCount() {
        if (itemModels == null)
            return 0;
        else return itemModels.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView img,gmail;

        public ItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            img = itemView.findViewById(R.id.img);
            gmail = itemView.findViewById(R.id.gmail);


        }


    }
}
