package com.studclips.app.util;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import android.os.Looper;

import com.studclips.app.model.Feed;
import com.studclips.app.model.VideoData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddVideoTask {
    private final Executor executor = Executors.newSingleThreadExecutor(); // change according to your requirements
    private final Handler handler = new Handler(Looper.getMainLooper());

    public interface Callback<R> {
        void onCompleteAddVideo(List<VideoData> feedListHome);
    }




    public <R> void executeAsync(Callback<R> callback, List<VideoData> data) {
        executor.execute(() -> {
            try {
                for (int i = 0; i < data.size(); i++) {
                    String videoUrl = data.get(i).getVideo();
                   // Bitmap bitmap = getBitmap(videoUrl);
                    //data.get(i).setVideoImageBitmap(bitmap);
                }
/*
                List<Feed> feedList;
                 Feed model1 = new Feed("https://assets.mixkit.co/videos/preview/mixkit-waves-in-the-water-1164-large.mp4", getBitmap("https://assets.mixkit.co/videos/preview/mixkit-waves-in-the-water-1164-large.mp4"));
                 Feed model2 = new Feed("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4", getBitmap("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"));
                 Feed models = new Feed("https://assets.mixkit.co/videos/preview/mixkit-winter-fashion-cold-looking-woman-concept-video-39874-large.mp4", getBitmap("https://assets.mixkit.co/videos/preview/mixkit-winter-fashion-cold-looking-woman-concept-video-39874-large.mp4"));
                 Feed model3 = new Feed("https://assets.mixkit.co/videos/preview/mixkit-photographer-working-outside-in-a-desert-34406-large.mp4", getBitmap("https://assets.mixkit.co/videos/preview/mixkit-photographer-working-outside-in-a-desert-34406-large.mp4"));
                 Feed model4 = new Feed("https://assets.mixkit.co/videos/preview/mixkit-tree-with-yellow-flowers-1173-large.mp4", getBitmap("https://assets.mixkit.co/videos/preview/mixkit-tree-with-yellow-flowers-1173-large.mp4"));
                 Feed model6 = new Feed("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4", getBitmap("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4"));
                 Feed model5 = new Feed("https://assets.mixkit.co/videos/preview/mixkit-mother-with-her-little-daughter-eating-a-marshmallow-in-nature-39764-large.mp4", getBitmap("https://assets.mixkit.co/videos/preview/mixkit-mother-with-her-little-daughter-eating-a-marshmallow-in-nature-39764-large.mp4"));
                 Feed model7 = new Feed("https://assets.mixkit.co/videos/preview/mixkit-the-spheres-of-a-christmas-tree-2720-large.mp4", getBitmap("https://assets.mixkit.co/videos/preview/mixkit-the-spheres-of-a-christmas-tree-2720-large.mp4"));
                Feed model8 = new Feed("https://assets.mixkit.co/videos/preview/mixkit-very-excited-little-girl-opening-a-christmas-gift-with-her-39744-large.mp4", getBitmap("https://assets.mixkit.co/videos/preview/mixkit-very-excited-little-girl-opening-a-christmas-gift-with-her-39744-large.mp4"));
                Feed model9 = new Feed("https://assets.mixkit.co/videos/preview/mixkit-woman-running-above-the-camera-on-a-running-track-32807-large.mp4", getBitmap("https://assets.mixkit.co/videos/preview/mixkit-woman-running-above-the-camera-on-a-running-track-32807-large.mp4"));
                feedList = new ArrayList<>();
                feedList.add(model1);
                feedList.add(model2);
                feedList.add(models);
                feedList.add(model3);
                feedList.add(model4);
                feedList.add(model5);
                feedList.add(model8);
                feedList.add(model9);
                feedList.add(model8);
                feedList.add(model9);

*/
                handler.post(() -> callback.onCompleteAddVideo(data));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Bitmap getBitmap(String videoPath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(videoPath, new HashMap<>());
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }
}
