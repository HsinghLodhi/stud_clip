package com.studclips.app;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import android.os.Looper;

import androidx.recyclerview.widget.RecyclerView;

import com.studclips.app.adapter.TopTenVideoAdapter;
import com.studclips.app.model.Feed;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MyTask {
    private final Executor executor = Executors.newSingleThreadExecutor(); // change according to your requirements
    private final Handler handler = new Handler(Looper.getMainLooper());

    public interface Callback<R> {
        void onComplete();
    }

    int firstSize, lastSize;

    public <R> void executeAsync(Callback<R> callback, RecyclerView.Adapter videoAdapter, List<Feed> feedList) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                  //  Feed model1 = new Feed("https://assets.mixkit.co/videos/preview/mixkit-hands-holding-a-smart-watch-with-the-stopwatch-running-32808-large.mp4", MyTask.this.getBitmap("https://assets.mixkit.co/videos/preview/mixkit-hands-holding-a-smart-watch-with-the-stopwatch-running-32808-large.mp4"));
                  //  Feed models = new Feed("https://assets.mixkit.co/videos/preview/mixkit-silhouette-of-urban-dancer-in-smoke-33898-large.mp4", MyTask.this.getBitmap("https://assets.mixkit.co/videos/preview/mixkit-silhouette-of-urban-dancer-in-smoke-33898-large.mp4"));
                  //  Feed model2 = new Feed("https://assets.mixkit.co/videos/preview/mixkit-photographer-working-outside-in-a-desert-34406-large.mp4", MyTask.this.getBitmap("https://assets.mixkit.co/videos/preview/mixkit-photographer-working-outside-in-a-desert-34406-large.mp4"));
                   // Feed model3 = new Feed("https://assets.mixkit.co/videos/preview/mixkit-synthetic-fabric-flowers-close-view-34501-large.mp4", MyTask.this.getBitmap("https://assets.mixkit.co/videos/preview/mixkit-synthetic-fabric-flowers-close-view-34501-large.mp4"));
                   // Feed model4 = new Feed("https://assets.mixkit.co/videos/preview/mixkit-rolling-on-roller-skates-on-the-ground-of-a-parking-34551-large.mp4", MyTask.this.getBitmap("https://assets.mixkit.co/videos/preview/mixkit-rolling-on-roller-skates-on-the-ground-of-a-parking-34551-large.mp4"));
                  //  Feed model5 = new Feed("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4", MyTask.this.getBitmap("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4"));
                  //  Feed model6 = new Feed("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4", MyTask.this.getBitmap("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4"));

                    Feed model8 = new Feed("https://assets.mixkit.co/videos/preview/mixkit-very-excited-little-girl-opening-a-christmas-gift-with-her-39744-large.mp4", getBitmap("https://assets.mixkit.co/videos/preview/mixkit-very-excited-little-girl-opening-a-christmas-gift-with-her-39744-large.mp4"));
                    Feed model9 = new Feed("https://assets.mixkit.co/videos/preview/mixkit-woman-running-above-the-camera-on-a-running-track-32807-large.mp4", getBitmap("https://assets.mixkit.co/videos/preview/mixkit-woman-running-above-the-camera-on-a-running-track-32807-large.mp4"));

                    feedList.add(model8);
                    feedList.add(model9);
                    feedList.add(model8);
                    feedList.add(model9);

/*                    feedList.add(model1);
                    feedList.add(models);
                    feedList.add(model2);
                    feedList.add(model3);
                    feedList.add(model4);
                    feedList.add(model5);
                    feedList.add(model6);*/
                    handler.post(() -> {
                        lastSize = feedList.size();
                        videoAdapter.notifyItemRangeChanged(firstSize, lastSize);
                        callback.onComplete();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

