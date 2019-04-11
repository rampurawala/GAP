package com.example.hp.gap.APIresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeacherListResponse {
    @SerializedName("TeacherClassList")
    @Expose
    private List<TeacherResponse> teacherClassList = null;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;

    public List<TeacherResponse> getTeacherClassList() {
        return teacherClassList;
    }

    public void setTeacherClassList(List<TeacherResponse> teacherClassList) {
        this.teacherClassList = teacherClassList;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
