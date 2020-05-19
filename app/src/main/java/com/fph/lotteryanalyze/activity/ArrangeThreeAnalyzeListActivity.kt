package com.fph.lotteryanalyze.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import com.fph.lotteryanalyze.utils.ArrangeThreeUtils
import com.fph.lotteryanalyze.R
import com.fph.lotteryanalyze.base.BaseActivity
import com.fph.lotteryanalyze.bean.ArrangeThreeFrequencyBean
import com.fph.lotteryanalyze.fragment.ArrangeThreeAnalyzeFragment
import kotlinx.android.synthetic.main.activity_arrange_three_analyze_list.*

class ArrangeThreeAnalyzeListActivity : BaseActivity() {

    val mTitles = arrayOf("百位", "十位", "个位", "全部")
    val mFragments = arrayListOf<ArrangeThreeAnalyzeFragment>()

    init {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_arrange_three_analyze_list
    }

    override fun init() {
        val analyzeAdapter = AnalyzeAdapter(supportFragmentManager)
        view_pager.adapter = analyzeAdapter
        tab_layout.setupWithViewPager(view_pager)
        val utils = ArrangeThreeUtils(this)
        val analyze = utils.analyze(0)
        printLog(analyze[0])
        val period = (utils.period.toInt() + 1).toString()
        for (i in mTitles.indices) {
            val fragment = ArrangeThreeAnalyzeFragment()
            val bundle = Bundle()
            bundle.putSerializable("data", analyze[i - 1])
            bundle.putString("period", period)
            fragment.arguments = bundle
            mFragments.add(fragment)
        }
    }

    fun printLog(list: List<ArrangeThreeFrequencyBean>) {
        for (item in list) {
            Log.e("***", item.toString())
        }
    }

    inner class AnalyzeAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getCount(): Int {
            return mTitles.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mTitles[position]
        }
    }
}
