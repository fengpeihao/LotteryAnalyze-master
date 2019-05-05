package com.fph.lotteryanalyze.adapter

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fph.lotteryanalyze.R
import com.fph.lotteryanalyze.db.DltLotteryEntity
import com.fph.lotteryanalyze.db.LotteryEntity
import com.fph.lotteryanalyze.widget.LotteryView

/**
 * Created by fengpeihao on 2018/4/19.
 */
class NewestDataAdapter : RecyclerView.Adapter<NewestDataAdapter.ViewHolder> {

    constructor(type: String) {
        mType = type
    }

    private var mSsqList = ArrayList<LotteryEntity>()
    private var mDltList = ArrayList<DltLotteryEntity>()
    private var mType = "ssq"

    fun setSsqList(list: List<LotteryEntity>?) {
        mSsqList.clear()
        mSsqList.addAll(list ?: ArrayList<LotteryEntity>())
        notifyDataSetChanged()
    }

    fun setDltList(list: List<DltLotteryEntity>?) {
        mDltList.clear()
        mDltList.addAll(list ?: ArrayList<DltLotteryEntity>())
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mType == "ssq") {
            val lotteryBean = mSsqList[position]
            holder.getView<LotteryView>(R.id.lottery_view).setData(lotteryBean.opencode)
            val date = lotteryBean.opentime.split(" ")[0]
            holder.setText(R.id.tv_date, date)
            holder.setText(R.id.tv_periods, lotteryBean.expect)
        } else {
            val lotteryBean = mDltList[position]
            holder.getView<LotteryView>(R.id.lottery_view).setData(lotteryBean.opencode)
            val date = lotteryBean.opentime.split(" ")[0]
            holder.setText(R.id.tv_date, date)
            holder.setText(R.id.tv_periods, lotteryBean.expect)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewestDataAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_newest_data, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (mType == "ssq") {
            return mSsqList.size
        } else {
            return mDltList.size
        }
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