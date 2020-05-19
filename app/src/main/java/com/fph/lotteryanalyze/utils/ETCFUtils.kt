package com.fph.lotteryanalyze.utils

import android.content.Context
import android.util.SparseArray
import com.fph.lotteryanalyze.bean.ArrangeThreeFrequencyBean
import com.fph.lotteryanalyze.bean.ETCFFrequencyBean
import com.fph.lotteryanalyze.db.ArrangeThreeDaoUtils
import com.fph.lotteryanalyze.db.ArrangeThreeEntity
import com.fph.lotteryanalyze.db.ETCFBean
import com.fph.lotteryanalyze.db.ETCFDaoUtils
import kotlin.math.max

class ETCFUtils(context: Context) {

    var list: ArrayList<ETCFFrequencyBean> = arrayListOf()
    val atNumber = arrayOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11")
    var entityList = ETCFDaoUtils(context).ascQueryAllData()
    var period: String = ""

    init {
        for (item in atNumber) {
            val bean = ETCFFrequencyBean()
            bean.number = item
            bean.totalPeriod = entityList.size
            list.add(bean)
        }
    }

    fun analyze(startPeriod: Int): ArrayList<ETCFFrequencyBean> {
        period = entityList[entityList.size - 1 - startPeriod].periods
        for (i in 0 until entityList.size - startPeriod) {
            val item = entityList[i]
            setData(item)
        }
        return list
    }

    private fun setData(entity: ETCFBean) {
        val number = entity.openNumber

        for (item in list) {
            if (number.contains(item.number)) {
                item.displayCount++ //出现次数+1
                //设置连续出现集合
                if (item.continuousCount == 0) {
                    val continuousBean = ETCFFrequencyBean.ContinuousBean()
                    continuousBean.startPeriods = entity.periods
                    continuousBean.periodCount = 1
                    item.displayList.add(continuousBean)
                } else {
                    item.displayList.lastOrNull()?.let {
                        it.periodCount++
                    }
                }
                item.omitList.lastOrNull()?.let {
                    it.endPeriods = entity.periods
                    it.periodCount = item.omitCount
                    item.maxOmit = max(item.maxOmit, item.omitCount)
                }

                //当前连续出现次数+1
                item.continuousCount++
                //重置当前遗漏次数
                item.omitCount = 0
            } else {
                //设置连续遗漏集合
                if (item.omitCount == 0) {
                    val omitContinuous = ETCFFrequencyBean.ContinuousBean()
                    omitContinuous.startPeriods = entity.periods
                    omitContinuous.periodCount = 1
                    item.omitList.add(omitContinuous)
                } else {
                    item.omitList.lastOrNull()?.let {
                        it.periodCount++
                    }
                }
                item.displayList.lastOrNull()?.let {
                    if (it.periodCount == 1) {
                        item.displayList.remove(it)
                    } else {
                        it.endPeriods = entity.periods
                    }
                }

                //当前遗漏次数+1
                item.omitCount++
                //重置当前连续次数
                item.continuousCount = 0
            }
        }
    }
}