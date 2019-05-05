package com.fph.lotteryanalyze.base

import android.app.Application
import android.content.Context

/**
 * Created by fengpeihao on 2018/1/8.
 */
class App : Application() {
    companion object {
        lateinit var context: Context;
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}