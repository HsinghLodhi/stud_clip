package com.studclips.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.studclips.app.R;
import com.studclips.app.model.Feed;
import com.studclips.app.ui.common.SignInActivity;
import com.studclips.app.ui.fan.FanHomeActivity;
import com.studclips.app.ui.fan.Fragment.FanHomeFragment;
import com.studclips.app.util.Global;
import com.studclips.app.util.SharedPreference;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.List;

public class FanVideoAdapter extends RecyclerView.Adapter<FanVideoAdapter.myHolder> {
    private Context context;
    List<Feed> feedList;
    ImageShowHomeFan imageShow;
    private static final String TAG = "MyAdapterVideo";

    public FanVideoAdapter(Context context, List<Feed> feedList, ImageShowHomeFan imageShow) {
        this.context = context;
        this.feedList = feedList;
        this.imageShow = imageShow;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myHolder(LayoutInflater.from(context).inflate(R.layout.player_home_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        imageShow.myVideoPosition(position);
        holder.image.setVisibility(View.VISIBLE);
        holder.setVideoData(feedList.get(position), imageShow, context);
        holder.rtbProductRating.setIsIndicator(!SharedPreference.isLogin(context));

        holder.rlRattingBar.setOnClickListener(v -> {
            if (!SharedPreference.isLogin(context)) {
                ((FanHomeActivity) context).callLoginScreen();
            }
        });

        holder.LikesLay.setOnClickListener(view -> {
            if (SharedPreference.isLogin(context)) {
                if (holder.likeImgPlayerHome.getDrawable().getConstantState() == ResourcesCompat.getDrawable(context.getResources(), R.drawable.like_unfilled, null).getConstantState()) {
                    holder.likeImgPlayerHome.setImageResource(R.drawable.like_filled_img);
                } else {
                    holder.likeImgPlayerHome.setImageResource(R.drawable.like_unfilled);
                }
            } else {
                /////// user is not login need to go on login screen
                ((FanHomeActivity) context).callLoginScreen();
            }
        });
    }


    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public static class myHolder extends RecyclerView.ViewHolder {
        int stopPosition; //stopPosition is an int
        VideoView videoView;
        ImageView image, likeImgPlayerHome;
        RelativeLayout rootLayoutRelative, rlRattingBar;
        LinearLayout LikesLay;
        MediaPlayer mediaPlayer;
        ScaleRatingBar rtbProductRating;

        private boolean isMute = false;

        public myHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.home_video);
            image = itemView.findViewById(R.id.home_image);
            likeImgPlayerHome = itemView.findViewById(R.id.likeImgPlayerHome);
            rootLayoutRelative = itemView.findViewById(R.id.home_root);
            rlRattingBar = itemView.findViewById(R.id.rlRattingBar);
            LikesLay = itemView.findViewById(R.id.LikesLay);
            rtbProductRating = itemView.findViewById(R.id.rtbProductRating);
        }

        void setVideoData(Feed data, ImageShowHomeFan imageClick, Context context) {
            mediaPlayer = new MediaPlayer();

            videoView.setVideoPath(data.getVideo_url());

            image.setImageBitmap(data.getImage_url());

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    imageClick.imagePlayPauseVideoHomeFan(videoView);
                    imageClick.imageShowHideHomeFan(image);
                    Log.e(TAG, "onCompletion:is playing " + videoView.isPlaying());
                    mp.start();
                }
            });

            videoView.setOnPreparedListener(mp -> {
                mediaPlayer = mp;
                isMute = false;
               /* if (isMute) {
                    mediaPlayer.setVolume(0f, 0f);
                } else {
                    mediaPlayer.setVolume(1f, 1f);
                }*/
                imageClick.imagePlayPauseVideoHomeFan(videoView);
                imageClick.imageShowHideHomeFan(image);
                Log.e(TAG, "onPrepared: isplaying  " + videoView.isPlaying());
                Log.e(TAG, "onPrepared:if is playing " + videoView.isPlaying());
                image.setVisibility(View.GONE);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) videoView.getLayoutParams();
                // layoutParams.width = screenWight;
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                videoView.setLayoutParams(layoutParams);
                mp.start();
                if (videoView.isPlaying()) {
                    image.setVisibility(View.GONE);
                }
            });

            rootLayoutRelative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (videoView.isPlaying() && mediaPlayer != null && !isMute) {
                        mediaPlayer.setVolume(0f, 0f);
                        isMute = true;
                    } else {
                        mediaPlayer.setVolume(1f, 1f);
                        isMute = false;
                    }
                }
            });

        }

    }

    public interface ImageShowHomeFan {
        void imageShowHideHomeFan(ImageView imageView);

        void imagePlayPauseVideoHomeFan(VideoView videoView);

        void myVideoPosition(int pos);

    }
}
