package com.studclips.app;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.JsonSyntaxException;
import com.studclips.app.api.APIClient;
import com.studclips.app.api.APIInterface;
import com.studclips.app.api.ApiCallback;
import com.studclips.app.model.GetVideosResponse;
import com.studclips.app.model.LikeUnlikeResposne;
import com.studclips.app.model.SearchUserResposne;
import com.studclips.app.model.SignUpResponse;
import com.studclips.app.ui.common.SettingActivity;
import com.studclips.app.model.AddVideoSuccess;
import com.studclips.app.util.BaseLoader;
import com.studclips.app.util.Global;
import com.studclips.app.util.SharedPreference;
import com.studclips.app.model.ForgotPassResposne;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCall {
    private static final String bearerToken = "Bearer ";
    private static RequestBody DEVICE_TYPE = RequestBody.create(MediaType.parse("text/plain"), Global.DEVICE_TYPE);
    static BaseLoader baseLoader;

    public static void signUpPlayer(Context context, RequestBody role, RequestBody device_token, MultipartBody.Part upload_photo, RequestBody firstName, RequestBody lastName, RequestBody email, RequestBody password, RequestBody state, RequestBody city, RequestBody gameId, final ApiCallback.PlayerSignUpCallBack callBack) {
        baseLoader = new BaseLoader(context);
        baseLoader.showLoader();
        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<SignUpResponse> responseCall = anInterface.signUpAPi(upload_photo, firstName, lastName, email, password, role, city, state, gameId, DEVICE_TYPE, device_token);
        responseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                baseLoader.hideLoader();
                if (response.isSuccessful()) {
                    callBack.onSuccessPlayerSignUp(response.body());
                } else {
                    JSONObject object = null;
                    try {
                        if (response.errorBody() != null) {
                            object = new JSONObject(response.errorBody().string());
                            callBack.onError(object.getString("error"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                baseLoader.hideLoader();
                if (t instanceof IOException) {
                    callBack.onError(context.getResources().getString(R.string.no_internet));
                } else if (t instanceof JsonSyntaxException) {
                    callBack.onError(context.getResources().getString(R.string.internal_error));
                } else {
                    callBack.onError(t.getMessage());
                }
            }
        });
    }
    public static void signUpFan(Context context, RequestBody role, RequestBody device_token, MultipartBody.Part upload_photo, RequestBody firstName, RequestBody lastName, RequestBody email, RequestBody password, final ApiCallback.FanSignUpCallBack callBack) {
        baseLoader = new BaseLoader(context);
        baseLoader.showLoader();
        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<SignUpResponse> responseCall = anInterface.signUpAPi(upload_photo, firstName, lastName, email, password, role, DEVICE_TYPE, device_token);
        responseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                baseLoader.hideLoader();
                if (response.isSuccessful()) {
                    callBack.onSuccessFanSignUp(response.body());
                } else {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(response.errorBody().toString());
                        callBack.onError(object.getString("error"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                baseLoader.hideLoader();
                if (t instanceof IOException) {
                    callBack.onError(context.getResources().getString(R.string.no_internet));
                } else if (t instanceof JsonSyntaxException) {
                    callBack.onError(context.getResources().getString(R.string.internal_error));
                } else {
                    callBack.onError(t.getMessage());
                }
            }
        });

    }

    public static void SignIn(Context context, ApiCallback.SignInCallBack callBack, HashMap<String, String> map) {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<SignUpResponse> logoutApiCall = apiInterface.loginApi(map);
        logoutApiCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccessSignIn(response.body());
                } else {
                    try {
                        JSONObject jsonObject;
                        jsonObject = new JSONObject(response.errorBody().string());
                        callBack.onError(jsonObject.getString("error"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    callBack.onError(context.getResources().getString(R.string.no_internet));
                } else if (t instanceof JsonSyntaxException) {
                    callBack.onError(context.getResources().getString(R.string.internal_error));
                } else {
                    callBack.onError(t.getMessage());
                }
            }
        });

    }

    public static void Logout(Context context, ApiCallback.LogoutCallBack callBack) {
        baseLoader = new BaseLoader(context);
        baseLoader.showLoader();
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ResponseBody> logoutApiCall = apiInterface.logoutApi(bearerToken + SharedPreference.getString(Global.token, context));
        logoutApiCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                baseLoader.hideLoader();
                JSONObject jsonObject;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        callBack.onSuccessLogout(jsonObject.getString("message"));
                    } catch (Exception e) {
                        callBack.onError(e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    try {
                        jsonObject = new JSONObject(response.errorBody().string());
                        callBack.onError(jsonObject.getString("error"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                baseLoader.hideLoader();
                if (t instanceof IOException) {
                    callBack.onError(context.getResources().getString(R.string.no_internet));
                } else if (t instanceof JsonSyntaxException) {
                    callBack.onError(context.getResources().getString(R.string.internal_error));
                } else {
                    callBack.onError(t.getMessage());
                }
            }
        });

    }

    public static void ChangePassword(Context context, HashMap<String, String> map, ApiCallback.LogoutCallBack callBack) {
        baseLoader = new BaseLoader(context);
        baseLoader.showLoader();
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ResponseBody> logoutApiCall = apiInterface.changePasswordApi(bearerToken + SharedPreference.getString(Global.token, context), map);
        logoutApiCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                baseLoader.hideLoader();
                JSONObject jsonObject;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        callBack.onSuccessLogout(jsonObject.getString("message"));
                    } catch (Exception e) {
                        callBack.onError(e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    try {
                        jsonObject = new JSONObject(response.errorBody().string());
                        callBack.onError(jsonObject.getString("error"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                baseLoader.hideLoader();
                if (t instanceof IOException) {
                    callBack.onError(context.getResources().getString(R.string.no_internet));
                } else if (t instanceof JsonSyntaxException) {
                    callBack.onError(context.getResources().getString(R.string.internal_error));
                } else {
                    callBack.onError(t.getMessage());
                }
            }
        });

    }

    public static void ForgotPassword(Context context, HashMap<String, String> forgotRequest, final ApiCallback.ForgotPasswordCallBack callBack) {
        baseLoader = new BaseLoader(context);
        baseLoader.showLoader();
        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<ForgotPassResposne> responseCall = anInterface.ForgotPasswordAPi(forgotRequest);
        responseCall.enqueue(new Callback<ForgotPassResposne>() {
            @Override
            public void onResponse(Call<ForgotPassResposne> call, Response<ForgotPassResposne> response) {
                baseLoader.hideLoader();
                if (response.isSuccessful()) {
                    callBack.onSuccessForgotPass(response.body());
                } else {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(response.errorBody().string());
                        callBack.onError(object.getString("error"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgotPassResposne> call, Throwable t) {
                baseLoader.hideLoader();
                if (t instanceof IOException) {
                    callBack.onError(context.getResources().getString(R.string.no_internet));
                } else if (t instanceof JsonSyntaxException) {
                    callBack.onError(context.getResources().getString(R.string.internal_error));
                } else {
                    callBack.onError(t.getMessage());
                }
            }
        });

    }

    public static void updateProfilePlayer(Context context, HashMap<String, String> map, ApiCallback.UpdateProfilePlayer callBack) {
        baseLoader = new BaseLoader(context);
        baseLoader.showLoader();
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<SignUpResponse> responseBodyCall = apiInterface.updateProfileApi(bearerToken + SharedPreference.getString(Global.token, context), map);
        responseBodyCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignUpResponse> call, @NonNull Response<SignUpResponse> response) {
                baseLoader.hideLoader();
                if (response.isSuccessful()) {
                    callBack.onSuccessProfile(response.body());
                } else {
                    JSONObject object;
                    try {
                        object = new JSONObject(response.errorBody().string());
                        callBack.onError(object.getString("error"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignUpResponse> call, @NonNull Throwable t) {
                baseLoader.hideLoader();
                if (t instanceof IOException) {
                    callBack.onError(context.getResources().getString(R.string.no_internet));
                } else if (t instanceof JsonSyntaxException) {
                    callBack.onError(context.getResources().getString(R.string.internal_error));
                } else {
                    callBack.onError(t.getMessage());
                }
            }
        });
    }

    public static void FanUpdateProfile(Context context, String toekn, MultipartBody.Part upload_photo, final ApiCallback.FanSignUpCallBack callBack) {
        baseLoader = new BaseLoader(context);
        baseLoader.showLoader();
        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<SignUpResponse> responseCall = anInterface.FanUpdateProfile(bearerToken + toekn, upload_photo);
        responseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                baseLoader.hideLoader();
                if (response.isSuccessful()) {
                    callBack.onSuccessFanSignUp(response.body());
                } else {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(response.errorBody().toString());
                        callBack.onError(object.getString("error"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                baseLoader.hideLoader();
                if (t instanceof IOException) {
                    callBack.onError(context.getResources().getString(R.string.no_internet));
                } else if (t instanceof JsonSyntaxException) {
                    callBack.onError(context.getResources().getString(R.string.internal_error));
                } else {
                    callBack.onError(t.getMessage());
                }
            }
        });

    }

    public static void updatePlayerPhoto(Context context, MultipartBody.Part part, HashMap<String, RequestBody> map, ApiCallback.UpdateProfilePlayer callBack) {
        baseLoader = new BaseLoader(context);
        baseLoader.showLoader();
        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<SignUpResponse> responseCall = anInterface.updateProfilePlayerApi(bearerToken + SharedPreference.getString(Global.token, context), map, part);
        responseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                baseLoader.hideLoader();
                if (response.isSuccessful()) {
                    callBack.onSuccessProfile(response.body());
                } else {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(response.errorBody().string());
                        callBack.onError(object.getString("error"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                baseLoader.hideLoader();
                if (t instanceof IOException) {
                    callBack.onError(context.getResources().getString(R.string.no_internet));
                } else if (t instanceof JsonSyntaxException) {
                    callBack.onError(context.getResources().getString(R.string.internal_error));
                } else {
                    callBack.onError(t.getMessage());
                }
            }
        });


    }

    public static void setting(Context context, HashMap<String, Integer> map, ApiCallback.UpdateSettings callBack) {
        baseLoader = new BaseLoader(context);
        baseLoader.showLoader();
        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<SignUpResponse> responseCall = anInterface.updateSettings(bearerToken + SharedPreference.getString(Global.token, context), map);
        responseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                baseLoader.hideLoader();
                if (response.isSuccessful()) {
                    callBack.onSuccessUpdateSetting(response.body());
                } else {
                    JSONObject object;
                    try {
                        object = new JSONObject(response.errorBody().string());
                        callBack.onError(object.getString("error"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                baseLoader.hideLoader();
                if (t instanceof IOException) {
                    callBack.onError(context.getResources().getString(R.string.no_internet));
                } else if (t instanceof JsonSyntaxException) {
                    callBack.onError(context.getResources().getString(R.string.internal_error));
                } else {
                    callBack.onError(t.getMessage());
                }
            }
        });

    }


    public static void subscription(Context context, HashMap<String, String> map, ApiCallback.Subscription callBack) {
        baseLoader = new BaseLoader(context);
        baseLoader.showLoader();
        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<ResponseBody> responseCall = anInterface.subscription(bearerToken + SharedPreference.getString(Global.token, context), map);
        responseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                baseLoader.hideLoader();
                if (response.isSuccessful()) {
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        callBack.onSuccessSubscription(jsonObject.getString("message"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    JSONObject object;
                    try {
                        object = new JSONObject(response.errorBody().string());
                        callBack.onError(object.getString("error"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                baseLoader.hideLoader();
                if (t instanceof IOException) {
                    callBack.onError(context.getResources().getString(R.string.no_internet));
                } else if (t instanceof JsonSyntaxException) {
                    callBack.onError(context.getResources().getString(R.string.internal_error));
                } else {
                    callBack.onError(t.getMessage());
                }
            }
        });

    }


    public static void AddVideoApi(Context context, String toekn, MultipartBody.Part upload_video, RequestBody caption, final ApiCallback.AddVideo callBack) {
        APIInterface anInterface = APIClient.getClient1().create(APIInterface.class);
        Call<AddVideoSuccess> responseCall = anInterface.AddVideo(bearerToken + toekn, upload_video, caption);
        responseCall.enqueue(new Callback<AddVideoSuccess>() {
            @Override
            public void onResponse(Call<AddVideoSuccess> call, Response<AddVideoSuccess> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccessAddVideo(response.body());
                } else {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(response.errorBody().toString());
                        callBack.onError(object.getString("error"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddVideoSuccess> call, Throwable t) {
                if (t instanceof IOException) {
                    callBack.onError(context.getResources().getString(R.string.no_internet));
                } else if (t instanceof JsonSyntaxException) {
                    callBack.onError(context.getResources().getString(R.string.internal_error));
                } else {
                    callBack.onError(t.getMessage());
                }
            }
        });

    }


    public static void getHomeVideos(Context context, HashMap<String, Object> map, ApiCallback.GetVideos callBack) {
        APIInterface anInterface = APIClient.getClient1().create(APIInterface.class);
        Call<GetVideosResponse> responseCall = anInterface.getVideos(bearerToken + SharedPreference.getString(Global.token, context), map);
        responseCall.enqueue(new Callback<GetVideosResponse>() {
            @Override
            public void onResponse(Call<GetVideosResponse> call, Response<GetVideosResponse> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccessGetVideo(response.body());
                } else {
                    JSONObject object = null;
                    try {
                        String error = response.errorBody().string();
                        Log.e("onResponse: ", "" + error);
                        object = new JSONObject(error);
                        callBack.onError(object.getString("error"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetVideosResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    callBack.onError(context.getResources().getString(R.string.no_internet));
                } else if (t instanceof JsonSyntaxException) {
                    callBack.onError(context.getResources().getString(R.string.internal_error));
                } else {
                    callBack.onError(t.getMessage());
                }
            }
        });

    }

    public static void LikeUnlikeVideos(Context context, HashMap<String, Integer> map, ApiCallback.LikeUnlikeVideos callBack) {
        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<LikeUnlikeResposne> responseCall = anInterface.LikeUnlikeVideo(bearerToken + SharedPreference.getString(Global.token, context), map);
        responseCall.enqueue(new Callback<LikeUnlikeResposne>() {
            @Override
            public void onResponse(Call<LikeUnlikeResposne> call, Response<LikeUnlikeResposne> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccessLikeUnlikeVideo(response.body());
                } else {
                    JSONObject object = null;
                    try {
                        String error = response.errorBody().string();
                        Log.e("onResponse: ", "" + error);
                        object = new JSONObject(error);
                        callBack.onError(object.getString("error"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LikeUnlikeResposne> call, Throwable t) {
                if (t instanceof IOException) {
                    callBack.onError(context.getResources().getString(R.string.no_internet));
                } else if (t instanceof JsonSyntaxException) {
                    callBack.onError(context.getResources().getString(R.string.internal_error));
                } else {
                    callBack.onError(t.getMessage());
                }
            }
        });

    }


    public static void RatingVideos(Context context, HashMap<String, Integer> map, ApiCallback.RatingVideos callBack) {
        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<LikeUnlikeResposne> responseCall = anInterface.RatingVideo(bearerToken + SharedPreference.getString(Global.token, context), map);
        responseCall.enqueue(new Callback<LikeUnlikeResposne>() {
            @Override
            public void onResponse(Call<LikeUnlikeResposne> call, Response<LikeUnlikeResposne> response) {
                if (response.isSuccessful()) {
                    callBack.onRatingVideo(response.body());
                } else {
                    JSONObject object = null;
                    try {
                        String error = response.errorBody().string();
                        Log.e("onResponse: ", "" + error);
                        object = new JSONObject(error);
                        callBack.onError(object.getString("error"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LikeUnlikeResposne> call, Throwable t) {
                if (t instanceof IOException) {
                    callBack.onError(context.getResources().getString(R.string.no_internet));
                } else if (t instanceof JsonSyntaxException) {
                    callBack.onError(context.getResources().getString(R.string.internal_error));
                } else {
                    callBack.onError(t.getMessage());
                }
            }
        });

    }

    public static void ViewCall(Context context, HashMap<String, Integer> map, ApiCallback.ViewCall callBack) {
        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<LikeUnlikeResposne> responseCall = anInterface.ViewCall(bearerToken + SharedPreference.getString(Global.token, context), map);
        responseCall.enqueue(new Callback<LikeUnlikeResposne>() {
            @Override
            public void onResponse(Call<LikeUnlikeResposne> call, Response<LikeUnlikeResposne> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccessView(response.body());
                } else {
                    JSONObject object = null;
                    try {
                        String error = response.errorBody().string();
                        Log.e("onResponse: ", "" + error);
                        object = new JSONObject(error);
                        callBack.onError(object.getString("error"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LikeUnlikeResposne> call, Throwable t) {
                if (t instanceof IOException) {
                    callBack.onError(context.getResources().getString(R.string.no_internet));
                } else if (t instanceof JsonSyntaxException) {
                    callBack.onError(context.getResources().getString(R.string.internal_error));
                } else {
                    callBack.onError(t.getMessage());
                }
            }
        });
    }

    public static void SearchUserApi(Context context, HashMap<String, String> map, ApiCallback.SearchUserCall callBack) {
        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<SearchUserResposne> responseCall = anInterface.SearchUserCall(bearerToken + SharedPreference.getString(Global.token, context), map);
        responseCall.enqueue(new Callback<SearchUserResposne>() {
            @Override
            public void onResponse(Call<SearchUserResposne> call, Response<SearchUserResposne> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccessSearchUser(response.body());
                } else {
                    JSONObject object = null;
                    try {
                        String error = response.errorBody().string();
                        Log.e("onResponse: ", "" + error);
                        object = new JSONObject(error);
                        callBack.onError(object.getString("error"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchUserResposne> call, Throwable t) {
                if (t instanceof IOException) {
                    callBack.onError(context.getResources().getString(R.string.no_internet));
                } else if (t instanceof JsonSyntaxException) {
                    callBack.onError(context.getResources().getString(R.string.internal_error));
                } else {
                    callBack.onError(t.getMessage());
                }
            }
        });

    }


}
