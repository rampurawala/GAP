package com.example.hp.gap.APIresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JoinClassResponse {
    @SerializedName("u_id")
    @Expose
    private String uId;
    @SerializedName("teacher_id")
    @Expose
    private String teacherId;
    @SerializedName("j_date")
    @Expose
    private String jDate;
    @SerializedName("c_id")
    @Expose
    private String cId;
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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getJDate() {
        return jDate;
    }

    public void setJDate(String jDate) {
        this.jDate = jDate;
    }

    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
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


