package com.example.hp.gap.ClassShare;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.gap.APIinterface.APIinterface;
import com.example.hp.gap.APImanager.APIClient;
import com.example.hp.gap.APIresponse.ClassStudentListResponse;
import com.example.hp.gap.APIresponse.ClassStudentResponse;
import com.example.hp.gap.APIresponse.ClassTeacherListResponse;
import com.example.hp.gap.APIresponse.ClassTeacherResponse;
import com.example.hp.gap.APIresponse.JoinClassResponse;
import com.example.hp.gap.APIresponse.MessageSendedResponse;
import com.example.hp.gap.APIresponse.RegistrationUserResponse;
import com.example.hp.gap.APIresponse.ServerImageResponse;
import com.example.hp.gap.CameraActivity;
import com.example.hp.gap.ClassesList.StudentClassList;
import com.example.hp.gap.GetterSetter.CameraGtSt;
;
import com.example.hp.gap.R;

import com.example.hp.gap.SharedPreferences.PreferenceManager;
import com.example.hp.gap.UserLoginActivity;
import com.example.hp.gap.UserRegistrationActivity;
import com.example.hp.gap.adapterFiles.AdapterStudentClassShare;
import com.example.hp.gap.adapterFiles.AdapterTeacherPeople;
import com.example.hp.gap.adapterFiles.CameraAdapter;
import com.example.hp.gap.adapterFiles.RecyclerImageAdapter;
import com.example.hp.gap.adapterFiles.RecyclerLinkAdapter;
import com.example.hp.gap.adapterFiles.RecyclerPdfAdapter;
import com.example.hp.gap.adapterFiles.RecyclerVideoAdapter;
import com.example.hp.gap.adapterFiles.SpinnerStudentListAdapter;
import com.example.hp.gap.adapterFiles.StudentMessageReceiveResponse;
import com.example.hp.gap.btmNavigation.BtmNavigationActivity;
import com.example.hp.gap.utils.CommonUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassShare extends AppCompatActivity {
    private BottomSheetBehavior bottomSheetBehavior;

    LinearLayout bottomsheetLayout;
    EditText msg;
    RecyclerView img_rc, vdo_rc, pdf_rc, link_rc;
    CameraActivity cameraActivity;
    //String mediaPath, mediaPath1;
    ProgressDialog progressDialog;
    PreferenceManager preferenceManager;
    Spinner spinnerStudents;
    TextView txt_Head, txt_data;
    String names[] = null;
    Bundle bundle;

    String username, userId, savedItems = "";

    CommonUtil commonUtil;

    public static final String MyPREFERENCES = "MyUserChoice";
    public static final String NAMEPREFERENCES = "NameChoice";
    ArrayList<String> selectedNames = null;
    SharedPreferences myPrefs, namePrefs;
    List<ClassStudentResponse> classStudentResponses;
    int PICK_IMAGE_MULTIPLE = 1;
    int PICK_VIDEO_MULTIPLE = 2;
    int PICK_PDF_MULTIPLE = 3;
    String imageEncoded, videoEncoded, pdfEncoded;
    List<String> imagesEncodedList, videoEncodedList, pdfEncodedList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Class Share");
        setContentView(R.layout.activity_class_share);
        //Intent i = getIntent();
       /* byte[] byteArray=i.getByteArrayExtra("img");
        Bitmap img= BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);*/

      /* if(this.getIntent()!=null){
           Intent i=getIntent();
          names=i.getStringArrayExtra("selectedArrayString");
           selectedNames=i.getStringArrayListExtra("selectedArray") ;

           Toast.makeText(ClassShare.this, String.valueOf(names.length), Toast.LENGTH_SHORT).show();
           Toast.makeText(ClassShare.this, String.valueOf(selectedNames.size()), Toast.LENGTH_SHORT).show();
        // Log.d("names",String.valueOf(names.length));
//         Log.d("selectedNames",String.valueOf(selectedNames.size()));
       }*/
        bundle = new Bundle();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        //Bitmap img = (Bitmap) this.getIntent().getParcelableExtra("img");
        bottomsheetLayout = findViewById(R.id.bottomsheet);
        msg = findViewById(R.id.msg);
        img_rc = findViewById(R.id.img_rc);
        vdo_rc = findViewById(R.id.vdo_rc);
        pdf_rc = findViewById(R.id.pdf_rc);
        link_rc = findViewById(R.id.link_rc);
        txt_Head = findViewById(R.id.txt_Head);
        txt_data = findViewById(R.id.txt_data);
        txt_data.setMovementMethod(new ScrollingMovementMethod());

        //spinnerStudents = findViewById(R.id.spinnerStudents);

        preferenceManager = new PreferenceManager(this);
        myPrefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        namePrefs = this.getSharedPreferences(NAMEPREFERENCES, Context.MODE_PRIVATE);
        img_rc.setLayoutManager(new LinearLayoutManager(this));
        vdo_rc.setLayoutManager(new LinearLayoutManager(this));
        pdf_rc.setLayoutManager(new LinearLayoutManager(this));
        link_rc.setLayoutManager(new LinearLayoutManager(this));
        commonUtil = new CommonUtil();
        //clear shared preference at the time of loading
        if (preferenceManager.getActivity().equalsIgnoreCase("SelectStudentShare")) {
            preferenceManager.setActivity("ClassShare");
            Intent i = getIntent();
            msg.setText(i.getStringExtra("msg"));
            //image
            Set<String> getmArrayuri = new HashSet<>();
            Set<String> getmArrayname = new HashSet<>();
            Set<String> getmArrayencoded = new HashSet<>();

            ArrayList<String> getmArrayUri = new ArrayList<String>();
            ArrayList<String> getmArrayName = new ArrayList<>();
            ArrayList<String> getmArrayEncoded = new ArrayList<>();

            getmArrayname = preferenceManager.getmarrayname();
            getmArrayuri = preferenceManager.getmarrayuri();
            getmArrayencoded = preferenceManager.getmarrayEncoded();


            if (getmArrayname.size() > 0) {
                for (String name : getmArrayname) {
                    Log.d("name", name);
                    getmArrayName.add(name);
                }

                for (String uri : getmArrayuri) {
                    Log.d("uriarray", uri);
                    getmArrayUri.add(uri);
                }

                for (String encode : getmArrayencoded) {
                    Log.d("encode", encode);
                    getmArrayEncoded.add(encode);
                }


            }
            if (getmArrayname.size() > 0) {
                ArrayList<ImageVideoGtSt> imgList = prepareList(getmArrayUri, getmArrayName, getmArrayEncoded);
                RecyclerImageAdapter recyclerImageAdapter = new RecyclerImageAdapter(imgList, ClassShare.this);
                recyclerImageAdapter.notifyDataSetChanged();
                img_rc.setAdapter(recyclerImageAdapter);
            }

            //video
            Set<String> getmArrayuriVideo = new HashSet<>();
            Set<String> getmArraynameVideo = new HashSet<>();
            Set<String> getmArrayencodedVideo = new HashSet<>();

            ArrayList<String> getmArrayUriVideo = new ArrayList<String>();
            ArrayList<String> getmArrayNameVideo = new ArrayList<>();
            ArrayList<String> getmArrayEncodedVideo = new ArrayList<>();

            getmArraynameVideo = preferenceManager.getmarraynameVIDEO();
            getmArrayuriVideo = preferenceManager.getmarrayuriVIDEO();
            getmArrayencodedVideo = preferenceManager.getmarrayEncodedVIDEO();


            if (getmArraynameVideo.size() > 0) {
                for (String name : getmArraynameVideo) {
                    Log.d("name", name);
                    getmArrayNameVideo.add(name);
                }

                for (String uri : getmArrayuriVideo) {
                    Log.d("uriarray", uri);
                    getmArrayUriVideo.add(uri);
                }

                for (String encode : getmArrayencodedVideo) {
                    Log.d("encode", encode);
                    getmArrayEncodedVideo.add(encode);
                }


            }
            if (getmArraynameVideo.size() > 0) {
                ArrayList<ImageVideoGtSt> imgList = prepareList(getmArrayUriVideo, getmArrayNameVideo, getmArrayEncodedVideo);
                RecyclerVideoAdapter recyclerVideoAdapter = new RecyclerVideoAdapter(imgList, ClassShare.this);
                recyclerVideoAdapter.notifyDataSetChanged();
                vdo_rc.setAdapter(recyclerVideoAdapter);
            }


            //pdf
            Set<String> getmArrayuriPDF = new HashSet<>();
            Set<String> getmArraynamePDF = new HashSet<>();
            Set<String> getmArrayencodedPDF = new HashSet<>();

            ArrayList<String> getmArrayUripdf = new ArrayList<String>();
            ArrayList<String> getmArrayNamepdf = new ArrayList<>();
            ArrayList<String> getmArrayEncodedpdf = new ArrayList<>();

            getmArraynamePDF = preferenceManager.getmarraynamePDF();
            getmArrayuriPDF = preferenceManager.getmarrayuriPDF();
            getmArrayencodedPDF = preferenceManager.getmarrayEncodedPDF();


            if (getmArraynamePDF.size() > 0) {
                for (String name : getmArraynamePDF) {
                    Log.d("name", name);
                    getmArrayNamepdf.add(name);
                }

                for (String uri : getmArrayuriPDF) {
                    Log.d("uriarray", uri);
                    getmArrayUripdf.add(uri);
                }

                for (String encode : getmArrayencodedPDF) {
                    Log.d("encode", encode);
                    getmArrayEncodedpdf.add(encode);
                }


            }
            if (getmArraynamePDF.size() > 0) {
                ArrayList<ImageVideoGtSt> pdfList = prepareList(getmArrayUripdf, getmArrayNamepdf, getmArrayEncodedpdf);
                RecyclerPdfAdapter recyclerPdfAdapter = new RecyclerPdfAdapter(pdfList, ClassShare.this);
                recyclerPdfAdapter.notifyDataSetChanged();
                pdf_rc.setAdapter(recyclerPdfAdapter);
            }

            //link
            Set<String> getmArraylink = new HashSet<>();

            ArrayList<String> getmArrayLink = new ArrayList<String>();

            getmArraylink = preferenceManager.getmarrayLink();
            if (getmArraylink.size() > 0) {
                for (String name : getmArraylink) {
                    Log.d("name", name);
                    getmArrayLink.add(name);
                }

            }
            if (getmArraylink.size() > 0) {
                ArrayList<ImageVideoGtSt> imgList = prepareList(getmArrayLink);
                RecyclerLinkAdapter recyclerLinkAdapter = new RecyclerLinkAdapter(imgList, ClassShare.this);
                recyclerLinkAdapter.notifyDataSetChanged();
                link_rc.setAdapter(recyclerLinkAdapter);
            }

            userId = myPrefs.getString(MyPREFERENCES.toString(), null);
            if (userId != null) {
                userId += "," + preferenceManager.getTeacherList();
                Toast.makeText(ClassShare.this, userId, Toast.LENGTH_SHORT).show();
            }
            username = namePrefs.getString(NAMEPREFERENCES.toString(), null);
            if (username != null) {
                txt_data.setText(username);
                Toast.makeText(ClassShare.this, username, Toast.LENGTH_SHORT).show();
            }


            //Toast.makeText(ClassShare.this, "SelectStudentShare", Toast.LENGTH_SHORT).show();
        } else {
            Set<String> getmuri = new HashSet<>();
            Set<String> getmAname = new HashSet<>();
            getmuri.clear();
            getmAname.clear();
            preferenceManager.setmarrayuri(getmuri);
            preferenceManager.setmarrayname(getmAname);
            preferenceManager.setmarrayEncoded(getmuri);

            preferenceManager.setmarrayuriVIDEO(getmuri);
            preferenceManager.setmarraynameVIDEO(getmAname);
            preferenceManager.setmarrayEncodedVIDEO(getmuri);


            preferenceManager.setmarrayuriPDF(getmuri);
            preferenceManager.setmarraynamePDF(getmAname);
            preferenceManager.setmarrayEncodedPDF(getmuri);


            preferenceManager.setmarrayLink(getmuri);

            preferenceManager.setStudentTeacherList(savedItems);

            preferenceManager.setTeacherList(savedItems);

        }
        //Spinner Only visible to teacher
        if (preferenceManager.getPanel() == 1) {
            //spinnerStudents.setVisibility(View.VISIBLE);

            txt_Head.setVisibility(View.VISIBLE);
            txt_data.setVisibility(View.VISIBLE);
            loadTeacher(preferenceManager.getClassId());

            // LoadSpinnerData(preferenceManager.getClassId());
        } else /* (preferenceManager.getPanel() == 0) */ {
            Toast.makeText(ClassShare.this, "done", Toast.LENGTH_SHORT).show();
            loadTeacher(preferenceManager.getClassId());
            loadStudent(preferenceManager.getClassId());


            userId = preferenceManager.getStudentTeacherList();
            Toast.makeText(ClassShare.this, "userID :: " + preferenceManager.getStudentTeacherList(), Toast.LENGTH_SHORT).show();

          /*  userId = myPrefs.getString(MyPREFERENCES.toString(), null);
            if (userId != null) {
                Log.d("String", userId);
                Toast.makeText(ClassShare.this, "userId:: " + userId, Toast.LENGTH_SHORT).show();
            }*/

        }


        txt_Head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                preferenceManager.setSEND_STATUS("100");
                preferenceManager.setActivity("SelectStudentShare");

                Intent i = new Intent(ClassShare.this, SelectStudentShare.class);

                    i.putExtra("msg", msg.getText().toString());

                /* startActivityForResult(i, 200);*/
                startActivity(i);
                ClassShare.this.finish();
            }
        });
      /*  spinnerStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ClassShare.this,SelectStudentShare.class);
                startActivity(i);
            }
        });*/
        //CameraGtSt modal[] = {new CameraGtSt(img, i.getStringExtra("imgName"))};

        // Toast.makeText(ClassShare.this, i.getStringExtra("imgName"), Toast.LENGTH_SHORT).show();
        //CameraAdapter adapter = new CameraAdapter(modal);
        //img_rc.setAdapter(adapter);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheetLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        LinearLayout pickPhoto = bottomsheetLayout.findViewById(R.id.takephotolayout);
        LinearLayout pickVideo = bottomsheetLayout.findViewById(R.id.recordvideolayout);
        LinearLayout pickPDF = bottomsheetLayout.findViewById(R.id.filelayout);
        LinearLayout linklayout = bottomsheetLayout.findViewById(R.id.linklayout);
        pickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent i = new Intent(ClassShare.this, CameraActivity.class);
                startActivity(i);*/

               /* Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                *//*Intent galleryIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);*//*
                startActivityForResult(galleryIntent, 0);*/


                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);


            }
        });
        pickVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);*/


                Intent intent = new Intent();
                intent.setType("video/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO_MULTIPLE);

            }
        });
        pickPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();


                intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_MULTIPLE);

            }
        });
        linklayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemDialog(ClassShare.this);
            }
        });

    }

    //get selected Images and Videos in recycler view

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

            }
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<String>();
                if (data.getData() != null) {
                   /* ArrayList<String> setmArrayUri = new ArrayList<String>();
                    ArrayList<String> setmArrayName = new ArrayList<>();


                    Set<String> setmArrayuri = new HashSet<>();
                    Set<String> setmArrayname = new HashSet<>();

                    Set<String> getmArrayuri = new HashSet<>();
                    Set<String> getmArrayname = new HashSet<>();


                    ArrayList<String> getmArrayUri = new ArrayList<String>();
                    ArrayList<String> getmArrayName = new ArrayList<>();
*/


                    ArrayList<String> setmArrayUri = new ArrayList<String>();
                    ArrayList<String> setmArrayName = new ArrayList<>();
                    ArrayList<String> setmArrayEncoded = new ArrayList<>();

                    Set<String> setmArrayuri = new HashSet<>();
                    Set<String> setmArrayname = new HashSet<>();
                    Set<String> setmArrayencoded = new HashSet<>();

                    Set<String> getmArrayuri = new HashSet<>();
                    Set<String> getmArrayname = new HashSet<>();
                    Set<String> getmArrayencoded = new HashSet<>();


                    ArrayList<String> getmArrayUri = new ArrayList<String>();
                    ArrayList<String> getmArrayName = new ArrayList<>();
                    ArrayList<String> getmArrayEncoded = new ArrayList<>();


                    getmArrayname = preferenceManager.getmarrayname();
                    getmArrayuri = preferenceManager.getmarrayuri();
                    getmArrayencoded = preferenceManager.getmarrayEncoded();

                    Log.d("firstDatauri", String.valueOf(getmArrayname.size()));

                    if (getmArrayname.size() > 0) {
                        for (String name : getmArrayname) {
                            Log.d("name", name);
                            getmArrayName.add(name);
                        }

                        for (String uri : getmArrayuri) {
                            Log.d("uriarray", uri);
                            getmArrayUri.add(uri);
                        }

                        for (String encode : getmArrayencoded) {
                            Log.d("encode", encode);
                            getmArrayEncoded.add(encode);
                        }

                    }


                    Uri mImageUri = data.getData();
                    String mImageuriString = mImageUri.toString();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);
                    String filename = imageEncoded.toString().substring(imageEncoded.toString().lastIndexOf("/") + 1);

                    cursor.close();
                    File file = new File(imageEncoded);
                    if ((file.length() / 1024) / 1024 > 1) {
                        Toast.makeText(ClassShare.this, "File name ::" + filename + "has size>1mb.size should be les than 1 mb", Toast.LENGTH_SHORT).show();
                    } else {
                        getmArrayUri.add(mImageuriString);
                        getmArrayName.add(filename);
                        getmArrayEncoded.add(imageEncoded);
                    }

                    setmArrayname.clear();
                    setmArrayuri.clear();
                    setmArrayencoded.clear();

                    preferenceManager.setmarrayuri(setmArrayuri);
                    preferenceManager.setmarrayname(setmArrayname);
                    preferenceManager.setmarrayEncoded(setmArrayencoded);


                    setmArrayname.addAll(getmArrayName);
                    setmArrayuri.addAll(getmArrayUri);
                    setmArrayencoded.addAll(getmArrayEncoded);

                    preferenceManager.setmarrayuri(setmArrayuri);
                    preferenceManager.setmarrayname(setmArrayname);
                    preferenceManager.setmarrayEncoded(setmArrayencoded);
