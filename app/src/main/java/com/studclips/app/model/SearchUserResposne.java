package com.studclips.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchUserResposne {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success")
    @Expose
    private List<SearchUserSuccess> success = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SearchUserSuccess> getSuccess() {
        return success;
    }

    public void setSuccess(List<SearchUserSuccess> success) {
        this.success = success;
    }
}
