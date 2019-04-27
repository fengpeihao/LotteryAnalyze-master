package com.fph.lotteryanalyze.activity;

import org.jetbrains.annotations.Nullable;

import com.feilu.kotlindemo.base.BaseFragment;
import com.fph.lotteryanalyze.R;
import com.fph.lotteryanalyze.adapter.OmitAdapter;
import com.fph.lotteryanalyze.utils.AnalyzeUtils;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import butterknife.BindView;

public class OmitFragment extends BaseFragment {

    @BindView(R.id.text_number)
    TextView mTextNumber;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private OmitAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_omit;
    }

    public static OmitFragment getInstace(int limit){
        OmitFragment omitFragment = new OmitFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("limit",limit);
        omitFragment.setArguments(bundle);
        return omitFragment;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        int limit = getArguments().getInt("limit", 0);
        mAdapter = new OmitAdapter("red");
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter.setList(new AnalyzeUtils("red").getSsqRedAnalyze(getContext(),limit));
    }

    public void refreshData(int limit){
        mAdapter.setList(new AnalyzeUtils("red").getSsqRedAnalyze(getContext(),limit));
    }
}
