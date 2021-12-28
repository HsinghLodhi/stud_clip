package com.studclips.app.ui.player;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.studclips.app.BaseActivity;
import com.studclips.app.R;
import com.studclips.app.adapter.SearchSchoolAdapter;
import com.studclips.app.util.Global;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchSchoolActivity extends BaseActivity implements TextWatcher, SearchSchoolAdapter.SelectInterface {
    private static final String TAG = "SearchSchoolActivity";
    @BindView(R.id.edit_search_school)
    EditText edit_search_school;
    @BindView(R.id.recycle_school)
    RecyclerView recycle_school;
    @BindView(R.id.tv_no_data)
    TextView tv_no_data;
    Intent intent;
    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        setRecycleAdapter();

    }

    private void initViews() {
        recycle_school.setLayoutManager(new LinearLayoutManager(getContext()));
        intent = getIntent();
        if (intent != null && intent.hasExtra(Global.SearchSchoolnameKey)) {
            edit_search_school.setText(intent.getStringExtra(Global.SearchSchoolnameKey));
        }
        edit_search_school.addTextChangedListener(this);
    }

    private void setRecycleAdapter() {
        if (list.isEmpty()) {
            recycle_school.setVisibility(View.GONE);
            tv_no_data.setVisibility(View.VISIBLE);
        } else {
            recycle_school.setVisibility(View.VISIBLE);
            tv_no_data.setVisibility(View.GONE);
            recycle_school.setAdapter(new SearchSchoolAdapter(list, this));
            recycle_school.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_search_school;
    }

    @Override
    @OnClick(R.id.ic_back_img)
    protected void onSubmit(View view) {
        switch (view.getId()) {
            case R.id.ic_back_img:
                onBackPressed();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.e(TAG, "beforeTextChanged: ");
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.e(TAG, "onTextChanged: ");
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() > 0) {
            if (!list.isEmpty())
                list.clear();
            for (int i = 0; i < 10; i++) {
                list.add(editable.toString() + " " + i);
            }
            recycle_school.setVisibility(View.VISIBLE);
            tv_no_data.setVisibility(View.GONE);
            recycle_school.setAdapter(new SearchSchoolAdapter(list, this));
            recycle_school.getAdapter().notifyDataSetChanged();
            //setRecycleAdapter();
        } else {
            if (!list.isEmpty())
                list.clear();
            recycle_school.setVisibility(View.GONE);
            tv_no_data.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void itemClicked(String name) {
        intent = getIntent();
        intent.putExtra(Global.SearchSchoolnameKey, name);
        setResult(Global.SearchSchoolKey, intent);
        onBackPressed();
    }
}