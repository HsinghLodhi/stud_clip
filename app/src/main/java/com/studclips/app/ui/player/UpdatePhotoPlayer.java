package com.studclips.app.ui.player;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.studclips.app.ApiCall;
import com.studclips.app.BaseActivity;
import com.studclips.app.R;
import com.studclips.app.api.ApiCallback;
import com.studclips.app.model.SignUpData;
import com.studclips.app.model.SignUpResponse;
import com.studclips.app.util.Global;
import com.studclips.app.util.SharedPreference;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UpdatePhotoPlayer extends BaseActivity implements ApiCallback.UpdateProfilePlayer {
    private Context context;
    private Bitmap photo;
    private Gson gson;
    private static final String TAG = "UpdatePhotoPlayer";
    @BindView(R.id.img_profile_pic)
    ImageView img_profile_pic;
    @BindView(R.id.sport_spn)
    Spinner sport_spn;
    @BindView(R.id.rootLayout)
    RelativeLayout rootLayout;
    @BindView(R.id.edit_City)
    EditText edit_City;
    @BindView(R.id.edit_state)
    EditText edit_state;
    private File file;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_update_photo_player;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sport_array_by_hint, R.layout.spinner_item_lay);
        sport_spn.setAdapter(adapter);
        SignUpData signUpData = Global.getSignUpData(context);
        img_profile_pic.setImageResource(R.drawable.ic_profile);
        gson = new Gson();
        Glide.with(context).load(signUpData.getProfilePhoto()).into(new CustomTarget<Drawable>() {
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

        if (!TextUtils.isEmpty(signUpData.getState())) {
            edit_state.setText(signUpData.getState());
        }
        if (!TextUtils.isEmpty(signUpData.getCity())) {
            edit_City.setText(signUpData.getCity());
        }
        if (!TextUtils.isEmpty(signUpData.getGameId())) {
            int position = Integer.parseInt(signUpData.getGameId());
            sport_spn.setSelection(position);
        }
    }

    @OnClick({R.id.ic_back_img, R.id.img_add_photo, R.id.button_update})
    @Override
    protected void onSubmit(View view) {
        if (view.getId() == R.id.ic_back_img) {
            onBackPressed();
        } else if (view.getId() == R.id.img_add_photo) {
            if (!Global.CheckAllPermission(getContext())) {
                requestPermissions(new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 222);
            } else {
                showAlert();
            }
        } else if (view.getId() == R.id.button_update) {
            MultipartBody.Part part = null;
            RequestBody photoReqBody, state, city, gameId;
            if (file != null) {
                photoReqBody = RequestBody.create(MediaType.parse("image/*"), file);
                part = MultipartBody.Part.createFormData("profile_photo", file.getName(), photoReqBody);
            }
            HashMap<String, RequestBody> map = new HashMap<>();
            state = RequestBody.create(MediaType.parse("text/plain"), edit_state.getText().toString().trim());
            city = RequestBody.create(MediaType.parse("text/plain"), edit_City.getText().toString().trim());
            gameId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(sport_spn.getSelectedItemPosition()));
            map.put("city", city);
            map.put("state", state);
            map.put("game_id", gameId);
            if (Global.isInternetConnected(context)) {
                ApiCall.updatePlayerPhoto(context, part, map, this);
            } else {
                Global.callBannerWithColor(view, getString(R.string.no_internet));
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 222 && grantResults.length > 0) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED && i == grantResults.length - 1) {
                    showAlert();
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
        Log.e("TAG", "onActivityResult: " + data);
        if (requestCode == Global.CameraRequestCode && data != null && resultCode == RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            Glide.with(context).load(photo).into(img_profile_pic);
            Uri uri = Global.getImageUri(getContext(), photo);
            String path = Global.getRealPathFromURI(uri, getContext());
            Log.e("path", path);
            file = new File(path);
        } else if (requestCode == Global.GalleryRequestCode && data != null) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String path = Global.getRealPathFromURI(selectedImage, context);
                Log.e("TAG", "onActivityResult: path gallery" + path);
                if (!TextUtils.isEmpty(path)) {
                    Glide.with(context).load(path).into(img_profile_pic);
                    file = new File(path);
                } else {
                    Toast.makeText(context, "Something wrong with image path", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onSuccessProfile(SignUpResponse response) {
        SignUpData signUpData = Global.getSignUpData(context);
        signUpData.setProfilePhoto(response.getSuccess().getProfilePhoto());
        signUpData.setCity(response.getSuccess().getCity());
        signUpData.setState(response.getSuccess().getState());
        signUpData.setGameId(response.getSuccess().getGameId());
        String updatedSignUp = gson.toJson(signUpData);
        SharedPreference.saveString(Global.SignupData, updatedSignUp, context);
        Intent intent = new Intent();
        showToast("Updated successfully");
        intent.putExtra("image_url", response.getSuccess().getProfilePhoto());
        setResult(121, intent);
        finish();
    }

    @Override
    public void onError(String error) {
        Global.callBannerWithColor(rootLayout, error);
    }
}