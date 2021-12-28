package com.studclips.app.ui.player.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;


import com.studclips.app.ApiCall;
import com.studclips.app.MyTask;
import com.studclips.app.R;
import com.studclips.app.adapter.PlayerVideoAdapter;
import com.studclips.app.api.ApiCallback;
import com.studclips.app.model.Feed;
import com.studclips.app.model.GetVideosResponse;
import com.studclips.app.model.LikeUnlikeResposne;
import com.studclips.app.model.VideoData;
import com.studclips.app.ui.common.FiltersActivity;
import com.studclips.app.util.AddVideoTask;
import com.studclips.app.util.BaseLoader;
import com.studclips.app.util.Global;
import com.studclips.app.util.SnapHelperOneByOne;
import com.willy.ratingbar.ScaleRatingBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.transform.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayerHomeFragment extends Fragment implements PlayerVideoAdapter.ImageShowHome, MyTask.Callback<R>, AddVideoTask.Callback<R>, ApiCallback.GetVideos, ApiCallback.LikeUnlikeVideos, ApiCallback.RatingVideos, ApiCallback.ViewCall {
    @BindView(R.id.playerHomeRR)
    RecyclerView playerHomeRR;
    @BindView(R.id.root_home)
    RelativeLayout root_home;
    @BindView(R.id.mainLayRl)
    RelativeLayout mainLayRl;
    ProgressBar progressPlayerHome;
    static List<VideoData> feedListHome = new ArrayList<>();
    ProgressBar homeProgressBar;
    VideoView videoView;
    LinearLayoutManager layoutManager;
    static int lastVideoPos;

    boolean isAllowLoad = true;
    private static final String TAG = "PlayerHomeFragment";
    int firstSize, lastSize;
    private PlayerVideoAdapter playerVideoAdapter;
    private Context context;
    private BaseLoader baseLoader;
    private int offset = 0;
    private int limit = 10;
    private int game_id;
    private String sort_by = "";
    private String search_name = "";
    private final int filter_req_code = 101;
    private int selectedPos;
    private int likestatus;
    private int givenRating;
    ImageView likeImgPlayerHome;
    TextView like_count_textview;
    ScaleRatingBar scaleRatingBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.player_home_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        baseLoader = new BaseLoader(context);
        layoutManager = new LinearLayoutManager(context);
        initView();
        return view;
    }

    @OnClick({R.id.player_filter_img})
    public void onSubmit(View view) {
        if (view.getId() == R.id.player_filter_img) {
            startActivityForResult(new Intent(getContext(), FiltersActivity.class), filter_req_code);
        }
    }

    private void initView() {
        if (feedListHome != null && feedListHome.size() > 0) {
            setAdapter();
        } else {
            CallGetVideos();
        }

        playerHomeRR.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && homeProgressBar != null && !videoView.isPlaying())
                    homeProgressBar.setVisibility(View.VISIBLE);
                if (playerVideoAdapter.getItemCount() > 9) {
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == playerVideoAdapter.getItemCount() - 1) {
                        AddMore();
                    }
                }
            }
        });
    }

    private void CallGetVideos() {

        baseLoader.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put("offset", offset);
        map.put("limit", limit);
        map.put("game_id", game_id);
        map.put("sort_by", sort_by);
        map.put("search_name", search_name);
        ApiCall.getHomeVideos(context, map, this);
    }

    private void AddMore() {
        firstSize = feedListHome.size();
        //  progressPlayerHome.setVisibility(View.VISIBLE);
        offset++;
        CallGetVideos();
        MyTask myTask = new MyTask();
        //   myTask.executeAsync(this, playerVideoAdapter, feedListHome);
    }

    @Override
    public void onComplete() {
        //  progressPlayerHome.setVisibility(View.GONE);
    }

    @Override
    public void onCompleteAddVideo(List<VideoData> feedListHomess) {
        baseLoader.hideLoader();
        feedListHome.addAll(feedListHomess);
        setAdapter();
        // progressPlayerHome.setVisibility(View.GONE);
    }

    void setAdapter() {

        if (feedListHome != null && feedListHome.size() > 9) {
            playerVideoAdapter.notifyDataSetChanged();
        } else {
            Log.e(TAG, "setAdapter: " + Calendar.getInstance().getTime());
            PagerSnapHelper linearSnapHelper = new SnapHelperOneByOne();
            playerHomeRR.setOnFlingListener(null);
            linearSnapHelper.attachToRecyclerView(playerHomeRR);
            playerVideoAdapter = new PlayerVideoAdapter(getContext(), feedListHome, this);
            playerHomeRR.setLayoutManager(layoutManager);
            playerHomeRR.scrollToPosition(lastVideoPos);
            playerHomeRR.setAdapter(playerVideoAdapter);
        }
    }

    @Override
    public void onSuccessGetVideo(GetVideosResponse response) {
        baseLoader.hideLoader();
        if (response.getSuccess() != null && response.getSuccess().getData() != null && response.getSuccess().getData().size() > 0) {
           /* AddVideoTask myTask = new AddVideoTask();
            myTask.executeAsync(this, response.getSuccess().getData());*/

            feedListHome.addAll(response.getSuccess().getData());
            for (int i = 0; i < feedListHome.size(); i++) {
                if (!feedListHome.get(i).getVideo().contains(".mp4")) {
                    feedListHome.remove(i);
                }
            }
            setAdapter();
        }
    }

    @Override
    public void onError(String error) {
        baseLoader.hideLoader();
        Global.callBannerWithColor(root_home, error);
        if (scaleRatingBar != null) {
            playerVideoAdapter.IsOnErrorRating(scaleRatingBar, selectedPos);
        }
    }


    @Override
    public void imageShowHideHome(ProgressBar progressBar) {
        this.homeProgressBar = progressBar;
    }

    @Override
    public void imagePlayPauseVideoHome(VideoView videoView) {
        this.videoView = videoView;
    }

    @Override
    public void myVideoPos(int pos) {
        lastVideoPos = pos;
    }

    @Override
    public void OnLikeUnlikeVideo(int pos, int status, int videoId, ImageView imageView, TextView textView) {
        this.selectedPos = pos;
        this.likestatus = status;
        this.likeImgPlayerHome = imageView;
        this.like_count_textview = textView;
        baseLoader.showLoader();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("video_id", videoId);
        map.put("status", status);
        ApiCall.LikeUnlikeVideos(context, map, this);
    }

    @Override
    public void OnRatingVideo(int pos, int rating, ScaleRatingBar ratingBar, int videoId) {
        this.selectedPos = pos;
        this.scaleRatingBar = ratingBar;
        this.givenRating = rating;
        baseLoader.showLoader();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("video_id", videoId);
        map.put("rating", rating);
        ApiCall.RatingVideos(context, map, this);
    }

    @Override
    public void OnViewCall(int pos, int videoId) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("video_id", videoId);
        ApiCall.ViewCall(context, map, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: " + requestCode);
        if (requestCode == filter_req_code && resultCode == Activity.RESULT_OK) {
            game_id = data.getIntExtra("gameId", 0);
            sort_by = data.getStringExtra("sortBy");
            search_name = data.getStringExtra("search_by_name");
            if (sort_by != null && sort_by.equalsIgnoreCase("Please select sort by")) {
                sort_by = "";
            }
            Log.e(TAG, "onActivityResult: " + sort_by + " " + search_name + " " + game_id);
            offset = 0;
            feedListHome = new ArrayList<>();
            CallGetVideos();
        }
    }

    @Override
    public void onSuccessLikeUnlikeVideo(LikeUnlikeResposne response) {
        baseLoader.hideLoader();
        if (feedListHome != null && feedListHome.size() > 0) {
           /* feedListHome.get(selectedPos).setIsLiked(likestatus);
            playerVideoAdapter.notifyItemChanged(selectedPos);*/
            playerVideoAdapter.IsLikeShow(likeImgPlayerHome, likestatus, selectedPos, like_count_textview);
        }
    }

    @Override
    public void onRatingVideo(LikeUnlikeResposne response) {
        baseLoader.hideLoader();
        Log.e(TAG, "onRatingVideo: " + response.getMessage());
        playerVideoAdapter.IsOnSuccessRating(selectedPos, givenRating);
    }

    @Override
    public void onSuccessView(LikeUnlikeResposne response) {
        Log.e(TAG, "onSuccessView: " + response.getMessage());
    }
}
