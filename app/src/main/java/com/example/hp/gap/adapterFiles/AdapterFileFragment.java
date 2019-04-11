package com.example.hp.gap.adapterFiles;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hp.gap.APIresponse.ClassStudentResponse;
import com.example.hp.gap.APIresponse.ClassTeacherResponse;
import com.example.hp.gap.APIresponse.FileListAll;
import com.example.hp.gap.MessageInfo.ViewImageAndVideo;
import com.example.hp.gap.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterFileFragment extends RecyclerView.Adapter<AdapterFileFragment.ItemViewHolder> {
    Context context;
    List<FileListAll> itemModels;
    List<FileListAll> classTeacherResponses;

    public AdapterFileFragment(Context context, List<FileListAll> itemModels) {
        this.context = context;
        this.itemModels = itemModels;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_view_all_files, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {

        if (itemModels.get(position).getFTypeId().contains("1")) {
            holder.imgView.setVisibility(View.VISIBLE);
            holder.VideoView.setVisibility(View.GONE);
            holder.pdfView.setVisibility(View.GONE);
            holder.linkview.setVisibility(View.GONE);
            Picasso.with((context)).load(itemModels.get(position).getFUrl()).placeholder(R.drawable.person_default_theme).into(holder.img);
            holder.filename.setText(itemModels.get(position).getFName());

        }
        if (itemModels.get(position).getFTypeId().contains("2")) {
            holder.VideoView.setVisibility(View.VISIBLE);
            holder.imgView.setVisibility(View.GONE);
            holder.pdfView.setVisibility(View.GONE);
            holder.linkview.setVisibility(View.GONE);
            /*final MediaController mediaController=new MediaController(context);
            Uri video=Uri.parse(android.get(position).getFUrl());
            holder.video.setMediaController(mediaController);
           holder.video.setVideoURI(video);
           holder.video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
               @Override
               public void onPrepared(MediaPlayer mp) {
                   holder.video.start();
                   try {
                       Thread.sleep(1);


                   }catch (InterruptedException e){
                       e.printStackTrace();
                   }
                   holder.video.pause();
                   mediaController.show();
               }
           });*/
            holder.video.setVideoURI(Uri.parse(itemModels.get(position).getFUrl()));
            holder.video.seekTo(1);
            holder.VideofileName.setText(itemModels.get(position).getFName());
            /*
            Picasso.with((context)).load(android.get(position).getFUrl()).placeholder(R.mipmap.ic_launcher).into(holder.img);
            holder.filename.setText(android.get(position).getFName());*/

        }
        if (itemModels.get(position).getFTypeId().contains("4")) {
            holder.imgView.setVisibility(View.GONE);
            holder.VideoView.setVisibility(View.GONE);
            holder.pdfView.setVisibility(View.VISIBLE);
            holder.linkview.setVisibility(View.GONE);
            holder.pdffileName.setText(itemModels.get(position).getFName());

        }

        if (itemModels.get(position).getFTypeId().contains("5")) {
            holder.imgView.setVisibility(View.GONE);
            holder.VideoView.setVisibility(View.GONE);
            holder.pdfView.setVisibility(View.GONE);
            holder.linkview.setVisibility(View.VISIBLE);
            holder.linkFilename.setText(itemModels.get(position).getFName());

        }
        // if (android.get(position).getFTypeId().contains("2")) {

           /* Picasso.with((context)).load(android.get(position).getFUrl()).placeholder(R.mipmap.ic_launcher).into( holder.img);
            holder.filename.setText(android.get(position).getFName());*/
           /* MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(android.get(position).getFUrl(), new HashMap<String, String>());
            Bitmap image = retriever.getFrameAtTime(2000000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);*/
        /*   Picasso.with((context)).load(Uri.parse(image.toString())).into( holder.img);*/


           /* try {
                Bitmap thumb = ThumbnailUtils.createVideoThumbnail(android.get(position).getFUrl(), MediaStore.Video.Thumbnails.MICRO_KIND);
                holder.img.setImageBitmap(thumb);
                holder.filename.setText(android.get(position).getFName());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }*/
        holder.viewAllFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemModels.get(position).getFTypeId().contains("1")) {
                    Intent i = new Intent(context, ViewImageAndVideo.class);
                    i.putExtra("url", itemModels.get(position).getFUrl());
                    i.putExtra("tag", "image");
                    context.startActivity(i);
                }
                if (itemModels.get(position).getFTypeId().contains("2")) {
                    Intent i = new Intent(context, ViewImageAndVideo.class);
                    i.putExtra("url", itemModels.get(position).getFUrl());
                    i.putExtra("tag", "video");
                    context.startActivity(i);
                }
                if (itemModels.get(position).getFTypeId().contains("4")) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(itemModels.get(position).getFUrl())));
                }


                //  context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(itemModels.get(position).getFUrl())));


            }
        });
        holder.linkOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(itemModels.get(position).getFUrl())));


            }
        });
    }

    @Override
    public int getItemCount() {
        if (itemModels == null)
            return 0;
        else
            return itemModels.size();

    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        android.widget.VideoView video;
        TextView filename, VideofileName, pdffileName, linkFilename, linkOpen;
        LinearLayout viewAllFiles, VideoView, imgView, pdfView, linkview;
        ImageView viewFile;

        public ItemViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            filename = itemView.findViewById(R.id.fileName);
            viewFile = itemView.findViewById(R.id.viewFile);
            video = itemView.findViewById(R.id.video);
            VideofileName = itemView.findViewById(R.id.VideofileName);
            VideoView = itemView.findViewById(R.id.VideoView);
            imgView = itemView.findViewById(R.id.imgView);
            pdffileName = itemView.findViewById(R.id.pdffileName);
            pdfView = itemView.findViewById(R.id.pdfView);
            linkFilename = itemView.findViewById(R.id.linkfileName);
            linkOpen = itemView.findViewById(R.id.linkOpen);
            linkview = itemView.findViewById(R.id.linkView);
            viewAllFiles = itemView.findViewById(R.id.viewAllFiles);
        }

    }


}
