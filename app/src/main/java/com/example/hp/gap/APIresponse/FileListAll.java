package com.example.hp.gap.APIresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileListAll {

    @SerializedName("message_master_id")
    @Expose
    private String messageMasterId;
    @SerializedName("f_name")
    @Expose
    private String fName;
    @SerializedName("f_due_date")
    @Expose
    private String fDueDate;
    @SerializedName("f_url")
    @Expose
    private String fUrl;
    @SerializedName("f_type_id")
    @Expose
    private String f_type_id;

    public String getMessageMasterId() {
        return messageMasterId;
    }

    public void setMessageMasterId(String messageMasterId) {
        this.messageMasterId = messageMasterId;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
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

    public String getFTypeId() {
        return f_type_id;
    }

    public void setFTypeId(String f_type_id) {
        this.f_type_id = f_type_id;
    }
}
