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

    override fun getNewestData(type: String) {
//        mModel.getNewestData(type, object : Common2Subscriber<LotteryBean>() {
//            override fun netError(message: String) {
//
//            }
//
//            override fun getData(t: LotteryBean) {
//                if (t.data != null)
//                    mView.getNewestData(t.data!!)
//            }
//        })
        if ("ssq".equals(type)) {
            val queryLast = LotteryDaoUtils(mView as Context).queryLast()
            mView.getNewestData(queryLast)
        } else {
            val queryLast = DltLotteryDaoUtils(mView as Context).queryLast()
            val arrayList = ArrayList<LotteryEntity>()
            for (item in queryLast) {
                val lotteryEntity = LotteryEntity()
                lotteryEntity.opencode = item.opencode
                lotteryEntity.expect = item.expect
                lotteryEntity.opentime = item.opentime
                arrayList.add(lotteryEntity)
            }
            mView.getNewestData(arrayList)
        }
    }
}