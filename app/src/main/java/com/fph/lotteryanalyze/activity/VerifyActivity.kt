package com.fph.lotteryanalyze.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.fph.lotteryanalyze.R
import com.fph.lotteryanalyze.base.BaseActivity
import com.fph.lotteryanalyze.bean.ArrangeThreeAnalyzeBean
import com.fph.lotteryanalyze.bean.LotteryGuessBean
import com.fph.lotteryanalyze.db.DltLotteryDaoUtils
import com.fph.lotteryanalyze.db.LotteryDaoUtils
import com.fph.lotteryanalyze.fragment.VerifyFragment
import com.fph.lotteryanalyze.utils.BaseAnalyzeUtils
import com.fph.lotteryanalyze.utils.DltLotteryAnalyzeUtils
import com.fph.lotteryanalyze.utils.LotteryAnalyzeUtils
import kotlinx.android.synthetic.main.activity_verify.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VerifyActivity : BaseActivity() {

    private val mTitles = arrayOf("红球", "蓝球")
    private val mTypes = arrayOf("red", "blue")
    private val mFragments = arrayListOf<VerifyFragment>()

    override fun getLayoutId(): Int {
        return R.layout.activity_verify
    }

    init {
        for (item in mTypes) {
            val fragment = VerifyFragment.getInstance(item)
            mFragments.add(fragment)
        }
    }

    override fun init() {
        tab_layout.setupWithViewPager(view_pager)
        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(i: Int): Fragment {
                return mFragments[i]
            }

            override fun getCount(): Int {
                return mTitles.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return mTitles[position]
            }
        }
        getData()
    }

    private fun getData() {
        showLoading()
        GlobalScope.launch {
            val category = intent.getStringExtra("category")
            val guessList = ArrayList<LotteryGuessBean>()
            if (category == "ssq") {
                val utils = LotteryAnalyzeUtils(this@VerifyActivity)
                analyze(guessList, utils, category)
            } else {
                val utils = DltLotteryAnalyzeUtils(this@VerifyActivity)
                analyze(guessList, utils, category)
            }
            launch(Dispatchers.Main) {
                cancelLoading()
                for (item in mFragments) {
                    item.setData(guessList)
                }
            }
        }
    }

    private fun analyze(guessList: ArrayList<LotteryGuessBean>, utils: BaseAnalyzeUtils, category: String) {
        for (i in 0..100) {
            val lotteryFrequencyBeans = utils.analyze(i)
            var redList = arrayListOf<ArrangeThreeAnalyzeBean>()
            var blueList = arrayListOf<ArrangeThreeAnalyzeBean>()
            for (item in lotteryFrequencyBeans[0]) {
                val analyzeBean = ArrangeThreeAnalyzeBean()
                analyzeBean.number = item.number
                analyzeBean.continuousChance = item.continuousFrequency
                analyzeBean.coveringChance = item.coveringChance
                analyzeBean.chance = item.chance
                redList.add(setAggregate(analyzeBean, item.currentOmit, item.continuousFrequency))
            }
            for (item in lotteryFrequencyBeans[1]) {
                val analyzeBean = ArrangeThreeAnalyzeBean()
                analyzeBean.number = item.number
                analyzeBean.continuousChance = item.continuousFrequency
                analyzeBean.coveringChance = item.coveringChance
                analyzeBean.chance = item.chance
                blueList.add(setAggregate(analyzeBean, item.currentOmit, item.continuousFrequency))
            }
            redList = sortList(redList)
            blueList = sortList(blueList)
            val guessBean = LotteryGuessBean()
            if (utils.period.toInt() + 1 > 19151 && utils.period.toInt() < 20000) {
                guessBean.period = "20001"
            } else {
                guessBean.period = (utils.period.toInt() + 1).toString()
            }
            if ("ssq" == category) {
                val lotteryEntity = LotteryDaoUtils(this).queryDataByExpect(guessBean.period)
                lotteryEntity?.let {
                    guessBean.openRedNumber = lotteryEntity.opencode!!.split("+")[0]
                    guessBean.openBlueNumber = lotteryEntity.opencode!!.split("+")[1]
                    guessBean.date = lotteryEntity.opentime
                }
            } else {
                val lotteryEntity = DltLotteryDaoUtils(this).queryDataByExpect(guessBean.period)
                lotteryEntity?.let {
                    guessBean.openRedNumber = lotteryEntity.opencode!!.split("+")[0]
                    guessBean.openBlueNumber = lotteryEntity.opencode!!.split("+")[1]
                    guessBean.date = lotteryEntity.opentime
                }
            }
            guessList.add(guessResult(category, guessBean, redList, blueList))
        }
    }

    private fun setAggregate(bean: ArrangeThreeAnalyzeBean, currentOmit: Int, continuousFrequency: Float): ArrangeThreeAnalyzeBean {
        bean.aggregate = if (currentOmit == 0) {
            bean.chance + bean.coveringChance + continuousFrequency
        } else {
            bean.chance + bean.coveringChance
        }
        return bean
    }

    private fun sortList(list: ArrayList<ArrangeThreeAnalyzeBean>): ArrayList<ArrangeThreeAnalyzeBean> {
        list.sortWith(object : Comparator<ArrangeThreeAnalyzeBean> {
            override fun compare(o1: ArrangeThreeAnalyzeBean, o2: ArrangeThreeAnalyzeBean): Int {
                return if (o1.aggregate > o2.aggregate) -1 else 1
            }
        })
        return list
    }

    private fun guessResult(category: String, guessBean: LotteryGuessBean, redList: ArrayList<ArrangeThreeAnalyzeBean>,
                            blueList: ArrayList<ArrangeThreeAnalyzeBean>): LotteryGuessBean {
        var redNum = 33
        var blueNum = 16
        if ("dlt" == category) {
            redNum = 35
            blueNum = 12
        }
        for (i in 0 until redNum) {
            val bean = redList[i]
            guessBean.guessRedNumber += bean.number
            if (i < redNum - 1) {
                guessBean.guessRedNumber += ","
            }
        }
        for (i in 0 until blueNum) {
            val bean = blueList[i]
            guessBean.guessBlueNumber += bean.number
            if (i < blueNum - 1) {
                guessBean.guessBlueNumber += ","
            }
        }
        return guessBean
    }
}
