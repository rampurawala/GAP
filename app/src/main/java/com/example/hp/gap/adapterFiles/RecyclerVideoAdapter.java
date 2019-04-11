package com.example.hp.gap.adapterFiles;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.hp.gap.ClassShare.ImageVideoGtSt;
import com.example.hp.gap.R;
import com.example.hp.gap.SharedPreferences.PreferenceManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RecyclerVideoAdapter extends RecyclerView.Adapter<RecyclerVideoAdapter.ItemViewHolder> {

    ArrayList<ImageVideoGtSt> mArrayUri = new ArrayList<ImageVideoGtSt>();
    PreferenceManager preferenceManager;
    Context context;

    public RecyclerVideoAdapter(ArrayList<ImageVideoGtSt> mArrayUri, Context context) {
        this.mArrayUri = mArrayUri;
        this.context = context;
        this.preferenceManager = new PreferenceManager(context);

    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.video_row_layout, parent, false);
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        final ImageVideoGtSt list = mArrayUri.get(position);
        holder.video.setVideoURI(Uri.parse(mArrayUri.get(position).getImg()));
        holder.video.seekTo(1);
       /* holder.video.setMediaController(new MediaController(context));
        holder.video.requestFocus();*/
        holder.videoname.setText(list.getName());

        holder.removeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String itemLabel = list.getName();

                // Remove the item on remove/button click
                mArrayUri.remove(position);

                /*
                    public final void notifyItemRemoved (int position)
                        Notify any registered observers that the item previously located at position
                        has been removed from the data set. The items previously located at and
                        after position may now be found at oldPosition - 1.

                        This is a structural change event. Representations of other existing items
                        in the data set are still considered up to date and will not be rebound,
                        though their positions may be altered.

                    Parameters
                        position : Position of the item that has now been removed
                */
                notifyItemRemoved(position);


                /*
                    public final void notifyItemRangeChanged (int positionStart, int itemCount)
                        Notify any registered observers that the itemCount items starting at
                        position positionStart have changed. Equivalent to calling
                        notifyItemRangeChanged(position, itemCount, null);.

                        This is an item change event, not a structural change event. It indicates
                        that any reflection of the data in the given position range is out of date
                        and should be updated. The items in the given range retain the same identity.

                    Parameters
                        positionStart : Position of the first item that has changed
                        itemCount : Number of items that have changed
                */
                ArrayList<String> getmArrayUri = new ArrayList<>();
                ArrayList<String> getmArrayName = new ArrayList<>();
                ArrayList<String> getmArrayEncode = new ArrayList<>();

                for (int i = 0; i < mArrayUri.size(); i++) {
                    getmArrayUri.add(mArrayUri.get(i).getImg());
                    getmArrayName.add(mArrayUri.get(i).getName());
                    getmArrayEncode.add(mArrayUri.get(i).getEncoded());
                }


                //Preference manager

/*

                Set<String> getmArrayuri = new HashSet<>();
                Set<String> getmArrayname = new HashSet<>();

                getmArrayname = preferenceManager.getmarrayname();
                getmArrayuri = preferenceManager.getmarrayuri();
                Log.d("sizedelete", String.valueOf(getmArrayuri.size()));

                for (String name : getmArrayname) {
                    getmArrayName.add(name);
                }

                for (String uri : getmArrayuri) {
                    getmArrayUri.add(uri);
                }
                Log.d("sizemarray", String.valueOf(getmArrayName.size()));
*/
/*

                getmArrayname.clear();
                getmArrayuri.clear();
                Log.d("position", String.valueOf(position));


                preferenceManager.setmarrayuri(getmArrayuri);
                preferenceManager.setmarrayname(getmArrayname);


*/

                /*getmArrayUri.remove(position);
                getmArrayName.remove(position);*/


                Log.d("sizeafter", String.valueOf(getmArrayName.size()));

              /* notifyItemRangeChanged(position,getmArrayUri.size());
              notifyItemRangeChanged(position,getmArrayName.size());*/
                notifyItemRangeChanged(position, mArrayUri.size());

                Set<String> setmArrayuri = new HashSet<>();
                Set<String> setmArrayname = new HashSet<>();
                Set<String> setmArrayencode = new HashSet<>();

               /*  ArrayList<String> newName= new ArrayList<String>();

                for(String newgetName:getmArrayName){
                    newName.add(newgetName);
                }
                ArrayList<String> newUri= new ArrayList<String>();
                for(String newgetUri:getmArrayUri){
                    newUri.add(newgetUri);
                }
*/
                setmArrayuri.addAll(getmArrayUri);
                setmArrayname.addAll(getmArrayName);
setmArrayencode.addAll(getmArrayEncode);

                preferenceManager.setmarrayuriVIDEO(setmArrayuri);
                preferenceManager.setmarraynameVIDEO(setmArrayname);
                preferenceManager.setmarrayEncodedVIDEO(setmArrayencode);

                Log.e("RemoveDataUri", String.valueOf(preferenceManager.getmarrayuriVIDEO().size()));


                //notifyItemRangeChanged(position, mArrayUri.size());

                // Show the removed item label
                Toast.makeText(context, "Removed : " + itemLabel, Toast.LENGTH_SHORT).show();


            }


        });
    }

    @Override
    public int getItemCount() {
        if (mArrayUri == null)
            return 0;
        else return mArrayUri.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView videoname;
        Button removeVideo;
        VideoView video;

        public ItemViewHolder(View itemView) {
            super(itemView);
            videoname = itemView.findViewById(R.id.videoname);
            video = itemView.findViewById(R.id.video);
            removeVideo = itemView.findViewById(R.id.removeVideo);
        }
    }

}
