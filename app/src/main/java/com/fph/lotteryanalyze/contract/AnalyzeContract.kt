package com.fph.lotteryanalyze.contract

import com.fph.lotteryanalyze.base.BaseModel
import com.fph.lotteryanalyze.base.BasePresenter
import com.fph.lotteryanalyze.base.BaseView
import com.fph.lotteryanalyze.base.Common2Subscriber
import com.fph.lotteryanalyze.bean.LotteryBean
import com.fph.lotteryanalyze.db.LotteryEntity
import com.fph.lotteryanalyze.model.AnalyzeModel

/**
 * Created by fengpeihao on 2018/4/19.
 */
interface AnalyzeContract {
    interface View : BaseView {
        fun getSsqData(data: List<LotteryEntity>)
    }

    interface Model : BaseModel {
        fun getSsqData(subscriber: Common2Subscriber<LotteryBean>)
    }

    abstract class Presenter : BasePresenter<View, AnalyzeModel>() {
        abstract fun getSsqData()
    }
}