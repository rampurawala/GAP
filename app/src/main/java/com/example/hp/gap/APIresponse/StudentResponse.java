package com.example.hp.gap.APIresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentResponse {
    @SerializedName("j_clsid")
    @Expose
    private Object jClsid;
    @SerializedName("u_id")
    @Expose
    private Object uId;
    @SerializedName("c_id")
    @Expose
    private String cId;
    @SerializedName("u_name")
    @Expose
    private String uName;
    @SerializedName("c_subject")
    @Expose
    private String c_subject;
    @SerializedName("u_email")
    @Expose
    private String uEmail;
    @SerializedName("c_name")
    @Expose
    private String cName;

    public Object getJClsid() {
        return jClsid;
    }

    public void setJClsid(Object jClsid) {
        this.jClsid = jClsid;
    }

    public Object getUId() {
        return uId;
    }

    public void setUId(Object uId) {
        this.uId = uId;
    }

    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

    public String getUEmail() {
        return uEmail;
    }

    public void setUEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public String getCSubject() {
        return c_subject;
    }

    public void setCSubject(String c_subject) {
        this.c_subject = c_subject;
    }
}
