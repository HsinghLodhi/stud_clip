package com.studclips.app.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


abstract public class GenericAdapterWithoutDB<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<T> myList;

    public GenericAdapterWithoutDB(Context context, ArrayList<T> myList) {
        this.context = context;
        this.myList = myList;
    }

    public abstract int getLayoutResId();

    public abstract void onBindData(T model, int position, View view);

    public abstract void onItemClick(T model, int position);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(getLayoutResId(), parent, false);
        return new MYHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindData(myList.get(position), position, ((MYHolder) holder).myItemView);

        ((MYHolder) holder).myItemView.setOnClickListener(v -> onItemClick(myList.get(position), position));
    }

    public void addItems(ArrayList<T> arrayList) {
        myList = arrayList;
        this.notifyDataSetChanged();
    }

    public T getItem(int position) {
        return myList.get(position);
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class MYHolder extends RecyclerView.ViewHolder {
        protected View myItemView;

        public MYHolder(@NonNull View itemView) {
            super(itemView);
            myItemView =  itemView;
        }
    }

}
