package com.fph.lotteryanalyze.presenter

import android.content.Context
import com.fph.lotteryanalyze.contract.AnalyzeContract
import com.fph.lotteryanalyze.db.DltLotteryDaoUtils
import com.fph.lotteryanalyze.db.LotteryDaoUtils
import com.fph.lotteryanalyze.db.LotteryEntity
import com.fph.lotteryanalyze.model.AnalyzeModel

/**
 * Created by fengpeihao on 2018/4/19.
 */
class AnalyzePresenter : AnalyzeContract.Presenter {

    constructor(view: AnalyzeContract.View) : super() {
        setVM(view, AnalyzeModel())
    }

    override fun getSsqData() {
        val queryLast = LotteryDaoUtils(mView as Context).queryLast()
        mView.getSsqData(queryLast)
    }
}