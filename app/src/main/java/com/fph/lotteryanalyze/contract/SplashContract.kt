package com.fph.lotteryanalyze.contract

import com.fph.lotteryanalyze.base.BaseModel
import com.fph.lotteryanalyze.base.BasePresenter
import com.fph.lotteryanalyze.base.BaseView
import com.fph.lotteryanalyze.activity.SplashActivity
import com.fph.lotteryanalyze.model.SplashModel

/**
 * Created by fengpeihao on 2018/4/19.
 */
interface SplashContract {
    interface View : BaseView {
//        fun getNewestData(data: List<LotteryEntity>)
    }

    interface Model : BaseModel {
//        fun getNewestData(type: String, subscriber: Common2Subscriber<LotteryBean>)
    }

    abstract class Presenter : BasePresenter<SplashActivity, SplashModel>() {
//        abstract fun getNewestData(type: String)
    }
}