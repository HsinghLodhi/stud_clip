package com.studclips.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddedVideoDetailModel {

    @SerializedName("video_id")
    @Expose
    private Integer videoId;
    @SerializedName("video")
    @Expose
    private String video;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("uploaded_datetime")
    @Expose
    private String uploadedDatetime;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("ratings")
    @Expose
    private Integer ratings;
    @SerializedName("views")
    @Expose
    private Integer views;

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUploadedDatetime() {
        return uploadedDatetime;
    }

    public void setUploadedDatetime(String uploadedDatetime) {
        this.uploadedDatetime = uploadedDatetime;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getRatings() {
        return ratings;
    }

    public void setRatings(Integer ratings) {
        this.ratings = ratings;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

}
