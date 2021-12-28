package com.studclips.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.studclips.app.R;
import com.studclips.app.model.Chat;
import com.studclips.app.model.MessageType;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Chat>chatlist;
    private final int SENDER=0;
    private final int RECEIVER=1;

    public ChatMessageAdapter(Context context, ArrayList<Chat> chatlist) {
        this.context = context;
        this.chatlist = chatlist;
    }

    @Override
    public int getItemViewType(int position) {
        Chat model=chatlist.get(position);
        if (model.getType().equalsIgnoreCase(MessageType.Sender.toString()))
            return SENDER;
        else
            return RECEIVER;
        //return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==SENDER)
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_chat_sender,parent,false));
        else
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_chat_receiver,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
holder.bind();
    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_msg)
        TextView tv_msg;
        @BindView(R.id.tv_time)
        TextView tv_time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind() {
            tv_msg.setText(chatlist.get(getAdapterPosition()).getMessage());
        }
    }
}
