package com.studclips.app.ui.player.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.studclips.app.adapter.ChatThreadAdapter;
import com.studclips.app.model.ChatThreads;
import com.studclips.app.R;
import com.studclips.app.ui.common.ChatMessageActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PlayerInboxFragment extends Fragment implements ChatThreadAdapter.OnClickItem, TextWatcher {
    Unbinder unbinder;
    private Context context;
    @BindView(R.id.rvChatThread)
    RecyclerView rvChatThread;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.tv_no_data)
    TextView tv_no_data;
    ChatThreadAdapter chatThreadAdapter;
    LinearLayoutManager layoutManager;
    List<ChatThreads> chatThreadsList;
    private static final String TAG = "InboxFragment";
    View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.player_inbox_fragment_layout, container, false);
        context = getContext();
        unbinder = ButterKnife.bind(this, rootView);
        onCreateData();
        return rootView;
    }

    private void onCreateData() {
        etSearch.addTextChangedListener(PlayerInboxFragment.this);
        chatThreadsList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(context);
        chatThreadsList.add(new ChatThreads("Jim Rodriguez", "Lorem ipsum dolor sit\namet, cons ", "https://www.fillmurray.com/640/360", "12:36 PM", 4));
        chatThreadsList.add(new ChatThreads("Maria Watson", "Lorem ipsum dolor sit\namet, cons et ", "https://www.placecage.com/640/360", "12:36 PM", 0));
        chatThreadsList.add(new ChatThreads("Aiony Haust", "Lorem ipsum dolor sit\namet, cons ", "https://images.unsplash.com/photo-1519985176271-adb1088fa94c?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=a0c8d632e977f94e5d312d9893258f59&auto=format&fit=crop&w=100&q=80", "12:36 PM", 0));
        chatThreadsList.add(new ChatThreads("Antenna", "Lorem ipsum dolor sit\namet, cons ", "https://images.unsplash.com/photo-1522205408450-add114ad53fe?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=368f45b0888aeb0b7b08e3a1084d3ede&auto=format&fit=crop&w=100&q=80", "12:36 PM", 2));
        chatThreadsList.add(new ChatThreads("Antenna", "Lorem ipsum dolor sit\namet, cons ", "https://images.unsplash.com/photo-1522205408450-add114ad53fe?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=368f45b0888aeb0b7b08e3a1084d3ede&auto=format&fit=crop&w=100&q=80", "12:36 PM", 0));
        chatThreadsList.add(new ChatThreads("Antenna", "Lorem ipsum dolor sit\namet, cons ", "https://images.unsplash.com/photo-1522205408450-add114ad53fe?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=368f45b0888aeb0b7b08e3a1084d3ede&auto=format&fit=crop&w=100&q=80", "12:36 PM", 4));
        chatThreadsList.add(new ChatThreads("Antenna", "Lorem ipsum dolor sit\namet, cons ", "https://images.unsplash.com/photo-1522205408450-add114ad53fe?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=368f45b0888aeb0b7b08e3a1084d3ede&auto=format&fit=crop&w=100&q=80", "12:36 PM", 0));
        chatThreadsList.add(new ChatThreads("Antenna", "Lorem ipsum dolor sit\namet, cons ", "https://images.unsplash.com/photo-1522205408450-add114ad53fe?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=368f45b0888aeb0b7b08e3a1084d3ede&auto=format&fit=crop&w=100&q=80", "12:36 PM", 0));
        chatThreadsList.add(new ChatThreads("Antenna", "Lorem ipsum dolor sit\namet, cons ", "https://images.unsplash.com/photo-1522205408450-add114ad53fe?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=368f45b0888aeb0b7b08e3a1084d3ede&auto=format&fit=crop&w=100&q=80", "12:36 PM", 5));
        chatThreadsList.add(new ChatThreads("Antenna", "Lorem ipsum dolor sit\namet, cons ", "https://images.unsplash.com/photo-1522205408450-add114ad53fe?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=368f45b0888aeb0b7b08e3a1084d3ede&auto=format&fit=crop&w=100&q=80", "12:36 PM", 0));
        chatThreadsList.add(new ChatThreads("Antenna", "Lorem ipsum dolor sit\namet, cons ", "https://images.unsplash.com/photo-1520342868574-5fa3804e551c?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=6ff92caffcdd63681a35134a6770ed3b&auto=format&fit=crop&w=100&q=80", "12:36 PM", 0));
        setAdapterData();
    }

    private void setAdapterData() {
        rvChatThread.setLayoutManager(layoutManager);
        chatThreadAdapter = new ChatThreadAdapter(context, chatThreadsList, this);
        rvChatThread.setAdapter(chatThreadAdapter);
    }


    @OnClick({R.id.rlSearchView})
    public void onSubmit(View view) {
        int id = view.getId();
        if (id == R.id.rlSearchView) {
            etSearch.requestFocus();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etSearch, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override
    public void onThreadClick(int pos) {
        startActivity(new Intent(context, ChatMessageActivity.class));
    }

    @Override
    public void onSearchNotFound(int size) {
        if (size == 0) {
            rvChatThread.setVisibility(View.GONE);
            tv_no_data.setVisibility(View.VISIBLE);
        } else {
            rvChatThread.setVisibility(View.VISIBLE);
            tv_no_data.setVisibility(View.GONE);
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        chatThreadAdapter.getFilter().filter(s.toString());
        if (chatThreadsList.size() > 0) {
            rvChatThread.setVisibility(View.VISIBLE);
            tv_no_data.setVisibility(View.GONE);
        } else {
            rvChatThread.setVisibility(View.GONE);
            tv_no_data.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void afterTextChanged(Editable s) {

    }
}
