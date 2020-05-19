package com.fph.lotteryanalyze.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.fph.lotteryanalyze.R
import com.fph.lotteryanalyze.base.BaseActivity
import com.fph.lotteryanalyze.db.LotteryDaoUtils
import com.fph.lotteryanalyze.db.OmitDaoUtils
import com.fph.lotteryanalyze.utils.AnalyzeUtils
import com.fph.lotteryanalyze.widget.LoadingDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        btn_ssq.setOnClickListener {
            val intent = Intent(this, AnalyzeActivity::class.java)
            startActivity(intent)
        }

        btn_dlt.setOnClickListener {
            val intent = Intent(this, DltAnalyzeActivity::class.java)
            startActivity(intent)
        }

        //双色球、大乐透历史数据
        btn_history.setOnClickListener {
            val intent = Intent(this, DataListActivity::class.java)
            startActivity(intent)
        }

        //双色球遗漏数据分析
        btn_ssq_omit.setOnClickListener {
            val intent = Intent(this, OmitActivity::class.java)
            intent.putExtra("category", "ssq")
            startActivity(intent)
        }

        //大乐透遗漏数据分析
        btn_dlt_omit.setOnClickListener {
            val intent = Intent(this, OmitActivity::class.java)
            intent.putExtra("category", "dlt")
            startActivity(intent)
        }

        //双色球数据猜测对比
        btn_ssq_verify.setOnClickListener {
            val intent = Intent(this, VerifyActivity::class.java)
            intent.putExtra("category", "ssq")
            startActivity(intent)
        }

        //大乐透数据猜测对比
        btn_dlt_verify.setOnClickListener {
            val intent = Intent(this, VerifyActivity::class.java)
            intent.putExtra("category", "dlt")
            startActivity(intent)
        }
        //排了三历史数据
        btn_at_history.setOnClickListener {
            startActivity(Intent(this, ArrangeThreeHistoryActivity::class.java))
        }
        //排列三遗漏数据分析
        btn_at_anaylyze_result.setOnClickListener {
            startActivity(Intent(this, ArrangeThreeAnalyzeListActivity::class.java))
        }
        //排列三数据猜测对比
        btn_at_anaylyze.setOnClickListener {
            startActivity(Intent(this, ArrangeThreeAnalyzeActivity::class.java))
        }
        //11选5历史数据
        btn_etcf_history.setOnClickListener {
            startActivity(Intent(this, ETCFHistoryActivity::class.java))
        }
        //11选5历史数据
        btn_etcf_anaylyze_result.setOnClickListener {
            startActivity(Intent(this, ETCFAnalyzeListActivity::class.java))
        }
        //11选5数据猜测对比
        btn_etcf_anaylyze.setOnClickListener {
            startActivity(Intent(this, ETCFAnalyzeActivity::class.java))
        }
    }
}