/*

                    getmArrayname = preferenceManager.getmarrayname();
                    getmArrayuri = preferenceManager.getmarrayuri();

                    Log.d("sizeafterDeletion", String.valueOf(getmArrayname.size()));
                    for (String name : getmArrayname) {
                        getmArrayName.add(name);
                    }

                    for (String uri : getmArrayuri) {
                        getmArrayUri.add(uri);
                    }

                    setmArrayname.clear();
                    setmArrayuri.clear();

                    preferenceManager.setmarrayuri(setmArrayuri);
                    preferenceManager.setmarrayname(setmArrayname);

                    setmArrayname.addAll(getmArrayName);
                    setmArrayuri.addAll(getmArrayUri);

                    preferenceManager.setmarrayuri(setmArrayuri);
                    preferenceManager.setmarrayname(setmArrayname);

                    getmArrayname = preferenceManager.getmarrayname();
                    getmArrayuri = preferenceManager.getmarrayuri();

                    getmArrayName.clear();
                    getmArrayUri.clear();

                    for (String name : getmArrayname) {
                        Log.d("name", name);
                        getmArrayName.add(name);
                    }

                    for (String uri : getmArrayuri) {
                        Log.d("uriarray", uri);
                        getmArrayUri.add(uri);
                    }
                    setmArrayname.addAll(getmArrayName);
                    setmArrayuri.addAll(getmArrayUri);

                    preferenceManager.setmarrayuri(setmArrayuri);
                    preferenceManager.setmarrayname(setmArrayname);
*/


                    Log.d("sizepreference", String.valueOf(getmArrayName.size()));
                    ArrayList<ImageVideoGtSt> imgList = prepareList(getmArrayUri, getmArrayName, getmArrayEncoded);
                    RecyclerImageAdapter recyclerImageAdapter = new RecyclerImageAdapter(imgList, ClassShare.this);
                    recyclerImageAdapter.notifyDataSetChanged();
                    img_rc.setAdapter(recyclerImageAdapter);


                  /*  galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
                    gvGallery.setAdapter(galleryAdapter);
                    gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                            .getLayoutParams();
                    mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);
*/
                } else {
                    if (data.getClipData() != null) {


                        ArrayList<String> setmArrayUri = new ArrayList<String>();
                        ArrayList<String> setmArrayName = new ArrayList<>();
                        ArrayList<String> setmArrayEncoded = new ArrayList<>();

                        Set<String> setmArrayuri = new HashSet<>();
                        Set<String> setmArrayname = new HashSet<>();
                        Set<String> setmArrayencoded = new HashSet<>();

                        Set<String> getmArrayuri = new HashSet<>();
                        Set<String> getmArrayname = new HashSet<>();
                        Set<String> getmArrayencoded = new HashSet<>();

                        ArrayList<String> getmArrayUri = new ArrayList<String>();
                        ArrayList<String> getmArrayName = new ArrayList<>();
                        ArrayList<String> getmArrayEncoded = new ArrayList<>();

                        getmArrayname = preferenceManager.getmarrayname();
                        getmArrayuri = preferenceManager.getmarrayuri();
                        getmArrayencoded = preferenceManager.getmarrayEncoded();

                        Log.d("firstDatauri", String.valueOf(getmArrayname.size()));

                        if (getmArrayname.size() > 0) {
                            for (String name : getmArrayname) {
                                Log.d("name", name);
                                getmArrayName.add(name);
                            }

                            for (String uri : getmArrayuri) {
                                Log.d("uriarray", uri);
                                getmArrayUri.add(uri);
                            }

                            for (String encode : getmArrayencoded) {
                                Log.d("encode", encode);
                                getmArrayEncoded.add(encode);
                            }


                        }


                        ClipData mClipData = data.getClipData();

                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            String mImageuriString = uri.toString();

                            Log.d("uri", uri.toString());
                            /* setmArrayUri.add(mImageuriString);*/


                            /* String filename= uri.toString().substring(uri.toString().lastIndexOf("/")+1);*/

                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            String filename = imageEncoded.toString().substring(imageEncoded.toString().lastIndexOf("/") + 1);

                            Log.d("url", imageEncoded.toString());
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();
                            File file = new File(imageEncoded);
                            if ((file.length() / 1024) / 1024 > 1) {
                                Toast.makeText(ClassShare.this, "File name ::" + filename + "has size>1 mb.size should be les than 1 mb", Toast.LENGTH_SHORT).show();
                            } else {
                                getmArrayUri.add(mImageuriString);
                                getmArrayEncoded.add(imageEncoded);
                                getmArrayName.add(filename);
                            }
                            /* setmArrayName.add(filename);*/

                            /*galleryAdapter = new GalleryAdapter(getApplicationContext(), mArrayUri);
                            gvGallery.setAdapter(galleryAdapter);
                            gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                                    .getLayoutParams();
                            mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);*/
                        }


                        setmArrayname.clear();
                        setmArrayuri.clear();
                        setmArrayencoded.clear();

                        preferenceManager.setmarrayuri(setmArrayuri);
                        preferenceManager.setmarrayname(setmArrayname);
                        preferenceManager.setmarrayEncoded(setmArrayencoded);


                        setmArrayname.addAll(getmArrayName);
                        setmArrayuri.addAll(getmArrayUri);
                        setmArrayencoded.addAll(getmArrayEncoded);

                        /*setmArrayname.addAll(setmArrayName);
                        setmArrayuri.addAll(setmArrayUri);*/


                        preferenceManager.setmarrayuri(setmArrayuri);
                        preferenceManager.setmarrayname(setmArrayname);
                        preferenceManager.setmarrayEncoded(setmArrayencoded);

                        /*getmArrayname = preferenceManager.getmarrayname();
                        getmArrayuri = preferenceManager.getmarrayuri();

                        Log.d("sizeafterDeletion", String.valueOf(getmArrayname.size()));

                        for (String name : getmArrayname) {
                            Log.d("name", name);
                            getmArrayName.add(name);
                        }

                        for (String uri : getmArrayuri) {
                            Log.d("uriarray", uri);
                            getmArrayUri.add(uri);
                        }

                        setmArrayname.clear();
                        setmArrayuri.clear();

                        preferenceManager.setmarrayuri(setmArrayuri);
                        preferenceManager.setmarrayname(setmArrayname);


                        setmArrayname.addAll(getmArrayName);
                        setmArrayuri.addAll(getmArrayUri);

                        preferenceManager.setmarrayuri(setmArrayuri);
                        preferenceManager.setmarrayname(setmArrayname);

                        getmArrayname = preferenceManager.getmarrayname();
                        getmArrayuri = preferenceManager.getmarrayuri();

                        getmArrayName.clear();
                        getmArrayUri.clear();

                        for (String name : getmArrayname) {
                            Log.d("name", name);
                            getmArrayName.add(name);
                        }

                        for (String uri : getmArrayuri) {
                            Log.d("uriarray", uri);
                            getmArrayUri.add(uri);
                        }*/




                       /* setmArrayname.addAll(getmArrayName);
                        setmArrayuri.addAll(getmArrayUri);

                        preferenceManager.setmarrayuri(setmArrayuri);
                        preferenceManager.setmarrayname(setmArrayname);*/


                        Log.d("sizepreference", String.valueOf(setmArrayname.size()));
                        ArrayList<ImageVideoGtSt> imgList = prepareList(getmArrayUri, getmArrayName, getmArrayEncoded);
                        RecyclerImageAdapter recyclerImageAdapter = new RecyclerImageAdapter(imgList, ClassShare.this);
                        recyclerImageAdapter.notifyDataSetChanged();
                        img_rc.setAdapter(recyclerImageAdapter);

                        Log.v("LOG_TAG", "Selected Images" + setmArrayUri.size());
                    }
                }
            } else if (requestCode == PICK_VIDEO_MULTIPLE && resultCode == RESULT_OK
                    && null != data)


            {
                // Get the Image from data

                String[] filePathColumn = {MediaStore.Video.Media.DATA};
                videoEncodedList = new ArrayList<String>();
                if (data.getData() != null) {
                   /* ArrayList<String> setmArrayUri = new ArrayList<String>();
                    ArrayList<String> setmArrayName = new ArrayList<>();


                    Set<String> setmArrayuri = new HashSet<>();
                    Set<String> setmArrayname = new HashSet<>();

                    Set<String> getmArrayuri = new HashSet<>();
                    Set<String> getmArrayname = new HashSet<>();


                    ArrayList<String> getmArrayUri = new ArrayList<String>();
                    ArrayList<String> getmArrayName = new ArrayList<>();
*/


                    ArrayList<String> setmArrayUri = new ArrayList<String>();
                    ArrayList<String> setmArrayName = new ArrayList<>();
                    ArrayList<String> setmArrayEncoded = new ArrayList<>();

                    Set<String> setmArrayuri = new HashSet<>();
                    Set<String> setmArrayname = new HashSet<>();
                    Set<String> setmArrayencoded = new HashSet<>();

                    Set<String> getmArrayuri = new HashSet<>();
                    Set<String> getmArrayname = new HashSet<>();
                    Set<String> getmArrayencoded = new HashSet<>();

                    ArrayList<String> getmArrayEncoded = new ArrayList<>();
                    ArrayList<String> getmArrayUri = new ArrayList<String>();
                    ArrayList<String> getmArrayName = new ArrayList<>();


                    getmArrayname = preferenceManager.getmarraynameVIDEO();
                    getmArrayuri = preferenceManager.getmarrayuriVIDEO();
                    getmArrayencoded = preferenceManager.getmarrayEncodedVIDEO();

                    Log.d("firstDatauri", String.valueOf(getmArrayname.size()));

                    if (getmArrayname.size() > 0) {
                        for (String name : getmArrayname) {
                            Log.d("name", name);
                            getmArrayName.add(name);
                        }

                        for (String uri : getmArrayuri) {
                            Log.d("uriarray", uri);
                            getmArrayUri.add(uri);
                        }


                        for (String encode : getmArrayencoded) {
                            Log.d("encode", encode);
                            getmArrayEncoded.add(encode);
                        }


                    }


                    Uri mImageUri = data.getData();
                    String mImageuriString = mImageUri.toString();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    videoEncoded = cursor.getString(columnIndex);
                    String filename = videoEncoded.toString().substring(videoEncoded.toString().lastIndexOf("/") + 1);

                    cursor.close();
                    File file = new File(videoEncoded);
                    if ((file.length() / 1024) / 1024 > 15) {
                        Toast.makeText(ClassShare.this, "File name ::" + filename + "has size>15mb.size should be les than 15 mb", Toast.LENGTH_SHORT).show();
                    } else {
                        getmArrayUri.add(mImageuriString);
                        getmArrayName.add(filename);
                        getmArrayEncoded.add(videoEncoded);
                    }
                    setmArrayname.clear();
                    setmArrayuri.clear();
                    setmArrayencoded.clear();

                    preferenceManager.setmarrayuriVIDEO(setmArrayuri);
                    preferenceManager.setmarraynameVIDEO(setmArrayname);
                    preferenceManager.setmarrayEncodedVIDEO(setmArrayencoded);


                    setmArrayname.addAll(getmArrayName);
                    setmArrayuri.addAll(getmArrayUri);
                    setmArrayencoded.addAll(getmArrayEncoded);

                    preferenceManager.setmarrayuriVIDEO(setmArrayuri);
                    preferenceManager.setmarraynameVIDEO(setmArrayname);
                    preferenceManager.setmarrayEncodedVIDEO(setmArrayencoded);
/*

                    getmArrayname = preferenceManager.getmarrayname();
                    getmArrayuri = preferenceManager.getmarrayuri();

                    Log.d("sizeafterDeletion", String.valueOf(getmArrayname.size()));
                    for (String name : getmArrayname) {
                        getmArrayName.add(name);
                    }

                    for (String uri : getmArrayuri) {
                        getmArrayUri.add(uri);
                    }

                    setmArrayname.clear();
                    setmArrayuri.clear();

                    preferenceManager.setmarrayuri(setmArrayuri);
                    preferenceManager.setmarrayname(setmArrayname);

                    setmArrayname.addAll(getmArrayName);
                    setmArrayuri.addAll(getmArrayUri);

                    preferenceManager.setmarrayuri(setmArrayuri);
                    preferenceManager.setmarrayname(setmArrayname);

                    getmArrayname = preferenceManager.getmarrayname();
                    getmArrayuri = preferenceManager.getmarrayuri();

                    getmArrayName.clear();
                    getmArrayUri.clear();

                    for (String name : getmArrayname) {
                        Log.d("name", name);
                        getmArrayName.add(name);
                    }

                    for (String uri : getmArrayuri) {
                        Log.d("uriarray", uri);
                        getmArrayUri.add(uri);
                    }
                    setmArrayname.addAll(getmArrayName);
                    setmArrayuri.addAll(getmArrayUri);

                    preferenceManager.setmarrayuri(setmArrayuri);
                    preferenceManager.setmarrayname(setmArrayname);
*/


                    Log.d("sizepreference", String.valueOf(getmArrayName.size()));
                    ArrayList<ImageVideoGtSt> imgList = prepareList(getmArrayUri, getmArrayName, getmArrayEncoded);
                    RecyclerVideoAdapter recyclerVideoAdapter = new RecyclerVideoAdapter(imgList, ClassShare.this);
                    recyclerVideoAdapter.notifyDataSetChanged();
                    vdo_rc.setAdapter(recyclerVideoAdapter);


                  /*  galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
                    gvGallery.setAdapter(galleryAdapter);
                    gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                            .getLayoutParams();
                    mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);
*/
                } else {
                    if (data.getClipData() != null) {


                        ArrayList<String> setmArrayUri = new ArrayList<String>();
                        ArrayList<String> setmArrayName = new ArrayList<>();
                        ArrayList<String> setmArrayEncoded = new ArrayList<>();

                        Set<String> setmArrayuri = new HashSet<>();
                        Set<String> setmArrayname = new HashSet<>();
                        Set<String> setmArrayencoded = new HashSet<>();

                        Set<String> getmArrayuri = new HashSet<>();
                        Set<String> getmArrayname = new HashSet<>();
                        Set<String> getmArrayencoded = new HashSet<>();

                        ArrayList<String> getmArrayUri = new ArrayList<String>();
                        ArrayList<String> getmArrayName = new ArrayList<>();
                        ArrayList<String> getmArrayEncoded = new ArrayList<>();

                        getmArrayname = preferenceManager.getmarraynameVIDEO();
                        getmArrayuri = preferenceManager.getmarrayuriVIDEO();
                        getmArrayencoded = preferenceManager.getmarrayEncodedVIDEO();

                        Log.d("firstDatauri", String.valueOf(getmArrayname.size()));

                        if (getmArrayname.size() > 0) {
                            for (String name : getmArrayname) {
                                Log.d("name", name);
                                getmArrayName.add(name);
                            }

                            for (String uri : getmArrayuri) {
                                Log.d("uriarray", uri);
                                getmArrayUri.add(uri);
                            }

                            for (String encode : getmArrayencoded) {
                                Log.d("encode", encode);
                                getmArrayEncoded.add(encode);
                            }


                        }


                        ClipData mClipData = data.getClipData();

                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            String mImageuriString = uri.toString();

                            Log.d("uri", uri.toString());
                            /* setmArrayUri.add(mImageuriString);*/


                            /* String filename= uri.toString().substring(uri.toString().lastIndexOf("/")+1);*/

                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            videoEncoded = cursor.getString(columnIndex);
                            String filename = videoEncoded.toString().substring(videoEncoded.toString().lastIndexOf("/") + 1);

                            Log.d("url", videoEncoded.toString());
                            videoEncodedList.add(videoEncoded);
                            cursor.close();
                            File file = new File(videoEncoded);
                            if ((file.length() / 1024) / 1024 > 15) {
                                Toast.makeText(ClassShare.this, "File name ::" + filename + "has size>15mb.size should be les than 15 mb", Toast.LENGTH_SHORT).show();
                            } else {
                                getmArrayUri.add(mImageuriString);
                                getmArrayEncoded.add(videoEncoded);
                                getmArrayName.add(filename);
                            }
                        }


                        setmArrayname.clear();
                        setmArrayuri.clear();
                        setmArrayencoded.clear();

                        preferenceManager.setmarrayuriVIDEO(setmArrayuri);
                        preferenceManager.setmarraynameVIDEO(setmArrayname);
                        preferenceManager.setmarrayEncodedVIDEO(setmArrayencoded);


                        setmArrayname.addAll(getmArrayName);
                        setmArrayuri.addAll(getmArrayUri);
                        setmArrayencoded.addAll(getmArrayEncoded);

                        preferenceManager.setmarrayuriVIDEO(setmArrayuri);
                        preferenceManager.setmarraynameVIDEO(setmArrayname);
                        preferenceManager.setmarrayEncodedVIDEO(setmArrayencoded);

                        Log.d("sizepreference", String.valueOf(setmArrayname.size()));
                        ArrayList<ImageVideoGtSt> imgList = prepareList(getmArrayUri, getmArrayName, getmArrayEncoded);
                        RecyclerVideoAdapter recyclerVideoAdapter = new RecyclerVideoAdapter(imgList, ClassShare.this);
                        recyclerVideoAdapter.notifyDataSetChanged();
                        vdo_rc.setAdapter(recyclerVideoAdapter);

                        Log.v("LOG_TAG", "Selected Images" + setmArrayUri.size());
                    }
                }
            } else if (requestCode == PICK_PDF_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                // String[] filePathColumn = {MediaStore.Images.ImageColumns.DATA};
                pdfEncodedList = new ArrayList<String>();
                if (data.getData() != null) {


                    ArrayList<String> setmArrayUri = new ArrayList<String>();
                    ArrayList<String> setmArrayName = new ArrayList<>();
                    ArrayList<String> setmArrayEncoded = new ArrayList<>();

                    Set<String> setmArrayuri = new HashSet<>();
                    Set<String> setmArrayname = new HashSet<>();
                    Set<String> setmArrayencoded = new HashSet<>();

                    Set<String> getmArrayuri = new HashSet<>();
                    Set<String> getmArrayname = new HashSet<>();
                    Set<String> getmArrayencoded = new HashSet<>();


                    ArrayList<String> getmArrayUri = new ArrayList<String>();
                    ArrayList<String> getmArrayName = new ArrayList<>();
                    ArrayList<String> getmArrayEncoded = new ArrayList<>();


                    getmArrayname = preferenceManager.getmarraynamePDF();
                    getmArrayuri = preferenceManager.getmarrayuriPDF();
                    getmArrayencoded = preferenceManager.getmarrayEncodedPDF();

                    Log.d("firstDatauri", String.valueOf(getmArrayname.size()));

                    if (getmArrayname.size() > 0) {
                        for (String name : getmArrayname) {
                            Log.d("name", name);
                            getmArrayName.add(name);
                        }

                        for (String uri : getmArrayuri) {
                            Log.d("uriarray", uri);
                            getmArrayUri.add(uri);
                        }

                        for (String uri : getmArrayencoded) {
                            Log.d("encodedarray", uri);
                            getmArrayEncoded.add(uri);
                        }


                    }


                    Uri mImageUri = data.getData();
                    String mImageuriString = mImageUri.toString();

                    Log.d("oneDatauriPDF", mImageUri.toString());

                    if (DocumentsContract.isDocumentUri(this, mImageUri)) {

                        // ExternalStorageProvider
                        if (isExternalStorageDocument(mImageUri)) {

                            final String docId = DocumentsContract.getDocumentId(mImageUri);
                            Log.d("docId", docId);
                            final String[] split = docId.split(":");
                            final String type = split[0];
                            Log.d("type", type);
                            if ("692F-4630".equalsIgnoreCase(type)) {

                                pdfEncoded = Environment.getExternalStorageDirectory() + "/" + split[1];
                            }

                            // TODO handle non-primary volumes
                        }
                        // DownloadsProvider
                        else if (isDownloadsDocument(mImageUri)) {

                            final String id = DocumentsContract.getDocumentId(mImageUri);
                            final Uri contentUri = ContentUris.withAppendedId(
                                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                            String[] projection = {MediaStore.Images.Media.DATA};
                            Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
                            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            cursor.moveToFirst();
                            pdfEncoded = cursor.getString(column_index);
                            cursor.close();
                        }

                    }

                    Log.d("pdfEncodedDatauriPDF", pdfEncoded.toString());

                    String filename = pdfEncoded.toString().substring(pdfEncoded.toString().lastIndexOf("/") + 1);


                    getmArrayUri.add(mImageuriString);
                    getmArrayName.add(filename);
                    getmArrayEncoded.add(pdfEncoded);
                    setmArrayname.clear();
                    setmArrayuri.clear();
                    setmArrayencoded.clear();

                    preferenceManager.setmarrayuriPDF(setmArrayuri);
                    preferenceManager.setmarraynamePDF(setmArrayname);
                    preferenceManager.setmarrayEncodedPDF(setmArrayencoded);


                    setmArrayname.addAll(getmArrayName);
                    setmArrayuri.addAll(getmArrayUri);
                    setmArrayencoded.addAll(getmArrayEncoded);

                    preferenceManager.setmarrayuriPDF(setmArrayuri);
                    preferenceManager.setmarraynamePDF(setmArrayname);
                    preferenceManager.setmarrayEncodedPDF(setmArrayencoded);


                    Log.d("sizepreference", String.valueOf(getmArrayName.size()));
                    ArrayList<ImageVideoGtSt> pdfList = prepareList(getmArrayUri, getmArrayName, getmArrayEncoded);
                    RecyclerPdfAdapter recyclerPdfAdapter = new RecyclerPdfAdapter(pdfList, ClassShare.this);
                    recyclerPdfAdapter.notifyDataSetChanged();
                    pdf_rc.setAdapter(recyclerPdfAdapter);


                } else {

                    if (data.getClipData() != null) {


                        ArrayList<String> setmArrayUri = new ArrayList<String>();
                        ArrayList<String> setmArrayName = new ArrayList<>();
                        ArrayList<String> setmArrayEncoded = new ArrayList<>();

                        Set<String> setmArrayuri = new HashSet<>();
                        Set<String> setmArrayname = new HashSet<>();
                        Set<String> setmArrayencoded = new HashSet<>();

                        Set<String> getmArrayuri = new HashSet<>();
                        Set<String> getmArrayname = new HashSet<>();
                        Set<String> getmArrayenocded = new HashSet<>();


                        ArrayList<String> getmArrayUri = new ArrayList<String>();
                        ArrayList<String> getmArrayName = new ArrayList<>();
                        ArrayList<String> getmArrayEncoded = new ArrayList<>();

                        getmArrayname = preferenceManager.getmarraynamePDF();
                        getmArrayuri = preferenceManager.getmarrayuriPDF();
                        getmArrayenocded = preferenceManager.getmarrayEncodedPDF();

                        Log.d("firstDatauri", String.valueOf(getmArrayname.size()));

                        if (getmArrayname.size() > 0) {
                            for (String name : getmArrayname) {
                                Log.d("name", name);
                                getmArrayName.add(name);
                            }

                            for (String uri : getmArrayuri) {
                                Log.d("uriarray", uri);
                                getmArrayUri.add(uri);
                            }

                            for (String encoded : getmArrayenocded) {
                                Log.d("uriarray", encoded);
                                getmArrayEncoded.add(encoded);
                            }


                        }


                        ClipData mClipData = data.getClipData();

                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            String mImageuriString = uri.toString();

                            Log.d("ClipDatauriPDF", uri.toString());
                            /* setmArrayUri.add(mImageuriString);*/

                            /* String filename= uri.toString().substring(uri.toString().lastIndexOf("/")+1);*/


                            Log.d("MultipleDatauriPDF", uri.toString());

                            if (DocumentsContract.isDocumentUri(this, uri)) {

                                // ExternalStorageProvider
                                if (isExternalStorageDocument(uri)) {

                                    final String docId = DocumentsContract.getDocumentId(uri);
                                    Log.d("docId", docId);
                                    final String[] split = docId.split(":");
                                    final String type = split[0];
                                    Log.d("type", type);
                                    if ("692F-4630".equalsIgnoreCase(type)) {

                                        pdfEncoded = Environment.getExternalStorageDirectory() + "/" + split[1];
                                    }

                                    // TODO handle non-primary volumes
                                }
                                // DownloadsProvider
                                else if (isDownloadsDocument(uri)) {

                                    final String id = DocumentsContract.getDocumentId(uri);
                                    final Uri contentUri = ContentUris.withAppendedId(
                                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                                    String[] projection = {MediaStore.Images.Media.DATA};
                                    Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
                                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                                    cursor.moveToFirst();
                                    pdfEncoded = cursor.getString(column_index);
                                    cursor.close();
                                }

                            }





                            /*// Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            pdfEncoded = cursor.getString(columnIndex);
*/
                            String filename = pdfEncoded.toString().substring(pdfEncoded.toString().lastIndexOf("/") + 1);

                            Log.d("url", pdfEncoded.toString());
                            pdfEncodedList.add(pdfEncoded);
                            // cursor.close();

                            getmArrayUri.add(mImageuriString);
                            getmArrayName.add(filename);
                            getmArrayEncoded.add(pdfEncoded);
                        }


                        setmArrayname.clear();
                        setmArrayuri.clear();
                        setmArrayencoded.clear();

                        preferenceManager.setmarrayuriPDF(setmArrayuri);
                        preferenceManager.setmarraynamePDF(setmArrayname);
                        preferenceManager.setmarrayEncodedPDF(setmArrayencoded);


                        setmArrayname.addAll(getmArrayName);
                        setmArrayuri.addAll(getmArrayUri);
                        setmArrayencoded.addAll(getmArrayEncoded);

                        /*setmArrayname.addAll(setmArrayName);
                        setmArrayuri.addAll(setmArrayUri);*/


                        preferenceManager.setmarrayuriPDF(setmArrayuri);
                        preferenceManager.setmarraynamePDF(setmArrayname);
                        preferenceManager.setmarrayEncodedPDF(setmArrayencoded);


                        Log.d("sizepreference", String.valueOf(setmArrayname.size()));
                        ArrayList<ImageVideoGtSt> pdfList = prepareList(getmArrayUri, getmArrayName, getmArrayEncoded);
                        RecyclerPdfAdapter recyclerPdfAdapter = new RecyclerPdfAdapter(pdfList, ClassShare.this);
                        recyclerPdfAdapter.notifyDataSetChanged();
                        pdf_rc.setAdapter(recyclerPdfAdapter);

                        Log.v("LOG_TAG", "Selected Images" + setmArrayUri.size());
                    }


                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private ArrayList<ImageVideoGtSt> prepareList(ArrayList<String> mArrayUri, ArrayList<String> mArrayName, ArrayList<String> mArrayEncode) {
        ArrayList<ImageVideoGtSt> img = new ArrayList<ImageVideoGtSt>();
        for (int i = 0; i < mArrayUri.size(); i++) {
            ImageVideoGtSt imageGtSt = new ImageVideoGtSt();
            imageGtSt.setImg(mArrayUri.get(i));
            imageGtSt.setName(mArrayName.get(i));
            imageGtSt.setEncoded(mArrayEncode.get(i));
            img.add(imageGtSt);
        }
        return img;

    }

    private ArrayList<ImageVideoGtSt> prepareList(ArrayList<String> mArrayLink) {
        ArrayList<ImageVideoGtSt> img = new ArrayList<ImageVideoGtSt>();
        for (int i = 0; i < mArrayLink.size(); i++) {
            ImageVideoGtSt imageGtSt = new ImageVideoGtSt();
            imageGtSt.setName(mArrayLink.get(i));
            img.add(imageGtSt);
        }
        return img;

    }


    /* @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         try {
             // When an Image is picked
             if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                 // Get the Image from data
                 Uri selectedImage = data.getData();
                 String[] filePathColumn = {MediaStore.Images.Media.DATA};

                 Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                 assert cursor != null;
                 cursor.moveToFirst();

                 int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                 mediaPath = cursor.getString(columnIndex);
                 String filename = selectedImage.toString().substring(selectedImage.toString().lastIndexOf("/") + 1);

                 // Set the Image in ImageView for Previewing the Media

                 cursor.close();

                 uploadImage();
             } else if (requestCode == 1 && resultCode == RESULT_OK && null != data) {

                 // Get the Video from data
                 Uri selectedVideo = data.getData();
                 String[] filePathColumn = {MediaStore.Video.Media.DATA};
                 Cursor cursor = getContentResolver().query(selectedVideo, filePathColumn, null, null, null);
                 assert cursor != null;
                 cursor.moveToFirst();
                 int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                 mediaPath1 = cursor.getString(columnIndex);
                 // Set the Video Thumb in ImageView Previewing the Media
                 cursor.close();
                 uploadVideo();

             } *//*else if (resultCode == RESULT_OK && requestCode == 200) {
                if (data.hasExtra("check")) {
                    Toast.makeText(this, data.getExtras().getString("check"),
                            Toast.LENGTH_SHORT).show();
                }
            }*//* else {
                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }
*/
    private void uploadImage(String mediaPath) {
        progressDialog.show();

        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(mediaPath);
        // Parsing any Media type file
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file1", file.getName(), requestBody1);
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);
        Call<ServerImageResponse> call = apiInterface.uploadFileImage(fileToUpload1);
        call.enqueue(new Callback<ServerImageResponse>() {
            @Override
            public void onResponse(Call<ServerImageResponse> call, Response<ServerImageResponse> response) {
                ServerImageResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerImageResponse> call, Throwable t) {

            }
        });
    }


    private void uploadPdf(String mediaPath) {
        progressDialog.show();

        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(mediaPath);
        // Parsing any Media type file
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file1", file.getName(), requestBody1);
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);
        Call<ServerImageResponse> call = apiInterface.uploadFilePdf(fileToUpload1);
        call.enqueue(new Callback<ServerImageResponse>() {
            @Override
            public void onResponse(Call<ServerImageResponse> call, Response<ServerImageResponse> response) {
                ServerImageResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerImageResponse> call, Throwable t) {

            }
        });
    }


    private void uploadVideo(String mediaPath1) {
        progressDialog.show();

        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(mediaPath1);
        // Parsing any Media type file
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file1", file.getName(), requestBody1);
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);
        Call<ServerImageResponse> call = apiInterface.uploadFileVideo(fileToUpload1);
        call.enqueue(new Callback<ServerImageResponse>() {
            @Override
            public void onResponse(Call<ServerImageResponse> call, Response<ServerImageResponse> response) {
                ServerImageResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerImageResponse> call, Throwable t) {

            }
        });
    }


                   @Override
                public boolean onCreateOptionsMenu(Menu menu) {
                    MenuInflater inflater = getMenuInflater();
                    inflater.inflate(R.menu.share_actionbar, menu);
                    return super.onCreateOptionsMenu(menu);
                }

                @Override
                public boolean onOptionsItemSelected(MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.attach) {
                        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

                        } else {
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        }
        if (id == R.id.send) {
            /*if (msg.getText().toString().isEmpty()) {
                Toast.makeText(ClassShare.this, "Enter Message Title ", Toast.LENGTH_SHORT).show();
            }*/
            Toast.makeText(ClassShare.this, "userID :: " + preferenceManager.getStudentTeacherList(), Toast.LENGTH_SHORT).show();
            if (preferenceManager.getPanel() == 1 && (preferenceManager.getSEND_STATUS().contains("200") || msg.getText().toString().isEmpty())) {
                Toast.makeText(ClassShare.this, "Select your Student to send message  Or Enter Title", Toast.LENGTH_LONG).show();
            } else if (preferenceManager.getPanel() == 0 && msg.getText().toString().isEmpty()) {
                Toast.makeText(ClassShare.this, "Enter Title", Toast.LENGTH_LONG).show();
            } else {
                if (preferenceManager.getPanel() == 1) {
                    preferenceManager.setSEND_STATUS("200");
                }
                insertMessage(msg.getText().toString(), preferenceManager.getClassId(), preferenceManager.getRegisteredUserId());

                ArrayList<String> imagesArrayName = new ArrayList<>();
                ArrayList<String> imagesArrayUrl = new ArrayList<>();
                ArrayList<String> imagesArrayEncode = new ArrayList<>();

                ArrayList<String> videoArrayName = new ArrayList<>();
                ArrayList<String> videoArrayUrl = new ArrayList<>();
                ArrayList<String> videoArrayEncode = new ArrayList<>();

                ArrayList<String> pdfArrayName = new ArrayList<>();
                ArrayList<String> pdfArrayUrl = new ArrayList<>();
                ArrayList<String> pdfArrayEncode = new ArrayList<>();

                ArrayList<String> LinkArray = new ArrayList<>();

                Set<String> getImageUri = new HashSet<>();
                Set<String> getImageName = new HashSet<>();
                Set<String> getImageEncode = new HashSet<>();

                Set<String> getVideoUri = new HashSet<>();
                Set<String> getVideoName = new HashSet<>();
                Set<String> getVideoEncode = new HashSet<>();

                Set<String> getpdfUri = new HashSet<>();
                Set<String> getpdfName = new HashSet<>();
                Set<String> getpdfEncode = new HashSet<>();

                Set<String> getLink = new HashSet<>();

                getImageUri = preferenceManager.getmarrayuri();
                getImageName = preferenceManager.getmarrayname();
                getImageEncode = preferenceManager.getmarrayEncoded();

                getVideoName = preferenceManager.getmarraynameVIDEO();
                getVideoUri = preferenceManager.getmarrayuriVIDEO();
                getVideoEncode = preferenceManager.getmarrayEncodedVIDEO();

                getpdfName = preferenceManager.getmarraynamePDF();
                getpdfUri = preferenceManager.getmarrayuriPDF();
                getpdfEncode = preferenceManager.getmarrayEncodedPDF();

                getLink = preferenceManager.getmarrayLink();

                //image
                if (getImageUri.size() < 0) {

                } else {
                    imagesArrayName.addAll(getImageName);
                    imagesArrayUrl.addAll(getImageUri);
                    imagesArrayEncode.addAll(getImageEncode);
                    for (int i = 0; i < imagesArrayName.size(); i++) {
                        uploadImage(imagesArrayEncode.get(i));
                    }

                }

                //video
                if (getVideoUri.size() < 0) {

                } else {

                    videoArrayUrl.addAll(getVideoUri);
                    videoArrayName.addAll(getVideoName);
                    videoArrayEncode.addAll(getVideoEncode);


                    for (int i = 0; i < videoArrayName.size(); i++) {
                        uploadVideo(videoArrayEncode.get(i));
                    }

                    /*videoName = new String[videoArrayUrl.size()];
                    videoUrl = new String[videoArrayUrl.size()];


                    //for Videos
                    for (int i = 0; i < videoArrayUrl.size(); i++) {
                        videoUrl[i] = videoArrayUrl.get(i);
                        videoName[i] = videoArrayName.get(i);
                    }*/

                }


                //pdf

                if (getpdfUri.size() < 0) {

                } else {

                    pdfArrayUrl.addAll(getpdfUri);
                    pdfArrayName.addAll(getpdfName);
                    pdfArrayEncode.addAll(getpdfEncode);


                    for (int i = 0; i < pdfArrayName.size(); i++) {
                        uploadPdf(pdfArrayEncode.get(i));
                    }
                }


                //link
                if (getLink.size() < 0) {

                } else {
                    LinkArray.addAll(getLink);
                    for (int i = 0; i < LinkArray.size(); i++) {
                        insertLink(LinkArray.get(i));
                    }
                }
                if (preferenceManager.getPanel() == 1) {
                    insertStudent(userId);
                    if (userId != null) {
                        Toast.makeText(ClassShare.this, userId, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    userId = preferenceManager.getStudentTeacherList();
                    insertStudent(userId);
                    if (userId != null) {
                        Toast.makeText(ClassShare.this, userId, Toast.LENGTH_SHORT).show();
                    }
                }

                // Toast.makeText(ClassShare.this, "Student Selected", Toast.LENGTH_LONG).show();

            }
            // setTeacherMessage();
        }

        return super.onOptionsItemSelected(item);
    }


    private void insertMessage(String message, int c_id, int u_id) {
        commonUtil.loading(ClassShare.this);
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

        Call<MessageSendedResponse> getBrandResponseCall = apiInterface.SET_MESSAGE_RESPONSE_CALL("qw", message, c_id, u_id);

        getBrandResponseCall.enqueue(new Callback<MessageSendedResponse>() {

            @Override
            public void onResponse(Call<MessageSendedResponse> call, Response<MessageSendedResponse> response) {
                Toast.makeText(ClassShare.this, response.body().getSuccess().toString(), Toast.LENGTH_SHORT).show();
                if (response.body().getSuccess() == 200) {
                    commonUtil.cancelLoading();
                    Toast.makeText(ClassShare.this, response.body().getSuccess().toString(), Toast.LENGTH_SHORT).show();


                } else {
                    commonUtil.cancelLoading();
                    Toast.makeText(ClassShare.this, "Invalid Data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<MessageSendedResponse> call, Throwable t) {
                commonUtil.cancelLoading();
                Log.d("123", t.getMessage());
                //Snackbar.make(v, "Can't connect. Check if you're online.", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });
    }


    private void insertLink(String link) {

        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

        Call<ServerImageResponse> getBrandResponseCall = apiInterface.SET_LINK_RESPONSE_CALL("qw", link);

        getBrandResponseCall.enqueue(new Callback<ServerImageResponse>() {

            @Override
            public void onResponse(Call<ServerImageResponse> call, Response<ServerImageResponse> response) {
                if (response.body().getSuccess()) {

                    Log.e("result", "Data Successfully Inserted");

                } else {

                    Toast.makeText(ClassShare.this, "Invalid Data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ServerImageResponse> call, Throwable t) {

                Log.d("123", t.getMessage());
                //Snackbar.make(v, "Can't connect. Check if you're online.", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });
    }


    //opens dialog box to enter class code
    private void showAddItemDialog(Context c) {
        final EditText taskEditText = new EditText(c);


        final AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Link")
                .setMessage("Enter Link")
                .setView(taskEditText)
                .setPositiveButton("Join", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  joinClass(taskEditText.getText().toString());

                        String task = String.valueOf(taskEditText.getText());
                        Set<String> setmArraylink = new HashSet<>();
                        Set<String> getmArraylink = new HashSet<>();
                        ArrayList<String> getmArrayLink = new ArrayList<String>();

                        getmArraylink = preferenceManager.getmarrayLink();
                        Log.d("firstDatauri", String.valueOf(getmArraylink.size()));
                        if (getmArraylink.size() > 0) {
                            for (String name : getmArraylink) {
                                Log.d("name", name);
                                getmArrayLink.add(name);
                            }

                        }

                        getmArrayLink.add(task);

                        setmArraylink.clear();

                        preferenceManager.setmarrayLink(setmArraylink);

                        setmArraylink.addAll(getmArrayLink);

                        preferenceManager.setmarrayLink(setmArraylink);

                        Log.d("sizepreference", String.valueOf(getmArrayLink.size()));
                        ArrayList<ImageVideoGtSt> imgList = prepareList(getmArrayLink);
                        RecyclerLinkAdapter recyclerLinkAdapter = new RecyclerLinkAdapter(imgList, ClassShare.this);
                        recyclerLinkAdapter.notifyDataSetChanged();
                        link_rc.setAdapter(recyclerLinkAdapter);

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();


        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

// OR you can use here setOnShowListener to disable button at first time.

// Now set the textchange listener for edittext
        taskEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!Patterns.WEB_URL.matcher(s).matches()) {
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                } else {
                    // Something into edit text. Enable the button.
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                // Check if edittext is empty
                if (TextUtils.isEmpty(s)) {
                    // Disable ok button
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                } /*else {
                    // Something into edit text. Enable the button.
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }*/

            }
        });

    }

    private void insertStudent(String array) {

        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

        Call<StudentMessageReceiveResponse> getBrandResponseCall = apiInterface.SET_STUDENT_RECEIVER_RESPONSE_CALL("qw", array);

        getBrandResponseCall.enqueue(new Callback<StudentMessageReceiveResponse>() {

            @Override
            public void onResponse(Call<StudentMessageReceiveResponse> call, Response<StudentMessageReceiveResponse> response) {

                if (response.body().getStatus() == 1) {
                    /*Intent i=new Intent(ClassShare.this, BtmNavigationActivity.class);
                    startActivity(i);*/
                    /* ClassShare.this.finish();*/
                    Log.e("result", "Data Successfully Inserted");


                } else {
                    Toast.makeText(ClassShare.this, "Invalid Data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<StudentMessageReceiveResponse> call, Throwable t) {

                Log.d("123", t.getMessage());
                //Snackbar.make(v, "Can't connect. Check if you're online.", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });
    }


    private void LoadSpinnerData(int classId) {

        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);
        Call<ClassStudentListResponse> getBrandResponseCall = apiInterface.GET_STUDENT_LIST_RESPONSE_CALL("qw", classId);
        getBrandResponseCall.enqueue(new Callback<ClassStudentListResponse>() {
            @Override
            public void onResponse(Call<ClassStudentListResponse> call, Response<ClassStudentListResponse> response) {
                if (response.body().getSuccess() == 200) {
                    List<ClassStudentResponse> classStudentResponses = response.body().getStudentList();

                    SpinnerStudentListAdapter spinnerAdapter = new SpinnerStudentListAdapter(ClassShare.this, R.layout.row_spinner_student, classStudentResponses);
                    spinnerStudents.setAdapter(spinnerAdapter);

                } else {

                }
            }

            @Override
            public void onFailure(Call<ClassStudentListResponse> call, Throwable t) {

            }
        });
    }


    private void loadStudent(int classId) {
        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);
        Call<ClassStudentListResponse> getBrandResponseCall = apiInterface.GET_STUDENT_LIST_RESPONSE_CALL("qw", classId);
        getBrandResponseCall.enqueue(new Callback<ClassStudentListResponse>() {
            @Override
            public void onResponse(Call<ClassStudentListResponse> call, Response<ClassStudentListResponse> response) {
                if (response.body().getSuccess() == 200) {
                    savedItems = "";
                    classStudentResponses = response.body().getStudentList();
                    Toast.makeText(ClassShare.this, "Student::" + String.valueOf(classStudentResponses.size()), Toast.LENGTH_SHORT).show();
                    if (classStudentResponses.size() >= 0) {
                        for (int i = 0; i < classStudentResponses.size(); i++) {
                           /* if (classStudentResponses.get(i).getUId().equals(preferenceManager.getRegisteredUserId())) {
                                continue;
                            } else {*/
                            if (savedItems.length() > 0) {
                                savedItems += "," + classStudentResponses.get(i).getUId();
                            } else {
                                savedItems += classStudentResponses.get(i).getUId();
                            }
                            /*}*/
                        }
                        Toast.makeText(ClassShare.this, "Student::" + savedItems, Toast.LENGTH_SHORT).show();
                        String list = preferenceManager.getStudentTeacherList();
                        if (list != "") {
                            savedItems = list + "," + savedItems;
                            preferenceManager.setStudentTeacherList(savedItems);
                        } else {
                            preferenceManager.setStudentTeacherList(savedItems);
                        }
                    }

                } else {
                    Toast.makeText(ClassShare.this, "Invalid Data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ClassStudentListResponse> call, Throwable t) {
                Toast.makeText(ClassShare.this, "Cannot connect!", Toast.LENGTH_SHORT).show();
                Log.d("123", t.getMessage());
            }
        });
    }

    private void loadTeacher(int classsId) {

        APIinterface apiInterface = APIClient.getClient().create(APIinterface.class);

        Call<ClassTeacherListResponse> getBrandResponseCall = apiInterface.GET_TEACHER_LIST_RESPONSE_CALL("qw", classsId);

        getBrandResponseCall.enqueue(new Callback<ClassTeacherListResponse>() {

            @Override
            public void onResponse(Call<ClassTeacherListResponse> call, Response<ClassTeacherListResponse> response) {
                commonUtil.cancelLoading();
                if (response.body().getSuccess() == 200) {
                    savedItems = "";
                    List<ClassTeacherResponse> classTeacherResponses = response.body().getClassTeacher();
                    Toast.makeText(ClassShare.this, "Teacher::" + String.valueOf(classTeacherResponses.size()), Toast.LENGTH_SHORT).show();
                    if (classTeacherResponses.size() >= 0) {
                        for (int i = 0; i < classTeacherResponses.size(); i++) {
                            if (savedItems.length() > 0) {
                                savedItems += "," + classTeacherResponses.get(i).getUId();
                            } else {
                                savedItems += classTeacherResponses.get(i).getUId();
                            }
                        }
                        preferenceManager.setTeacherList(savedItems);
                        Toast.makeText(ClassShare.this, "Teacher ::" + savedItems, Toast.LENGTH_SHORT).show();
                        String list = preferenceManager.getStudentTeacherList();
                        if (list != "") {
                            savedItems = list + "," + savedItems;
                            preferenceManager.setStudentTeacherList(savedItems);
                        } else {
                            preferenceManager.setStudentTeacherList(savedItems);
                        }

                    }

                } else {
                    Toast.makeText(ClassShare.this, "Invalid Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ClassTeacherListResponse> call, Throwable t) {
                commonUtil.cancelLoading();
                Toast.makeText(ClassShare.this, "Cannot connect!", Toast.LENGTH_SHORT).show();
                Log.d("123", t.getMessage());
            }
        });
    }


    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }


    private void SaveSelections() {
// save the selections in the shared preference in private mode for the user


    }


    private void insertfiles() {
    }
}
