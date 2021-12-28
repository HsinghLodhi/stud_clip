package com.studclips.app.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by HoangAnhTuan on 1/21/2018.
 */

public class Feed implements Serializable {
    private String video_url;
    private Bitmap Image_url;

    public Feed(String video_url, Bitmap image_url) {
        this.video_url = video_url;
        Image_url = image_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public Bitmap getImage_url() {
        return Image_url;
    }

    public void setImage_url(Bitmap image_url) {
        Image_url = image_url;
    }
}
