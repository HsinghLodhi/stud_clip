package com.studclips.app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VideoSuccess implements Serializable {

    @SerializedName("data")
    private List<VideoData> data = null;


    public void setData(List<VideoData> data) {
        this.data = data;
    }

    public List<VideoData> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "VideoSuccess{" +
                "data=" + data +
                '}';
    }
}
