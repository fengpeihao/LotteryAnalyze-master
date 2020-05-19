package com.fph.lotteryanalyze.utils

import android.content.Context
import android.util.SparseArray
import com.fph.lotteryanalyze.bean.ArrangeThreeFrequencyBean
import com.fph.lotteryanalyze.db.ArrangeThreeDaoUtils
import com.fph.lotteryanalyze.db.ArrangeThreeEntity
import kotlin.math.max

class ArrangeThreeUtils(context: Context) {

    val ALL: Int = -1
    val FIRST: Int = 0
    val SECOND: Int = 1
    val THREE: Int = 2
    var map: SparseArray<ArrayList<ArrangeThreeFrequencyBean>> = SparseArray()
    val atNumber = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
    var entityList = ArrangeThreeDaoUtils(context).ascQueryAllData()
    var period: String = ""

    init {
        for (i in ALL..THREE) {
            val list = ArrayList<ArrangeThreeFrequencyBean>()
            for (item in atNumber) {
                val bean = ArrangeThreeFrequencyBean()
                bean.location = i
                bean.number = item
                bean.totalPeriod = entityList.size
                list.add(bean)
            }
            map.put(i, list)
        }
    }

    fun analyze(startPeriod: Int): SparseArray<ArrayList<ArrangeThreeFrequencyBean>> {
        period = entityList[entityList.size - 1 - startPeriod].periods
        for (i in 0 until entityList.size - startPeriod) {
            val item = entityList[i]
            setData(ALL, item, map[ALL]!!)
            setData(FIRST, item, map[FIRST]!!)
            setData(SECOND, item, map[SECOND]!!)
            setData(THREE, item, map[THREE]!!)
        }
        return map
    }

   private fun setData(location: Int, entity: ArrangeThreeEntity, list: ArrayList<ArrangeThreeFrequencyBean>) {
        val number = when (location) {
            ALL -> entity.number
            FIRST -> entity.firstNumber
            SECOND -> entity.secondNumber
            THREE -> entity.threeNumber
            else -> entity.number
        }

        for (item in list) {
            if (number.contains(item.number)) {
                item.displayCount++ //出现次数+1
                //设置连续出现集合
                if (item.continuousCount == 0) {
                    val continuousBean = ArrangeThreeFrequencyBean.ContinuousBean()
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
                    val omitContinuous = ArrangeThreeFrequencyBean.ContinuousBean()
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