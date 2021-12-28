package com.studclips.app.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.studclips.app.R;
import com.studclips.app.model.ChatThreads;
import com.studclips.app.model.SearchUserSuccess;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectUserAdapter extends RecyclerView.Adapter<SelectUserAdapter.myHolder> {
    private Context context;
    private List<SearchUserSuccess> searchUserSuccessList;
    private OnClickItem onClickItem;


    public SelectUserAdapter(Context context, List<SearchUserSuccess> objectList, OnClickItem onClickItem) {
        this.context = context;
        this.searchUserSuccessList = objectList;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myHolder(LayoutInflater.from(context).inflate(R.layout.select_user_item_lay, parent, false));
    }

    @Override

    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        SearchUserSuccess userSuccess = searchUserSuccessList.get(position);
        holder.tvName.setText(userSuccess.getFirstName() + " " + userSuccess.getLastName());
        Glide.with(context).load(userSuccess.getProfilePhoto()).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                holder.img_profile_pic.setImageDrawable(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                holder.img_profile_pic.setImageResource(R.drawable.ic_profile);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                holder.img_profile_pic.setImageResource(R.drawable.ic_profile);
            }
        });

        holder.rlTop.setOnClickListener(v -> {
            String name = userSuccess.getFirstName() + " " + userSuccess.getLastName();
            onClickItem.onThreadClick(position, name);
        });
    }

    @Override
    public int getItemCount() {
        return searchUserSuccessList.size();
    }

    static class myHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_Name)
        TextView tvName;
        @BindView(R.id.img_profile_pic)
        ImageView img_profile_pic;
        @BindView(R.id.rlTop)
        RelativeLayout rlTop;

        public myHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface OnClickItem {
        void onThreadClick(int pos, String name);
    }

}
