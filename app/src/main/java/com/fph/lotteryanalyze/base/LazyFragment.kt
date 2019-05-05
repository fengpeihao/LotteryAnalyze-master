package com.fph.lotteryanalyze.base

import android.os.Bundle
import android.view.View
import com.fph.lotteryanalyze.base.BaseFragment

/**
 * Created by fengpeihao on 2018/1/22.
 */

abstract class LazyFragment : BaseFragment() {
    protected var isFragmentCreated = false
    protected var isVisibleToUser = false
    protected var isFirstShow = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFragmentCreated = true
        if (isVisibleToUser && isFirstShow) {
            isFirstShow = false
            lazyInit()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        this.isVisibleToUser = isVisibleToUser
        if (isVisibleToUser && isFragmentCreated && isFirstShow) {
            isFirstShow = false
            lazyInit()
        }
        super.setUserVisibleHint(isVisibleToUser)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && isFragmentCreated && isFirstShow) {
            isFirstShow = false
            lazyInit()
        }
    }

    abstract fun lazyInit()
}
