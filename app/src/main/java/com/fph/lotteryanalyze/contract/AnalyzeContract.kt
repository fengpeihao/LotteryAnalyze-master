package com.fph.lotteryanalyze.contract

import com.feilu.kotlindemo.base.BaseModel
import com.feilu.kotlindemo.base.BasePresenter
import com.feilu.kotlindemo.base.BaseView
import com.feilu.kotlindemo.base.Common2Subscriber
import com.fph.lotteryanalyze.bean.LotteryBean
import com.fph.lotteryanalyze.db.LotteryEntity
import com.fph.lotteryanalyze.model.AnalyzeModel

/**
 * Created by fengpeihao on 2018/4/19.
 */
interface AnalyzeContract {
    interface View : BaseView {
        fun getNewestData(data: List<LotteryEntity>)
    }

    interface Model : BaseModel {
        fun getNewestData(type: String, subscriber: Common2Subscriber<LotteryBean>)
    }

    abstract class Presenter : BasePresenter<View, AnalyzeModel>() {
        abstract fun getNewestData(type: String)
    }
}