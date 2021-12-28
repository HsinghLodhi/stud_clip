package com.studclips.app.ui.fan.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;


import com.studclips.app.R;
import com.studclips.app.adapter.FanTopTenVideoAdapter;
import com.studclips.app.model.Feed;
import com.studclips.app.model.VideoData;
import com.studclips.app.ui.common.FiltersActivity;
import com.studclips.app.util.AddVideoTask;
import com.studclips.app.util.SnapHelperOneByOne;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FanTopTenFragment extends Fragment implements  FanTopTenVideoAdapter.ImageShow, AddVideoTask.Callback<R> {
    @BindView(R.id.fan_recyclerViewVideoList)
    RecyclerView fan_recyclerViewVideoList;
    @BindView(R.id.rlMain)
    RelativeLayout rlMain;
    static List<Feed> feedList = new ArrayList<>();
    FanTopTenVideoAdapter videoAdapter;
    ImageView imageView;
    VideoView videoView;
    LinearLayoutManager layoutManager;
    private static final String TAG = "PlayerTopTenFragment";
    static int lastVideoPos;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fan_top_ten_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        layoutManager = new LinearLayoutManager(getContext());
        initData();
        return view;
    }

    @OnClick({R.id.fan_top_ten_filter})
    public void onSubmit(View view) {
        if (view.getId() == R.id.fan_top_ten_filter) {
            startActivity(new Intent(getContext(), FiltersActivity.class));
        }
    }

    private void initData() {
        if (feedList != null && feedList.size() > 0) {
            setAdapter();
        } else {
            AddVideoTask myTask = new AddVideoTask();
           // myTask.executeAsync(this, response.getSuccess().getData());
        }

        fan_recyclerViewVideoList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && imageView != null && !videoView.isPlaying())
                    imageView.setVisibility(View.VISIBLE);
            }
        });

    }



    @Override
    public void imageShowHide(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void imagePlayPauseVideo(VideoView videoView) {
        this.videoView = videoView;
    }

    @Override
    public void myVideoPosition(int pos) {
        lastVideoPos = pos;
    }

    @Override
    public void onCompleteAddVideo(List<VideoData> feedListHome) {
    //    feedList.addAll(feedListHome);
        setAdapter();
    }

    private void setAdapter() {
        rlMain.setVisibility(View.VISIBLE);
        PagerSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(fan_recyclerViewVideoList);
        videoAdapter = new FanTopTenVideoAdapter(getContext(), feedList, this);
        fan_recyclerViewVideoList.setLayoutManager(layoutManager);
        fan_recyclerViewVideoList.scrollToPosition(lastVideoPos);
        fan_recyclerViewVideoList.setAdapter(videoAdapter);
    }

}
