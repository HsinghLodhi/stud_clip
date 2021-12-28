package com.studclips.app.api;

import com.studclips.app.model.AddVideoSuccess;
import com.studclips.app.model.ForgotPassResposne;

import com.studclips.app.model.GetVideosResponse;
import com.studclips.app.model.LikeUnlikeResposne;
import com.studclips.app.model.SearchUserResposne;
import com.studclips.app.model.SignUpResponse;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface APIInterface {

    //Sign up APi
    @Multipart
    @POST("register")
    Call<SignUpResponse> signUpAPi(
            @Part MultipartBody.Part profile_photo,
            @Part("first_name") RequestBody name,
            @Part("last_name") RequestBody last_name,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("role") RequestBody role,
            @Part("city") RequestBody city,
            @Part("state") RequestBody state,
            @Part("game_id") RequestBody game_id,
            @Part("device_type") RequestBody device_type,
            @Part("device_token") RequestBody device_token
    );


    @POST("forget-password")
    Call<ForgotPassResposne> ForgotPasswordAPi(@Body HashMap<String, String> request);

    @Multipart
    @POST("register")
    Call<SignUpResponse> signUpAPi(
            @Part MultipartBody.Part profile_photo,
            @Part("first_name") RequestBody name,
            @Part("last_name") RequestBody last_name,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("role") RequestBody role,
            @Part("device_type") RequestBody device_type,
            @Part("device_token") RequestBody device_token
    );

    @GET("logout")
    Call<ResponseBody> logoutApi(@Header("Authorization") String token);

    @POST("login")
    Call<SignUpResponse> loginApi(@Body HashMap<String, String> token);

    @POST("change-password")
    Call<ResponseBody> changePasswordApi(@Header("Authorization") String token, @Body HashMap<String, String> map);

    @POST("update-profile")
    Call<SignUpResponse> updateProfileApi(@Header("Authorization") String token, @Body HashMap<String, String> map);

    @Multipart
    @POST("update-profile")
    Call<SignUpResponse> updateProfilePlayerApi(@Header("Authorization") String token, @PartMap HashMap<String, RequestBody> map, @Part MultipartBody.Part profile_photo);

    @Multipart
    @POST("update-profile")
    Call<SignUpResponse> FanUpdateProfile(
            @Header("Authorization") String token,
            @Part MultipartBody.Part profile_photo
    );


    @POST("setting")
    Call<SignUpResponse> updateSettings(@Header("Authorization") String token, @Body HashMap<String, Integer> map);

    @POST("subscription")
    Call<ResponseBody> subscription(@Header("Authorization") String token, @Body HashMap<String, String> map);



    @Multipart
    @POST("add-video")
    Call<AddVideoSuccess> AddVideo(
            @Header("Authorization") String token,
            @Part MultipartBody.Part video_path,
            @Part("caption") RequestBody caption
    );

    @POST("videos")
    Call<GetVideosResponse> getVideos(@Header("Authorization") String token,@Body HashMap<String, Object> map );

    @POST("like-unlike")
    Call<LikeUnlikeResposne> LikeUnlikeVideo(@Header("Authorization") String token, @Body HashMap<String, Integer> map );

    @POST("rating")
    Call<LikeUnlikeResposne> RatingVideo(@Header("Authorization") String token, @Body HashMap<String, Integer> map );

    @POST("view")
    Call<LikeUnlikeResposne> ViewCall(@Header("Authorization") String token, @Body HashMap<String, Integer> map );

    @POST("search-user")
    Call<SearchUserResposne> SearchUserCall(@Header("Authorization") String token, @Body HashMap<String, String> map );

}
