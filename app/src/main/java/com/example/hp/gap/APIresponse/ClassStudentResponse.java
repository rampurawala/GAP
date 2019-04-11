package com.example.hp.gap.APIresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassStudentResponse {
    @SerializedName("c_id")
    @Expose
    private String cId;
    @SerializedName("u_name")
    @Expose
    private String uName;
    @SerializedName("u_id")
    @Expose
    private String uId;

    @SerializedName("u_profile_pic")
    @Expose
    private String u_profile_pic;

    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

    public String getUId() {
        return uId;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }


    public String getUProfilePic() {
        return u_profile_pic;
    }

    public void setUProfilePic(String u_profile_pic) {
        this.u_profile_pic = u_profile_pic;
    }


}
