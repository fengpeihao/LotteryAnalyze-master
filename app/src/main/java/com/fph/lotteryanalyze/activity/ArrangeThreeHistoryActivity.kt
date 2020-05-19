package com.fph.lotteryanalyze.activity

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.fph.lotteryanalyze.R
import com.fph.lotteryanalyze.adapter.ArrangeThreeHistoryAdapter
import com.fph.lotteryanalyze.base.BaseActivity
import com.fph.lotteryanalyze.db.ArrangeThreeDaoUtils
import kotlinx.android.synthetic.main.activity_arrange_three_history.*

class ArrangeThreeHistoryActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_arrange_three_history
    }

    override fun init() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        val adapter = ArrangeThreeHistoryAdapter()
        recycler_view.adapter = adapter
        val list = ArrangeThreeDaoUtils(this).queryLimitData(100)
        adapter.setData(list)

    }
}