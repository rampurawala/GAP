package com.example.hp.gap.adapterFiles;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.gap.APIresponse.FileList;
import com.example.hp.gap.MessageInfo.ViewImageAndVideo;
import com.example.hp.gap.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdapterFileList extends RecyclerView.Adapter<AdapterFileList.ItemViewHolder> {

    List<FileList> android;
    Context context;
    ProgressDialog mProgressDialog;
    String VideoUrl,PdfUrl;
    private AsyncTask mMyTask;
    private static final String IMAGE_DIRECTORY = "/GAP";
  /*  VideoRequestHandler videoRequestHandler;
    Picasso picassoInstance;*/

    public AdapterFileList(List<FileList> android, Context context) {
        this.android = android;
        this.context = context;
        // Initialize the progress dialog
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        // Progress dialog horizontal style
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // Progress dialog title
        mProgressDialog.setTitle("AsyncTask");
        // Progress dialog message
        mProgressDialog.setMessage("Please wait, we are downloading your file...");

    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message_file_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        //  holder.img.setImageURI(Uri.parse(android.get(position).getFUrl()));
        if (android.get(position).getFTypeId().contains("1")) {
            holder.imgView.setVisibility(View.VISIBLE);
            holder.VideoView.setVisibility(View.GONE);
            holder.pdfView.setVisibility(View.GONE);
            holder.linkview.setVisibility(View.GONE);
            Picasso.with((context)).load(android.get(position).getFUrl()).placeholder(R.drawable.person_default_theme).into(holder.img);
            holder.filename.setText(android.get(position).getFName());

        }
        if (android.get(position).getFTypeId().contains("2")) {
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
            holder.video.setVideoURI(Uri.parse(android.get(position).getFUrl()));
                    holder.video.seekTo(1);
                    holder.VideofileName.setText(android.get(position).getFName());
            /*
            Picasso.with((context)).load(android.get(position).getFUrl()).placeholder(R.mipmap.ic_launcher).into(holder.img);
            holder.filename.setText(android.get(position).getFName());*/

        }
        if (android.get(position).getFTypeId().contains("4")) {
            holder.imgView.setVisibility(View.GONE);
            holder.VideoView.setVisibility(View.GONE);
            holder.pdfView.setVisibility(View.VISIBLE);
            holder.linkview.setVisibility(View.GONE);
            holder.pdffileName.setText(android.get(position).getFName());

        }

        if (android.get(position).getFTypeId().contains("5")) {
            holder.imgView.setVisibility(View.GONE);
            holder.VideoView.setVisibility(View.GONE);
            holder.pdfView.setVisibility(View.GONE);
            holder.linkview.setVisibility(View.VISIBLE);
            holder.linkFilename.setText(android.get(position).getFName());

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
        holder.messageFile_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(android.get(position).getFUrl())));


            }
        });
        holder.linkOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(android.get(position).getFUrl())));


            }
        });
        holder.viewFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ViewImageAndVideo.class);
                i.putExtra("url", android.get(position).getFUrl());
                i.putExtra("tag","image");
                context.startActivity(i);
            }
        });
        holder.VideoviewFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ViewImageAndVideo.class);
                i.putExtra("url", android.get(position).getFUrl());
                i.putExtra("tag","video");
                context.startActivity(i);
            }
        });

        holder.pdfviewFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(android.get(position).getFUrl())));
               /* Intent i = new Intent(context, ViewImageAndVideo.class);
                i.putExtra("url", android.get(position).getFUrl());
                i.putExtra("tag","pdf");
                context.startActivity(i);*/
            }
        });

        holder.downloadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyTask = new ImageDownloadTask()
                        .execute(stringToURL(android.get(position).getFUrl()));

            }
        });


        holder.VideodownloadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadVideo();
                VideoUrl = android.get(position).getFUrl();
            }
        });


        holder.pdfdownloadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadpdf();
                PdfUrl = android.get(position).getFUrl();
            }
        });
    }

    //Image Download
    private class ImageDownloadTask extends AsyncTask<URL, Void, Bitmap> {
        // Before the tasks execution
        protected void onPreExecute() {
            // Display the progress dialog on async task start
            mProgressDialog.show();
        }

        // Do the task in background/non UI thread
        protected Bitmap doInBackground(URL... urls) {
            URL url = urls[0];
            HttpURLConnection connection = null;

            try {
                // Initialize a new http url connection
                connection = (HttpURLConnection) url.openConnection();

                // Connect the http url connection
                connection.connect();

                // Get the input stream from http url connection
                InputStream inputStream = connection.getInputStream();

                /*
                    BufferedInputStream
                        A BufferedInputStream adds functionality to another input stream-namely,
                        the ability to buffer the input and to support the mark and reset methods.
                */
                /*
                    BufferedInputStream(InputStream in)
                        Creates a BufferedInputStream and saves its argument,
                        the input stream in, for later use.
                */
                // Initialize a new BufferedInputStream from InputStream
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                /*
                    decodeStream
                        Bitmap decodeStream (InputStream is)
                            Decode an input stream into a bitmap. If the input stream is null, or
                            cannot be used to decode a bitmap, the function returns null. The stream's
                            position will be where ever it was after the encoded data was read.

                        Parameters
                            is InputStream : The input stream that holds the raw data
                                              to be decoded into a bitmap.
                        Returns
                            Bitmap : The decoded bitmap, or null if the image data could not be decoded.
                */
                // Convert BufferedInputStream to Bitmap object
                Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);

                // Return the downloaded bitmap
                return bmp;

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Disconnect the http url connection
                connection.disconnect();
            }
            return null;
        }

        // When all async task done
        protected void onPostExecute(Bitmap result) {
            // Hide the progress dialog
            mProgressDialog.dismiss();

            if (result != null) {
                // Display the downloaded image into ImageView

                // Save bitmap to internal storage
                saveImage(result);

                // Set the ImageView image from internal storage

            } else {
                // Notify user that an error occurred while downloading image
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();

            }
        }
    }

    // Custom method to convert string to url
    protected URL stringToURL(String urlString) {
        try {
            URL url = new URL(urlString);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(context,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    //video Download
    private void downloadVideo() {
        Downback DB = new Downback();
        DB.execute("");

    }
    private void downloadpdf() {
        Downbackpdf DB = new Downbackpdf();
        DB.execute("");
    }


    private class Downback extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
           mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
          mProgressDialog.dismiss();
            Toast.makeText(context, "Video Downloaded Successfully..", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected String doInBackground(String... strings) {
            final String vidurl = VideoUrl;
            downloadfile(vidurl);
            return "true";

        }


    }

    private void downloadfile(String vidurl) {

        SimpleDateFormat sd = new SimpleDateFormat("yymmhh");
        String date = sd.format(new Date());
        String name = "video" + date + ".mp4";

        try {
            File rootFile = new File(
                    Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
            // have the object build the directory structure, if needed.
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }

            /*String rootDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    + File.separator + "My_Video";
            File rootFile = new File(rootDir);
            rootFile.mkdir();*/
            URL url = new URL(vidurl);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            FileOutputStream f = new FileOutputStream(new File(rootFile,
                    name));

            InputStream in = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            MediaScannerConnection.scanFile(context,
                    new String[]{rootFile.getPath()},
                    new String[]{"video/mp4"}, null);
            f.close();
        } catch (IOException e) {
            Log.d("Error....", e.toString());
        }


    }


    private class Downbackpdf extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            mProgressDialog.dismiss();
            Toast.makeText(context, "File Downloaded Successfully..", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            final String pdfurl = PdfUrl;
            downloadfilepdf(pdfurl);
            return "true";

        }


    }

    private void downloadfilepdf(String vidurl) {

        SimpleDateFormat sd = new SimpleDateFormat("yymmhh");
        String date = sd.format(new Date());
        String name = "pdf" + date + ".pdf";

        try {
            File rootFile = new File(
                    Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
            // have the object build the directory structure, if needed.
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }

            /*String rootDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    + File.separator + "My_Video";
            File rootFile = new File(rootDir);
            rootFile.mkdir();*/
            URL url = new URL(vidurl);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            FileOutputStream f = new FileOutputStream(new File(rootFile,
                    name));

            InputStream in = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
        } catch (IOException e) {
            Log.d("Error....", e.toString());
        }


    }

    @Override
    public int getItemCount() {
        if (android == null)
            return 0;
        else
            return android.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        android.widget.VideoView video;
        TextView filename, VideofileName,pdffileName,linkFilename,linkOpen;
        LinearLayout messageFile_list, VideoView, imgView,pdfView,linkview;
        ImageView viewFile, downloadFile, VideodownloadFile, VideoviewFile,pdfdownloadFile,pdfviewFile;

        public ItemViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            filename = itemView.findViewById(R.id.fileName);
            messageFile_list = itemView.findViewById(R.id.messageFile_list);
            viewFile = itemView.findViewById(R.id.viewFile);
            downloadFile = itemView.findViewById(R.id.downloadFile);
            video = itemView.findViewById(R.id.video);
            VideofileName = itemView.findViewById(R.id.VideofileName);
            VideoView = itemView.findViewById(R.id.VideoView);
            imgView = itemView.findViewById(R.id.imgView);
            VideodownloadFile = itemView.findViewById(R.id.VideodownloadFile);
            VideoviewFile = itemView.findViewById(R.id.VideoviewFile);
            pdffileName=itemView.findViewById(R.id.pdffileName);
            pdfView=itemView.findViewById(R.id.pdfView);
            pdfdownloadFile=itemView.findViewById(R.id.pdfdownloadFile);
            pdfviewFile=itemView.findViewById(R.id.pdfviewFile);
            linkFilename=itemView.findViewById(R.id.linkfileName);
            linkOpen=itemView.findViewById(R.id.linkOpen);
            linkview=itemView.findViewById(R.id.linkView);
        }
    }


}
