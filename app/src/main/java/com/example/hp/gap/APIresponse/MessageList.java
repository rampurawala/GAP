package com.example.hp.gap.APIresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageList {
    @SerializedName("message_master_id")
    @Expose
    private String messageMasterId;
    @SerializedName("message_title")
    @Expose
    private String messageTitle;
    @SerializedName("message_due_date")
    @Expose
    private String message_due_date;


    @SerializedName("c_id")
    @Expose
    private String cId;
    @SerializedName("sender_id")
    @Expose
    private String senderId;

    @SerializedName("u_name")
    @Expose
    private String uName;

    @SerializedName("u_profile_pic")
    @Expose
    private String u_profile_pic;

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





    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

 public String getMdate() {
        return message_due_date;
    }

    public void setMdate(String message_due_date) {
        this.message_due_date = message_due_date;
    }


    public String getUProfilePic() {
        return u_profile_pic;
    }

    public void setUProfilePic(String u_profile_pic) {
        this.u_profile_pic = u_profile_pic;
    }


}
