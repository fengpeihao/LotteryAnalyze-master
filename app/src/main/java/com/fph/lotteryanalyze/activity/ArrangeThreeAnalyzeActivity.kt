package com.fph.lotteryanalyze.activity

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.SparseArray
import com.fph.lotteryanalyze.R
import com.fph.lotteryanalyze.base.BaseActivity
import com.fph.lotteryanalyze.bean.ArrangeThreeAnalyzeBean
import com.fph.lotteryanalyze.bean.ArrangeThreeGuessBean
import com.fph.lotteryanalyze.db.ArrangeThreeDaoUtils
import com.fph.lotteryanalyze.fragment.ArrangeThreeGuessFragment
import com.fph.lotteryanalyze.utils.ArrangeThreeUtils
import kotlinx.android.synthetic.main.activity_arrange_three_analyze.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ArrangeThreeAnalyzeActivity : BaseActivity() {

    val mList = arrayListOf<ArrangeThreeGuessFragment>()
    val map = SparseArray<ArrayList<ArrangeThreeGuessBean>>()
    val titles = arrayOf("猜测1", "猜测2", "猜测3")

    init {
        for (i in 0..2) {
            val fragment = ArrangeThreeGuessFragment()
            mList.add(fragment)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_arrange_three_analyze
    }

    override fun init() {
        view_pager.adapter = Adapter(supportFragmentManager)
        view_pager.offscreenPageLimit = 3
        tab_layout.setupWithViewPager(view_pager)
        analyze()
    }

    inner class Adapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment {
            return mList[position]
        }

        override fun getCount(): Int {
            return mList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }

    fun analyze() {
        showLoading()
        GlobalScope.launch {
            val guessList1 = ArrayList<ArrangeThreeGuessBean>()
            val guessList2 = ArrayList<ArrangeThreeGuessBean>()
            val guessList3 = ArrayList<ArrangeThreeGuessBean>()
            for (i in 0..100) {
                var guessBean1 = ArrangeThreeGuessBean()
                var guessBean2 = ArrangeThreeGuessBean()
                var guessBean3 = ArrangeThreeGuessBean()
                val utils = ArrangeThreeUtils(this@ArrangeThreeAnalyzeActivity)
                val result = utils.analyze(i)
                var list1 = arrayListOf<ArrangeThreeAnalyzeBean>()
                var list2 = arrayListOf<ArrangeThreeAnalyzeBean>()
                var list3 = arrayListOf<ArrangeThreeAnalyzeBean>()
                for (i in 0..9) {
                    val firstItem = result[0][i]
                    val secondItem = result[1][i]
                    val threeItem = result[2][i]
                    val allItem = result[-1][i]

                    val bean1 = ArrangeThreeAnalyzeBean()
                    val bean2 = ArrangeThreeAnalyzeBean()
                    val bean3 = ArrangeThreeAnalyzeBean()
                    bean1.number = allItem.number
                    bean2.number = allItem.number
                    bean3.number = allItem.number
                    bean1.chance = allItem.chance
                    bean1.coveringChance = allItem.coveringChance
                    bean1.continuousChance = allItem.continuousFrequency
                    bean2.chance = firstItem.chance + secondItem.chance + threeItem.chance /*+ allItem.chance*/
                    bean2.coveringChance = firstItem.coveringChance + secondItem.coveringChance + threeItem.coveringChance /*+ allItem.coveringChance*/
                    bean2.continuousChance = firstItem.continuousFrequency + secondItem.continuousFrequency + threeItem.continuousFrequency /*+ allItem.continuousFrequency*/
                    bean3.chance = firstItem.chance + secondItem.chance + threeItem.chance + allItem.chance
                    bean3.coveringChance = firstItem.coveringChance + secondItem.coveringChance + threeItem.coveringChance + allItem.coveringChance
                    bean3.continuousChance = firstItem.continuousFrequency + secondItem.continuousFrequency + threeItem.continuousFrequency + allItem.continuousFrequency
                    list1.add(setAggregate(bean1, allItem.currentOmit, allItem.continuousFrequency))
                    list2.add(setAggregate(bean2, firstItem.currentOmit + secondItem.currentOmit + threeItem.currentOmit,
                            firstItem.continuousFrequency + secondItem.continuousFrequency + threeItem.continuousFrequency))
                    list3.add(setAggregate(bean3, firstItem.currentOmit + secondItem.currentOmit + threeItem.currentOmit + allItem.currentOmit,
                            firstItem.continuousFrequency + secondItem.continuousFrequency + threeItem.continuousFrequency + allItem.continuousFrequency))

                }
                list1 = sortList(list1)
                list2 = sortList(list2)
                list3 = sortList(list3)
                if (utils.period.toInt() + 1 > 19351 && utils.period.toInt() < 20000) {
                    guessBean1.period = "20001"
                    guessBean2.period = "20001"
                    guessBean3.period = "20001"
                } else {
                    guessBean1.period = (utils.period.toInt() + 1).toString()
                    guessBean2.period = (utils.period.toInt() + 1).toString()
                    guessBean3.period = (utils.period.toInt() + 1).toString()
                }
                guessBean1.resultNumber = ArrangeThreeDaoUtils(this@ArrangeThreeAnalyzeActivity).queryDataByPeriod(guessBean1.period)?.number
                        ?: ""
                guessBean2.resultNumber = ArrangeThreeDaoUtils(this@ArrangeThreeAnalyzeActivity).queryDataByPeriod(guessBean2.period)?.number
                        ?: ""
                guessBean3.resultNumber = ArrangeThreeDaoUtils(this@ArrangeThreeAnalyzeActivity).queryDataByPeriod(guessBean3.period)?.number
                        ?: ""
                guessList1.add(guessResult(guessBean1, list1))
                guessList2.add(guessResult(guessBean2, list2))
                guessList3.add(guessResult(guessBean3, list3))
            }
            map.put(0, guessList1)
            map.put(1, guessList2)
            map.put(2, guessList3)
            launch(context = Dispatchers.Main){
                cancelLoading()
                for (i in 0..2) {
                    mList[i].setData(map.get(i))
                }
            }
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

    private fun guessResult(guessBean: ArrangeThreeGuessBean, list: ArrayList<ArrangeThreeAnalyzeBean>): ArrangeThreeGuessBean {
        for (item in list) {
            guessBean.guessNumber += item.number
        }
        guessBean.isCorrectly = !guessBean.resultNumber.contains(guessBean.guessNumber.last()) &&
                !guessBean.resultNumber.contains(guessBean.guessNumber[8]) &&
                !guessBean.resultNumber.contains(guessBean.guessNumber[7]) &&
//                !guessBean.resultNumber.contains(guessBean.guessNumber[6]) &&
                !isDoubleNumber(guessBean.resultNumber)
        return guessBean
    }

    private fun isDoubleNumber(number: String): Boolean {
        if (number.isEmpty()) return false
        val split = number.split(" ")
        return split[0] == split[1] || split[0] == split[2] || split[1] == split[2]
    }


}