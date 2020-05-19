package com.fph.lotteryanalyze.activity

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.fph.lotteryanalyze.R
import com.fph.lotteryanalyze.adapter.ArrangeThreeAnalyzeAdapter
import com.fph.lotteryanalyze.base.BaseActivity
import com.fph.lotteryanalyze.bean.ArrangeThreeAnalyzeBean
import com.fph.lotteryanalyze.utils.ETCFUtils
import kotlinx.android.synthetic.main.fragment_analyze.*

class ETCFAnalyzeListActivity : BaseActivity() {

    var mList: ArrayList<ArrangeThreeAnalyzeBean> = ArrayList()
    lateinit var mAdapter: ArrangeThreeAnalyzeAdapter
    override fun getLayoutId(): Int {
        return R.layout.fragment_analyze
    }

    override fun init() {
        getData()
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        mAdapter = ArrangeThreeAnalyzeAdapter()
        recycler_view.adapter = mAdapter
        mAdapter.setData(mList)
    }

    fun getData() {
        val arrayList = ETCFUtils(this).analyze(0)
        val period = ETCFUtils(this).period
        for (item in arrayList) {
            val bean = ArrangeThreeAnalyzeBean()
            bean.periods = period
            bean.chance = item.chance
            bean.coveringChance = item.coveringChance
            bean.continuousChance = item.continuousFrequency
            bean.number = item.number
            if (item.currentOmit == 0) {
                bean.aggregate = item.chance + item.coveringChance + item.continuousFrequency
            } else {
                bean.aggregate = item.chance + item.coveringChance
            }
            mList.add(bean)
        }
    }
}
