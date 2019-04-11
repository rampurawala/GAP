package com.example.hp.gap.APIresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherResponse {


    @SerializedName("c_id")
    @Expose
    private String cId;
    @SerializedName("c_name")
    @Expose
    private String cName;
    @SerializedName("c_code")
    @Expose
    private String cCode;
    @SerializedName("c_subject")
    @Expose
    private String c_subject;
    @SerializedName("u_name")
    @Expose
    private String uName;
    @SerializedName("u_email")
    @Expose
    private String uEmail;
    @SerializedName("count")
    @Expose
    private String count;
    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public String getCCode() {
        return cCode;
    }

    public void setCCode(String cCode) {
        this.cCode = cCode;
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

  public String getCSubject() {
        return c_subject;
    }

    public void setCSubject(String c_subject) {
        this.c_subject = c_subject;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}

