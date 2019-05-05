package com.fph.lotteryanalyze.api

import com.fph.lotteryanalyze.bean.DltLotteryBean
import com.fph.lotteryanalyze.bean.LotteryBean
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by fengpeihao on 2018/4/19.
 */
interface ApiService {
    @GET
    fun getSsqData(@Url url: String): Flowable<LotteryBean>

    @GET
    fun getDltData(@Url url: String): Flowable<DltLotteryBean>

    @GET
    fun getHtmlData(@Url url: String):Flowable<String>
}