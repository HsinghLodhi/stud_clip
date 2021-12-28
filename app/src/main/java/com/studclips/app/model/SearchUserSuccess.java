package com.studclips.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchUserSuccess {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("profile_photo")
    @Expose
    private String profilePhoto;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("game_id")
    @Expose
    private Object gameId;
    @SerializedName("role")
    @Expose
    private Object role;
    @SerializedName("school_type")
    @Expose
    private Object schoolType;
    @SerializedName("school_team")
    @Expose
    private Object schoolTeam;
    @SerializedName("class")
    @Expose
    private Object _class;
    @SerializedName("height")
    @Expose
    private Object height;
    @SerializedName("weight")
    @Expose
    private Object weight;
    @SerializedName("position")
    @Expose
    private Object position;
    @SerializedName("highlighted_video")
    @Expose
    private Object highlightedVideo;
    @SerializedName("has_subscription")
    @Expose
    private Integer hasSubscription;
    @SerializedName("is_likes")
    @Expose
    private Integer isLikes;
    @SerializedName("is_ratings")
    @Expose
    private Integer isRatings;
    @SerializedName("is_newvideo")
    @Expose
    private Integer isNewvideo;
    @SerializedName("is_message")
    @Expose
    private Integer isMessage;
    @SerializedName("videos")
    @Expose
    private Integer videos;
    @SerializedName("reviews")
    @Expose
    private Integer reviews;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("views")
    @Expose
    private Integer views;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Object getGameId() {
        return gameId;
    }

    public void setGameId(Object gameId) {
        this.gameId = gameId;
    }

    public Object getRole() {
        return role;
    }

    public void setRole(Object role) {
        this.role = role;
    }

    public Object getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(Object schoolType) {
        this.schoolType = schoolType;
    }

    public Object getSchoolTeam() {
        return schoolTeam;
    }

    public void setSchoolTeam(Object schoolTeam) {
        this.schoolTeam = schoolTeam;
    }

    public Object getClass_() {
        return _class;
    }

    public void setClass_(Object _class) {
        this._class = _class;
    }

    public Object getHeight() {
        return height;
    }

    public void setHeight(Object height) {
        this.height = height;
    }

    public Object getWeight() {
        return weight;
    }

    public void setWeight(Object weight) {
        this.weight = weight;
    }

    public Object getPosition() {
        return position;
    }

    public void setPosition(Object position) {
        this.position = position;
    }

    public Object getHighlightedVideo() {
        return highlightedVideo;
    }

    public void setHighlightedVideo(Object highlightedVideo) {
        this.highlightedVideo = highlightedVideo;
    }

    public Integer getHasSubscription() {
        return hasSubscription;
    }

    public void setHasSubscription(Integer hasSubscription) {
        this.hasSubscription = hasSubscription;
    }

    public Integer getIsLikes() {
        return isLikes;
    }

    public void setIsLikes(Integer isLikes) {
        this.isLikes = isLikes;
    }

    public Integer getIsRatings() {
        return isRatings;
    }

    public void setIsRatings(Integer isRatings) {
        this.isRatings = isRatings;
    }

    public Integer getIsNewvideo() {
        return isNewvideo;
    }

    public void setIsNewvideo(Integer isNewvideo) {
        this.isNewvideo = isNewvideo;
    }

    public Integer getIsMessage() {
        return isMessage;
    }

    public void setIsMessage(Integer isMessage) {
        this.isMessage = isMessage;
    }

    public Integer getVideos() {
        return videos;
    }

    public void setVideos(Integer videos) {
        this.videos = videos;
    }

    public Integer getReviews() {
        return reviews;
    }

    public void setReviews(Integer reviews) {
        this.reviews = reviews;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
