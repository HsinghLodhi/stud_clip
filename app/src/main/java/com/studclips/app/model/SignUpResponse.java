package com.studclips.app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SignUpResponse implements Serializable {
    @SerializedName("message")
    private String message;
    @SerializedName("success")
    private SignUpData success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SignUpData getSuccess() {
        return success;
    }

    public void setSuccess(SignUpData success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "SignUpResponse{" +
                "message='" + message + '\'' +
                ", success=" + success +
                '}';
    }
}
