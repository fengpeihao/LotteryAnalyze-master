package com.fph.lotteryanalyze.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fph.lotteryanalyze.R;
import com.fph.lotteryanalyze.adapter.AnalyzeHistoryAdapter;
import com.fph.lotteryanalyze.base.LazyFragment;
import com.fph.lotteryanalyze.utils.AnalyzeUtils;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

public class SsqAnalyzeFragment extends LazyFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private AnalyzeHistoryAdapter mAdapter;
    private String mType;

    public static SsqAnalyzeFragment getInstance(String type) {
        SsqAnalyzeFragment sssqAnalyzeFragment = new SsqAnalyzeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        sssqAnalyzeFragment.setArguments(bundle);
        return sssqAnalyzeFragment;
    }

    @Override
    public void lazyInit() {
        mAdapter.setList(new AnalyzeUtils(mType).getSsqAnalyzeData(getContext(), 0));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_analyze;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        mType = getArguments().getString("type");
        mAdapter = new AnalyzeHistoryAdapter(mType);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }
}
