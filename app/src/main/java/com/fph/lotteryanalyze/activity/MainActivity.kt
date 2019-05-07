package com.fph.lotteryanalyze.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.fph.lotteryanalyze.R
import com.fph.lotteryanalyze.db.LotteryDaoUtils
import com.fph.lotteryanalyze.db.OmitDaoUtils
import com.fph.lotteryanalyze.utils.AnalyzeUtils
import com.fph.lotteryanalyze.widget.LoadingDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var loadingDialog: LoadingDialog? = null

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
        getData()
    }

    fun getData() {
        Thread {
            kotlin.run {
                val maxExpect = OmitDaoUtils(this@MainActivity).queryMaxExpect()
                var openTime = "15001"
                if (!TextUtils.isEmpty(maxExpect)) {
                    openTime = maxExpect
                }
                val expect = LotteryDaoUtils(this@MainActivity).queryMaxExpect()
                val analyzeUtils = AnalyzeUtils("red", this@MainActivity)
                if (getLimit(openTime, expect) == 0) {
                    return@run
                }
                for (i in 0..getLimit(openTime, expect)) {
                    analyzeUtils.getSsqVerifyData(i)
                }
            }
        }.start()
    }

    fun showLoading() {
        if (loadingDialog == null)
            loadingDialog = LoadingDialog.Builder(this).build()
        loadingDialog?.showDialog()
    }

    fun cancelLoading() {
        loadingDialog?.cancelDialog()
    }

    fun getLimit(preTime: String, currentTime: String): Int {
        var limit = 200;
        val preYear = preTime.substring(0, 2)
        val prePeriods = preTime.substring(2, preTime.length)
        val currentYear = currentTime.substring(0, 2)
        val currentPeriods = currentTime.substring(2, preTime.length)
        if (preYear == currentYear) {
            limit = currentPeriods.toInt() - prePeriods.toInt()
        }
        return limit
    }
}
