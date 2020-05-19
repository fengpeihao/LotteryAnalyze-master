package com.fph.lotteryanalyze.bean

import java.io.Serializable

class ArrangeThreeFrequencyBean : Serializable {
    /**
     * 号码 [0，9]
     */
    var number: String = ""

    /**
     * 总期数
     */
    var totalPeriod: Int = 0

    /**
     * 在号码中的位置 个位2，十位1，百位0，不分位-1
     */
    var location: Int = 0

    /**
     * [出现次数] 指该号码历史上出现的次数；
     */
    var displayCount: Int = 0

    /**
     * [理论次数] = [期数÷循环周期]指遗漏对象理论上应该出现的次数
     */
    var theoryCount: Float = 0f
        get() {
            return totalPeriod / cycle
        }

    /**
     * [循环周期] 循环周期是指理想情况下该对象多少期出现一次，它是一个理论值。一般而言，循环周期越大，则该对象的出现次数越少，循环周期越小，则该对象的出现次数越多。
     */
    var cycle: Float = 10f
        get() {
            if (location == -1)
                return 3f
            else
                return avgOmit
        }

    /**
     * [出现频率] = [出现次数÷期数×100%]出现频率是指该遗漏对象出现次数在全部遗漏对象中所占的比例；
     */
    var displayFrequency = 0f
        get() {
            return displayCount / totalPeriod.toFloat()
        }

    /**
     * [平均遗漏] 是指所有遗漏值的平均值；
     */
    var avgOmit: Float = 0f
        get() {
            var count = 0
            for (item in omitList) {
                count += item.periodCount
            }
            return count / omitList.size.toFloat()
        }

    /**
     * [理论遗漏] 是指理想情况下该对象遗漏的期数，它是一个理论值。
     */
    var theoryOmit: Float = 0f
        get() {
            return cycle
        }

    /**
     * [最大遗漏] 历史上遗漏的最大值
     */
    var maxOmit: Int = 0
    /**
     * 遗漏集合
     */
    var omitList = arrayListOf<ContinuousBean>()

    /**
     * [上次遗漏] 指该号码上次开出之前的遗漏次数；
     */
    var preOmit: Int = 0
        get() {
            if (omitCount == 0) {
                field = omitList.last().periodCount
            } else {
                field = omitList[omitList.size - 2].periodCount
            }
            return field
        }

    /**
     * [本期遗漏] 指该号码自上次开出之后的遗漏次数；
     */
    var currentOmit: Int = 0
        get() {
            if (omitCount != 0) {
                omitList.lastOrNull()?.let {
                    field = it.periodCount
                }
            } else {
                field = omitCount
            }
            return field
        }

    /**
     * 缓存遗漏期数
     */
    var omitCount: Int = 0

    /**
     * [欲出几率] = [本期遗漏÷平均遗漏]
     * 欲出几率反应了该遗漏对象的理想出现几率，如果欲出几率大于2，则该形态可称之为冷态。
     * 欲出几率为2.55说明如果按理想状况，该号码从最近一次出现那一期到现在应该要再出现2.55次，但实际一次未出现。
     * 因为是一个理想值，所以并不能完全反应实际情况，只能作为参考。
     */
    var chance: Float = 0f
        get() {
            return (currentOmit % avgOmit) / avgOmit
//            val fl = currentOmit / avgOmit
//            return fl.toString().replace(fl.toString().split(".")[0], "0").toFloat()
        }

    /**
     * [回补几率] = (上期遗漏-本期遗漏)÷循环周期
     * 因为虽然某些组合会出现一个比较大的冷态，但是在冷态之后一般不会接着再出一个大冷态，
     * 而是在一个周期内便再次出现甚至多次出现，因此回补几率越大的组合，从统计规律来看近期越容易出。
     */
    var coveringChance: Float = 0f
        get() {
            return ((preOmit - currentOmit) % cycle) / cycle
        }

    /**
     * 连续出现集合
     */
    var displayList = arrayListOf<ContinuousBean>()

    /**
     * 连续期数缓存
     */
    var continuousCount: Int = 0

    /**
     * 连续出现几率 连续出现次/出现的总次数
     */
    var continuousFrequency: Float = 0f
        get() {
            var count = 0
            for (item in displayList) {
                count += item.periodCount - 1
            }
            return count / displayCount.toFloat()
        }

    var finalFrequency: Float = 0f
        get() {
            if (currentOmit == 0) {
                return chance + coveringChance + continuousFrequency
            } else {
                return chance + coveringChance
            }
        }

    /**
     * 连续
     */
    class ContinuousBean {
        var startPeriods: String = ""
        var endPeriods: String = ""
        var periodCount: Int = 0
    }

    override fun toString(): String {
        return "ArrangeThreeFrequencyBean(number='$number', " +
                "总期数=$totalPeriod, " +
                "位置=$location, " +
                "出现次数=$displayCount, " +
                "理论次数=$theoryCount, " +
                "出现频率=$displayFrequency, " +
                "平均遗漏=$avgOmit, " +
                "理论遗漏=$theoryOmit, " +
                "历史最大遗漏=$maxOmit, " +
                "上期遗漏=$preOmit, " +
                "本期遗漏=$currentOmit, " +
                "欲出几率=$chance, " +
                "回补几率=$coveringChance, " +
                "连续出现几率=$continuousFrequency)"
    }
}

