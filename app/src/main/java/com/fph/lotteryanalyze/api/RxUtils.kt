package com.fph.lotteryanalyze.base

import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.components.support.RxFragment
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by fengpeihao on 2017/11/11.
 */
open class RxUtils {

    companion object {

        /**
         * 统一线程处理
         * @param <T>
         * @return
        </T> */
        fun <T> rxSchedulerHelper(activity: RxAppCompatActivity): FlowableTransformer<T, T> {    //compose简化线程
            return FlowableTransformer { observable ->
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
            }
        }

        /**
         * 统一线程处理
         * @param <T>
         * @return
        </T> */
        fun <T> rxSchedulerHelper(fragment: RxFragment): FlowableTransformer<T, T> {    //compose简化线程
            return FlowableTransformer { observable ->
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
            }
        }

        /**
         * 统一线程处理
         * @param <T>
         * @return
        </T> */
        fun <T> rxSchedulerHelper(): FlowableTransformer<T, T> {    //compose简化线程
            return FlowableTransformer { observable ->
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }
}