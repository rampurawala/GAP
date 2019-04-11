package com.example.hp.gap.APIresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClassTeacherListResponse {

    @SerializedName("ClassTeacher")
    @Expose
    private List<ClassTeacherResponse> classTeacher = null;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;

    public List<ClassTeacherResponse> getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(List<ClassTeacherResponse> classTeacher) {
        this.classTeacher = classTeacher;
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
