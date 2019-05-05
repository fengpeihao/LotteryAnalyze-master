package com.fph.lotteryanalyze.model

import com.fph.lotteryanalyze.api.UrlContants
import com.fph.lotteryanalyze.base.Common2Subscriber
import com.fph.lotteryanalyze.base.RxUtils
import com.fph.lotteryanalyze.bean.LotteryBean
import com.fph.lotteryanalyze.contract.AnalyzeContract
import com.fph.lotteryanalyze.contract.DltAnalyzeContract

/**
 * Created by fengpeihao on 2018/4/19.
 */
class AnalyzeModel : AnalyzeContract.Model {

    override fun getSsqData(subscriber: Common2Subscriber<LotteryBean>) {
        var url = UrlContants.DoubleBallUrl
        apiService.getSsqData(url)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(subscriber)
    }
}