package com.studclips.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.studclips.app.R;
import com.studclips.app.model.Feed;
import com.studclips.app.model.VideoData;
import com.studclips.app.ui.player.Fragment.PlayerHomeFragment;
import com.studclips.app.ui.player.Fragment.PlayerTopTenFragment;
import com.studclips.app.util.BaseLoader;
import com.studclips.app.util.Global;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PlayerVideoAdapter extends RecyclerView.Adapter<PlayerVideoAdapter.myHodler> {
    private Context context;
    List<VideoData> feedList;
    ImageShowHome imageShow;
    boolean IsRatingChanged;
    private static final String TAG = "MyAdapterVideo";


    public PlayerVideoAdapter(Context context, List<VideoData> feedList, ImageShowHome imageShow) {
        this.context = context;
        this.feedList = feedList;
        this.imageShow = imageShow;
    }

    @NonNull
    @Override
    public myHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myHodler(LayoutInflater.from(context).inflate(R.layout.player_home_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull myHodler holder, int position) {
        holder.setIsRecyclable(false);
        VideoData data = feedList.get(position);
        holder.setVideoData(feedList.get(position), imageShow, context);
        imageShow.myVideoPos(position);
        holder.progressBar.setVisibility(View.VISIBLE);
        IsRatingChanged = true;

        Glide.with(context).load(data.getProfile_photo()).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                holder.img_profile_pic.setImageDrawable(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                holder.img_profile_pic.setImageResource(R.drawable.ic_profile);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                holder.img_profile_pic.setImageResource(R.drawable.ic_profile);
            }
        });


        String viewCount, likeCount;
        viewCount = String.valueOf(data.getViews()).concat(data.getViews() == 1 ? "\nView" : "\nViews");
        likeCount = String.valueOf(data.getLikes()).concat(data.getLikes() == 1 ? " Like" : " Likes");
        holder.tvViewsCount.setText(viewCount);
        holder.tv_likes_count.setText(likeCount);
        // Convert timestamp to long time
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = df.parse(data.getUploadedDatetime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = d.getTime();
        long cTime = new Date().getTime();
        long difference = cTime - time;
        int days = (int) (difference / (1000 * 60 * 60 * 24));
        int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
        int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
        hours = (hours < 0 ? -hours : hours);
        Log.e("======= Hours", " :: " + hours);
        holder.tvVideoTime.setText(hours + " hrs");

        holder.tvVideoCaption.setText(data.getCaption());
        if (!TextUtils.isEmpty(data.getFirstName()) || !TextUtils.isEmpty(data.getLastName())) {
            holder.tvName.setText(data.getFirstName().concat(" " + data.getLastName()));
        }
        holder.rtbProductRating.setRating(data.getRatings());

        holder.rtbProductRating.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating) {
                if (IsRatingChanged) {
                    imageShow.OnRatingVideo(position, (int) rating, holder.rtbProductRating, data.getVideoId());
                }
                IsRatingChanged = true;
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.e(TAG, "getItemCount: " + feedList.size());
        return feedList.size();
    }

    public static class myHodler extends RecyclerView.ViewHolder {
        VideoView videoView;
        ImageView likeImgPlayerHome;
        RelativeLayout rootLayoutRelative;
        LinearLayout LikesLay;
        MediaPlayer mediaPlayer;
        ScaleRatingBar rtbProductRating;
        TextView tvVideoCaption, tvName, tv_likes_count, tvViewsCount, tvVideoTime;
        ImageView img_profile_pic;
        ProgressBar progressBar;
        boolean isMute = false;

        public myHodler(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.home_video);
            tvName = itemView.findViewById(R.id.tvName);
            tv_likes_count = itemView.findViewById(R.id.tv_likes_count);
            tvViewsCount = itemView.findViewById(R.id.tvViewsCount);
            tvVideoTime = itemView.findViewById(R.id.tvVideoTime);
            tvVideoCaption = itemView.findViewById(R.id.tvVideoCaption);
            img_profile_pic = itemView.findViewById(R.id.img_profile_pic);
            // image = itemView.findViewById(R.id.home_image);
            progressBar = itemView.findViewById(R.id.progressBar);
            likeImgPlayerHome = itemView.findViewById(R.id.likeImgPlayerHome);
            rootLayoutRelative = itemView.findViewById(R.id.home_root);
            LikesLay = itemView.findViewById(R.id.LikesLay);
            rtbProductRating = itemView.findViewById(R.id.rtbProductRating);
        }

        void setVideoData(VideoData data, ImageShowHome imageClick, Context context) {
            mediaPlayer = new MediaPlayer();
            // image.setImageBitmap(data.getVideoImageBitmap());
            //videoView.setVideoPath(data.getVideo());

            try {
                // Get the URL from String VideoURL
                Uri mVideo = Uri.parse(data.getVideo());
                videoView.setVideoURI(mVideo);

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();

            }

            videoView.requestFocus();

            videoView.setOnCompletionListener(mp -> {
                imageClick.imagePlayPauseVideoHome(videoView);
                imageClick.imageShowHideHome(progressBar);
                videoView.start();
            });

            videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    Log.e(TAG, "onInfo: \n " + what + "  " + extra);
                    return false;
                }
            });


            videoView.setOnErrorListener((mp, what, extra) -> {
                Log.e(TAG, "onError: " + what + "   " + extra + "   " + mp);
                return false;
            });

            videoView.setOnPreparedListener(mp -> {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) videoView.getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                videoView.setLayoutParams(layoutParams);
                imageClick.imagePlayPauseVideoHome(videoView);
                imageClick.imageShowHideHome(progressBar);
                isMute = false;
                videoView.start();

                if (videoView.isPlaying()) {
                    progressBar.setVisibility(View.GONE);
                    imageClick.OnViewCall(getAdapterPosition(), data.getVideoId());
                    // image.setVisibility(View.GONE);
                }
            });

            rootLayoutRelative.setOnClickListener(v -> {
                if (videoView.isPlaying() && mediaPlayer != null && !isMute) {
                    mediaPlayer.setVolume(0f, 0f);
                    isMute = true;
                } else {
                    mediaPlayer.setVolume(1f, 1f);
                    isMute = false;
                }
            });


            if (data.getIsLiked() == 0) {
                likeImgPlayerHome.setImageResource(R.drawable.like_unfilled);
            } else {
                likeImgPlayerHome.setImageResource(R.drawable.like_filled_img);
            }

            LikesLay.setOnClickListener(view -> {
                if (likeImgPlayerHome.getDrawable().getConstantState() == ResourcesCompat.getDrawable(context.getResources(), R.drawable.like_unfilled, null).getConstantState()) {
                    imageClick.OnLikeUnlikeVideo(getAdapterPosition(), 1, data.getVideoId(), likeImgPlayerHome, tv_likes_count);
                    // likeImgPlayerHome.setImageResource(R.drawable.like_filled_img);
                } else {
                    imageClick.OnLikeUnlikeVideo(getAdapterPosition(), 0, data.getVideoId(), likeImgPlayerHome, tv_likes_count);
                    //  likeImgPlayerHome.setImageResource(R.drawable.like_unfilled);
                }
            });
        }
    }

    public interface ImageShowHome {
        void imageShowHideHome(ProgressBar progressBar);

        void imagePlayPauseVideoHome(VideoView videoView);

        void myVideoPos(int pos);

        void OnLikeUnlikeVideo(int pos, int status, int videoId, ImageView imageView, TextView textView);

        void OnRatingVideo(int pos, int rating, ScaleRatingBar ratingBar, int videoId);

        void OnViewCall(int pos, int videoId);

    }

    public void IsLikeShow(ImageView imageView, int status, int pos, TextView textView) {
        if (status == 0) {
            imageView.setImageResource(R.drawable.like_unfilled);
            if (feedList.get(pos).getLikes() > 0) {
                feedList.get(pos).setLikes(feedList.get(pos).getLikes() - 1);
            }
        } else {
            imageView.setImageResource(R.drawable.like_filled_img);
            feedList.get(pos).setLikes(feedList.get(pos).getLikes() + 1);
        }
        feedList.get(pos).setIsLiked(status);
        textView.setText(String.valueOf(feedList.get(pos).getLikes()).concat(feedList.get(pos).getLikes() == 1 ? " Like" : " Likes"));
    }

    public void IsOnSuccessRating(int pos, int rating) {
        feedList.get(pos).setRatings(rating);
        IsRatingChanged = true;
    }

    public void IsOnErrorRating(ScaleRatingBar ratingBar, int pos) {
        IsRatingChanged = false;
        ratingBar.setRating(feedList.get(pos).getRatings());
    }
}
