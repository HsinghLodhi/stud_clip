package com.studclips.app.ui.player;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.studclips.app.BaseActivity;
import com.studclips.app.R;
import com.studclips.app.ui.player.Fragment.PlayerHomeFragment;
import com.studclips.app.ui.player.Fragment.PlayerInboxFragment;
import com.studclips.app.ui.player.Fragment.PlayerTopTenFragment;
import com.studclips.app.ui.player.Fragment.PlayerUploadVideoFragment;
import com.studclips.app.ui.player.Fragment.PlayerUserFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class PlayerHomeActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.frameHome)
    FrameLayout frameHome;
    @BindView(R.id.frameTopTen)
    FrameLayout frameTopTen;
    @BindView(R.id.frameAddVideo)
    FrameLayout frameAddVideo;
    @BindView(R.id.frameInbox)
    FrameLayout frameInbox;
    @BindView(R.id.frameAccount)
    FrameLayout frameAccount;
    final Fragment homeFragment = new PlayerHomeFragment();
    final Fragment playerTopTenFragment = new PlayerTopTenFragment();
    final Fragment uploadVideoFragment = new PlayerUploadVideoFragment();
    final Fragment playerInboxFragment = new PlayerInboxFragment();
    final Fragment playerUserFragment = new PlayerUserFragment();
    final FragmentManager fm = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentFragment(homeFragment);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }


    private void setCurrentFragment(Fragment fragment) {
        fm.beginTransaction().replace(R.id.frameHome, fragment).commit();
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_player_home;
    }

    @Override
    @OnClick()
    protected void onSubmit(View view) {

    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.home_tab:
                setCurrentFragment(homeFragment);
                return true;
            case R.id.top_ten_tab:
                setCurrentFragment(playerTopTenFragment);
                return true;
            case R.id.upload_video_tab:
                setCurrentFragment(uploadVideoFragment);
                return true;
            case R.id.inbox_tab:
                setCurrentFragment(playerInboxFragment);
                return true;
            case R.id.user_tab:
                setCurrentFragment(playerUserFragment);
                return true;
        }
        return false;
    }
 }