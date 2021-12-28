package com.studclips.app.ui.player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.studclips.app.ApiCall;
import com.studclips.app.BaseActivity;
import com.studclips.app.R;
import com.studclips.app.api.ApiCallback;
import com.studclips.app.model.SignUpData;
import com.studclips.app.model.SignUpResponse;
import com.studclips.app.util.Global;
import com.studclips.app.util.SharedPreference;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class SubscriptionActivity extends BaseActivity implements ApiCallback.Subscription {
    private Context context;
    @BindView(R.id.ic_back_img)
    ImageView ic_back_img;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.rootLayout)
    LinearLayout rootLayout;
    private SignUpData signUpData;
    private boolean isFromUserProfile = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        onCreateData();
    }

    private void onCreateData() {
        if (getIntent().hasExtra("isFromUserProfile") && getIntent().getBooleanExtra("isFromUserProfile", false)) {
            isFromUserProfile = true;
        } else {
            isFromUserProfile = false;
        }
        ic_back_img.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.close_light, null));
    }

    @OnClick({R.id.llback, R.id.btn_Subscribe})
    public void onSubmit(View view) {
        if (view.getId() == R.id.llback) {
            onBackPressed();
        } else if (view.getId() == R.id.btn_Subscribe) {
            HashMap<String, String> map = new HashMap<>();
            map.put("type", "purchase");
            ApiCall.subscription(context, map, this);
        }
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_subscription;
    }

    @Override
    public void onSuccessSubscription(String response) {
        showToast("You are subscribe now");
        signUpData = Global.getSignUpData(context);
        signUpData.setHasSubscription("yes");
        Global.setSignUpData(context, signUpData);
        setResult(111,new Intent().putExtra("SignUpObj",signUpData));
        SharedPreference.saveBoolean(Global.isSubscribe, true, context);
        finish();
    }

    @Override
    public void onError(String error) {
        Global.callBannerWithColor(rootLayout, error);
    }
}