package com.example.hp.gap.APIresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ToDoListListResponse {

    @SerializedName("itemList")
    @Expose
    private List<ToDoListResponse> itemList = null;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;

    public List<ToDoListResponse> getItemList() {
        return itemList;
    }

    public void setItemList(List<ToDoListResponse> itemList) {
        this.itemList = itemList;
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
