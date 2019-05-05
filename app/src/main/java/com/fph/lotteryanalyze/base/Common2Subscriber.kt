package com.fph.lotteryanalyze.base

import io.reactivex.subscribers.ResourceSubscriber

/**
 * Created by fengpeihao on 2017/11/11.
 */
abstract class Common2Subscriber<T> : ResourceSubscriber<T> {

    constructor() : super()

    override fun onNext(t: T) {
        getData(t)
    }

    override fun onComplete() {

    }

    override fun onError(e: Throwable) {
        netError("网络异常")
    }

    abstract fun netError(message: String)

    abstract fun getData(t: T)
}