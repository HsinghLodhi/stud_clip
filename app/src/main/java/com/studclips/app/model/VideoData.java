package com.studclips.app.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VideoData implements Serializable {

    @SerializedName("user_id")
    private Integer userId;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("video_id")
    private Integer videoId;
    @SerializedName("video")
    private String video;
    @SerializedName("caption")
    private String caption;
    @SerializedName("uploaded_datetime")
    private String uploadedDatetime;
    @SerializedName("profile_photo")
    private String profile_photo;
    @SerializedName("likes")
    private Integer likes;
    @SerializedName("ratings")
    private Integer ratings;
    @SerializedName("views")
    private Integer views;
    @SerializedName("is_liked")
    private Integer isLiked;

    private Bitmap videoImageBitmap;

    public Bitmap getVideoImageBitmap() {
        return videoImageBitmap;
    }

    public void setVideoImageBitmap(Bitmap videoImageBitmap) {
        this.videoImageBitmap = videoImageBitmap;
    }

    public VideoData(Integer userId, String firstName, String lastName, Integer videoId, String video, String caption, String uploadedDatetime,String profilePhoto, Integer likes, Integer ratings, Integer views, Integer isLiked) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.videoId = videoId;
        this.video = video;
        this.caption = caption;
        this.uploadedDatetime = uploadedDatetime;
        this.profile_photo = profilePhoto;
        this.likes = likes;
        this.ratings = ratings;
        this.views = views;
        this.isLiked = isLiked;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public Integer getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Integer isLiked) {
        this.isLiked = isLiked;
    }

    @Override
    public String toString() {
        return "VideoData{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", videoId=" + videoId +
                ", video='" + video + '\'' +
                ", caption='" + caption + '\'' +
                ", uploadedDatetime='" + uploadedDatetime + '\'' +
                ", profile_photo='" + profile_photo + '\'' +
                ", likes=" + likes +
                ", ratings=" + ratings +
                ", views=" + views +
                ", isLiked=" + isLiked +
                ", videoImageBitmap=" + videoImageBitmap +
                '}';
    }
}
