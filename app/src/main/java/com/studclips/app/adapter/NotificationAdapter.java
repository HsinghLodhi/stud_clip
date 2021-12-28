package com.studclips.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.studclips.app.NotificationModel;
import com.studclips.app.R;

import java.util.List;

import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.myHolder> {
    private Context context;
    private List<NotificationModel> notificationList;
    OnItemClick onItemClick;

    public NotificationAdapter(Context context, List<NotificationModel> notificationList, OnItemClick onItemClick) {
        this.notificationList = notificationList;
        this.onItemClick = onItemClick;
        this.context = context;

    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        NotificationModel data = notificationList.get(position);
        Glide.with(holder.img_profile_pic).load(data.getProfile()).into(holder.img_profile_pic);
        holder.tv_Message.setText(data.getMessage());
        holder.tv_Name.setText(data.getName());
        //////        for dummy purpose /////////////

        if (position == 0) {
            holder.ivNotificationIcon.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.thumbs_up, null));
        } else if (position == 1) {
            holder.ivNotificationIcon.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.notification_star, null));
        } else if (position == 2) {
            holder.ivNotificationIcon.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_video_grey, null));
        } else if (position == 3) {
            holder.ivNotificationIcon.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.not_message_unread, null));
        } else if (position == 4) {
            holder.ivNotificationIcon.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.thumbs_up, null));
        } else if (position == 5) {
            holder.ivNotificationIcon.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.notification_star, null));
        } else if (position == 6) {
            holder.ivNotificationIcon.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_video_grey, null));
        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }


    public static class myHolder extends RecyclerView.ViewHolder {
        ImageView img_profile_pic, ivNotificationIcon;
        TextView tv_Name, tv_Message;

        public myHolder(@NonNull View itemView) {
            super(itemView);
            img_profile_pic = itemView.findViewById(R.id.img_profile_pic);
            ivNotificationIcon = itemView.findViewById(R.id.ivNotificationIcon);
            tv_Name = itemView.findViewById(R.id.tv_Name);
            tv_Message = itemView.findViewById(R.id.tv_Message);


        }
    }

    public interface OnItemClick {
        void onItemClick(int pos);
    }
}
