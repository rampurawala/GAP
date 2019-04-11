package com.example.hp.gap.APIresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FileListAllListResponse {

    @SerializedName("FileListAll")
    @Expose
    private List<FileListAll> fileListAll = null;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;

    public List<FileListAll> getFileListAll() {
        return fileListAll;
    }

    public void setFileListAll(List<FileListAll> fileListAll) {
        this.fileListAll = fileListAll;
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
