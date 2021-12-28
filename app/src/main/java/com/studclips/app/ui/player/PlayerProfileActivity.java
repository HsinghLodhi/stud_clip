package com.studclips.app.ui.player;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.studclips.app.ApiCall;
import com.studclips.app.BaseActivity;
import com.studclips.app.R;
import com.studclips.app.api.ApiCallback;
import com.studclips.app.model.SignUpData;
import com.studclips.app.model.SignUpResponse;
import com.studclips.app.util.Global;
import com.studclips.app.util.SharedPreference;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class PlayerProfileActivity extends BaseActivity implements ApiCallback.UpdateProfilePlayer {
    private static final String TAG = "PlayerProfileActivity";
    @BindView(R.id.edit_search_school)
    EditText edit_search_school;
    @BindView(R.id.school_type_spn)
    Spinner school_type_spn;
    @BindView(R.id.rootLayout)
    LinearLayout rootLayout;
    /* @BindView(R.id.tv_search_school)
     TextView tv_search_school;*/
    @BindView(R.id.position_spn)
    EditText position_spn;
    @BindView(R.id.edit_weight)
    EditText edit_weight;
    @BindView(R.id.edit_height)
    EditText edit_height;
    @BindView(R.id.edit_class)
    EditText edit_class;
    @BindView(R.id.edit_link)
    EditText edit_link;
    @BindView(R.id.tv_skip)
    TextView tv_skip;
    @BindView(R.id.button_lets_start)
    Button button_lets_start;
    private boolean isFromUserProfile = false;
    private Context context;
    SignUpData signUpData;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_player_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        initViews();
        setUiData();
    }

    private void setUiData() {
        signUpData = Global.getSignUpData(context);
        if (signUpData != null) {
            if (!TextUtils.isEmpty(signUpData.getSchoolTeam())) {
                edit_search_school.setText(signUpData.getSchoolTeam());
            }
            if (!TextUtils.isEmpty(signUpData.get_class())) {
                edit_class.setText(signUpData.get_class());
            }
            if (!TextUtils.isEmpty(signUpData.getHeight())) {
                edit_height.setText(signUpData.getHeight());
            }
            if (!TextUtils.isEmpty(signUpData.getWeight())) {
                edit_weight.setText(signUpData.getWeight());
            }
            if (!TextUtils.isEmpty(signUpData.getPosition())) {
                position_spn.setText(signUpData.getPosition());
            }
            if (!TextUtils.isEmpty(signUpData.getHighlightedVideo())) {
                edit_link.setText(signUpData.getHighlightedVideo());
            }
        }

    }

    private void initViews() {
        if (getIntent().hasExtra("isFromUserProfile") && getIntent().getBooleanExtra("isFromUserProfile", false)) {
            tv_skip.setVisibility(View.GONE);
            button_lets_start.setText("Update Profile");
            isFromUserProfile = true;
        } else {
            tv_skip.setVisibility(View.VISIBLE);
            button_lets_start.setText(getString(R.string.let_s_start_btn_txt));
            isFromUserProfile = false;
        }
        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.position_array, R.layout.spinner_item_lay);
        school_type_spn.setAdapter(adapter);
        //school_team_spn.setAdapter(adapter);
        position_spn.setAdapter(adapter);
        */
    }


    @Override
    @OnClick({R.id.ic_back_img, R.id.button_lets_start, R.id.tv_skip/*, R.id.tv_search_school*/})
    protected void onSubmit(View view) {
        switch (view.getId()) {
            case R.id.ic_back_img:
                onBackPressed();
                break;
            case R.id.button_lets_start:
                if (TextUtils.isEmpty(edit_search_school.getText().toString())) {
                    Global.callBannerWithColor(view, "Please enter school.");
                } else if (TextUtils.isEmpty(edit_class.getText().toString())) {
                    Global.callBannerWithColor(view, "Please enter class.");
                } else if (TextUtils.isEmpty(edit_height.getText().toString())) {
                    Global.callBannerWithColor(view, "Please enter height.");
                } else if (TextUtils.isEmpty(edit_weight.getText().toString())) {
                    Global.callBannerWithColor(view, "Please enter weight.");
                } else if (TextUtils.isEmpty(position_spn.getText().toString().trim())) {
                    Global.callBannerWithColor(view, "Please enter position.");
                } else if (TextUtils.isEmpty(edit_link.getText().toString())) {
                    Global.callBannerWithColor(view, "Please enter video link.");
                } else if (!Global.IsValidUrl(edit_link.getText().toString().trim())) {
                    Global.callBannerWithColor(view, "Please enter valid video link.");
                } else {
                    if (!TextUtils.isEmpty(signUpData.getHasSubscription()) && signUpData.getHasSubscription().equalsIgnoreCase("yes")) {
                        if (Global.isInternetConnected(context))
                            callUpdateProfileAPi();
                        else
                            Global.callBannerWithColor(view, getString(R.string.no_internet));
                    } else {
                        Intent intent = new Intent(this, SubscriptionActivity.class);
                        startActivityForResult(intent, 111);
                    }
                }
                break;
            /*case R.id.tv_search_school:
                Intent intent = new Intent(this, SearchSchoolActivity.class);
                if (!TextUtils.isEmpty(tv_search_school.getText().toString()))
                    intent.putExtra(Global.SearchSchoolnameKey, tv_search_school.getText().toString());
                startActivityForResult(intent, Global.SearchSchoolKey);
                break;*/
            case R.id.tv_skip:
                SharedPreference.saveBoolean(Global.isSkipProfile, true, getContext());
                finishAffinity();
                startActivity(PlayerHomeActivity.class);
                break;
        }
    }

    private void callUpdateProfileAPi() {
        HashMap<String, String> map = new HashMap<>();
        map.put("first_name", signUpData.getFirstName());
        map.put("last_name", signUpData.getLastName());
        map.put("city", signUpData.getCity());
        map.put("state", signUpData.getState());
        map.put("game_id", signUpData.getGameId());
        map.put("school_team", edit_search_school.getText().toString().trim());
        map.put("class", edit_class.getText().toString().trim());
        map.put("height", edit_height.getText().toString().trim());
        map.put("weight", edit_weight.getText().toString().trim());
        map.put("position", position_spn.getText().toString().trim());
        map.put("highlighted_video", edit_link.getText().toString().trim());
        ApiCall.updateProfilePlayer(getContext(), map, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == 111 && data != null) {
            signUpData = (SignUpData) data.getSerializableExtra("SignUpObj");
        }
    }


    @Override
    public void onSuccessProfile(SignUpResponse response) {
        SignUpData oldSignUpData = Global.getSignUpData(context);
        Log.e(TAG, "oldSignUpData :1 \n\n\n\n  " + oldSignUpData);
        SignUpData newSignUpData = response.getSuccess();
        newSignUpData.setToken(oldSignUpData.getToken());
        Global.setSignUpData(context, newSignUpData);
        Log.e(TAG, "onSuccessProfile:2 \n" + newSignUpData);
        if (signUpData.getHasSubscription().equalsIgnoreCase("yes")&&!isFromUserProfile) {
            startActivity(PlayerHomeActivity.class);
        }
        finish();
    }

    @Override
    public void onError(String error) {
        Global.callBannerWithColor(rootLayout, error);

    }
}