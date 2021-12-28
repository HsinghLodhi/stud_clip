package com.studclips.app.ui.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.google.gson.Gson;
import com.studclips.app.ApiCall;
import com.studclips.app.BaseActivity;
import com.studclips.app.R;
import com.studclips.app.api.ApiCallback;
import com.studclips.app.model.SignUpResponse;
import com.studclips.app.ui.fan.FanHomeActivity;
import com.studclips.app.ui.player.PlayerHomeActivity;
import com.studclips.app.ui.player.PlayerProfileActivity;
import com.studclips.app.util.BaseLoader;
import com.studclips.app.util.Global;
import com.studclips.app.util.SharedPreference;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity implements ApiCallback.SignInCallBack {
    private static final String TAG = "SignInActivity";
    @BindView(R.id.edit_email)
    EditText edit_email;
    @BindView(R.id.edit_password)
    EditText edit_password;
    @BindView(R.id.eyeImage)
    ImageView eyeImage;
    @BindView(R.id.ivCheckBox)
    ImageView ivCheckBox;
    @BindView(R.id.eyeLayout)
    LinearLayout eyeLayout;
    @BindView(R.id.rootLayout)
    ConstraintLayout rootLayout;
    private boolean showPassword = false, isChecked = false;
    private Context context;
    private Gson gson;
    private BaseLoader baseLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        baseLoader = new BaseLoader(context);
        isChecked = SharedPreference.getBoolean(Global.rememberMe, context);
        if (isChecked) {
            String email = SharedPreference.getString(Global.email, context);
            String password = SharedPreference.getString(Global.password, context);
            edit_email.setText(email);
            edit_password.setText(password);
        }
        if (isChecked) {
            ivCheckBox.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.checked_img, null));
            isChecked = true;
        } else {
            ivCheckBox.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.unchecked_img, null));
            isChecked = false;
        }
        onCreateViews();
    }

    private void onCreateViews() {
        gson = new Gson();
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_sign_in;
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.tv_skip, R.id.button_login, R.id.tv_not_account, R.id.tv_register, R.id.tv_forget, R.id.eyeLayout, R.id.llCheckLay})
    public void onSubmit(View view) {
        switch (view.getId()) {
            case R.id.tv_not_account:
                //  showToast(getResources().getString(R.string.don_t_have_an_account_txt) + " Clicked");
                break;
            case R.id.tv_register:
                startActivity(UserTypeActivity.class);
                break;
            case R.id.tv_skip:
                startActivity(FanHomeActivity.class);
                finishAffinity();
                break;
            case R.id.button_login:
                if (Global.isEditTextsEmpty(edit_email)) {
                    Global.callBannerWithColor(view, getString(R.string.alert_message_empty_email));
                } else if (!Global.isEmailValid(edit_email.getText().toString())) {
                    Global.callBannerWithColor(view, getString(R.string.alert_message_invalid_email));
                } else if (Global.isEditTextsEmpty(edit_password)) {
                    Global.callBannerWithColor(view, getString(R.string.alert_message_empty_pass));
                } else {
                    if (!Global.isInternetConnected(context)) {
                        showToast(getResources().getString(R.string.no_internet));
                    } else {
                        callLoginApi();
                    }
                }
                break;
            case R.id.tv_forget:
                startActivity(ForgotPasswordActivity.class);
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
                    edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    eyeImage.setImageResource(R.drawable.eye_hide);
                    edit_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                edit_password.setSelection(edit_password.getText().length());
                showPassword = !showPassword;
                break;

        }
    }

    private void callLoginApi() {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", edit_email.getText().toString().trim());
        map.put("password", edit_password.getText().toString().trim());
        map.put("device_type", Global.DEVICE_TYPE);
        map.put("device_token", "");
        baseLoader.showLoader();
        ApiCall.SignIn(context, this, map);


    }

    @Override
    public void onError(String error) {
        baseLoader.hideLoader();
        Global.callBannerWithColor(rootLayout, error);
    }

    @Override
    public void onSuccessSignIn(SignUpResponse response) {
        baseLoader.hideLoader();
        Class activityName;
        String playerType;
        Log.e(TAG, "onSuccessSignIn: "+response.getSuccess().getGameId());
        if (response.getSuccess().getRole().equalsIgnoreCase("2")) {
            playerType = "2";
            activityName = FanHomeActivity.class;
        } else {
            activityName = response.getSuccess().get_class() == null || response.getSuccess().getPosition() == null ? PlayerProfileActivity.class : PlayerHomeActivity.class;
            playerType = "3";
        }
        SharedPreference.saveString(Global.token, response.getSuccess().getToken(), getContext());
        SharedPreference.saveBoolean(Global.isLogin, true, getContext());
        String stringUser = gson.toJson(response.getSuccess());
        SharedPreference.saveString(Global.SignupData, stringUser, getContext());
        SharedPreference.saveString(Global.password, edit_password.getText().toString().trim(), getContext());
        SharedPreference.saveString(Global.playerType, playerType, getContext());
        SharedPreference.saveString(Global.email, edit_email.getText().toString().trim(), getContext());
        if (isChecked) {
            SharedPreference.saveBoolean(Global.rememberMe, true, context);
        }
        startActivity(activityName);
        finishAffinity();
    }
}