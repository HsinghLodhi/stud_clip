package com.studclips.app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetVideosResponse implements Serializable {
    @SerializedName("message")
    String message;
    @SerializedName("success")
    VideoSuccess success;

    public GetVideosResponse(String message, VideoSuccess success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public VideoSuccess getSuccess() {
        return success;
    }

    public void setSuccess(VideoSuccess success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "GetVideosResponse{" +
                "message='" + message + '\'' +
                ", success=" + success +
                '}';
    }
}
