package com.fph.lotteryanalyze.adapter

import android.graphics.Color
import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fph.lotteryanalyze.R
import com.fph.lotteryanalyze.bean.AnalyzeBean

/**
 * Created by fengpeihao on 2018/4/25.
 */

class AnalyzeAdapter : RecyclerView.Adapter<AnalyzeAdapter.ViewHolder>() {


    private var mList = ArrayList<AnalyzeBean>()

    fun setData(list: ArrayList<AnalyzeBean>) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_anaylze, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnalyzeAdapter.ViewHolder, position: Int) {
        val bean = mList[position]
        var code = bean.redCode ?: ""
        var spannableString = SpannableString(code)
        spannableString.setSpan(ForegroundColorSpan(Color.RED), 0, code.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        if (!TextUtils.isEmpty(bean.blueCode) && !TextUtils.isEmpty(code)) {
            code = code.plus("+").plus(bean.blueCode)
            spannableString = SpannableString(code)
            spannableString.setSpan(ForegroundColorSpan(Color.RED), 0, bean.redCode?.length ?: 0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(ForegroundColorSpan(Color.BLACK), bean.redCode?.length ?: 0, (bean.redCode?.length ?: 0) + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(ForegroundColorSpan(Color.BLUE), (bean.redCode?.length ?: 0) + 1, code.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        } else if (TextUtils.isEmpty(code)) {
            spannableString = SpannableString(bean.blueCode)
            spannableString.setSpan(ForegroundColorSpan(Color.BLUE), 0, bean.blueCode?.length ?: 0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        holder.setText(R.id.tv_code, spannableString)
        holder.setText(R.id.tv_count, bean.num.toString() + "次")
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var views = SparseArray<View>()
        fun <T : View> getView(@IdRes viewId: Int): T {
            var view = views.get(viewId)
            if (view == null) {
                view = itemView.findViewById<T>(viewId)
                views.put(viewId, view)
            }
            return view as T
        }

        fun setText(@IdRes viewId: Int, value: CharSequence) {
            val view = getView<TextView>(viewId)
            view.setText(value)
        }
    }
}
