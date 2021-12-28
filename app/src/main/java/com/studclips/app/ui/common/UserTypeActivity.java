package com.studclips.app.ui.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.studclips.app.BaseActivity;
import com.studclips.app.R;
import com.studclips.app.ui.player.SignUpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserTypeActivity extends BaseActivity {

    @BindView(R.id.playerLay)
    RelativeLayout playerLay;
    @BindView(R.id.FanLay)
    RelativeLayout FanLay;
    @BindView(R.id.button_continue)
    Button button_continue;
    private Intent intent ;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        ButterKnife.bind(this);
        intent = new Intent(this, SignUpActivity.class);
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_user_type;
    }

    @OnClick({R.id.playerLay, R.id.FanLay, R.id.ic_back_img, R.id.button_continue})
    public void onSubmit(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.playerLay:
                playerLay.setBackgroundResource(R.drawable.ic_player_active);
                FanLay.setBackgroundResource(R.drawable.ic_fan_deactive);
                button_continue.setAlpha(1);
                button_continue.setEnabled(true);
                intent.putExtra("isFan", false);
                break;
            case R.id.FanLay:
                FanLay.setBackgroundResource(R.drawable.ic_fan_active);
                playerLay.setBackgroundResource(R.drawable.ic_player_deactive);
                button_continue.setAlpha(1);
                button_continue.setEnabled(true);
                intent.putExtra("isFan", true);// replace from true for fan flow

                break;
            case R.id.ic_back_img:
                onBackPressed();
                break;
            case R.id.button_continue:
                startActivity(intent);
                break;
        }
    }

}