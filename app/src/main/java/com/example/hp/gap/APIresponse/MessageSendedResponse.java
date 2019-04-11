package com.example.hp.gap.APIresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageSendedResponse {
    @SerializedName("message_master_id")
    @Expose
    private String messageMasterId;
    @SerializedName("message_title")
    @Expose
    private String messageTitle;
    @SerializedName("c_id")
    @Expose
    private String cId;
    @SerializedName("sender_id")
    @Expose
    private String senderId;

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success")
    @Expose
    private Integer success;

    public String getMessageMasterId() {
        return messageMasterId;
    }

    public void setMessageMasterId(String messageMasterId) {
        this.messageMasterId = messageMasterId;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer status) {
        this.success = success;
    }
}
