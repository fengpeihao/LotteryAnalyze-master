package com.fph.lotteryanalyze.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.fph.lotteryanalyze.R;
import com.fph.lotteryanalyze.adapter.OmitAdapter;
import com.fph.lotteryanalyze.base.LazyFragment;
import com.fph.lotteryanalyze.bean.LotteryFrequencyBean;
import com.fph.lotteryanalyze.utils.AnalyzeUtils;
import com.fph.lotteryanalyze.utils.DltLotteryAnalyzeUtils;
import com.fph.lotteryanalyze.utils.LotteryAnalyzeUtils;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import butterknife.BindView;

public class OmitFragment extends LazyFragment {

    @BindView(R.id.text_number)
    TextView mTextNumber;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private OmitAdapter mAdapter;
    private String mType = "red";
    private String mCategory = "ssq";
    private int mLimit;
    private LotteryAnalyzeUtils mAnalyzeUtils;
    private DltLotteryAnalyzeUtils mDltAnalyzeUtils;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_omit;
    }

    public static OmitFragment getInstance(int limit, String type, String category) {
        OmitFragment omitFragment = new OmitFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("limit", limit);
        bundle.putString("type", type);
        bundle.putString("category", category);
        omitFragment.setArguments(bundle);
        return omitFragment;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        mLimit = getArguments().getInt("limit", 0);
        mType = getArguments().getString("type");
        mCategory = getArguments().getString("category");
        mAdapter = new OmitAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    public void refreshData(int limit) {


        mAdapter.setList(getList(limit));
    }

    @Override
    public void lazyInit() {
        mAdapter.setList(getList(mLimit));
    }

    private ArrayList<LotteryFrequencyBean> getList(int limit) {
        ArrayList<LotteryFrequencyBean> list;
        if ("ssq".equals(mCategory)) {
            if (mAnalyzeUtils == null) {
                mAnalyzeUtils = new LotteryAnalyzeUtils(getContext());
            }
            if ("red".equals(mType)) {
                list = mAnalyzeUtils.analyze(limit).get(0);
            } else {
                list = mAnalyzeUtils.analyze(limit).get(1);
            }
        } else {
            if (mDltAnalyzeUtils == null) {
                mDltAnalyzeUtils = new DltLotteryAnalyzeUtils(getContext());
            }
            if ("red".equals(mType)) {
                list = mDltAnalyzeUtils.analyze(limit).get(0);
            } else {
                list = mDltAnalyzeUtils.analyze(limit).get(1);
            }
        }
        return list;
    }
}
