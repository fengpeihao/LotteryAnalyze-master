package com.fph.lotteryanalyze.bean

import com.fph.lotteryanalyze.db.LotteryEntity

/**
 * Created by fengpeihao on 2018/4/19.
 */
class LotteryBean {
    /**
     * rows : 20
     * code : ssq
     * info : 免费接口随机延迟3-6分钟，实时接口请访问www.opencai.net查询、购买或续费
     * data : [{"expect":"2018043","opencode":"01,04,06,08,21,24+07","opentime":"2018-04-17 21:18:20","opentimestamp":1523971100},{"expect":"2018042","opencode":"06,10,21,28,29,31+12","opentime":"2018-04-15 21:18:20","opentimestamp":1523798300},{"expect":"2018041","opencode":"07,08,20,23,24,32+13","opentime":"2018-04-12 21:18:20","opentimestamp":1523539100},{"expect":"2018040","opencode":"01,03,08,13,18,23+16","opentime":"2018-04-10 21:18:20","opentimestamp":1523366300},{"expect":"2018039","opencode":"08,12,18,19,23,32+03","opentime":"2018-04-08 21:18:20","opentimestamp":1523193500},{"expect":"2018038","opencode":"15,23,24,25,28,29+09","opentime":"2018-04-05 21:18:20","opentimestamp":1522934300},{"expect":"2018037","opencode":"01,06,07,08,27,30+10","opentime":"2018-04-03 21:18:20","opentimestamp":1522761500},{"expect":"2018036","opencode":"08,17,24,26,28,33+04","opentime":"2018-04-01 21:18:20","opentimestamp":1522588700},{"expect":"2018035","opencode":"07,10,11,17,23,28+15","opentime":"2018-03-29 21:18:20","opentimestamp":1522329500},{"expect":"2018034","opencode":"01,05,11,22,23,26+15","opentime":"2018-03-27 21:18:20","opentimestamp":1522156700},{"expect":"2018033","opencode":"04,19,20,22,28,33+06","opentime":"2018-03-25 21:18:20","opentimestamp":1521983900},{"expect":"2018032","opencode":"21,22,23,24,25,32+06","opentime":"2018-03-22 21:18:20","opentimestamp":1521724700},{"expect":"2018031","opencode":"02,16,18,19,27,30+14","opentime":"2018-03-20 21:18:20","opentimestamp":1521551900},{"expect":"2018030","opencode":"13,14,20,21,25,33+07","opentime":"2018-03-18 21:18:20","opentimestamp":1521379100},{"expect":"2018029","opencode":"01,02,09,14,22,25+05","opentime":"2018-03-15 21:18:20","opentimestamp":1521119900},{"expect":"2018028","opencode":"03,08,11,14,18,23+16","opentime":"2018-03-13 21:18:20","opentimestamp":1520947100},{"expect":"2018027","opencode":"02,07,09,14,18,28+05","opentime":"2018-03-11 21:18:20","opentimestamp":1520774300},{"expect":"2018026","opencode":"04,07,12,14,26,32+04","opentime":"2018-03-08 21:18:20","opentimestamp":1520515100},{"expect":"2018025","opencode":"04,13,16,19,21,25+14","opentime":"2018-03-06 21:18:20","opentimestamp":1520342300},{"expect":"2018024","opencode":"11,19,22,26,31,32+02","opentime":"2018-03-04 21:18:20","opentimestamp":1520169500}]
     */

    var rows: Int = 0
    var code: String? = null
    var info: String? = null
    var data: List<LotteryEntity>? = null
}