package com.example.hp.gap.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class PreferenceManager {
    public static PreferenceManager preferenceManager;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    Set<String> demo = new HashSet<>();

    public static PreferenceManager getInstance() {
        return preferenceManager;
    }

    public PreferenceManager(Context context) {
        preferenceManager = this;
        mSharedPreferences = context.getSharedPreferences(VariableBag.PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void clearPreferences() {
        mEditor.clear();
        mEditor.commit();
    }

    public void removePref(Context context, String keyToRemove) {
        mSharedPreferences = context.getSharedPreferences(VariableBag.PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.remove(keyToRemove);
        mEditor.commit();
    }

    /*set preference for registration*/

    public int getRegisteredUserId() {
        int intUserId = mSharedPreferences.getInt(VariableBag.USER_ID, 1);
        return intUserId;
    }

    public String getRegisteredUserName() {
        String strUserName = mSharedPreferences.getString(VariableBag.USER_NAME, "1");
        return strUserName;
    }

    public String getRegisteredUserEmail() {
        String strUserEmail = mSharedPreferences.getString(VariableBag.USER_EMAIL, "1");
        return strUserEmail;
    }

    public String getRegisteredProfilePic() {
        String strProfilePic = mSharedPreferences.getString(VariableBag.USER_PROFILEPIC, "1");
        return strProfilePic;
    }

    public String getRegisteredUserPassword() {
        String strUserPassword = mSharedPreferences.getString(VariableBag.USER_PASSWORD, "1");
        return strUserPassword;
    }

    public void setRegisteredUserId(int intUserId) {
        mEditor.putInt(VariableBag.USER_ID, intUserId).commit();
    }

    public void setRegisteredUserName(String strUserName) {
        mEditor.putString(VariableBag.USER_NAME, strUserName).commit();
    }

    public void setRegisteredProfilePic(String strProfilePic) {
        mEditor.putString(VariableBag.USER_PROFILEPIC, strProfilePic).commit();
    }

    public void setRegisteredUserEmail(String strUserEmail) {
        mEditor.putString(VariableBag.USER_EMAIL, strUserEmail).commit();
    }

    public void setRegisteredUserPassword(String strUserPassword) {
        mEditor.putString(VariableBag.USER_PASSWORD, strUserPassword).commit();
    }

    public void setRegistration() {
        mEditor.putBoolean(VariableBag.REGISTRATION, true);
        mEditor.commit();
    }

    public void setFlag(boolean value) {
        mEditor.putBoolean(VariableBag.FLAG, value);
        mEditor.commit();
    }

    public boolean getFlag() {
        boolean registration = mSharedPreferences.getBoolean(VariableBag.FLAG, false);
        return registration;
    }

    public boolean getRegistration() {
        boolean registration = mSharedPreferences.getBoolean(VariableBag.REGISTRATION, false);
        return registration;
    }

    public void setLoginSession() {
        mEditor.putBoolean(VariableBag.LOGIN, true);
        mEditor.commit();
    }

    public boolean getLoginSession() {
        boolean login = mSharedPreferences.getBoolean(VariableBag.LOGIN, false);
        return login;
    }

    public int getPanel() {
        int intpanel = mSharedPreferences.getInt(VariableBag.PANEL, 1);
        return intpanel;
    }

    public void setPanel(int intpanel) {
        mEditor.putInt(VariableBag.PANEL, intpanel).commit();
    }


    public void setClassId(int intClassId) {
        mEditor.putInt(VariableBag.CLASSID, intClassId).commit();
    }

    public void setClassName(String strClassName) {
        mEditor.putString(VariableBag.CLASSNAME, strClassName).commit();
    }


    public int getClassId() {
        int intClassId = mSharedPreferences.getInt(VariableBag.CLASSID, 1);
        return intClassId;
    }

    public String getClassName() {
        String strClassName = mSharedPreferences.getString(VariableBag.CLASSNAME, "1");
        return strClassName;
    }

    public String getActivity() {
        String context = mSharedPreferences.getString(VariableBag.ACTIVITY, "1");
        return context;
    }

    public void setActivity(String context) {
        mEditor.putString(VariableBag.ACTIVITY, context).commit();
    }

  public String getCCode() {
        String code = mSharedPreferences.getString(VariableBag.CLASSCODE, "1");
        return code;
    }

    public void setCCode(String code) {
        mEditor.putString(VariableBag.CLASSCODE, code).commit();
    }

  public String getCSubject() {
        String subject = mSharedPreferences.getString(VariableBag.CLASSSUBJECT, "1");
        return subject;
    }

    public void setCSubject(String subject) {
        mEditor.putString(VariableBag.CLASSSUBJECT, subject).commit();
    }
 public String getCTeacherName() {
        String name = mSharedPreferences.getString(VariableBag.CLASSTEACHERNAME, "1");
        return name;
    }

    public void setCTeacherName(String name) {
        mEditor.putString(VariableBag.CLASSTEACHERNAME, name).commit();
    }


    public String getSEND_STATUS() {
        String STATUS = mSharedPreferences.getString(VariableBag.SEND_STATUS, "200");
        return STATUS;
    }

    public void setSEND_STATUS(String STATUS) {
        mEditor.putString(VariableBag.SEND_STATUS, STATUS).commit();
    }


    // Images
    public void setmarrayuri(Set<String> marrayuri) {

        mEditor.putStringSet(VariableBag.MARRAYURIIMAGE, marrayuri);
        mEditor.commit();
    }

    public Set<String> getmarrayuri() {
        Set<String> marrayuri = mSharedPreferences.getStringSet(VariableBag.MARRAYURIIMAGE, demo);
        return marrayuri;
    }

    public void setmarrayname(Set<String> marrayname) {

        mEditor.putStringSet(VariableBag.MARRAYNAMEIMAGE, marrayname);
        mEditor.commit();
    }

    public Set<String> getmarrayname() {
        Set<String> marrayname = mSharedPreferences.getStringSet(VariableBag.MARRAYNAMEIMAGE, demo);
        return marrayname;
    }

    //video
    public void setmarrayuriVIDEO(Set<String> marrayuri) {

        mEditor.putStringSet(VariableBag.MARRAYURIVIDEO, marrayuri);
        mEditor.commit();
    }

    public Set<String> getmarrayuriVIDEO() {
        Set<String> marrayuri = mSharedPreferences.getStringSet(VariableBag.MARRAYURIVIDEO, demo);
        return marrayuri;
    }

    public void setmarraynameVIDEO(Set<String> marrayname) {

        mEditor.putStringSet(VariableBag.MARRAYNAMEVIDEO, marrayname);
        mEditor.commit();
    }

    public Set<String> getmarraynameVIDEO() {
        Set<String> marrayname = mSharedPreferences.getStringSet(VariableBag.MARRAYNAMEVIDEO, demo);
        return marrayname;
    }

    public void setmarrayEncodedVIDEO(Set<String> marrayEncodedVideo) {

        mEditor.putStringSet(VariableBag.MARRAYENCODEDVIDEO, marrayEncodedVideo);
        mEditor.commit();
    }

    public Set<String> getmarrayEncodedVIDEO() {
        Set<String> marrayEncodedVideo = mSharedPreferences.getStringSet(VariableBag.MARRAYENCODEDVIDEO, demo);
        return marrayEncodedVideo;
    }

    public void setmarrayEncoded(Set<String> marrayEncoded) {

        mEditor.putStringSet(VariableBag.MARRAYENCODEDIMAGE, marrayEncoded);
        mEditor.commit();
    }

    public Set<String> getmarrayEncoded() {
        Set<String> marrayEncoded = mSharedPreferences.getStringSet(VariableBag.MARRAYENCODEDIMAGE, demo);
        return marrayEncoded;
    }


    //pdf
    public void setmarrayuriPDF(Set<String> marrayuri) {

        mEditor.putStringSet(VariableBag.MARRAYURIPDF, marrayuri);
        mEditor.commit();
    }

    public Set<String> getmarrayuriPDF() {
        Set<String> marrayuri = mSharedPreferences.getStringSet(VariableBag.MARRAYURIPDF, demo);
        return marrayuri;
    }

    public void setmarraynamePDF(Set<String> marrayname) {

        mEditor.putStringSet(VariableBag.MARRAYNAMEPDF, marrayname);
        mEditor.commit();
    }

    public Set<String> getmarraynamePDF() {
        Set<String> marrayname = mSharedPreferences.getStringSet(VariableBag.MARRAYNAMEPDF, demo);
        return marrayname;
    }

    public void setmarrayEncodedPDF(Set<String> marrayEncodedPdf) {

        mEditor.putStringSet(VariableBag.MARRAYENCODEDPDF, marrayEncodedPdf);
        mEditor.commit();
    }

    public Set<String> getmarrayEncodedPDF() {
        Set<String> marrayEncodedPdf = mSharedPreferences.getStringSet(VariableBag.MARRAYENCODEDPDF, demo);
        return marrayEncodedPdf;
    }


    public Set<String> getmarrayLink() {
        Set<String> marraylink = mSharedPreferences.getStringSet(VariableBag.MARRAYLINK, demo);
        return marraylink;
    }

    public void setmarrayLink(Set<String> marraylink) {

        mEditor.putStringSet(VariableBag.MARRAYLINK, marraylink);
        mEditor.commit();
    }

    public String getStudentTeacherList() {
        String list = mSharedPreferences.getString(VariableBag.STUDENTTEACHERLIST, null);
        return list;
    }

    public void setStudentTeacherList(String list) {
        mEditor.putString(VariableBag.STUDENTTEACHERLIST, list).commit();
    }

    public String getTeacherList() {
        String list = mSharedPreferences.getString(VariableBag.TEACHERLIST, null);
        return list;
    }

    public void setTeacherList(String list) {
        mEditor.putString(VariableBag.TEACHERLIST, list).commit();
    }


    public String getCurrentActivity() {
        String list = mSharedPreferences.getString(VariableBag.CURRENTACTIVITY, null);
        return list;
    }

    public void setCurrentActivity(String list) {
        mEditor.putString(VariableBag.CURRENTACTIVITY, list).commit();
    }



    public int getCheck() {
        int check = mSharedPreferences.getInt(VariableBag.CHECK, 1);
        return check;
    }

    public void setCheck(int check) {
        mEditor.putInt(VariableBag.CHECK, check).commit();
    }


}
