package com.fph.lotteryanalyze.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import com.fph.lotteryanalyze.R
import com.fph.lotteryanalyze.api.UrlContants
import com.fph.lotteryanalyze.db.LotteryDaoUtils
import com.fph.lotteryanalyze.db.LotteryEntity
import com.fph.lotteryanalyze.widget.LoadingDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import java.io.FileWriter

class MainActivity : AppCompatActivity() {

    private var loadingDialog: LoadingDialog? = null
    private val list = ArrayList<LotteryEntity>()
    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg?.what) {
                1 -> cancelLoading()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_ssq.setOnClickListener {
            val intent = Intent(this, AnalyzeActivity::class.java)
            startActivity(intent)
        }

        btn_dlt.setOnClickListener {
            val intent = Intent(this, DltAnalyzeActivity::class.java)
            startActivity(intent)
        }

        btn_ssq_analyze.setOnClickListener {
            val intent = Intent(this, SsqAnalyzeActivity::class.java)
            intent.putExtra("type", "ssq")
            startActivity(intent)
        }

        btn_history.setOnClickListener {
            val intent = Intent(this, DataListActivity::class.java)
            startActivity(intent)
        }

        btn_ssq_omit.setOnClickListener {
            val intent = Intent(this, OmitActivity::class.java)
            intent.putExtra("type", "ssq")
            startActivity(intent)
        }

        btn_ssq_limit_omit.setOnClickListener {
            val intent = Intent(this, OmitLimitActivity::class.java)
            intent.putExtra("type", "ssq")
            startActivity(intent)
        }

        btn_ssq_verify.setOnClickListener {
            val intent = Intent(this, VerifyActivity::class.java)
            intent.putExtra("type", "ssq")
            startActivity(intent)
        }
    }

    fun getData() {
        showLoading()
        Thread {
            kotlin.run {
                getLotteryData()
            }
        }.start()

    }

    fun getLotteryData() {
        parse(2018)
        LotteryDaoUtils(this).insertMultLottery(list)
        outPut(list)
    }

    fun parse(year: Int) {
        if (year < 2003) return
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
                    stringBuilder.append(td.text())
                } else if (td.className().contains("blueqiu")) {
                    stringBuilder.append("+").append(td.text())
                }
            }
            lotteryEntity.opencode = stringBuilder.toString()
            list.add(lotteryEntity)
        }
        if (year > 2003) {
            parse(year - 1)
        }
    }

    fun outPut(list: ArrayList<LotteryEntity>) {
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

    fun showLoading() {
        if (loadingDialog == null)
            loadingDialog = LoadingDialog.Builder(this).build()
        loadingDialog?.showDialog()
    }

    fun cancelLoading() {
        loadingDialog?.cancelDialog()
    }
}
