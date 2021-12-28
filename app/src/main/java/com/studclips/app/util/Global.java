package com.studclips.app.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.shasin.notificationbanner.Banner;
import com.studclips.app.R;
import com.studclips.app.model.SignUpData;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Global {
    public static final String SearchSchoolnameKey = "SchoolName";
    public static final int SearchSchoolKey = 9898;
    public static final int GalleryRequestCode = 1;
    public static final int CameraRequestCode = 2;
    public static final String playerType = "playerType";
    public static final String isLogin = "isLogin";
    public static final String fan = "2";
    public static final String player = "3";
    public static final String isSubscribe = "isSubscribe";
    public static String DEVICE_TYPE = "Android";
    public static String SignupData = "SignupData";
    public static String password = "password";
    public static String token = "token";
    public static String isSkipProfile = "isSkipProfile";
    public static String rememberMe = "rememberMe";
    public static String email = "email";


    /**
     * medthod to check all edittexts is empty ot not.
     *
     * @param abs
     * @return boolean true for EditTexts is empty rather false
     */
    public static boolean isEditTextsEmpty(EditText... abs) {
        for (EditText edit : abs) {
            if (TextUtils.isEmpty(edit.getText().toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean CheckAllPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    /**
     * method is used to show Alert Dialog
     *
     * @return instance of AlertDialog.builder
     */
    public static AlertDialog.Builder showAlertDialog(Context context, boolean isCancelable, String title, String msg, String txtPositiveBtn, DialogInterface.OnClickListener listenerPositive, String txtNegativeBtn, DialogInterface.OnClickListener listenerNegative) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Dialog_Alert);
        dialog.setCancelable(isCancelable);
        if (!TextUtils.isEmpty(title)) dialog.setTitle(title);
        if (!TextUtils.isEmpty(msg)) dialog.setMessage(msg);
        if (!TextUtils.isEmpty(txtPositiveBtn))
            dialog.setPositiveButton(txtPositiveBtn, listenerPositive);
        if (!TextUtils.isEmpty(txtNegativeBtn))
            dialog.setNegativeButton(txtNegativeBtn, listenerNegative);
        dialog.show();
        return dialog;
    }

    public static void startActivity(Context context, Class classname) {
        context.startActivity(new Intent(context, classname));
    }

    public static void showGalleryAndCameraAlert(Context context, DialogInterface.OnClickListener listenerGallery, DialogInterface.OnClickListener listenerCamera) {
        showAlertDialog(context, true, "", "Select image from?", "Gallery", listenerGallery, "Camera", listenerCamera);
    }

    public static void openCamera(Activity activity) {
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(camera_intent, CameraRequestCode);
    }

    public static void openGallery(Activity activity) {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(pickPhoto, GalleryRequestCode);//one can be replaced with any action code
    }

    public static void callBannerWithColor(View rootView, String text) {
        TextView textView;
        Banner.make(rootView, ((Activity) rootView.getContext()), Banner.TOP, R.layout.error_banner_layout);
        textView = Banner.getInstance().getBannerView().findViewById(R.id.tvErrorMessage);
        textView.setText(text);
        Banner.getInstance().setCustomAnimationStyle(R.style.NotificationAnimationTop);
        Banner.getInstance().setDuration(2000);
        Banner.getInstance().show();
    }


    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    public static String getRealPathFromURI(Uri uri, Context context) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public static String getRealPathFromVideoURI(Uri uri, Context context) {
        String[] proj = { MediaStore.Video.VideoColumns.DATA };
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA);
        return cursor.getString(idx);
    }


    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting() && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected();
    }

    public static boolean IsValidUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            return URLUtil.isValidUrl(urlString) && Patterns.WEB_URL.matcher(urlString).matches();
        } catch (MalformedURLException ignored) {
        }
        return false;
    }

    public static SignUpData getSignUpData(Context context) {
        String signUpData = SharedPreference.getString(Global.SignupData, context);
        Gson gson = new Gson();
        return gson.fromJson(signUpData, SignUpData.class);
    }

    public static void setSignUpData(Context context, SignUpData signUpData) {
        Gson gson = new Gson();
        String signUpStr = gson.toJson(signUpData);
        SharedPreference.saveString(Global.SignupData, signUpStr, context);
    }

}
