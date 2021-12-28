package com.studclips.app.ui.fan;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.studclips.app.BaseActivity;
import com.studclips.app.R;
import com.studclips.app.ui.common.SignInActivity;
import com.studclips.app.ui.fan.Fragment.FanHomeFragment;
import com.studclips.app.ui.fan.Fragment.FanInboxFragment;
import com.studclips.app.ui.fan.Fragment.FanNotificationFragment;
import com.studclips.app.ui.fan.Fragment.FanTopTenFragment;
import com.studclips.app.ui.fan.Fragment.FanUserFragment;
import com.studclips.app.util.Global;
import com.studclips.app.util.SharedPreference;

import butterknife.BindView;
import butterknife.OnClick;

public class FanHomeActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.frameFanHome)
    FrameLayout frameFanHome;
    @BindView(R.id.frameTopTenFan)
    FrameLayout frameTopTenFan;
    @BindView(R.id.frameNotificationFan)
    FrameLayout frameNotificationFan;
    @BindView(R.id.frameInboxFan)
    FrameLayout frameInboxFan;
    @BindView(R.id.frameUserFan)
    FrameLayout frameUserFan;
    final Fragment fanHomeFragment = new FanHomeFragment();
    final Fragment fanTopTenFragment = new FanTopTenFragment();
    final Fragment fanNotificationFragment = new FanNotificationFragment();
    final Fragment fanInboxFragment = new FanInboxFragment();
    final Fragment fanUserFragment = new FanUserFragment();
    final FragmentManager fm = getSupportFragmentManager();
    boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateData();
    }

    private void onCreateData() {
        setCurrentFragment(fanHomeFragment);
        isLogin = SharedPreference.getBoolean(Global.isLogin, getContext());
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }


    private void setCurrentFragment(Fragment fragment) {
        fm.beginTransaction().replace(R.id.frameFanHome, fragment).commit();
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_fan_home;
    }

    @Override
    @OnClick()
    protected void onSubmit(View view) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_tab:
                setCurrentFragment(fanHomeFragment);
                return true;
            case R.id.top_ten_tab:
                if (isLogin) {
                    setCurrentFragment(fanTopTenFragment);
                } else {
                    Global.showAlertDialog(FanHomeActivity.this, false, "Warning", "You have to register as a Fan or Player to view Top10 Videos", "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callLoginScreen();
                        }
                    }, "", null);
                }
                return true;
            case R.id.notification_tab:
                if (isLogin) {
                    setCurrentFragment(fanNotificationFragment);
                } else {
                    Global.showAlertDialog(FanHomeActivity.this, false, "Warning", "You have to register as a Fan to view Fan Notifications", "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callLoginScreen();
                        }
                    }, "", null);
                }
                return true;
            case R.id.inbox_tab:
                if (isLogin) {
                    setCurrentFragment(fanInboxFragment);
                } else {
                    Global.showAlertDialog(FanHomeActivity.this, false, "Warning", "You have to register as a Fan or Player to view Messages", "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callLoginScreen();
                        }
                    }, "", null);

                }
                return true;
            case R.id.user_tab:
                setCurrentFragment(fanUserFragment);
                return true;
        }
        return false;
    }

    public void callLoginScreen() {
        startActivity(SignInActivity.class);
        finishAffinity();
    }
}