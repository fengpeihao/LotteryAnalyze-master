package com.feilu.kotlindemo.base

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.Unbinder
import com.fph.lotteryanalyze.widget.LoadingDialog
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 * Created by fengpeihao on 2017/7/31.
 */

abstract class BaseActivity : RxAppCompatActivity() {
    var loadingDialog: LoadingDialog? = null
    protected lateinit var mBind: Unbinder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        mBind = ButterKnife.bind(this)
        init()
    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun init()

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    protected fun startActivity(cls: Class<*>) {
        val intent = Intent(this, cls)
        startActivity(intent)
    }

    protected fun startActivity(cls: Class<*>, bundle: Bundle) {
        val intent = Intent(this, cls)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    protected fun startActivityForResult(cls: Class<*>, requestCode: Int) {
        val intent = Intent(this, cls)
        startActivityForResult(intent, requestCode)
    }

    protected fun startActivityForResult(cls: Class<*>, requestCode: Int, bundle: Bundle) {
        val intent = Intent(this, cls)
        intent.putExtras(bundle)
        startActivityForResult(intent, requestCode)
    }

    fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.Builder(this).build()
        }
        loadingDialog!!.showDialog()
    }

    fun cancelLoading() {
        loadingDialog?.cancelDialog()
    }

    override fun onDestroy() {
        cancelLoading()
        mBind.unbind()
        super.onDestroy()
    }
}
