package com.fph.lotteryanalyze.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fph.lotteryanalyze.base.BaseActivity;
import com.fph.lotteryanalyze.R;
import com.fph.lotteryanalyze.adapter.NewestDataAdapter;
import com.fph.lotteryanalyze.db.LotteryDaoUtils;
import com.fph.lotteryanalyze.db.LotteryEntity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DataListActivity extends BaseActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data_list;
    }

    @Override
    protected void init() {
        mRecyclerView = findViewById(R.id.recycler_view);
        LotteryDaoUtils lotteryDaoUtils = new LotteryDaoUtils(this);
        List<LotteryEntity> lotteryEntities = lotteryDaoUtils.queryAllLottery();
        Collections.sort(lotteryEntities, new Comparator<LotteryEntity>() {
            @Override
            public int compare(LotteryEntity o1, LotteryEntity o2) {
                return o2.getOpentime().compareTo(o1.getOpentime());
            }
        });
        NewestDataAdapter adapter = new NewestDataAdapter();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setList(lotteryEntities);
    }
}
