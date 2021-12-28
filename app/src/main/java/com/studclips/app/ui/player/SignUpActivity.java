package com.studclips.app.ui.player;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;

import com.google.gson.Gson;
import com.studclips.app.ApiCall;
import com.studclips.app.BaseActivity;
import com.studclips.app.R;
import com.studclips.app.api.ApiCallback;
import com.studclips.app.model.SignUpResponse;
import com.studclips.app.ui.common.TermsAndPrivacyActivity;
import com.studclips.app.ui.fan.FanHomeActivity;
import com.studclips.app.util.Global;
import com.studclips.app.util.SharedPreference;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SignUpActivity extends BaseActivity implements ApiCallback.PlayerSignUpCallBack, ApiCallback.FanSignUpCallBack {

    @BindView(R.id.rv_addres)
    RelativeLayout rv_addres;
    @BindView(R.id.rlRoot)
    RelativeLayout rlRoot;
    @BindView(R.id.nested_sign_up)
    NestedScrollView nested_sign_up;
    @BindView(R.id.sport_spn)
    Spinner sport_spn;
    @BindView(R.id.edit_name)
    EditText edit_name;
    @BindView(R.id.edit_last_name)
    EditText edit_last_name;
    @BindView(R.id.edit_email)
    EditText edit_email;
    @BindView(R.id.edit_password)
    EditText edit_password;
    @BindView(R.id.edit_password_confirm)
    EditText edit_password_confirm;
    @BindView(R.id.edit_state)
    EditText edit_state;
    @BindView(R.id.edit_City)
    EditText edit_City;
    @BindView(R.id.img_profile_pic)
    ImageView img_profile_pic;
    @BindView(R.id.checkbox_terms)
    CheckBox checkbox_terms;
    @BindView(R.id.ivCheckBox)
    ImageView ivCheckBox;
    @BindView(R.id.eyeImage)
    ImageView eyeImage;
    @BindView(R.id.eyeLayout)
    LinearLayout eyeLayout;
    @BindView(R.id.button_continue)
    Button button_continue;
    @BindView(R.id.tv_skip)
    TextView tv_skip;
    private Bitmap photo;
    private boolean showPassword = false, isChecked = false, isPlayer, isPhotoSelected = false;
    private File file;
    private Gson gson;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sport_array_by_hint, R.layout.spinner_item_lay);
        sport_spn.setAdapter(adapter);
        context = this;
        gson = new Gson();
        if (getIntent() != null && getIntent().getBooleanExtra("isFan", false)) {
            isPlayer = false;
            tv_skip.setVisibility(View.VISIBLE);
            button_continue.setText("Submit");
        } else {
            isPlayer = true;
            tv_skip.setVisibility(View.GONE);
            button_continue.setText("Continue");
        }
    }


    @Override
    protected int getActivityLayout() {
        return R.layout.activity_sign_up;
    }

    @OnClick({R.id.ic_back_img, R.id.button_continue, R.id.button_sign_up, R.id.img_add_photo, R.id.tv_privacy, R.id.tv_terms, R.id.eyeLayout, R.id.llCheckLay, R.id.tv_skip})
    public void onSubmit(View view) {
        switch (view.getId()) {
            case R.id.ic_back_img:
                onBackPressed();
                break;
            case R.id.tv_skip:
                startActivity(FanHomeActivity.class);
                finishAffinity();
                break;
            case R.id.button_sign_up:
                if (Global.isEditTextsEmpty(edit_state)) {
                    Global.callBannerWithColor(view, getString(R.string.alert_msg_empty_state));
                } else if (Global.isEditTextsEmpty(edit_City)) {
                    Global.callBannerWithColor(view, getString(R.string.alert_msg_empty_city));
                } else if (sport_spn.getSelectedItemPosition() == 0) {
                    Global.callBannerWithColor(view, getString(R.string.alert_msg_select_sport));
                } else {
                    if (Global.isInternetConnected(context))
                        initCommonData();
                    else {
                        Global.callBannerWithColor(view, getString(R.string.no_internet));
                    }
                }
                break;
            case R.id.button_continue:
                if (!isPhotoSelected) {
                    Global.callBannerWithColor(view, getString(R.string.alert_msg_select_photo));
                } else if (Global.isEditTextsEmpty(edit_name)) {
                    Global.callBannerWithColor(view, getString(R.string.alert_message_empty_first_name));
                } else if (Global.isEditTextsEmpty(edit_last_name)) {
                    Global.callBannerWithColor(view, getString(R.string.alert_message_empty_last_name));
                } else if (Global.isEditTextsEmpty(edit_email)) {
                    Global.callBannerWithColor(view, getString(R.string.alert_message_empty_email));
                } else if (!Global.isEmailValid(edit_email.getText().toString())) {
                    Global.callBannerWithColor(view, getString(R.string.alert_message_invalid_email));
                } else if (Global.isEditTextsEmpty(edit_password)) {
                    Global.callBannerWithColor(view, getString(R.string.alert_message_empty_pass));
                } else if (Global.isEditTextsEmpty(edit_password_confirm)) {
                    Global.callBannerWithColor(view, getString(R.string.alert_message_empty_confirm_pass));
                } else if (!edit_password.getText().toString().equals(edit_password_confirm.getText().toString())) {
                    Global.callBannerWithColor(view, getString(R.string.alert_msg_unmatch_confirm_pass));
                } else if (!isChecked) {
                    Global.callBannerWithColor(view, getString(R.string.alert_msg_terms_privacy));
                } else {
                    if (getIntent() != null && getIntent().getBooleanExtra("isFan", false)) {
                        initCommonData();

                    } else {
                        nested_sign_up.setVisibility(View.GONE);
                        rv_addres.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.img_add_photo:
                if (!Global.CheckAllPermission(getContext())) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 222);
                } else {
                    showAlert();
                }
                break;
            case R.id.tv_privacy:
                startActivity(new Intent(this, TermsAndPrivacyActivity.class).putExtra("head", getResources().getString(R.string.privacy_policy_txt)));
                break;
            case R.id.tv_terms:
                startActivity(new Intent(this, TermsAndPrivacyActivity.class).putExtra("head", getResources().getString(R.string.terms_and_condition_txt)));
                break;

            case R.id.llCheckLay:
                if (isChecked) {
                    ivCheckBox.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.unchecked_img, null));
                    isChecked = false;
                } else {
                    ivCheckBox.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.checked_img, null));
                    isChecked = true;
                }
                break;
            case R.id.eyeLayout:
                if (showPassword) {
                    eyeImage.setImageResource(R.drawable.eye_show);
                    edit_password_confirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    eyeImage.setImageResource(R.drawable.eye_hide);
                    edit_password_confirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                edit_password_confirm.setSelection(edit_password_confirm.getText().length());
                showPassword = !showPassword;
                break;
        }
    }


    void initCommonData() {
        RequestBody first_name, last_name, email, password, photoReqBody, state, city, gameId, role, device_token;
        MultipartBody.Part part;
        photoReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        part = MultipartBody.Part.createFormData("profile_photo", file.getName(), photoReqBody);
        first_name = RequestBody.create(MediaType.parse("text/plain"), edit_name.getText().toString().trim());
        last_name = RequestBody.create(MediaType.parse("text/plain"), edit_last_name.getText().toString().trim());
        email = RequestBody.create(MediaType.parse("text/plain"), edit_email.getText().toString().trim());
        password = RequestBody.create(MediaType.parse("text/plain"), edit_password.getText().toString().trim());
        device_token = RequestBody.create(MediaType.parse("text/plain"), "1");
        role = RequestBody.create(MediaType.parse("text/plain"), isPlayer ? "3" : "2");
        if (isPlayer) {
            state = RequestBody.create(MediaType.parse("text/plain"), edit_state.getText().toString().trim());
            city = RequestBody.create(MediaType.parse("text/plain"), edit_City.getText().toString().trim());
            gameId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(sport_spn.getSelectedItemPosition()));
            ApiCall.signUpPlayer(getContext(), role, device_token, part, first_name, last_name, email, password, state, city, gameId, this);
        } else {
            ApiCall.signUpFan(getContext(), role, device_token, part, first_name, last_name, email, password, this);
        }
    }

    private void showAlert() {
        // Global.showAlertDialog(getContext(), true, "", "Select image from?", "Gallery", (dialogInterface, i) -> openGallery(), "Camera", (dialogInterface, i) -> openCamera());
        Global.showGalleryAndCameraAlert(getContext(), (dialogInterface, i) -> Global.openGallery(this), (dialogInterface, i) -> Global.openCamera(this));
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
                    }/* else {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG", "onActivityResult: " + data);
        if (requestCode == Global.CameraRequestCode && data != null) {
            photo = (Bitmap) data.getExtras().get("data");
            img_profile_pic.setImageBitmap(photo);
            Uri uri = Global.getImageUri(getContext(), photo);
            String path = Global.getRealPathFromURI(uri, getContext());
            Log.e("path", path);
            file = new File(path);
            isPhotoSelected = true;
        } else if (requestCode == Global.GalleryRequestCode && data != null) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String path = Global.getRealPathFromURI(selectedImage, context);
                Log.e("TAG", "onActivityResult: path gallery" + path);
                if (!TextUtils.isEmpty(path)) {
                    img_profile_pic.setImageURI(selectedImage);
                    file = new File(path);
                    isPhotoSelected = true;
                } else {
                    Toast.makeText(context, "Something wrong with image path", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (rv_addres.getVisibility() == View.VISIBLE) {
            rv_addres.setVisibility(View.GONE);
            nested_sign_up.setVisibility(View.VISIBLE);
        } else
            super.onBackPressed();
    }

    @Override
    public void onSuccessPlayerSignUp(SignUpResponse response) {

        SharedPreference.saveBoolean(Global.rememberMe, true, context);
        String stringUser = gson.toJson(response.getSuccess());
        SharedPreference.saveString(Global.SignupData, stringUser, getContext());
        SharedPreference.saveBoolean(Global.isLogin, true, getContext());
        SharedPreference.saveString(Global.token, response.getSuccess().getToken(), getContext());
        SharedPreference.saveString(Global.playerType, response.getSuccess().getRole(), getContext());
        SharedPreference.saveString(Global.password, edit_password.getText().toString().trim(), getContext());
        SharedPreference.saveString(Global.email, edit_email.getText().toString().trim(), getContext());

        startActivity(PlayerProfileActivity.class);
        finishAffinity();
    }

    @Override
    public void onError(String error) {
        Global.callBannerWithColor(rlRoot, error);
    }

    @Override
    public void onSuccessFanSignUp(SignUpResponse response) {
        SharedPreference.saveBoolean(Global.rememberMe, true, context);
        String stringUser = gson.toJson(response.getSuccess());
        SharedPreference.saveString(Global.SignupData, stringUser, getContext());
        SharedPreference.saveBoolean(Global.isLogin, true, getContext());
        SharedPreference.saveString(Global.token, response.getSuccess().getToken(), getContext());
        SharedPreference.saveString(Global.playerType, Global.fan, getContext());
        SharedPreference.saveString(Global.password, edit_password.getText().toString().trim(), getContext());
        SharedPreference.saveString(Global.email, edit_email.getText().toString().trim(), getContext());
        startActivity(FanHomeActivity.class);
        finishAffinity();
    }
}