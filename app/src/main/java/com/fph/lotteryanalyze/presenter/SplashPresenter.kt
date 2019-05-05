package com.fph.lotteryanalyze.presenter

import android.annotation.SuppressLint
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.widget.Toast
import com.fph.lotteryanalyze.base.Common2Subscriber
import com.fph.lotteryanalyze.base.RxUtils
import com.fph.lotteryanalyze.activity.SplashActivity
import com.fph.lotteryanalyze.api.ApiModel
import com.fph.lotteryanalyze.api.UrlContants
import com.fph.lotteryanalyze.contract.SplashContract
import com.fph.lotteryanalyze.db.DltLotteryDaoUtils
import com.fph.lotteryanalyze.db.DltLotteryEntity
import com.fph.lotteryanalyze.db.LotteryDaoUtils
import com.fph.lotteryanalyze.db.LotteryEntity
import com.fph.lotteryanalyze.model.SplashModel
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by fengpeihao on 2018/4/19.
 */
class SplashPresenter : SplashContract.Presenter {

    private val list = ArrayList<LotteryEntity>()
    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                1 -> mView.ssqLoaded()
                2 -> mView.dltLoaded()
            }
        }
    }

    constructor(view: SplashActivity) : super() {
        setVM(view, SplashModel())
    }

    fun getDLTData() {
        Thread {
            kotlin.run {
                getDLTLotteryData()
            }
        }.start()
    }

    private fun getDLTLotteryData() {
        getDLT()
    }

    private fun getDLT() {
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
                dltParse(s.toString())
            }
        })
    }

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
        DltLotteryDaoUtils(mView).insertMultLottery(arrayList)
        mHandler.sendEmptyMessage(2)
    }

    fun getData() {
        Thread {
            kotlin.run {
                getLotteryData()
            }
        }.start()
    }

    private fun getLotteryData() {
        val format = SimpleDateFormat("yyyy").format(Date())
        parse(format.toInt())
        LotteryDaoUtils(mView).insertMultLottery(list)
        mHandler.sendEmptyMessage(1)
//        outPut(list)
    }

    private fun parse(year: Int) {
        val maxOpenTime = LotteryDaoUtils(mView).queryMaxOpenTime()
        var openTime = "2003"
        if (!TextUtils.isEmpty(maxOpenTime)) {
            openTime = maxOpenTime.substring(0, 4)
        }
        val time = openTime.toInt()
        if (year < time) return
        val url = UrlContants.SSQDataUrl + year + ".html"
        val connect = Jsoup.connect(url)
        var document: Document? = null
        try {
            document = connect.get()
        } catch (e: Exception) {

        }
        if (document == null) return
        val table_ssq = document.getElementById("table_ssq")
        val trs = table_ssq.getElementsByTag("tr")
        for (item in trs) {
            val tds = item.getElementsByTag("td")
            val stringBuilder = StringBuilder()
            val lotteryEntity = LotteryEntity()
            for (td in tds) {
                if (td.className().contains("qh7")) {
                    val dateElement = td.getElementsByTag("a")
                    val dateStr = dateElement[0].attr("title")
                    val qihao = dateElement[0].text()
                    lotteryEntity.expect = qihao
                    lotteryEntity.opentime = dateStr.substring(5)
                    continue
                } else if (td.className().contains("redqiu")) {
                    if (stringBuilder.length != 0) stringBuilder.append(",")
                    val i = td.text().toInt()
                    var tdText = i.toString()
                    if (i < 10) {
                        tdText = "0".plus(tdText)
                    }
                    stringBuilder.append(tdText)
                } else if (td.className().contains("blueqiu")) {
                    val i = td.text().toInt()
                    var tdText = i.toString()
                    if(i<10){
                        tdText = "0".plus(tdText)
                    }
                    stringBuilder.append("+").append(tdText)
                }
            }
            lotteryEntity.opencode = stringBuilder.toString()
            list.add(lotteryEntity)
        }
        if (year > 2003) {
            parse(year - 1)
        }
    }

    private fun outPut(list: ArrayList<LotteryEntity>) {
        val path = Environment.getExternalStorageDirectory().absolutePath + "/lottery/ssq.txt"
        val file = File(path)
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        if (file.exists()) {
            file.delete()
        }
        val fileWriter = FileWriter(file, true)
        for (item in list) {
            val text = "开奖日期:" + item.opentime + " 开奖期号:" + item.expect + " 开奖号码:" + item.opencode + "\r\n"
            fileWriter.write(text)
        }
        fileWriter.flush()
        fileWriter.close()
        mHandler.sendEmptyMessage(1)
    }
}