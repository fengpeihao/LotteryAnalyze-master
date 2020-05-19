package com.fph.lotteryanalyze.utils

import android.util.SparseArray
import com.fph.lotteryanalyze.bean.LotteryFrequencyBean


abstract class BaseAnalyzeUtils {
    var period: String = ""

    abstract fun analyze(startPeriod: Int): SparseArray<ArrayList<LotteryFrequencyBean>>
}