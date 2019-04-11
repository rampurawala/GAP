package com.example.hp.gap.APIresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileList {

    @SerializedName("f_id")
    @Expose
    private String fId;
    @SerializedName("f_name")
    @Expose
    private String fName;
    @SerializedName("f_exp_date")
    @Expose
    private String fExpDate;
    @SerializedName("f_type_id")
    @Expose
    private String fTypeId;
    @SerializedName("f_due_date")
    @Expose
    private String fDueDate;
    @SerializedName("f_url")
    @Expose
    private String fUrl;
    @SerializedName("message_master_id")
    @Expose
    private String messageMasterId;

    public String getFId() {
        return fId;
    }

    public void setFId(String fId) {
        this.fId = fId;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getFExpDate() {
        return fExpDate;
    }

    public void setFExpDate(String fExpDate) {
        this.fExpDate = fExpDate;
    }

    public String getFTypeId() {
        return fTypeId;
    }

    public void setFTypeId(String fTypeId) {
        this.fTypeId = fTypeId;
    }

    public String getFDueDate() {
        return fDueDate;
    }

    public void setFDueDate(String fDueDate) {
        this.fDueDate = fDueDate;
    }

    public String getFUrl() {
        return fUrl;
    }

    public void setFUrl(String fUrl) {
        this.fUrl = fUrl;
    }

    public String getMessageMasterId() {
        return messageMasterId;
    }

    public void setMessageMasterId(String messageMasterId) {
        this.messageMasterId = messageMasterId;
    }
}
