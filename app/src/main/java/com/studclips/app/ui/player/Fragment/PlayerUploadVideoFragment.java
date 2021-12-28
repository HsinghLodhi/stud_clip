package com.studclips.app.ui.player.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abedelazizshe.lightcompressorlibrary.CompressionListener;
import com.abedelazizshe.lightcompressorlibrary.VideoCompressor;
import com.abedelazizshe.lightcompressorlibrary.VideoQuality;
import com.gowtham.library.utils.CompressOption;
import com.gowtham.library.utils.TrimType;
import com.gowtham.library.utils.TrimVideo;
import com.gowtham.library.utils.TrimmerUtils;
import com.studclips.app.ApiCall;
import com.studclips.app.R;
import com.studclips.app.api.ApiCallback;
import com.studclips.app.model.AddVideoSuccess;
import com.studclips.app.util.BaseLoader;
import com.studclips.app.util.Global;
import com.studclips.app.util.SharedPreference;

import java.io.File;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.DIRECTORY_DOCUMENTS;

public class PlayerUploadVideoFragment extends Fragment implements ApiCallback.AddVideo {

    private String pathFinal;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    private static final int REQUEST_TAKE_VIDEO = 552;
    Unbinder unbinder;
    private Context context;
    @BindView(R.id.tvOpenGallery)
    TextView tvOpenGallery;
    @BindView(R.id.tvOpenCamera)
    TextView tvOpenCamera;
    @BindView(R.id.llOptionLay)
    ScrollView llOptionLay;
    @BindView(R.id.closeLay)
    LinearLayout closeLay;
    @BindView(R.id.rlVideoView)
    RelativeLayout rlVideoView;
    @BindView(R.id.videoView)
    VideoView videoView;
    @BindView(R.id.button_upload)
    Button button_upload;
    @BindView(R.id.etCaption)
    EditText etCaption;
    MediaController ctrl;
    static Uri videoUri;
    private static String videoSource = "";
    private View rootView;
    boolean isSubscribe;
    int video_trimming_duration;

