package com.example.hp.gap.adapterFiles;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.gap.APIresponse.MessageFileListResponse;
import com.example.hp.gap.APIresponse.MessageList;
import com.example.hp.gap.AssignmentInfo.AssignmentInfo;
import com.example.hp.gap.GetterSetter.StreamGtSt;
import com.example.hp.gap.MessageInfo.MessageInfo;
import com.example.hp.gap.R;
import com.example.hp.gap.utils.CommonUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterStreamFragment extends RecyclerView.Adapter<AdapterStreamFragment.ItemViewHolder> {
    Context context;
    List<MessageList> itemModels;


    public AdapterStreamFragment(Context context, List<MessageList> itemModels) {
        this.context = context;
        this.itemModels = itemModels;

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_stream, parent, false);

        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {

        holder.name.setText(itemModels.get(position).getUName());
       /* holder.date.setText(itemModels.getDate());*/
        /*holder.img.setImageResource(itemModal.getImage());*/
        holder.msg.setText(itemModels.get(position).getMessageTitle());
        holder.date.setText(itemModels.get(position).getMdate());
        Picasso.with((context)).load(itemModels.get(position).getUProfilePic()).placeholder(R.drawable.person_default_theme).into(holder.img);

        holder.lv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,MessageInfo.class);
               /* int message_id=Integer.parseInt(itemModels.get(position).getMessageMasterId());*/
                i.putExtra("senderName",itemModels.get(position).getUName());
                i.putExtra("assignmentTitle",itemModels.get(position).getMessageTitle());
                i.putExtra("profilePic",itemModels.get(position).getUProfilePic());
                i.putExtra("message_id",itemModels.get(position).getMessageMasterId());
                context.startActivity(i);
                //Toast.makeText(context, itemModal.getName(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (itemModels == null)
            return 0;
        else return itemModels.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name,date,msg;
        ImageView img;
        LinearLayout lv;
        public ItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.assName);
            date = itemView.findViewById(R.id.assDate);
            img = itemView.findViewById(R.id.img_msg);
            msg=itemView.findViewById(R.id.msg);
            lv=itemView.findViewById(R.id.lv);
        }


    }

}
