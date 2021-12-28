package com.studclips.app.ui.fan.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.studclips.app.NotificationModel;
import com.studclips.app.R;
import com.studclips.app.adapter.NotificationAdapter;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FanNotificationFragment extends Fragment implements  NotificationAdapter.OnItemClick {
    @BindView(R.id.rvNotification)
    RecyclerView rvNotification;
    NotificationAdapter notificationAdapter;
    private List<NotificationModel> notificationList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fan_notification_fragment_layout, container, false);
        ButterKnife.bind(this,view);
        onCreateData();
        return view;
    }


    private void onCreateData() {
        notificationList = new ArrayList<>();
        notificationList.add(new NotificationModel("Maria Watson", "https://www.fillmurray.com/640/360", "", "Has liked your video"));
        notificationList.add(new NotificationModel("Aiony Haust", "https://www.placecage.com/640/360", "", "Has replied your comment"));
        notificationList.add(new NotificationModel("Antenna", "https://images.unsplash.com/photo-1519985176271-adb1088fa94c?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=a0c8d632e977f94e5d312d9893258f59&auto=format&fit=crop&w=100&q=80", "", "Has posted new video"));
        notificationList.add(new NotificationModel("Wily Dade", "https://images.unsplash.com/photo-1522205408450-add114ad53fe?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=368f45b0888aeb0b7b08e3a1084d3ede&auto=format&fit=crop&w=100&q=80", "", "New Message"));
        notificationList.add(new NotificationModel("Maria Watson", "https://www.fillmurray.com/640/360", "", "Has liked your video"));
        notificationList.add(new NotificationModel("Aiony Haust", "https://www.fillmurray.com/640/360", "", "Has rated your video 3 star"));
        notificationList.add(new NotificationModel("Antenna", "https://images.unsplash.com/photo-1522205408450-add114ad53fe?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=368f45b0888aeb0b7b08e3a1084d3ede&auto=format&fit=crop&w=100&q=80", "", "Has posted new video"));
        setAdapterData();
    }

    private void setAdapterData() {
        notificationAdapter = new NotificationAdapter(getContext(), notificationList, this);
        rvNotification.setAdapter(notificationAdapter);
    }


    @Override
    public void onItemClick(int pos) {
    }
}