    private static final String TAG = "PlayerUploadVideoFragme";
    private String fpath;
    int width;
    int height;
    private BaseLoader baseLoader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.player_upload_video_fragment_layout, container, false);
        context = getContext();
        baseLoader = new BaseLoader(context);
        unbinder = ButterKnife.bind(this, rootView);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
        layoutParams.width = getScreenWight();
        layoutParams.height = getScreenWight();
        videoView.setLayoutParams(layoutParams);
        ctrl = new MediaController(context);
        isSubscribe = SharedPreference.getBoolean(Global.isSubscribe, context);
        video_trimming_duration = isSubscribe ? 30 : 15;
        Log.e(TAG, "onCreateView: " + video_trimming_duration);
        if (videoUri != null && !videoUri.equals(Uri.EMPTY) && !TextUtils.isEmpty(videoSource)) {
            if (videoSource.equalsIgnoreCase("camera")) {
                setVideoUriCamera();
            } else {
                setVideoUriGallery();
            }
        }
        return rootView;
    }

    @OnClick({R.id.tvOpenGallery, R.id.tvOpenCamera, R.id.closeLay, R.id.cameraLay, R.id.galleryLay,
            R.id.button_upload})
    public void onSubmit(View view) {
        switch (view.getId()) {
            case R.id.tvOpenGallery:
            case R.id.galleryLay:
                openVideo();
                break;
            case R.id.tvOpenCamera:
            case R.id.cameraLay:
                dispatchTakeVideoIntent();
                break;
            case R.id.closeLay:
                OnCrossCall();
                break;
            case R.id.button_upload:
                if (Validate()) {
                    if (videoView != null) {
                        videoView.pause();
                    }
                    AddVideoApiCall();
                }
                break;
        }
    }

    private boolean Validate() {
        if (etCaption.getText().toString().trim().length() == 0) {
            Global.callBannerWithColor(etCaption, context.getResources().getString(R.string.please_enter_caption));
            return false;
        }
        if (videoUri == null || videoUri.equals(Uri.EMPTY) || TextUtils.isEmpty(videoSource)) {
            Global.callBannerWithColor(etCaption, context.getResources().getString(R.string.please_add_video));
            return false;
        }
        return true;
    }

    private void dispatchTakeVideoIntent() {
        if (!Global.CheckAllPermission(getContext())) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 222);
        } else {
            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, video_trimming_duration);
            takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            takeVideoIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
            }
        }
    }

    public void openVideo() {
        if (!Global.CheckAllPermission(getContext())) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 222);
        } else {
            try {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_TAKE_VIDEO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void AddVideoApiCall() {
        baseLoader.showLoader();
        //  File myFile = new File(pathFinal);
        //  String path = myFile.getAbsolutePath();

        File fileVideo;
        if (Build.VERSION_CODES.R > Build.VERSION.SDK_INT) {
           /* dir = new File(Environment.getExternalStorageDirectory().getPath()
                    + "//MyApp");*/
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/studclips_videos");
            if (!myDir.exists()) {
                myDir.mkdirs();
            }
            Random generator = new Random();
            int n = 10000;
            n = generator.nextInt(n);
            String fName = "Video_" + n + ".mp4";
            fileVideo = new File(myDir, fName);
        } else {
            File dir = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS).getPath()
                    + "/studclips_videos");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Random generator = new Random();
            int n = 10000;
            n = generator.nextInt(n);
            String fName = "Video_" + n + ".mp4";
            fileVideo = new File(dir, fName);
        }
        if (fileVideo.exists()) {
            fileVideo.delete();
        }

        Log.e(TAG, "AddVideoApiCall: " + new File(pathFinal).length() / 1024 + " \n" + pathFinal);
/*        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), myFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("video", myFile.getName(), requestFile);
        RequestBody caption = RequestBody.create(MediaType.parse("text/plain"), etCaption.getText().toString().trim());
        ApiCall.AddVideoApi(context, SharedPreference.getString(Global.token, context), body, caption, PlayerUploadVideoFragment.this);*/
        VideoCompressor.start(pathFinal, fileVideo.getPath(), new CompressionListener() {
            @Override
            public void onStart() {
                // Compression start
            }

            @Override
            public void onSuccess() {
                // On Compression success
                Log.e("camera Video size1:: ", "" + (fileVideo.length() / 1024));
                Log.e("camera Video path:: ", "" + fileVideo.getPath());
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileVideo);
                MultipartBody.Part body = MultipartBody.Part.createFormData("video", fileVideo.getName(), requestFile);
                RequestBody caption = RequestBody.create(MediaType.parse("text/plain"), etCaption.getText().toString().trim());
                ApiCall.AddVideoApi(context, SharedPreference.getString(Global.token, context), body, caption, PlayerUploadVideoFragment.this);
            }

            @Override
            public void onFailure(String failureMessage) {
                Global.callBannerWithColor(rootView, failureMessage);
                baseLoader.hideLoader();
            }

            @Override
            public void onProgress(float v) {
                // Update UI with progress value
                Log.e(TAG, "onProgress: " + v);
            }

            @Override
            public void onCancelled() {
                baseLoader.hideLoader();
                // On Cancelled
            }
        }, VideoQuality.HIGH, false, false);

    }

    private void OnCrossCall() {
        videoSource = "";
        videoUri = null;
        ctrl.setVisibility(View.GONE);
        videoView.setMediaController(ctrl);
        llOptionLay.setVisibility(View.VISIBLE);
        rlVideoView.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        ////////// camera code
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            videoUri = intent.getData();
            videoSource = "camera";
            setVideoUriCamera();
            //openTrimActivityForCamera(videoUri.toString());
        }
        ////////// trim code
        else if (requestCode == REQUEST_TAKE_VIDEO && resultCode == RESULT_OK) {
            if (intent.getData() != null) {
                int[] wAndh = TrimmerUtils.getVideoWidthHeight(getActivity(), Uri.parse(intent.getData().toString()));
                width = wAndh[0];
                height = wAndh[1];
                openTrimActivity(String.valueOf(intent.getData()));
            } else {
                Toast.makeText(context, "video uri is null", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == TrimVideo.VIDEO_TRIMMER_REQ_CODE && intent != null) {
            videoUri = Uri.parse(TrimVideo.getTrimmedVideoPath(intent));
            videoSource = "gallery";
            setVideoUriGallery();
        }
    }

    private void setVideoUriCamera() {
        setVideoView();
        pathFinal = Global.getRealPathFromVideoURI(videoUri, context);
    }

    private void setVideoView() {
        llOptionLay.setVisibility(View.GONE);
        rlVideoView.setVisibility(View.VISIBLE);
        videoView.setMediaController(new MediaController(context));
        videoView.setVideoURI(videoUri);
        videoView.seekTo(1);
    }

    private void setVideoUriGallery() {
        setVideoView();
        pathFinal = String.valueOf(videoUri);
        Log.e(TAG, "setImageUriGallery: " + new File(pathFinal).length() / 1024);
    }


    private int getScreenWight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    private void openTrimActivity(String data) {

        TrimVideo.activity(data)
                // .setCompressOption(new CompressOption(30, "1m", width, height))
                .setTrimType(TrimType.FIXED_DURATION)
                .setFixedDuration(video_trimming_duration)
                .start(this);

    }

    private void openTrimActivityForCamera(String data) {

        TrimVideo.activity(data)
                .setCompressOption(new CompressOption(30, "1m", width, height))
                .start(this);

    }

    @Override
    public void onSuccessAddVideo(AddVideoSuccess response) {
        baseLoader.hideLoader();
        Log.e("add video resposne ", "" + response.getMessage());
        etCaption.getText().clear();
        Toast.makeText(context, "Video added successfully", Toast.LENGTH_SHORT).show();
        // Global.callBannerWithColor(button_upload, "Video added successfully");
        OnCrossCall();
    }

    @Override
    public void onError(String error) {
        baseLoader.hideLoader();
        Global.callBannerWithColor(etCaption, error);
    }
}
