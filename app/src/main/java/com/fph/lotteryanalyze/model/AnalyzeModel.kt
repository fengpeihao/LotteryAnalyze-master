package com.fph.lotteryanalyze.model

import com.feilu.kotlindemo.base.Common2Subscriber
import com.feilu.kotlindemo.base.RxUtils
import com.fph.lotteryanalyze.api.UrlContants
import com.fph.lotteryanalyze.bean.LotteryBean
import com.fph.lotteryanalyze.contract.AnalyzeContract

/**
 * Created by fengpeihao on 2018/4/19.
 */
class AnalyzeModel : AnalyzeContract.Model {

   override fun getNewestData(type: String,subscriber: Common2Subscriber<LotteryBean>) {
       var url = UrlContants.SuperLottoUrl
        if("ssq".equals(type)){
            url = UrlContants.DoubleBallUrl
        }
       apiService.getNewestData(url)
               .compose(RxUtils.rxSchedulerHelper())
               .subscribeWith(subscriber)
    }
}