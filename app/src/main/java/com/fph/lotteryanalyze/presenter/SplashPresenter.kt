package com.fph.lotteryanalyze.presenter

import android.annotation.SuppressLint
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.fph.lotteryanalyze.base.Common2Subscriber
import com.fph.lotteryanalyze.base.RxUtils
import com.fph.lotteryanalyze.activity.SplashActivity
import com.fph.lotteryanalyze.api.ApiModel
import com.fph.lotteryanalyze.api.UrlContants
import com.fph.lotteryanalyze.contract.SplashContract
import com.fph.lotteryanalyze.db.*
import com.fph.lotteryanalyze.model.SplashModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by fengpeihao on 2018/4/19.
 */
class SplashPresenter : SplashContract.Presenter {

    constructor(view: SplashActivity) : super() {
        setVM(view, SplashModel())
    }

    //获取大乐透历史数据
    fun getDLTData() {
        val maxExpect = DltLotteryDaoUtils(mView).queryMaxExpect()
        var fromExpect = "2007001"
        if (!TextUtils.isEmpty(maxExpect)) {
            fromExpect = maxExpect
        }
        val format = SimpleDateFormat("yyyy").format(Date())
        val endExpect = (format.toInt() + 1).toString().plus("001")
        ApiModel.getApiService().getHtmlData("http://chart.lottery.gov.cn//dltBasicKaiJiangHaoMa.do?typ=2&issueFrom=" + fromExpect + "&issueTo=" + endExpect)
                .compose(RxUtils.rxSchedulerHelper()).subscribeWith(object : Common2Subscriber<String>() {
                    override fun netError(message: String) {
                        Toast.makeText(mView, message, Toast.LENGTH_SHORT).show()
                    }

                    override fun getData(t: String) {
                        val stringBuilder = StringBuilder()
                        val s = stringBuilder.append("<html><body><table><tbody id=\"content\">").append(t).append("</tbody></table></body></html>")
                        GlobalScope.launch {
                            dltParse(s.toString())
                            Log.e("***", "大乐透数据入库完成")
                            launch(Dispatchers.Main) {
                                mView.dltLoaded()
                            }
                        }
                    }
                })
    }

    //解析大乐透数据
    private fun dltParse(html: String) {
        val document = Jsoup.parse(html)
        val element = document.getElementById("content")
        val charts_table = element.getElementsByTag("tr")
        val arrayList = ArrayList<DltLotteryEntity>()
        for (aa in charts_table) {
            val trs = aa.getElementsByTag("tr")
            for (tr in trs) {
                if (!tr.className().equals("DatRow")) continue
                val tds = tr.getElementsByTag("td")
                val stringBuilder = StringBuilder()
                val lotteryEntity = DltLotteryEntity()
                for (td in tds) {
                    if (td.className().contains("Issue")) {
                        lotteryEntity.expect = td.text()
                        lotteryEntity.opentime = td.text()
                    } else if (td.className().contains("B_1")) {
                        if (stringBuilder.length != 0) stringBuilder.append(",")
                        stringBuilder.append(td.text())
                    } else if (td.className().contains("B_5")) {
                        if (!stringBuilder.contains("+")) {
                            stringBuilder.append("+")
                        } else {
                            stringBuilder.append(",")
                        }
                        stringBuilder.append(td.text())
                    }
                }
                lotteryEntity.opencode = stringBuilder.toString()
                arrayList.add(lotteryEntity)
            }
        }
        Log.e("***", "大乐透数据解析完成，入库中")
        DltLotteryDaoUtils(mView).insertMultLottery(arrayList)
    }

    var ssqStartPeriods = "03001"
    var ssqEndPeriods = "20153"
    fun getSSQData() {
        //http://datachart.500.com/ssq/history/newinc/history.php?start=03001&end=20153
        val maxOpenTime = LotteryDaoUtils(mView).queryMaxOpenTime()
        if (maxOpenTime.isNotEmpty()) {
            ssqStartPeriods = maxOpenTime
        }
        val arrayList = ArrayList<LotteryEntity>()
        ApiModel.getApiService().getHtmlData("http://datachart.500.com/ssq/history/newinc/history.php?" +
                "start=" + ssqStartPeriods + "&end=" + ssqEndPeriods)
                .compose(RxUtils.rxSchedulerHelper()).subscribeWith(object : Common2Subscriber<String>() {
                    override fun netError(message: String) {
                        Toast.makeText(mView, message, Toast.LENGTH_SHORT).show()
                    }

                    override fun getData(t: String) {
                        Log.e("***", "双色球数据下载完毕，入库中")
                        val document = Jsoup.parse(t)
//                        val tablelist = document.getElementById("tablelist")
                        val tdata = document.getElementById("tdata")
                        val trs = tdata.getElementsByTag("tr")
                        for (item in trs) {
                            val td = item.getElementsByTag("td")
                            val lotteryEntity = LotteryEntity()
                            lotteryEntity.expect = td[0].text()//期数
                            lotteryEntity.opencode = td[1].text() + "," + td[2].text() + "," + td[3].text() + "," + td[4].text() + "," + td[5].text() + "," + td[6].text() + "+" + td[7].text()//开奖号码
                            lotteryEntity.opentime = td[td.lastIndex].text()
                            arrayList.add(lotteryEntity)
                        }
                        LotteryDaoUtils(mView).insertMultLottery(arrayList)
                        Log.e("***", "双色球数据入库完成")
                        mView.ssqLoaded()
                    }
                })
    }

