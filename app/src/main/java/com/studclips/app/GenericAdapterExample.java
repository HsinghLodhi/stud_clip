package com.studclips.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.studclips.app.adapter.GenericAdapterWithoutDB;
import com.studclips.app.model.ChatThreads;

import java.util.ArrayList;

public class GenericAdapterExample extends AppCompatActivity {
    ArrayList<ChatThreads> list;
    private LinearLayoutManager layoutManager;
    RecyclerView recyclerviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_adapter_example);
        recyclerviewList = findViewById(R.id.recyclerviewList);
        layoutManager = new LinearLayoutManager(this);
        list = new ArrayList<>();
        recyclerviewList.setLayoutManager(layoutManager);
        for (int i = 0; i < 10; i++) {
            list.add(new ChatThreads("Rober", "how are you", "", "9:0 pm", 8));
        }
        recyclerviewList.setAdapter(new GenericAdapterWithoutDB<ChatThreads>(this, list) {
            @Override
            public int getLayoutResId() {
                return R.layout.item_layout_generic_adapter;
            }

            @Override
            public void onBindData(ChatThreads model, int position, View view) {
                TextView tvMessage, tvName, tvUnreadCount;
                tvMessage = view.findViewById(R.id.tv_Message);
                tvName = view.findViewById(R.id.tv_Name);
                tvUnreadCount = view.findViewById(R.id.tv_UnreadCount);
                tvMessage.setText(model.getMessage());
                tvName.setText(model.getName());
                tvUnreadCount.setText(String.valueOf(model.getMessageCount()));
            }

            @Override
            public void onItemClick(ChatThreads model, int position) {
                Toast.makeText(GenericAdapterExample.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

     /*   binding.recyclerviewList.setAdapter(new GenericAdapter<ChatThreads, ItemLayoutGenericAdapterBinding>(GenericAdapterExample.this, list) {
            @Override
            public int getLayoutResId() {
                return R.layout.item_layout_generic_adapter;
            }

            @Override
            public void onBindData(ChatThreads model, int position, ItemLayoutGenericAdapterBinding dataBinding) {
                dataBinding.tvMessage.setText(model.getMessage());
                dataBinding.tvName.setText(model.getName());
                dataBinding.tvUnreadCount.setText(String.valueOf(model.getMessageCount()));
            }

            @Override
            public void onItemClick(ChatThreads model, int position) {
                Toast.makeText(GenericAdapterExample.this, "" +  " - " + position, Toast.LENGTH_SHORT).show();
            }
        });*/

    }
}