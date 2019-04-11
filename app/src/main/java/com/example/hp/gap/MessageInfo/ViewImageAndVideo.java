package com.example.hp.gap.MessageInfo;

import android.content.Intent;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.hp.gap.R;
import com.example.hp.gap.utils.TouchImageView;
import com.squareup.picasso.Picasso;

import java.net.URI;

public class ViewImageAndVideo extends AppCompatActivity {
    TouchImageView tv;
    LinearLayout linearVideoView,linearPdfView;
    VideoView VideoView;
    WebView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        Intent i = getIntent();
        String url = i.getStringExtra("url");
        if (i.getStringExtra("tag").equals("image")) {
            tv = findViewById(R.id.frag_imageview);
            tv.setVisibility(View.VISIBLE);

            Picasso.with((this)).load(url).placeholder(R.mipmap.ic_launcher).into(tv);

        }
        if (i.getStringExtra("tag").equals("video")) {
            VideoView = findViewById(R.id.VideoView);
            linearVideoView = findViewById(R.id.linearVideoView);
            linearVideoView.setVisibility(View.VISIBLE);

            VideoView.setVideoURI(Uri.parse(url));

            MediaController mediaController = new
                    MediaController(this);
            mediaController.setAnchorView(VideoView);
            /* VideoView.requestFocus();*/
            VideoView.setMediaController(mediaController);

            VideoView.start();


        }
        if (i.getStringExtra("tag").equals("pdf")) {
            pdfView = findViewById(R.id.pdfView);
            linearPdfView = findViewById(R.id.linearPdfView);
            linearPdfView.setVisibility(View.VISIBLE);

            String pdf =url; //YOUR URL TO PDF
            String googleDocsUrl = "http://docs.google.com/viewer?url="+ pdf;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(googleDocsUrl ), "text/html");
            startActivity(intent);




            /*String url1 = "http://docs.google.com/gview?embedded=true&url=" + Uri.encode(url);

            Log.i("pdfUrl", "Opening PDF: " + url);

            pdfView.getSettings().setJavaScriptEnabled(true);
            pdfView.loadUrl(url1);*/
           // pdfView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url="+url);


        }


    }


}
