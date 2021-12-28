package com.studclips.app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    Unbinder unbinder;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayout());
        unbinder = ButterKnife.bind(this);
        context = this;
    }

    protected abstract int getActivityLayout();
    protected abstract void onSubmit(View view);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public Context getContext() {
        return context;
    }
    public void showToast(String text){
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
    public void hideKeyboard(View view) {
        try {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception ignored) {
        }
    }
    public void startActivity(Class classname){
        startActivity(new Intent(this,classname));
    }

    /*public void adjustFontScale(Configuration configuration) {
        if (configuration.fontScale > 1.30) {
            Log.e(TAG, "fontScale=" + configuration.fontScale);
            configuration.fontScale = 1.30f;
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.fontScale * metrics.density;
            getBaseContext().getResources().updateConfiguration(configuration, metrics);
        }
    }*/
}
