package com.example.hp.gap.adapterFiles;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.gap.APIresponse.ClassStudentResponse;
import com.example.hp.gap.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterStudentPeople extends RecyclerView.Adapter<AdapterStudentPeople.ItemViewHolder> {
    Context context;
    List<ClassStudentResponse> itemModels;

    public AdapterStudentPeople(Context context, List<ClassStudentResponse> itemModels) {
        this.context = context;
        this.itemModels = itemModels;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_people, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        holder.name.setText(itemModels.get(position).getUName());
        Picasso.with((context)).load(itemModels.get(position).getUProfilePic()).placeholder(R.drawable.person_default_theme).into(holder.img);

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
