package com.studclips.app.ui.common;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

import com.studclips.app.ApiCall;
import com.studclips.app.BaseActivity;
import com.studclips.app.R;
import com.studclips.app.api.ApiCallback;
import com.studclips.app.model.SignUpData;
import com.studclips.app.model.SignUpResponse;
import com.studclips.app.util.Global;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class FanSettingActivity extends BaseActivity implements ApiCallback.UpdateSettings {
    @BindView(R.id.ivMessageSwitch)
    ImageView ivMessageSwitch;
    @BindView(R.id.ivVideoSwitch)
    ImageView ivVideoSwitch;
    @BindView(R.id.rootLayout)
    RelativeLayout rootLayout;
    boolean isNewVideo, isMessage;
    SignUpData signUpData;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        signUpData = Global.getSignUpData(context);
        setUiData();
    }

    private void setUiData() {
        if (signUpData.getIsNewvideo() != null && signUpData.getIsNewvideo() == 1) {
            isNewVideo = true;
            ivVideoSwitch.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.switch_on, null));
        } else {
            isNewVideo = false;
            ivVideoSwitch.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.switch_off, null));
        }
        if (signUpData.getIsMessage() != null && signUpData.getIsMessage() == 1) {
            isMessage = true;
            ivMessageSwitch.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.switch_on, null));
        } else {
            isMessage = false;
            ivMessageSwitch.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.switch_off, null));
        }
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_fan_setting;
    }

    @Override
    @OnClick({R.id.ivMessageSwitch, R.id.ivVideoSwitch, R.id.rlChangePassword, R.id.backll})
    protected void onSubmit(View view) {
        if (view.getId() == R.id.ivMessageSwitch) {
            ivMessageSwitch.setImageDrawable(!isMessage ? ResourcesCompat.getDrawable(getResources(), R.drawable.switch_on, null) : ResourcesCompat.getDrawable(getResources(), R.drawable.switch_off, null));
            isMessage = !isMessage;
            callSettingApi();
        } else if (view.getId() == R.id.ivVideoSwitch) {
            ivVideoSwitch.setImageDrawable(!isNewVideo ? ResourcesCompat.getDrawable(getResources(), R.drawable.switch_on, null) : ResourcesCompat.getDrawable(getResources(), R.drawable.switch_off, null));
            isNewVideo = !isNewVideo;
            callSettingApi();
        } else if (view.getId() == R.id.rlChangePassword) {
            startActivity(ChangePasswordActivity.class);
        } else if (view.getId() == R.id.backll) {
            onBackPressed();
        }
    }

    private void callSettingApi() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("is_newvideo", isNewVideo ? 1 : 0);
        map.put("is_message", isMessage ? 1 : 0);
        Log.e("TAG", "callSettingApi: " + map);
        if (Global.isInternetConnected(context)) {
            ApiCall.setting(context, map, this);
        } else {
            Global.callBannerWithColor(rootLayout, getString(R.string.no_internet));
        }
    }

    @Override
    public void onSuccessUpdateSetting(SignUpResponse response) {
        signUpData.setIsNewvideo(response.getSuccess().getIsNewvideo() != null && response.getSuccess().getIsNewvideo() == 1 ? 1 : 0);
        signUpData.setIsMessage(response.getSuccess().getIsMessage() != null && response.getSuccess().getIsMessage()== 1 ? 1 : 0);
        setUiData();
        Global.setSignUpData(context, signUpData);
        Toast.makeText(context, "Settings Changed successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String error) {
        Global.callBannerWithColor(rootLayout, error);
    }
}