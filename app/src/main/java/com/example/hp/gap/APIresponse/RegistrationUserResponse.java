package com.example.hp.gap.APIresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationUserResponse {
    @SerializedName("u_id")
    @Expose
    private String uId;
    @SerializedName("u_name")
    @Expose
    private String uname;
    @SerializedName("u_email")
    @Expose
    private String uEmail;
    @SerializedName("u_password")
    @Expose
    private String uPassword;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getUId() {
        return uId;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }

    public String getUName() {
        return uname;
    }

    public void setUName(String uname) {
        this.uname = uname;
    }


    public String getUEmail() {
        return uEmail;
    }

    public void setUEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getUPassword() {
        return uPassword;
    }

    public void setUPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
