package com.studclips.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPassResposne {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success")
    @Expose
    private ForgotSuccess success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ForgotSuccess getSuccess() {
        return success;
    }

    public void setSuccess(ForgotSuccess success) {
        this.success = success;
    }

}
