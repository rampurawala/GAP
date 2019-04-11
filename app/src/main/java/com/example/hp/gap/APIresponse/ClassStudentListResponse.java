package com.example.hp.gap.APIresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClassStudentListResponse {

    @SerializedName("StudentList")
    @Expose
    private List<ClassStudentResponse> studentList = null;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;

    public List<ClassStudentResponse> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<ClassStudentResponse> studentList) {
        this.studentList = studentList;
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
