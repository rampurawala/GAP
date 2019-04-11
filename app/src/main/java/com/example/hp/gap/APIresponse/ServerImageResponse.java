package com.example.hp.gap.APIresponse;

import com.google.gson.annotations.SerializedName;

public class ServerImageResponse {
    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }
}
