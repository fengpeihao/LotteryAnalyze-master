package com.fph.lotteryanalyze.bean

/**
 * Created by fengpeihao on 2018/4/25.
 */

class AnalyzeBean {
    var redCode: String? = null
    var blueCode: String? = null
    var num: Int = 0

    constructor(redCode: String?, blueCode: String?, num: Int) {
        this.redCode = redCode
        this.blueCode = blueCode
        this.num = num
    }

    constructor()
    constructor(redCode: String?, num: Int) {
        this.redCode = redCode
        this.num = num
    }
}
