package com.studclips.app.ui.player.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.studclips.app.adapter.TopTenVideoAdapter;
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

public class PlayerTopTenFragment extends Fragment implements TopTenVideoAdapter.ImageShow, AddVideoTask.Callback<R> {
    @BindView(R.id.recyclerViewVideoList)
    RecyclerView recyclerViewVideoList;
    @BindView(R.id.root_topten)
    RelativeLayout root_topten;
    @BindView(R.id.mainLayRoot)
    RelativeLayout mainLayRoot;
    static List<Feed> feedList = new ArrayList<>();
    TopTenVideoAdapter videoAdapter;
    ImageView imageView;
    VideoView videoView;
    LinearLayoutManager layoutManager;
    static int lastVideoPos;
    private static final String TAG = "PlayerTopTenFragment";
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.player_top_ten_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        layoutManager = new LinearLayoutManager(getContext());
        initView();
        return view;
    }

    @OnClick({R.id.top_ten_filter})
    public void onSubmit(View view) {
        if (view.getId() == R.id.top_ten_filter) {
            startActivity(new Intent(getContext(), FiltersActivity.class));
        }
    }

    private void initView() {
        initData();
    }

    private void initData() {
        if (feedList != null && feedList.size() > 0) {
            setAdapter();
        } else {
            AddVideoTask myTask = new AddVideoTask();
           // myTask.executeAsync(this, response.getSuccess().getData());
        }
        recyclerViewVideoList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
    public void imagePlayPauseVideo(android.widget.VideoView videoView) {
        this.videoView = videoView;
    }

    @Override
    public void myVideoPos(int pos) {
        lastVideoPos = pos;
    }

    @Override
    public void onCompleteAddVideo(List<VideoData> feedListHome) {
        //feedList.addAll(feedListHome);
        setAdapter();
    }

    private void setAdapter() {
        mainLayRoot.setVisibility(View.VISIBLE);
        recyclerViewVideoList.setOnFlingListener(null);
        PagerSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(recyclerViewVideoList);
        videoAdapter = new TopTenVideoAdapter(getContext(), feedList, this);
        recyclerViewVideoList.setLayoutManager(layoutManager);
        recyclerViewVideoList.scrollToPosition(lastVideoPos);
        recyclerViewVideoList.setAdapter(videoAdapter);
    }
}
