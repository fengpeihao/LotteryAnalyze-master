package com.fph.lotteryanalyze.contract

import com.fph.lotteryanalyze.base.BaseModel
import com.fph.lotteryanalyze.base.BasePresenter
import com.fph.lotteryanalyze.base.BaseView
import com.fph.lotteryanalyze.base.Common2Subscriber
import com.fph.lotteryanalyze.bean.DltLotteryBean
import com.fph.lotteryanalyze.db.DltLotteryEntity
import com.fph.lotteryanalyze.model.DltAnalyzeModel

/**
 * Created by fengpeihao on 2018/4/19.
 */
interface DltAnalyzeContract {
    interface View : BaseView {
        fun getDltData(data: List<DltLotteryEntity>)
    }

    interface Model : BaseModel {
        fun getDltData(subscriber: Common2Subscriber<DltLotteryBean>)
    }

    abstract class Presenter : BasePresenter<View, DltAnalyzeModel>() {
        abstract fun getDltData()
    }
}