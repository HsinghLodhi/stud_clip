package com.studclips.app.ui.player;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import com.studclips.app.BaseActivity;
import com.studclips.app.R;
import com.studclips.app.adapter.VideoAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MyVideoActivity extends BaseActivity {
    private ArrayList<String>list=new ArrayList<>();
    @BindView(R.id.recycle_video)
    RecyclerView recycle_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),3);
        recycle_video.setLayoutManager(gridLayoutManager);
        for (int i = 0; i < 9; i++) {
            list.add(""+i);
        }
        recycle_video.setAdapter(new VideoAdapter(getContext(),list));
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_my_video;
    }

    @Override
    @OnClick({R.id.backll})
    protected void onSubmit(View view) {
        switch (view.getId()) {
            case R.id.backll:
                onBackPressed();
                break;
        }
    }
}