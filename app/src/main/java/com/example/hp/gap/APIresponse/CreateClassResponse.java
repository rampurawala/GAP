package com.example.hp.gap.APIresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateClassResponse {
    @SerializedName("c_id")
    @Expose
    private String cId;
    @SerializedName("c_name")
    @Expose
    private String cName;
    @SerializedName("c_subject")
    @Expose
    private Object cSubject;
    @SerializedName("c_code")
    @Expose
    private String cCode;
    @SerializedName("u_id")
    @Expose
    private String uId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

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

    public Object getCSubject() {
        return cSubject;
    }

    public void setCSubject(Object cSubject) {
        this.cSubject = cSubject;
    }

    public String getCCode() {
        return cCode;
    }

    public void setCCode(String cCode) {
        this.cCode = cCode;
    }

    public String getUId() {
        return uId;
    }

    public void setUId(String uId) {
        this.uId = uId;
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
