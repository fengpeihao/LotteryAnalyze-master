package com.fph.lotteryanalyze.base

import com.fph.lotteryanalyze.api.ApiModel
import com.fph.lotteryanalyze.api.ApiService

/**
 * Created by fengpeihao on 2017/7/22.
 */

interface BaseModel {
    val apiService: ApiService
        get() = ApiModel.getApiService()
}
