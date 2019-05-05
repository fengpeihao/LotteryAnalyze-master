package com.fph.lotteryanalyze.presenter

import android.content.Context
import com.fph.lotteryanalyze.contract.DltAnalyzeContract
import com.fph.lotteryanalyze.db.DltLotteryDaoUtils
import com.fph.lotteryanalyze.model.DltAnalyzeModel

/**
 * Created by fengpeihao on 2018/4/19.
 */
class DltAnalyzePresenter : DltAnalyzeContract.Presenter {

    constructor(view: DltAnalyzeContract.View) : super() {
        setVM(view, DltAnalyzeModel())
    }

    override fun getDltData() {
        val queryLast = DltLotteryDaoUtils(mView as Context).queryLast()

        mView.getDltData(queryLast)
    }
}