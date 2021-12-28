package com.studclips.app.ui.common;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.studclips.app.BaseActivity;
import com.studclips.app.R;

import butterknife.BindView;
import butterknife.OnClick;

public class TermsAndPrivacyActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        if (intent != null) {
            tv_title.setText(intent.getStringExtra("head"));
        }
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_terms_and_privacy;
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.ic_back_img})
    public void onSubmit(View view) {
        switch (view.getId()) {
            case R.id.ic_back_img:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}