package com.fph.lotteryanalyze.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import com.fph.lotteryanalyze.R
import com.fph.lotteryanalyze.base.BaseActivity
import com.fph.lotteryanalyze.contract.SplashContract
import com.fph.lotteryanalyze.db.ArrangeThreeDaoUtils
import com.fph.lotteryanalyze.db.ETCFDaoUtils
import com.fph.lotteryanalyze.presenter.SplashPresenter
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by fengpeihao on 2018/4/24.
 */

class SplashActivity : BaseActivity(), SplashContract.View {

    private val mPresenter = SplashPresenter(this)
    private var isSSQLoaded = false
    private var isDLTLoaded = false
    private var isATLoaded = false
    private var isETCFLoaded = false
    private var isAnimFinish = false

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun init() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 222)
        } else {
            //获取双色球历史数据
            mPresenter.getSSQData()
            //获取大乐透历史数据
            mPresenter.getDLTData()

            val lastPeriods = ArrangeThreeDaoUtils(this).queryLastPeriods()
            //获取排列三历史数据
            mPresenter.getPls(lastPeriods)
            val lastETCFPeriods = ETCFDaoUtils(this).queryLastPeriods()
            mPresenter.getETCFData(lastETCFPeriods)
        }
        tv_dream_come_true.startAnimation(0f, 1f)
        GlobalScope.launch {
            delay(5000)
            isAnimFinish = true
            launch(Dispatchers.Main) { skipToMain() }
        }
    }

    @Synchronized
    private fun skipToMain() {
        if (isSSQLoaded && isDLTLoaded && isATLoaded && isETCFLoaded && isAnimFinish) {
            tv_dream_come_true.stopAnimation()
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    fun ssqLoaded() {
        isSSQLoaded = true
        skipToMain()
    }

    fun dltLoaded() {
        isDLTLoaded = true
        skipToMain()
    }

    fun arrangeThreeLoaded() {
        isATLoaded = true
        skipToMain()
    }

    fun etcfLoaded() {
        isETCFLoaded = true
        skipToMain()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            222 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPresenter.getSSQData()
                mPresenter.getDLTData()
                mPresenter.getPls("")
                mPresenter.getETCFData("")
            }
            else -> {
            }
        }
    }
}
