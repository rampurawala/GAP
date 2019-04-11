package com.example.hp.gap.APIresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentListResponse {
    @SerializedName("StudentClassList")
    @Expose
    private List<StudentResponse> studentClassList = null;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;

    public List<StudentResponse> getStudentClassList() {
        return studentClassList;
    }

    public void setStudentClassList(List<StudentResponse> studentClassList) {
        this.studentClassList = studentClassList;
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
