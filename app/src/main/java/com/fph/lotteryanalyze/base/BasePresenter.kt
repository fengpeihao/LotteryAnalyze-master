package com.fph.lotteryanalyze.base

/**
 * Created by fengpeihao on 2017/7/31.
 */

open class BasePresenter<V : BaseView, M : BaseModel> {

    protected lateinit var mView: V
    protected lateinit var mModel: M

    fun setVM(view: V,model: M) {
        mView = view
        mModel = model
    }
}
