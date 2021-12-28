package com.studclips.app.ui.fan.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;


import com.studclips.app.MyTask;
import com.studclips.app.R;
import com.studclips.app.adapter.FanVideoAdapter;
import com.studclips.app.model.Feed;
import com.studclips.app.model.VideoData;
import com.studclips.app.ui.common.FiltersActivity;
import com.studclips.app.ui.common.SignInActivity;
import com.studclips.app.ui.fan.FanHomeActivity;
import com.studclips.app.util.AddVideoTask;
import com.studclips.app.util.Global;
import com.studclips.app.util.SharedPreference;
import com.studclips.app.util.SnapHelperOneByOne;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FanHomeFragment extends Fragment implements FanVideoAdapter.ImageShowHomeFan, MyTask.Callback<R>, AddVideoTask.Callback<R> {

    @BindView(R.id.fanHomeRR)
    RecyclerView fanHomeRR;
    @BindView(R.id.progressFanHome)
    ProgressBar progressFanHome;
    @BindView(R.id.mainLayRl)
    RelativeLayout mainLayRl;
    static List<Feed> feedListHome;
    static int lastVideoPos;
    ImageView imageView;
    VideoView videoView;
    LinearLayoutManager layoutManager;
    boolean isAllowLoad = true, isLogin;
    private static final String TAG = "PlayerHomeFragment";
    int firstSize, lastSize;
    FanVideoAdapter fanVideoAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fan_home_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        layoutManager = new LinearLayoutManager(getContext());
        isLogin = SharedPreference.isLogin(getContext());
        initView();
        return view;
    }

    @OnClick({R.id.fan_filter_img})
    public void onSubmit(View view) {
        if (view.getId() == R.id.fan_filter_img) {
            if (isLogin) {
                startActivity(new Intent(getContext(), FiltersActivity.class));
            } else {
                Global.showAlertDialog(getContext(), true, "Warning", "You have to register as a Fan or Player to view Filters", "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((FanHomeActivity) getActivity()).callLoginScreen();
                    }
                }, "", null);
            }
        }
    }

    private void initView() {
        if (feedListHome != null && feedListHome.size() > 0) {
            setAdapter();
        } else {
            //shimerLay.startShimmerAnimation();
            feedListHome = new ArrayList<>();
            AddVideoTask myTask = new AddVideoTask();
            // myTask.executeAsync(this, response.getSuccess().getData());
        }
        fanHomeRR.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && imageView != null && !videoView.isPlaying())
                    imageView.setVisibility(View.VISIBLE);

                if (fanVideoAdapter.getItemCount() > 6 && isAllowLoad) {
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == fanVideoAdapter.getItemCount() - 1) {
                        AddMore();
                    }
                }


            }
        });
    }


    private void AddMore() {
        firstSize = feedListHome.size();
        isAllowLoad = false;
        MyTask myTask = new MyTask();
        myTask.executeAsync(this, fanVideoAdapter, feedListHome);
    }

    @Override
    public void onComplete() {
        progressFanHome.setVisibility(View.GONE);
    }

    @Override
    public void imageShowHideHomeFan(ImageView imageView) {
        this.imageView = imageView;

    }

    @Override
    public void imagePlayPauseVideoHomeFan(VideoView videoView) {
        this.videoView = videoView;
    }

    @Override
    public void myVideoPosition(int pos) {
        lastVideoPos = pos;
    }

    @Override
    public void onCompleteAddVideo(List<VideoData> list) {
        //  feedListHome.addAll(list);
        setAdapter();
    }

    private void setAdapter() {
        //shimerLay.stopShimmerAnimation();
        mainLayRl.setVisibility(View.VISIBLE);
        PagerSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        fanHomeRR.setOnFlingListener(null);
        linearSnapHelper.attachToRecyclerView(fanHomeRR);
        fanVideoAdapter = new FanVideoAdapter(getContext(), feedListHome, this);
        fanHomeRR.scrollToPosition(lastVideoPos);
        fanHomeRR.setLayoutManager(layoutManager);
        fanHomeRR.setAdapter(fanVideoAdapter);
    }
}
