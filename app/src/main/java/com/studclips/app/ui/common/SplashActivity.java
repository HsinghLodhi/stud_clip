package com.studclips.app.ui.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.Gson;
import com.studclips.app.ApiCall;
import com.studclips.app.BaseActivity;
import com.studclips.app.R;
import com.studclips.app.api.ApiCallback;
import com.studclips.app.model.SignUpData;
import com.studclips.app.model.SignUpResponse;
import com.studclips.app.ui.fan.FanHomeActivity;
import com.studclips.app.ui.player.PlayerHomeActivity;
import com.studclips.app.ui.player.PlayerProfileActivity;
import com.studclips.app.util.Global;
import com.studclips.app.util.SharedPreference;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity implements ApiCallback.SignInCallBack {
    private Context context;
    @BindView(R.id.rootLayout)
    ConstraintLayout rootLayout;
    private static final String TAG = "SplashActivity";
    String playerType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        new Handler().postDelayed(() -> {
            boolean isLogin = SharedPreference.getBoolean(Global.isLogin, getContext());
            playerType = SharedPreference.getString(Global.playerType, getContext());
            if (isLogin && !TextUtils.isEmpty(playerType)) {
                callLoginApi();
            } else {
                SharedPreference.removeAll(getContext());
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, SignInActivity.class));
            }
        }, 1500);
    }

    private void callLoginApi() {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", SharedPreference.getString(Global.email, context));
        map.put("password", SharedPreference.getString(Global.password, context));
        map.put("device_type", Global.DEVICE_TYPE);
        map.put("device_token", "");
        ApiCall.SignIn(context, this, map);
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_main;
    }

    @Override
    @OnClick
    protected void onSubmit(View view) {
    }

    @Override
    public void onSuccessSignIn(SignUpResponse response) {
        Global.setSignUpData(context, response.getSuccess());
        SignUpData playerSignUpData = Global.getSignUpData(context);
        SharedPreference.saveString(Global.token, response.getSuccess().getToken(), getContext());
        SharedPreference.saveBoolean(Global.isLogin, true, getContext());
        SharedPreference.saveString(Global.playerType, playerType, getContext());
        if (playerType.equalsIgnoreCase(Global.player)) {
            boolean isSkip = SharedPreference.getBoolean(Global.isSkipProfile, getContext());
            if (isSkip) {
                startActivity(PlayerHomeActivity.class);
            } else if (playerSignUpData.get_class() == null || playerSignUpData.getPosition() == null) {
                startActivity(PlayerProfileActivity.class);
            } else {
                startActivity(PlayerHomeActivity.class);
            }
        } else if (playerType.equalsIgnoreCase(Global.fan)) {
            startActivity(FanHomeActivity.class);
        } else {
            startActivity(SignInActivity.class);
        }
        finishAffinity();
    }

    @Override
    public void onError(String error) {
        if (error.equalsIgnoreCase("Invalid Credentials")) {
            SharedPreference.removeAll(context);
            startActivity(SignInActivity.class);
            finishAffinity();
        } else
            Global.callBannerWithColor(rootLayout, error);
    }
}