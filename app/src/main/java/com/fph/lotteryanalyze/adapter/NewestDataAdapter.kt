package com.fph.lotteryanalyze.adapter

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fph.lotteryanalyze.R
import com.fph.lotteryanalyze.db.LotteryEntity
import com.fph.lotteryanalyze.widget.LotteryView

/**
 * Created by fengpeihao on 2018/4/19.
 */
class NewestDataAdapter : RecyclerView.Adapter<NewestDataAdapter.ViewHolder>() {

    private var mList = ArrayList<LotteryEntity>()

    fun setList(list: List<LotteryEntity>?) {
        mList.clear()
        mList.addAll(list ?: ArrayList<LotteryEntity>())
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lotteryBean = mList[position]
        holder.getView<LotteryView>(R.id.lottery_view).setData(lotteryBean.opencode)
        val date = lotteryBean.opentime.split(" ")[0]
        holder.setText(R.id.tv_date, date)
        holder.setText(R.id.tv_periods, lotteryBean.expect)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewestDataAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_newest_data, null)
        return ViewHolder(view)
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