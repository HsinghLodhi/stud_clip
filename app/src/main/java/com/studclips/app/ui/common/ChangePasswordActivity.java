package com.studclips.app.ui.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.studclips.app.ApiCall;
import com.studclips.app.BaseActivity;
import com.studclips.app.R;
import com.studclips.app.api.ApiCallback;
import com.studclips.app.util.Global;
import com.studclips.app.util.SharedPreference;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
//https://studclips.gridironstuds.com/public/index.php/api/videos
public class ChangePasswordActivity extends BaseActivity implements ApiCallback.LogoutCallBack {

    private boolean showPassword = false, showPasswordOld = false, isChecked = false;
    @BindView(R.id.eyeLayout)
    LinearLayout eyeLayout;
    @BindView(R.id.eyeLayoutOld)
    LinearLayout eyeLayoutOld;
    @BindView(R.id.eyeImage)
    ImageView eyeImage;
    @BindView(R.id.eyeImageOld)
    ImageView eyeImageOld;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_confirm_password)
    EditText et_confirm_password;
    @BindView(R.id.et_old_password)
    EditText et_old_password;
    @BindView(R.id.rootLayout)
    RelativeLayout rootLayout;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_change_password;
    }

    @Override
    @OnClick({R.id.eyeLayout, R.id.backll, R.id.btnChangePassword, R.id.eyeLayoutOld})
    protected void onSubmit(View view) {
        if (view.getId() == R.id.backll) {
            onBackPressed();
        } else if (view.getId() == R.id.eyeLayoutOld) {
            if (showPasswordOld) {
                eyeImageOld.setImageResource(R.drawable.eye_show);
                et_old_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                eyeImageOld.setImageResource(R.drawable.eye_hide);
                et_old_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            et_old_password.setSelection(et_old_password.getText().length());
            showPasswordOld = !showPasswordOld;
        } else if (view.getId() == R.id.eyeLayout) {
            if (showPassword) {
                eyeImage.setImageResource(R.drawable.eye_show);
                et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                eyeImage.setImageResource(R.drawable.eye_hide);
                et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            et_password.setSelection(et_password.getText().length());
            showPassword = !showPassword;
        } else if (view.getId() == R.id.btnChangePassword) {
            if (isValidateData(view)) {
                if (Global.isInternetConnected(context)) {
                    callChangePasswordApi();
                } else {
                    Global.callBannerWithColor(rootLayout, getString(R.string.no_internet));
                }
            }
        }
    }

    private void callChangePasswordApi() {
        HashMap<String, String> map = new HashMap<>();
        map.put("old_password", et_old_password.getText().toString().trim());
        map.put("password", et_password.getText().toString().trim());
        map.put("confirm_password", et_confirm_password.getText().toString().trim());
        ApiCall.ChangePassword(context, map, this);
    }

    private boolean isValidateData(View view) {
        if (TextUtils.isEmpty(et_old_password.getText().toString().trim())) {
            Global.callBannerWithColor(view, getString(R.string.alert_message_empty_old_pass));
            return false;
        } else if (TextUtils.isEmpty(et_password.getText().toString().trim())) {
            Global.callBannerWithColor(view, getString(R.string.alert_message_empty_pass));
            return false;
        } else if (TextUtils.isEmpty(et_confirm_password.getText().toString().trim())) {
            Global.callBannerWithColor(view, getString(R.string.alert_message_empty_confirm_pass));
            return false;
        } else if (et_old_password.getText().toString().trim().equalsIgnoreCase(et_password.getText().toString().trim())) {
            Global.callBannerWithColor(view, getString(R.string.alert_msg_match_confirm_pass));
            return false;
        } else if (!et_password.getText().toString().trim().equalsIgnoreCase(et_confirm_password.getText().toString().trim())) {
            Global.callBannerWithColor(view, getString(R.string.alert_msg_unmatch_confirm_pass));
            return false;
        }
        return true;
    }

    @Override
    public void onSuccessLogout(String response) {
        SharedPreference.saveString(Global.password, et_password.getText().toString().trim(), context);
        showToast(response);
        finish();
    }

    @Override
    public void onError(String error) {
        Global.callBannerWithColor(rootLayout, error);
    }
}