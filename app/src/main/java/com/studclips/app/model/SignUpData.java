package com.studclips.app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SignUpData implements Serializable {
    @SerializedName("user_id")
    private Integer userId;
    @SerializedName("token")
    private String token;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("email")
    private String email;
    @SerializedName("profile_photo")
    private String profilePhoto;
    @SerializedName("city")
    private String city;
    @SerializedName("status")
    private Integer status;
    @SerializedName("state")
    private String state;
    @SerializedName("game_id")
    private String game_id;
    @SerializedName("role")
    private String role;
    @SerializedName("school_type")
    private String schoolType;
    @SerializedName("school_team")
    private String schoolTeam;
    @SerializedName("class")
    private String  _class;
    @SerializedName("height")
    private String height;
    @SerializedName("weight")
    private String weight;
    @SerializedName("position")
    private String position;
    @SerializedName("highlighted_video")
    private String highlightedVideo;
    @SerializedName("has_subscription")
    private String hasSubscription;
    @SerializedName("is_likes")
    private Integer isLikes;
    @SerializedName("is_ratings")
    private Integer isRatings;
    @SerializedName("is_newvideo")
    private Integer isNewvideo;
    @SerializedName("is_message")
    private Integer isMessage;
    @SerializedName("device_type")
    private String deviceType;
    @SerializedName("device_token")
    private String deviceToken;
    @SerializedName("videos")
    private int videosCount;
    @SerializedName("reviews")
    private int reviewsCount;
    @SerializedName("likes")
    private int likesCount;
    @SerializedName("views")
    private int viewsCount;

    public SignUpData(Integer userId, String token, String firstName, String lastName, String email, String profilePhoto, String city, Integer status, String state, String game_id, String role, String schoolType, String schoolTeam, String _class, String height, String weight, String position, String highlightedVideo, String hasSubscription, Integer isLikes, Integer isRatings, Integer isNewvideo, Integer isMessage, String deviceType, String deviceToken, int videosCount, int reviewsCount, int likesCount, int viewsCount) {
        this.userId = userId;
        this.token = token;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.profilePhoto = profilePhoto;
        this.city = city;
        this.status = status;
        this.state = state;
        this.game_id = game_id;
        this.role = role;
        this.schoolType = schoolType;
        this.schoolTeam = schoolTeam;
        this._class = _class;
        this.height = height;
        this.weight = weight;
        this.position = position;
        this.highlightedVideo = highlightedVideo;
        this.hasSubscription = hasSubscription;
        this.isLikes = isLikes;
        this.isRatings = isRatings;
        this.isNewvideo = isNewvideo;
        this.isMessage = isMessage;
        this.deviceType = deviceType;
        this.deviceToken = deviceToken;
        this.videosCount = videosCount;
        this.reviewsCount = reviewsCount;
        this.likesCount = likesCount;
        this.viewsCount = viewsCount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getGameId() {
        return game_id;
    }

    public void setGameId(String game_id) {
        this.game_id = game_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public String getSchoolTeam() {
        return schoolTeam;
    }

    public void setSchoolTeam(String schoolTeam) {
        this.schoolTeam = schoolTeam;
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getHighlightedVideo() {
        return highlightedVideo;
    }

    public void setHighlightedVideo(String highlightedVideo) {
        this.highlightedVideo = highlightedVideo;
    }

    public String getHasSubscription() {
        return hasSubscription;
    }

    public void setHasSubscription(String hasSubscription) {
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

    public int getVideosCount() {
        return videosCount;
    }

    public void setVideosCount(int videosCount) {
        this.videosCount = videosCount;
    }

    public int getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(int reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    @Override
    public String toString() {
        return "SignUpData{" +
                "userId=" + userId +
                ", token='" + token + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                ", city='" + city + '\'' +
                ", status=" + status +
                ", state='" + state + '\'' +
                ", sportId='" + game_id + '\'' +
                ", role='" + role + '\'' +
                ", schoolType='" + schoolType + '\'' +
                ", schoolTeam='" + schoolTeam + '\'' +
                ", _class='" + _class + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", position='" + position + '\'' +
                ", highlightedVideo='" + highlightedVideo + '\'' +
                ", hasSubscription='" + hasSubscription + '\'' +
                ", isLikes='" + isLikes + '\'' +
                ", isRatings='" + isRatings + '\'' +
                ", isNewvideo='" + isNewvideo + '\'' +
                ", isMessage='" + isMessage + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", deviceToken='" + deviceToken + '\'' +
                ", videosCount=" + videosCount +
                ", reviewsCount=" + reviewsCount +
                ", likesCount=" + likesCount +
                ", viewsCount=" + viewsCount +
                '}';
    }
}
