package com.fph.lotteryanalyze.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.fph.lotteryanalyze.R;
import com.fph.lotteryanalyze.adapter.OmitAdapter;
import com.fph.lotteryanalyze.base.LazyFragment;
import com.fph.lotteryanalyze.utils.AnalyzeUtils;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

public class OmitFragment extends LazyFragment {

    @BindView(R.id.text_number)
    TextView mTextNumber;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private OmitAdapter mAdapter;
    private String mType = "red";
    private int mLimit;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_omit;
    }

    public static OmitFragment getInstance(int limit, String type) {
        OmitFragment omitFragment = new OmitFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("limit", limit);
        bundle.putString("type", type);
        omitFragment.setArguments(bundle);
        return omitFragment;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        mLimit = getArguments().getInt("limit", 0);
        mType = getArguments().getString("type");
        mAdapter = new OmitAdapter(mType);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    public void refreshData(int limit) {
        mAdapter.setList(new AnalyzeUtils(mType).getSsqRedAnalyze(getContext(), limit));
    }

    @Override
    public void lazyInit() {
        mAdapter.setList(new AnalyzeUtils(mType).getSsqRedAnalyze(getContext(), mLimit));
    }
}
