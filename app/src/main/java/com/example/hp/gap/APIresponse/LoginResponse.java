package com.example.hp.gap.APIresponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("u_id")
    @Expose
    private String uId;
    @SerializedName("u_name")
    @Expose
    private String uname;
    @SerializedName("u_email")
    @Expose
    private String uEmail;
    @SerializedName("u_password")
    @Expose
    private String uPassword;

    @SerializedName("u_profile_pic")
    @Expose
    private String u_profile_pic;


    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;





    public String getUId() {
        return uId;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }

    public String getUName() {
        return uname;
    }

    public void setUName(String uname) {
        this.uname = uname;
    }

    public String getUEmail() {
        return uEmail;
    }

    public void setUEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getUPassword() {
        return uPassword;
    }

    public void setUPassword(String uPassword) {
        this.uPassword = uPassword;
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

    public String getProfilePic() {
        return u_profile_pic;
    }

    public void setProfilePic(String u_profile_pic) {
        this.u_profile_pic = u_profile_pic;
    }
}
