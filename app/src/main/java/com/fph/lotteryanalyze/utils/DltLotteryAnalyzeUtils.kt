package com.fph.lotteryanalyze.utils

import android.content.Context
import android.util.SparseArray
import com.fph.lotteryanalyze.bean.LotteryFrequencyBean
import com.fph.lotteryanalyze.db.DltLotteryDaoUtils
import com.fph.lotteryanalyze.db.DltLotteryEntity
import kotlin.math.max

class DltLotteryAnalyzeUtils(context: Context) : BaseAnalyzeUtils() {
    private val dltBlueNumbers = arrayOf("01", "02", "03", "04", "05", "06", "07", "08", "09",
            "10", "11", "12")
    private val dltRedNumbers = arrayOf("01", "02", "03", "04", "05", "06", "07", "08", "09",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
            "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35")
    private var map: SparseArray<ArrayList<LotteryFrequencyBean>> = SparseArray()
    val entityList = DltLotteryDaoUtils(context).ascQueryAllData()

    init {
        val redBeans = arrayListOf<LotteryFrequencyBean>()
        for (item in dltRedNumbers) {
            val bean = LotteryFrequencyBean()
            bean.location = 2
            bean.number = item
            bean.totalPeriod = entityList.size
            redBeans.add(bean)
        }
        map.put(0, redBeans)
        val blueBeans = arrayListOf<LotteryFrequencyBean>()
        for (item in dltBlueNumbers) {
            val bean = LotteryFrequencyBean()
            bean.location = 2
            bean.number = item
            bean.totalPeriod = entityList.size
            blueBeans.add(bean)
        }
        map.put(1, blueBeans)
    }

    override fun analyze(startPeriod: Int): SparseArray<ArrayList<LotteryFrequencyBean>> {
        period = entityList[entityList.size - 1 - startPeriod].expect
        for (i in 0 until entityList.size - startPeriod) {
            val entity = entityList[i]
            val split = entity.opencode.split("+")
            analyzeData(split[0], entity, map[0])
            analyzeData(split[1], entity, map[1])
        }
        return map
    }

    private fun analyzeData(openNumber: String, entity: DltLotteryEntity, list: List<LotteryFrequencyBean>) {
        for (item in list) {
            if (openNumber.contains(item.number)) {
                item.displayCount++ //出现次数+1
                //设置连续出现集合
                if (item.continuousCount == 0) {
                    val continuousBean = LotteryFrequencyBean.ContinuousBean()
                    continuousBean.startPeriods = entity.expect
                    continuousBean.periodCount = 1
                    item.displayList.add(continuousBean)
                } else {
                    item.displayList.lastOrNull()?.let {
                        it.periodCount++
                    }
                }
                item.omitList.lastOrNull()?.let {
                    it.endPeriods = entity.expect
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
                    val omitContinuous = LotteryFrequencyBean.ContinuousBean()
                    omitContinuous.startPeriods = entity.expect
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
                        it.endPeriods = entity.expect
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