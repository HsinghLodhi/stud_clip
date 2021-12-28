package com.studclips.app.ui.player.Fragment;

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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.studclips.app.ApiCall;
import com.studclips.app.R;
import com.studclips.app.api.ApiCallback;
import com.studclips.app.model.SignUpData;
import com.studclips.app.ui.common.SettingActivity;
import com.studclips.app.ui.common.SignInActivity;
import com.studclips.app.ui.player.MyVideoActivity;
import com.studclips.app.ui.player.NotificationActivity;
import com.studclips.app.ui.player.PlayerProfileActivity;
import com.studclips.app.ui.player.SubscriptionActivity;
import com.studclips.app.ui.player.UpdatePhotoPlayer;
import com.studclips.app.util.Global;
import com.studclips.app.util.SharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class PlayerUserFragment extends Fragment implements ApiCallback.LogoutCallBack {
    private static final String TAG = "PlayerUserFragment";
    @BindView(R.id.tv_views_count)
    TextView tv_views_count;
    @BindView(R.id.tv_likes_count)
    TextView tv_likes_count;
    @BindView(R.id.tv_reviews_count)
    TextView tv_reviews_count;
    @BindView(R.id.tv_videos_count)
    TextView tv_videos_count;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.img_profile_pic)
    ImageView img_profile_pic;
    @BindView(R.id.rv_payment)
    RelativeLayout rv_payment;
    @BindView(R.id.rv_videos)
    RelativeLayout rv_videos;
    @BindView(R.id.rootLayout)
    RelativeLayout rootLayout;
    @BindView(R.id.tvReviewTxt)
    TextView tvReviewTxt;
    @BindView(R.id.tvVideoTxt)
    TextView tvVideoTxt;
    @BindView(R.id.tvLikesTxt)
    TextView tvLikesTxt;
    @BindView(R.id.tvViewTxt)
    TextView tvViewTxt;
    private Context context;
    private Bitmap photo;
    private Gson gson;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.player_user_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        gson = new Gson();
        initViews();
        return view;
    }

    private void initViews() {
        String signUpDataStr = SharedPreference.getString(Global.SignupData, context);
        SignUpData signUpData = gson.fromJson(signUpDataStr, SignUpData.class);
        setImage(signUpData.getProfilePhoto());
        img_profile_pic.setImageResource(R.drawable.ic_profile);
        if (!TextUtils.isEmpty(signUpData.getCity()))
            tv_address.setText(signUpData.getCity());

        if (!TextUtils.isEmpty(signUpData.getFirstName()))
            tv_name.setText(signUpData.getFirstName().concat(" " + signUpData.getLastName()));

        tv_videos_count.setText(String.valueOf(signUpData.getLikesCount()));
        tv_reviews_count.setText(String.valueOf(signUpData.getReviewsCount()));
        tv_likes_count.setText(String.valueOf(signUpData.getLikesCount()));
        tv_views_count.setText(String.valueOf(signUpData.getViewsCount()));


        tvViewTxt.setText(signUpData.getViewsCount() == 1 ? "View" : "Views");
        tvLikesTxt.setText(signUpData.getLikesCount() == 1 ? "Like" : "Likes");
        tvVideoTxt.setText(signUpData.getVideosCount() == 1 ? "Video" : "Videos");
        tvReviewTxt.setText(signUpData.getReviewsCount() == 1 ? "ReView" : "ReViews");

    }


    @OnClick({R.id.rv_edit, R.id.rv_edit_pic, R.id.rv_payment, R.id.rv_videos, R.id.rv_sign_out, R.id.rv_notification, R.id.rv_setting})
    public void onSubmit(View view) {
        switch (view.getId()) {
            case R.id.rv_edit:
                Intent intent = new Intent(context, PlayerProfileActivity.class);
                intent.putExtra("isFromUserProfile", true);
                startActivity(intent);
                break;
            case R.id.rv_payment:
                Intent intent2 = new Intent(context, SubscriptionActivity.class);
                intent2.putExtra("isFromUserProfile", true);
                startActivity(intent2);
                break;
            case R.id.rv_videos:
                startActivity(new Intent(context, MyVideoActivity.class));
                break;
            case R.id.rv_notification:
                startActivity(new Intent(context, NotificationActivity.class));
                break;
            case R.id.rv_setting:
                startActivity(new Intent(context, SettingActivity.class));
                break;

            case R.id.rv_sign_out:
                Global.showAlertDialog(context, true, "Sign Out", "Are you sure?", "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!Global.isInternetConnected(context)) {
                            Toast.makeText(context, "Not Connected to internet", Toast.LENGTH_SHORT).show();
                        } else {
                            ApiCall.Logout(context, PlayerUserFragment.this);
                        }
                    }
                }, "No", null);
                break;
            case R.id.rv_edit_pic:
                Intent intent1 = new Intent(context, UpdatePhotoPlayer.class);
                startActivityForResult(intent1, 111);
              /*  if (!Global.CheckAllPermission(getContext())) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 222);
                } else {
                    showAlert();
                }*/

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
                            showAlert();
                        }
                    }
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
        if (requestCode == Global.CameraRequestCode && data != null) {
            photo = (Bitmap) data.getExtras().get("data");
            img_profile_pic.setImageBitmap(photo);
           /* Uri uri = getImageUri(context, photo);
            String path = getRealPathFromURI(uri);
            Log.e("path", path);
            File file = new File(path);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("img", file.getName(), requestFile);
            baseLoader.showLoader();
            ApiCall.UpdatePhotoCall(MySharedPreference.getInfo(Global.Token, context), body, this);*/
        } else if (requestCode == Global.GalleryRequestCode && data != null) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                img_profile_pic.setImageURI(selectedImage);
            }
        } else if (requestCode == 111 && resultCode == 121 && data != null) {
            Log.e(TAG, "onActivityResult: " + data);
            String image_url = data.getStringExtra("image_url");
            if (!TextUtils.isEmpty(image_url)) {
                setImage(image_url);
            }
        }
    }

    private void setImage(String iamgeUrl) {
        Glide.with(context).load(iamgeUrl).into(new CustomTarget<Drawable>() {
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
        startActivity(new Intent(context, SignInActivity.class));
        getActivity().finishAffinity();
    }

    @Override
    public void onError(String error) {
        Global.callBannerWithColor(rootLayout, error);
    }
}
