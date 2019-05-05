package com.fph.lotteryanalyze.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fph.lotteryanalyze.R;
import com.fph.lotteryanalyze.adapter.NewestDataAdapter;
import com.fph.lotteryanalyze.base.LazyFragment;
import com.fph.lotteryanalyze.db.DltLotteryDaoUtils;
import com.fph.lotteryanalyze.db.DltLotteryEntity;
import com.fph.lotteryanalyze.db.LotteryDaoUtils;
import com.fph.lotteryanalyze.db.LotteryEntity;

import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;

public class DataListFragment extends LazyFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private String mType;
    private NewestDataAdapter mAdapter;

    public static DataListFragment getInstance(String type) {
        DataListFragment dataListFragment = new DataListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        dataListFragment.setArguments(bundle);
        return dataListFragment;
    }

    @Override
    public void lazyInit() {
        if ("ssq".equals(mType)) {
            LotteryDaoUtils lotteryDaoUtils = new LotteryDaoUtils(getContext());
            List<LotteryEntity> ssqData = lotteryDaoUtils.queryAllLottery();
            Collections.sort(ssqData, new Comparator<LotteryEntity>() {
                @Override
                public int compare(LotteryEntity o1, LotteryEntity o2) {
                    return o2.getOpentime().compareTo(o1.getOpentime());
                }
            });
            mAdapter.setSsqList(ssqData);
        } else {
            DltLotteryDaoUtils dltLotteryDaoUtils = new DltLotteryDaoUtils(getContext());
            List<DltLotteryEntity> dltData = dltLotteryDaoUtils.queryAllLottery();
            Collections.sort(dltData, new Comparator<DltLotteryEntity>() {
                @Override
                public int compare(DltLotteryEntity o1, DltLotteryEntity o2) {
                    return o2.getOpentime().compareTo(o1.getOpentime());
                }
            });
            mAdapter.setDltList(dltData);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_data_list;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        mType = getArguments().getString("type", "ssq");
        mAdapter = new NewestDataAdapter(mType);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
