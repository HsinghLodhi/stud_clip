package com.studclips.app.ui.player;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.studclips.app.ApiCall;
import com.studclips.app.BaseActivity;
import com.studclips.app.api.ApiCallback;
import com.studclips.app.model.ChatThreads;
import com.studclips.app.R;
import com.studclips.app.adapter.SelectUserAdapter;
import com.studclips.app.adapter.ChatThreadAdapter;
import com.studclips.app.model.SearchUserResposne;
import com.studclips.app.model.SearchUserSuccess;
import com.studclips.app.util.BaseLoader;
import com.studclips.app.util.Global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UsersActivity extends BaseActivity implements TextWatcher, SelectUserAdapter.OnClickItem, ApiCallback.SearchUserCall {
    private Context context;
    @BindView(R.id.rvChatThread)
    RecyclerView rvChatThread;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.tv_no_data)
    TextView tv_no_data;
    SelectUserAdapter selectUserAdapter;
    LinearLayoutManager layoutManager;
    List<SearchUserSuccess> searchUserSuccessList;
    private BaseLoader baseLoader;
    private static final String TAG = "InboxFragment";
    boolean showListData = false, showOtherView = false;// for dummy purpose

    @Override
    protected int getActivityLayout() {

        return R.layout.activity_users;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        baseLoader = new BaseLoader(context);
        onCreateData();
    }

    private void onCreateData() {
        etSearch.addTextChangedListener(this);
      /*  chatThreadsList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(context);
        showListData = getIntent().getBooleanExtra("show_data", false);
        if (showListData) {
            showOtherView = false;
            rvChatThread.setVisibility(View.VISIBLE);
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
        } else {
            showOtherView = true;
            rvChatThread.setVisibility(View.GONE);
        }*/

    }


    @OnClick({R.id.rlSearchView, R.id.llBacklay, R.id.searchImage})
    public void onSubmit(View view) {
        int id = view.getId();
        if (id == R.id.llBacklay) {
            onBackPressed();
        } /*else if (id == R.id.rlSearchView) {
            etSearch.requestFocus();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etSearch, InputMethodManager.SHOW_IMPLICIT);
        }*/ else if (id == R.id.searchImage) {
            if (etSearch.getText().toString().length() > 0) {
                baseLoader.showLoader();
                HashMap<String, String> map = new HashMap<>();
                map.put("name", etSearch.getText().toString().trim());
                ApiCall.SearchUserApi(context, map, this);
            }
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
      /*  selectUserAdapter.getFilter().filter(s.toString());
        if (chatThreadsList.size() > 0) {
            rvChatThread.setVisibility(View.VISIBLE);
            tv_no_data.setVisibility(View.GONE);
        } else {
            rvChatThread.setVisibility(View.GONE);
            tv_no_data.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onThreadClick(int pos, String name) {

        Intent intent = new Intent();
        intent.putExtra("searched_name", name);
        setResult(111, intent);
        finish();
    }

    @Override
    public void onSuccessSearchUser(SearchUserResposne response) {
        baseLoader.hideLoader();
        if (response.getSuccess() != null && response.getSuccess().size() > 0) {
            rvChatThread.setVisibility(View.VISIBLE);
            tv_no_data.setVisibility(View.GONE);
            selectUserAdapter = new SelectUserAdapter(context, response.getSuccess(), this);
            rvChatThread.setLayoutManager(new LinearLayoutManager(context));
            rvChatThread.setAdapter(selectUserAdapter);
        } else {
            rvChatThread.setVisibility(View.GONE);
            tv_no_data.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onError(String error) {
        baseLoader.hideLoader();
        Global.callBannerWithColor(etSearch, error);
    }
}