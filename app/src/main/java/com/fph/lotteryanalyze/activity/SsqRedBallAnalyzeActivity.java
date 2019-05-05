package com.fph.lotteryanalyze.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fph.lotteryanalyze.R;
import com.fph.lotteryanalyze.adapter.AnalyzeHistoryAdapter;
import com.fph.lotteryanalyze.base.BaseActivity;
import com.fph.lotteryanalyze.utils.AnalyzeUtils;

public class SsqRedBallAnalyzeActivity extends BaseActivity {

    private AnalyzeHistoryAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_analyze_history;
    }

    @Override
    protected void init() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new AnalyzeHistoryAdapter("red");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setList(new AnalyzeUtils("red").getSsqBlueAnalyzeData(this));
    }
}
