package com.fph.lotteryanalyze.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.Unbinder
import com.fph.lotteryanalyze.widget.LoadingDialog
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * Created by fengpeihao on 2017/7/14.
 */
abstract class BaseFragment : RxFragment() {

    protected var rootView: View? = null
    protected lateinit var mBind: Unbinder
    private var loadingDialog: LoadingDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(getLayoutId(), null)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBind = ButterKnife.bind(this, view)
        init(savedInstanceState)
    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun init(savedInstanceState: Bundle?)

    fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun startActivity(cls: Class<*>) {
        val intent = Intent(context, cls)
        startActivity(intent)
    }

    fun startActivity(cls: Class<*>, bundle: Bundle) {
        val intent = Intent(context, cls)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    fun startActivityForResult(cls: Class<*>, requestCode: Int) {
        val intent = Intent(context, cls)
        startActivityForResult(intent, requestCode)
    }

    fun startActivityForResult(cls: Class<*>, requestCode: Int, bundle: Bundle) {
        val intent = Intent(context, cls)
        intent.putExtras(bundle)
        startActivityForResult(intent, requestCode)
    }

    fun showLoading() {
        loadingDialog?.showDialog() ?: LoadingDialog.Builder(context).build().showDialog()
    }

    fun cancelLoading() {
        loadingDialog?.cancelDialog()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBind.unbind()
    }
}