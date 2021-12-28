package com.studclips.app.util;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Looper;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.studclips.app.R;
import com.studclips.app.model.Feed;
import com.studclips.app.ui.player.Fragment.PlayerHomeFragment;
import com.studclips.app.ui.player.Fragment.PlayerInboxFragment;
import com.studclips.app.ui.player.Fragment.PlayerTopTenFragment;
import com.studclips.app.ui.player.Fragment.PlayerUploadVideoFragment;
import com.studclips.app.ui.player.Fragment.PlayerUserFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddFragmentTask {
    private final Executor executor = Executors.newSingleThreadExecutor(); // change according to your requirements
    private final Handler handler = new Handler(Looper.getMainLooper());

    public interface Callback<R> {
        void onAddedFragment();
    }



    public <R> void executeAsync(Callback<R> callback,FragmentManager fm) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final Fragment homeFragment = new PlayerHomeFragment();
                    final Fragment playerTopTenFragment = new PlayerTopTenFragment();
                    final Fragment uploadVideoFragment = new PlayerUploadVideoFragment();
                    final Fragment playerInboxFragment = new PlayerInboxFragment();
                    final Fragment playerUserFragment = new PlayerUserFragment();
                    fm.beginTransaction().add(com.studclips.app.R.id.frameHome, homeFragment, "1").commit();
                    fm.beginTransaction().add(com.studclips.app.R.id.frameTopTen, playerTopTenFragment, "2").commit();//.hide(playerTopTenFragment).commit();
                    fm.beginTransaction().add(com.studclips.app.R.id.frameAddVideo, uploadVideoFragment, "3").commit();//.hide(uploadVideoFragment).commit();
                    fm.beginTransaction().add(com.studclips.app.R.id.frameInbox, playerInboxFragment, "4").commit();//.hide(playerInboxFragment).commit();
                    fm.beginTransaction().add(com.studclips.app.R.id.frameAccount, playerUserFragment, "5").commit();// .hide(playerUserFragment).commit();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onAddedFragment();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

}
