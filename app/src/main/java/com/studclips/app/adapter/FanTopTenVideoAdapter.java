package com.studclips.app.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.studclips.app.R;
import com.studclips.app.model.Feed;
import com.studclips.app.ui.fan.Fragment.FanTopTenFragment;
import com.studclips.app.ui.player.Fragment.PlayerTopTenFragment;

import java.util.List;

public class FanTopTenVideoAdapter extends RecyclerView.Adapter<FanTopTenVideoAdapter.myHodler> {
    private Context context;
    List<Feed> feedList;
    ImageShow imageShow;
    private static final String TAG = "MyAdapterVideo";

    public FanTopTenVideoAdapter(Context context, List<Feed> feedList, ImageShow imageShow) {
        this.context = context;
        this.feedList = feedList;
        this.imageShow = imageShow;
    }

    @NonNull
    @Override
    public myHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myHodler(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull myHodler holder, int position) {
        imageShow.myVideoPosition(position);
        holder.image.setVisibility(View.VISIBLE);
        holder.setVideoData(feedList.get(position), imageShow, context);
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public static class myHodler extends RecyclerView.ViewHolder {
        int stopPosition; //stopPosition is an int
        VideoView videoView;
        ImageView image, likeImgTopTen;
        RelativeLayout rootLayoutRelative;
        LinearLayout LikesLayTopTen;
        MediaPlayer mediaPlayer;
        boolean isMute = false;

        public myHodler(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.video);
            image = itemView.findViewById(R.id.image);
            likeImgTopTen = itemView.findViewById(R.id.likeImgTopTen);
            rootLayoutRelative = itemView.findViewById(R.id.root);
            LikesLayTopTen = itemView.findViewById(R.id.LikesLayTopTen);
        }

        void setVideoData(Feed data, ImageShow imageClick, Context context) {
            mediaPlayer = new MediaPlayer();
            videoView.setVideoPath(data.getVideo_url());
            image.setImageBitmap(data.getImage_url());

            //Glide.with(image).load(path + data.getImage_url()).into(image);

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    imageClick.imagePlayPauseVideo(videoView);
                    imageClick.imageShowHide(image);
                    Log.e(TAG, "onCompletion:is playing " + videoView.isPlaying());
                    mp.start();
                }
            });


            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer = mp;
                    isMute = false;
                    /*if (isMute) {
                        mediaPlayer.setVolume(0f, 0f);
                    } else {
                        mediaPlayer.setVolume(1f, 1f);
                    } */
                    imageClick.imagePlayPauseVideo(videoView);
                    imageClick.imageShowHide(image);
                    Log.e(TAG, "onPrepared: isplaying  " + videoView.isPlaying());
                    Log.e(TAG, "onPrepared:if is playing " + videoView.isPlaying());
                    image.setVisibility(View.GONE);
                    Log.e("Image visi< ", "" + image.getVisibility());
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) videoView.getLayoutParams();
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    videoView.setLayoutParams(layoutParams);
                    mp.start();
                    if (videoView.isPlaying()) {
                        image.setVisibility(View.GONE);
                    }
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

            LikesLayTopTen.setOnClickListener(view -> {
                if (likeImgTopTen.getDrawable().getConstantState() == ResourcesCompat.getDrawable(context.getResources(), R.drawable.like_unfilled, null).getConstantState()) {
                    likeImgTopTen.setImageResource(R.drawable.like_filled_img);
                } else {
                    likeImgTopTen.setImageResource(R.drawable.like_unfilled);
                }
            });

        }
    }

    public interface ImageShow {
        void imageShowHide(ImageView imageView);

        void imagePlayPauseVideo(VideoView videoView);

        void myVideoPosition(int pos);

    }
}
