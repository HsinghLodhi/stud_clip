package com.studclips.app.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.studclips.app.R;
import com.studclips.app.util.Global;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    private ArrayList<String> list;
    private Context context;

    public VideoAdapter(Context context, ArrayList<String> list) {
        this.list = list;
        this.context = context;
    }

    public VideoAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_videos_adaptor, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_opt)
        ImageView img_opt;
        @BindView(R.id.img_play)
        ImageView img_play;
        @BindView(R.id.img_profile_pic)
        ImageView img_profile_pic;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            PopupMenu popup = new PopupMenu(context, img_opt);
            popup.getMenuInflater().inflate(R.menu.poupup_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.delete:
                            Global.showAlertDialog(context, true, "Delete!", "Are you sure?", "Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    list.remove(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());
                                }
                            }, "No",null);
                            //  onSelect.updateDeleteMyPost(position, feedList.get(position).getId(), "delete", feedList.get(position));
                            break;
                        case R.id.edit:
                            // onSelect.updateDeleteMyPost(position, feedList.get(position).getId(), "update", feedList.get(position));
                            break;
                    }
                    return true;
                }
            });

            img_opt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popup.show();//showing popup menu
                }
            });
        }

        private void showToast(String msg) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }

        public void bind() {
        }
    }
}
