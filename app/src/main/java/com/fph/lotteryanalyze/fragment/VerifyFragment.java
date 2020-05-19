package com.fph.lotteryanalyze.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fph.lotteryanalyze.R;
import com.fph.lotteryanalyze.adapter.VerifyAdapter;
import com.fph.lotteryanalyze.base.LazyFragment;
import com.fph.lotteryanalyze.bean.LotteryGuessBean;
import com.fph.lotteryanalyze.db.OmitDaoUtils;
import com.fph.lotteryanalyze.db.OmitEntity;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import butterknife.BindView;

public class VerifyFragment extends LazyFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private String mType;
    private VerifyAdapter mAdapter;

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
        mAdapter = new VerifyAdapter(mType);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    public void setData(List<LotteryGuessBean> list){
        mAdapter.setList(list);
    }
}
