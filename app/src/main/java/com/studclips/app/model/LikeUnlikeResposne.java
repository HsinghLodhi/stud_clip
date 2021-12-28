package com.studclips.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikeUnlikeResposne {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success")
    @Expose
    private LikeUnlikeSucess success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LikeUnlikeSucess getSuccess() {
        return success;
    }

    public void setSuccess(LikeUnlikeSucess success) {
        this.success = success;
    }
}

