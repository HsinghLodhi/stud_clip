package com.studclips.app.ui.common;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.studclips.app.BaseActivity;
import com.studclips.app.model.ChatThreads;
import com.studclips.app.R;
import com.studclips.app.ui.player.UsersActivity;
import com.studclips.app.util.Global;

import butterknife.BindView;
import butterknife.OnClick;

public class FiltersActivity extends BaseActivity {
    private Context context;
    @BindView(R.id.spinnerSortBy)
    Spinner spinnerSortBy;
    @BindView(R.id.spinnerBySport)
    Spinner spinnerBySport;
    @BindView(R.id.root)
    RelativeLayout root;
    @BindView(R.id.etSearch)
    TextView etSearch;
    ArrayAdapter<CharSequence> sportByAdapter;
    ArrayAdapter<CharSequence> sortByAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        onCreateData();
    }

    private void onCreateData() {
        sportByAdapter = ArrayAdapter.createFromResource(this,
                R.array.sport_array_by_hint, R.layout.spinner_item_grey);
        spinnerBySport.setAdapter(sportByAdapter);
        sortByAdapter = ArrayAdapter.createFromResource(this,
                R.array.sort_by_arr, R.layout.spinner_item_grey);
        spinnerSortBy.setAdapter(sortByAdapter);
    }


    @OnClick({R.id.rlSearchView, R.id.backll, R.id.tvClearAll, R.id.tvApplyFilter})
    public void onSubmit(View view) {
        int id = view.getId();
        if (id == R.id.backll) {
            onBackPressed();
        } else if (id == R.id.tvClearAll) {
            Global.showAlertDialog(context, true, "Clear All", "Are you sure?", "Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    spinnerSortBy.setSelection(0);
                    spinnerBySport.setSelection(0);
                    etSearch.setText("");

                }
            }, "No", null);

        } else if (id == R.id.tvApplyFilter) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("search_by_name", etSearch.getText().toString().trim());
            returnIntent.putExtra("sortBy", String.valueOf(spinnerSortBy.getSelectedItem()));
            returnIntent.putExtra("gameId", spinnerBySport.getSelectedItemPosition());
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } else if (id == R.id.rlSearchView) {
            Intent intent = new Intent(context, UsersActivity.class);
            intent.putExtra("show_data", true);
            startActivityForResult(intent, 111);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 111 && resultCode == 111 && data != null) {
            if (data.getStringExtra("searched_name") != null) {
                etSearch.setText(data.getStringExtra("searched_name"));
            }
        }


    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_filters;
    }
}