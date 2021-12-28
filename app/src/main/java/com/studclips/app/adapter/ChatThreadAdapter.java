package com.studclips.app.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.studclips.app.model.ChatThreads;
import com.studclips.app.R;
import com.studclips.app.util.Global;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatThreadAdapter extends RecyclerView.Adapter<ChatThreadAdapter.myHolder> implements Filterable {
    private static final String TAG = "ChatThreadAdapter";
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private Context context;
    private List<ChatThreads> chatThreadsList;
    private OnClickItem onClickItem;
    private List<ChatThreads> filterChatThreadsList;
    private int pos = -1;
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ChatThreads> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(filterChatThreadsList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ChatThreads item : filterChatThreadsList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            chatThreadsList.clear();
            chatThreadsList.addAll((List) results.values);
            onClickItem.onSearchNotFound(chatThreadsList.size());
            notifyDataSetChanged();

        }
    };

    public ChatThreadAdapter(Context context, List<ChatThreads> objectList, OnClickItem onClickItem) {
        this.context = context;
        this.chatThreadsList = objectList;
        this.onClickItem = onClickItem;
        filterChatThreadsList = new ArrayList<>(chatThreadsList);
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myHolder(LayoutInflater.from(context).inflate(R.layout.chat_thread_item_lay, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        ChatThreads chatThreads = chatThreadsList.get(position);
        holder.tvName.setText(chatThreads.getName());
        holder.tv_Message.setText(chatThreads.getMessage());
        holder.tv_time.setText(chatThreads.getTime());
        holder.tv_UnreadCount.setText(String.valueOf(chatThreads.getMessageCount()));
        holder.tv_UnreadCount.setVisibility(chatThreads.getMessageCount() == 0 ? View.GONE : View.VISIBLE);
        Glide.with(context).load(chatThreads.getImage()).into(holder.img_profile_pic);

        holder.rlTop.setOnClickListener(v -> {
            onClickItem.onThreadClick(position);
        });
        viewBinderHelper.bind(holder.swipe, "" + position);
        holder.swipe.setSwipeListener(new SwipeRevealLayout.SwipeListener() {
            @Override
            public void onClosed(SwipeRevealLayout view) {
            }

            @Override
            public void onOpened(SwipeRevealLayout view) {
                if (pos != -1 && pos != position) {
                    viewBinderHelper.closeLayout("" + pos);
                }
                pos = position;
            }

            @Override
            public void onSlide(SwipeRevealLayout view, float slideOffset) {
            }
        });
        holder.llDeleteDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: " + position);
                Global.showAlertDialog(context, true, "Delete", "Are you sure?", "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        chatThreadsList.remove(position);
                        notifyItemRemoved(position);
                    }
                }, "No", null);
            }
        });
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    @Override
    public int getItemCount() {
        return chatThreadsList.size();
    }

    public interface OnClickItem {
        void onThreadClick(int pos);

        void onSearchNotFound(int size);
    }

    static class myHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_Name)
        TextView tvName;
        @BindView(R.id.tv_Message)
        TextView tv_Message;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_UnreadCount)
        TextView tv_UnreadCount;
        @BindView(R.id.img_profile_pic)
        ImageView img_profile_pic;
        @BindView(R.id.rlTop)
        RelativeLayout rlTop;
        @BindView(R.id.llDeleteDialog)
        LinearLayout llDeleteDialog;
        @BindView(R.id.swipe)
        SwipeRevealLayout swipe;

        public myHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
