package com.studclips.app.ui.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.studclips.app.ApiCall;
import com.studclips.app.BaseActivity;
import com.studclips.app.R;
import com.studclips.app.api.ApiCallback;
import com.studclips.app.model.ForgotPassResposne;
import com.studclips.app.util.Global;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends BaseActivity implements ApiCallback.ForgotPasswordCallBack {

    @BindView(R.id.ic_back_img)
    ImageView ic_back_img;
    @BindView(R.id.resetPassBtn)
    Button resetPassBtn;
    @BindView(R.id.edit_email)
    EditText edit_email;
    @BindView(R.id.rootLayout)
    RelativeLayout rootLayout;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        context = this;
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_forgot_password;
    }

    @OnClick({R.id.ic_back_img, R.id.resetPassBtn})
    public void onSubmit(View view) {
        switch (view.getId()) {
            case R.id.ic_back_img:
                onBackPressed();
                break;
            case R.id.resetPassBtn:
                if (edit_email.getText().toString().length() == 0)
                    Global.callBannerWithColor(view, getString(R.string.alert_message_empty_email));
                else if (!Global.isEmailValid(edit_email.getText().toString()))
                    Global.callBannerWithColor(view, getString(R.string.alert_message_invalid_email));
                else {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("email", edit_email.getText().toString().trim());
                    if (Global.isInternetConnected(context)) {
                        ApiCall.ForgotPassword(context, map, this);
                    } else {
                        Global.callBannerWithColor(rootLayout, getString(R.string.no_internet));
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onSuccessForgotPass(ForgotPassResposne response) {

        Toast.makeText(context, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onError(String error) {
        Global.callBannerWithColor(edit_email, error);
    }
}