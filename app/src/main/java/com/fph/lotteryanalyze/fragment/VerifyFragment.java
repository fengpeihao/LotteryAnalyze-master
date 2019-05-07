package com.fph.lotteryanalyze.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fph.lotteryanalyze.R;
import com.fph.lotteryanalyze.adapter.VerifyAdapter;
import com.fph.lotteryanalyze.base.LazyFragment;
import com.fph.lotteryanalyze.bean.VerifyBean;
import com.fph.lotteryanalyze.utils.AnalyzeUtils;

import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;

public class VerifyFragment extends LazyFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private String mType;
    private VerifyAdapter mAdapter;
    private List<VerifyBean> mVerifyData;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                mAdapter.setList(mVerifyData);
            }
        }
    };

    public static VerifyFragment getInstance(String type) {
        VerifyFragment verifyFragment = new VerifyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        verifyFragment.setArguments(bundle);
        return verifyFragment;
    }

    @Override
    public void lazyInit() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_verify;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        mType = getArguments().getString("type");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new VerifyAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }
}
