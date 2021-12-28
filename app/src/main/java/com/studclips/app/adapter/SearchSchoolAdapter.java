package com.studclips.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.studclips.app.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchSchoolAdapter extends RecyclerView.Adapter<SearchSchoolAdapter.MyViewHolder> {
    private ArrayList<String> arrayList;
    private SelectInterface selectInterface;

    public SearchSchoolAdapter(ArrayList<String> arrayList, SelectInterface selectInterface) {
        this.arrayList = arrayList;
        this.selectInterface = selectInterface;
    }

    public interface SelectInterface {
        void itemClicked(String name);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_school, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_school_name)
        TextView tv_school_name;
        @BindView(R.id.rv_school_name)
        RelativeLayout rv_school_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind() {
            tv_school_name.setText(arrayList.get(getAdapterPosition()));
            rv_school_name.setOnClickListener(view -> selectInterface.itemClicked(arrayList.get(getAdapterPosition())));
        }
    }
}
