package com.fph.lotteryanalyze.model

import com.fph.lotteryanalyze.api.UrlContants
import com.fph.lotteryanalyze.base.Common2Subscriber
import com.fph.lotteryanalyze.base.RxUtils
import com.fph.lotteryanalyze.bean.DltLotteryBean
import com.fph.lotteryanalyze.contract.DltAnalyzeContract

/**
 * Created by fengpeihao on 2018/4/19.
 */
class DltAnalyzeModel : DltAnalyzeContract.Model {

   override fun getDltData(subscriber: Common2Subscriber<DltLotteryBean>) {
       var url = UrlContants.SuperLottoUrl
       apiService.getDltData(url)
               .compose(RxUtils.rxSchedulerHelper())
               .subscribeWith(subscriber)
    }
}