package com.studclips.app.ui.common;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.studclips.app.BaseActivity;
import com.studclips.app.R;
import com.studclips.app.adapter.ChatMessageAdapter;
import com.studclips.app.model.Chat;
import com.studclips.app.model.MessageType;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatMessageActivity extends BaseActivity {
    private static final String TAG = "ChatMessageActivity";
    @BindView(R.id.img_send)
    ImageView img_send;
    @BindView(R.id.img_profile_pic)
    ImageView img_profile_pic;
    @BindView(R.id.edit_text_msg)
    EditText edit_text_msg;
    @BindView(R.id.recycle_chat)
    RecyclerView recycle_chat;
    @BindView(R.id.rv_typing)
    RelativeLayout rv_typing;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.card_profile_pic)
    CardView card_profile_pic;
    private ArrayList<Chat> chatList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                chatList.add(new Chat(MessageType.Sender.toString(), "Hi How are you Hi How are you Hi How are you Hi How are you"));
            } else {
                chatList.add(new Chat(MessageType.Receiver.toString(), "yaap i am fine yaap i am fine yaap i am fine yaap i am fine"));
            }
        }
        recycle_chat.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recycle_chat.setAdapter(new ChatMessageAdapter(getContext(), chatList));
        recycle_chat.scrollToPosition(chatList.size() - 1);
        tv_type.setText(String.format("%s is typing...", tv_title.getText()));
        edit_text_msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //rv_typing.setVisibility(View.VISIBLE);
               /* if (!TextUtils.isEmpty(charSequence.toString()))
                    setTintColor(1);*/
                Log.e(TAG, "beforeTextChanged: ");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e(TAG, "onTextChanged: ");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                message=edit_text_msg.getText().toString().trim();
                if (TextUtils.isEmpty(message)) {
                    //rv_typing.setVisibility(View.GONE);
                    setTintColor(0);
                } else {
                    setTintColor(1);
                }
                Log.e(TAG, "afterTextChanged: ");
            }
        });
    }

    private void setTintColor(int i) {
        if (i == 1)
            img_send.setColorFilter(ContextCompat.getColor(getContext(), R.color.white), PorterDuff.Mode.SRC_IN);
        else
            img_send.setColorFilter(ContextCompat.getColor(getContext(), R.color.grey), PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_chat_message;
    }

    String message = "";

    @Override
    @OnClick({R.id.backll, R.id.img_send, R.id.card_profile_pic, R.id.tv_title})
    protected void onSubmit(View view) {
        switch (view.getId()) {
            case R.id.card_profile_pic:
                showToast("profile pic clicked");
                break;
            case R.id.tv_title:
                showToast(tv_title.getText() + " clicked");
                break;
            case R.id.backll:
                onBackPressed();
                break;
            case R.id.img_send:
                message = edit_text_msg.getText().toString().trim();
                if (!TextUtils.isEmpty(message)) {
                    rv_typing.setVisibility(View.GONE);
                    chatList.add(new Chat(MessageType.Sender.toString(), edit_text_msg.getText().toString()));
                    recycle_chat.scrollToPosition(chatList.size() - 1);
                    edit_text_msg.setText("");
                }
                break;
        }
    }
}