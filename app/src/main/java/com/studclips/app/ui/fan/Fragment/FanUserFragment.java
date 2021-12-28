package com.studclips.app.ui.fan.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.studclips.app.ApiCall;
import com.studclips.app.R;
import com.studclips.app.api.ApiCallback;
import com.studclips.app.model.SignUpData;
import com.studclips.app.model.SignUpResponse;
import com.studclips.app.ui.common.FanSettingActivity;
import com.studclips.app.ui.common.SignInActivity;
import com.studclips.app.ui.fan.FanHomeActivity;
import com.studclips.app.util.Global;
import com.studclips.app.util.SharedPreference;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

public class FanUserFragment extends Fragment implements ApiCallback.LogoutCallBack, ApiCallback.FanSignUpCallBack {
    private static final String TAG = "FanUserFragment";
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.img_profile_pic)
    ImageView img_profile_pic;
    @BindView(R.id.rootLayout)
    RelativeLayout rootLayout;
    private Context context;
    private Bitmap photo;
    private Gson gson;
    boolean isLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fan_user_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        isLogin = SharedPreference.getBoolean(Global.isLogin, context);
        gson = new Gson();
        initViews();
        return view;
    }


    private void initViews() {
        String signUpData = SharedPreference.getString(Global.SignupData, context);
        SignUpData playerSignUpData = gson.fromJson(signUpData, SignUpData.class);
        if (playerSignUpData != null) {
            Glide.with(context).load(playerSignUpData.getProfilePhoto()).into(new CustomTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    img_profile_pic.setImageDrawable(resource);
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {
                    img_profile_pic.setImageResource(R.drawable.ic_profile);
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    super.onLoadFailed(errorDrawable);
                    img_profile_pic.setImageResource(R.drawable.ic_profile);
                }
            });
            tv_address.setText("California");
            if (!TextUtils.isEmpty(playerSignUpData.getFirstName()))
                tv_name.setText(playerSignUpData.getFirstName().concat(" " + playerSignUpData.getLastName()));
        }

    }


    @OnClick({R.id.rv_edit_pic, R.id.rv_sign_out, R.id.rv_setting})
    public void onSubmit(View view) {
        switch (view.getId()) {
            case R.id.rv_setting:
                if (isLogin)
                    startActivity(new Intent(context, FanSettingActivity.class));
                else {
                    Global.showAlertDialog(context, true, "Warning", "You have to register as a Fan or Player to view Settings", "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().startActivity(new Intent(context, SignInActivity.class));
                            getActivity().finishAffinity();
                        }
                    }, "", null);
                }
                break;
            case R.id.rv_sign_out:
                Global.showAlertDialog(context, true, "Sign Out", "Are you sure?", "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (isLogin) {
                            if (!Global.isInternetConnected(context)) {
                                Toast.makeText(context, "Not Connected to internet", Toast.LENGTH_SHORT).show();
                            } else {
                                ApiCall.Logout(context, FanUserFragment.this);
                            }
                        } else {
                            getActivity().startActivity(new Intent(context, SignInActivity.class));
                            getActivity().finishAffinity();
                        }

                    }
                }, "No", null);
                break;
            case R.id.rv_edit_pic:
                if (isLogin) {
                    if (!Global.CheckAllPermission(getContext())) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 222);
                    } else {
                        showAlert();
                    }
                } else {
                    Global.showAlertDialog(context, true, "Warning", "You have to register as a Fan or Player to Edit Profile", "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().startActivity(new Intent(context, SignInActivity.class));
                            getActivity().finishAffinity();
                        }
                    }, "", null);
                }

                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 222) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        if (i == grantResults.length - 1) {
                            //openCamera();
                            showAlert();
                        }
                    } /*else {
                        if (!Global.CheckAllPermission(getContext())) {
                            requestPermissions(permissions, 222);
                            break;
                        }
                    }*/
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showAlert() {
        Global.showGalleryAndCameraAlert(context,
                (dialogInterface, i) -> {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, Global.GalleryRequestCode);
                }, (dialogInterface, i) -> {
                    Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(camera_intent, Global.CameraRequestCode);
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: My");
        Log.e(TAG, "requestCode : " + requestCode);
        Log.e(TAG, "data: " + data);
        if (requestCode == Global.CameraRequestCode && data != null) {
            photo = (Bitmap) data.getExtras().get("data");
            //img_profile_pic.setImageBitmap(photo);
            Uri uri = Global.getImageUri(getContext(), photo);
            String path = Global.getRealPathFromURI(uri, getContext());
            Log.e("path", path);
            if (!TextUtils.isEmpty(path)) {
                if (Global.isInternetConnected(context)) {
                    FanUpdateProfileCall(path);
                } else {
                    Global.callBannerWithColor(rootLayout, getString(R.string.no_internet));
                }
            } else {
                Toast.makeText(context, "Something wrong with image path", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == Global.GalleryRequestCode && data != null) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String path = Global.getRealPathFromURI(selectedImage, context);
                Log.e("TAG", "onActivityResult: path gallery" + path);
                if (!TextUtils.isEmpty(path)) {
                    //  img_profile_pic.setImageURI(selectedImage);
                    if (Global.isInternetConnected(context)) {
                        FanUpdateProfileCall(path);
                    } else {
                        Global.callBannerWithColor(rootLayout, getString(R.string.no_internet));
                    }
                } else {
                    Toast.makeText(context, "Something wrong with image path", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    private void FanUpdateProfileCall(String path) {
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("profile_photo", file.getName(), requestFile);
        ApiCall.FanUpdateProfile(context, SharedPreference.getString(Global.token, context), body, this);
    }

    @Override
    public void onSuccessLogout(String response) {
        boolean rememberMe = SharedPreference.getBoolean(Global.rememberMe, context);
        String email, password;
        password = SharedPreference.getString(Global.password, context);
        email = SharedPreference.getString(Global.email, context);
        SharedPreference.removeAll(context);
        if (rememberMe) {
            SharedPreference.saveBoolean(Global.rememberMe, true, context);
            SharedPreference.saveString(Global.email, email, context);
            SharedPreference.saveString(Global.password, password, context);
        }
        boolean b = SharedPreference.getBoolean(Global.rememberMe, context);
        startActivity(new Intent(context, SignInActivity.class));
        getActivity().finishAffinity();
    }

    @Override
    public void onError(String error) {
        Global.callBannerWithColor(rootLayout, error);
    }

    @Override
    public void onSuccessFanSignUp(SignUpResponse response) {
        Log.e("response ", "" + response);
        String signUpData = SharedPreference.getString(Global.SignupData, context);
        SignUpData playerSignUpData = gson.fromJson(signUpData, SignUpData.class);
        playerSignUpData.setProfilePhoto(response.getSuccess().getProfilePhoto());

        String updatedData = gson.toJson(playerSignUpData);
        SharedPreference.saveString(Global.SignupData, updatedData, context);

        Glide.with(context).load(response.getSuccess().getProfilePhoto()).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                img_profile_pic.setImageDrawable(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                img_profile_pic.setImageResource(R.drawable.ic_profile);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                img_profile_pic.setImageResource(R.drawable.ic_profile);
            }
        });

    }
}
