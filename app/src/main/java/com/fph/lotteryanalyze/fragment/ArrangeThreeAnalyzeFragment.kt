package com.fph.lotteryanalyze.fragment

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.fph.lotteryanalyze.R
import com.fph.lotteryanalyze.adapter.ArrangeThreeAnalyzeAdapter
import com.fph.lotteryanalyze.base.BaseFragment
import com.fph.lotteryanalyze.bean.ArrangeThreeAnalyzeBean
import com.fph.lotteryanalyze.bean.ArrangeThreeFrequencyBean
import kotlinx.android.synthetic.main.fragment_analyze.*

class ArrangeThreeAnalyzeFragment : BaseFragment() {

    var mList: ArrayList<ArrangeThreeAnalyzeBean> = ArrayList()
    lateinit var mAdapter: ArrangeThreeAnalyzeAdapter
    override fun getLayoutId(): Int {
        return R.layout.fragment_analyze
    }

    override fun init(savedInstanceState: Bundle?) {
        getIntentData()
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        mAdapter = ArrangeThreeAnalyzeAdapter()
        recycler_view.adapter = mAdapter
        mAdapter.setData(mList)
    }

    fun getIntentData() {
        val arrayList = arguments!!.getSerializable("data") as List<ArrangeThreeFrequencyBean>
        val period = arguments!!.getString("period")
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