    val arrayList = ArrayList<ArrangeThreeEntity>()
    var plsStartPeriods = "04001"
    val format = SimpleDateFormat("yy").format(Date())
    val plsEndPeriods = format + "351"
    //获取排列三历史数据
    fun getPls(lastPeriods: String) {
        //view-source:http://datachart.500.com/pls/history/inc/history.php?start=04001&end=20351&limit=16350
        if (lastPeriods.isNotEmpty()) {
            plsStartPeriods = lastPeriods
        }
        ApiModel.getApiService().getHtmlData("http://datachart.500.com/pls/history/inc/history.php?" +
                "start=" + plsStartPeriods + "&end=" + plsEndPeriods + "&limit=" + (plsEndPeriods.toInt() - plsStartPeriods.toInt()))
                .compose(RxUtils.rxSchedulerHelper()).subscribeWith(object : Common2Subscriber<String>() {
                    override fun netError(message: String) {
                        Toast.makeText(mView, message, Toast.LENGTH_SHORT).show()
                    }

                    override fun getData(t: String) {
                        Log.e("***", "排列三数据下载完毕，入库中")
                        val document = Jsoup.parse(t)
                        val tablelist = document.getElementById("tablelist")
                        val trs = tablelist.getElementsByTag("tr")
                        for (i in 2..trs.lastIndex) {
                            val element = trs[i]
                            val td = element.getElementsByTag("td")
                            val arrangeThreeEntity = ArrangeThreeEntity()
                            arrangeThreeEntity.periods = td[0].text()//期数
                            arrangeThreeEntity.number = td[1].text()//开奖号码
                            arrangeThreeEntity.firstNumber = td[1].text().split(" ")[0]
                            arrangeThreeEntity.secondNumber = td[1].text().split(" ")[1]
                            arrangeThreeEntity.threeNumber = td[1].text().split(" ")[2]
                            arrangeThreeEntity.date = td[td.lastIndex].text()//开奖时间
                            arrayList.add(arrangeThreeEntity)
                        }
                        ArrangeThreeDaoUtils(mView).insertMultiData(arrayList)
                        Log.e("***", "排列三数据入库完成")
                        mView.arrangeThreeLoaded()
                    }
                })
    }

    fun getETCFData(period: String) {
        //https://jx11x5.icaile.com/?top=1000
        ApiModel.getApiService().getHtmlData("https://jx11x5.icaile.com/?top=3000")
                .compose(RxUtils.rxSchedulerHelper()).subscribeWith(object : Common2Subscriber<String>() {
                    override fun netError(message: String) {
                        Toast.makeText(mView, message, Toast.LENGTH_SHORT).show()
                    }

                    override fun getData(t: String) {
                        Log.e("***", "11选5数据下载完毕，入库中")
                        val arrayList = arrayListOf<ETCFBean>()
                        val document = Jsoup.parse(t)
                        val section = document.getElementsByTag("section")
                        val divs = section[0].getElementsByTag("div")
                        val tables = divs[2].getElementsByTag("table")
                        val tbodys = tables[1].getElementsByTag("tbody")
                        val trs = tbodys[0].getElementsByAttributeValue("class","bline round")
                        for (item in trs) {
                            val td = item.getElementsByTag("td")
                            if (period >= td[0].text()) continue

                            val etcfBean = ETCFBean()
                            etcfBean.periods = td[0].text()//期数
                            etcfBean.firstNumber = td[1].text()
                            etcfBean.secondNumber = td[2].text()
                            etcfBean.threeNumber = td[3].text()
                            etcfBean.fourNumber = td[4].text()
                            etcfBean.fiveNumber = td[5].text()
//                            etcfBean.date = td[td.lastIndex].text()//开奖时间
                            etcfBean.openNumber = etcfBean.firstNumber + "," + etcfBean.secondNumber + "," +
                                    etcfBean.threeNumber + "," + etcfBean.fourNumber + "," + etcfBean.fiveNumber//开奖号码
                            arrayList.add(etcfBean)
                        }
                        ETCFDaoUtils(mView).insertMultiData(arrayList)
                        Log.e("***", "11选5数据入库完成")
                        mView.etcfLoaded()
                    }
                })
    }
}