package com.fph.lotteryanalyze.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.fph.lotteryanalyze.R
import com.fph.lotteryanalyze.bean.LotteryFrequencyBean

class OmitAdapter : RecyclerView.Adapter<OmitAdapter.ViewHolder>() {

    private var mList: List<LotteryFrequencyBean>? = null

    fun setList(list: List<LotteryFrequencyBean>) {
        mList = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_ormit, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val frequencyBean = mList!![i]
        with(viewHolder) {
            //回补几率
            mTvAnaplerosisFrequency.text = String.format("%.3f", frequencyBean.coveringChance)
            //出现次数
            mTvAriseCount.text = frequencyBean.displayCount.toString()
            //出现频率
            mTvAriseFrequency.text = String.format("%.3f", frequencyBean.displayFrequency)
            //平均遗漏
            mTvAveOmit.text = String.format("%.3f", frequencyBean.avgOmit)
            //当前遗漏
            mTvCurrentOmit.text = frequencyBean.currentOmit.toString()
            //连出几率
            mTvContinuousChance.text = String.format("%.3f", frequencyBean.continuousFrequency)
            //预出几率
            mTvBeforehandFrequency.text = String.format("%.3f", frequencyBean.chance)
            //最大遗漏
            mTvMaxOmit.text = frequencyBean.maxOmit.toString()
            mTvNumber.text = frequencyBean.number
            //上期遗漏
            mTvPreOmit.text = frequencyBean.preOmit.toString()
            //理论出现次数
            mTvTheoryCount.text = String.format("%.0f", frequencyBean.theoryCount)
            if (i % 2 == 0) {
                mLayoutRoot.setBackgroundColor(ContextCompat.getColor(mLayoutRoot.context, R.color.color_white))
            } else {
                mLayoutRoot.setBackgroundColor(ContextCompat.getColor(mLayoutRoot.context, R.color.color_ADD8E6))
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mList == null) 0 else mList!!.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mTvNumber: TextView = view.findViewById(R.id.tv_number)
        var mTvAriseCount: TextView = view.findViewById(R.id.tv_arise_count)
        var mTvTheoryCount: TextView = view.findViewById(R.id.tv_theory_count)
        var mTvAriseFrequency: TextView = view.findViewById(R.id.tv_arise_frequency)
        var mTvAveOmit: TextView = view.findViewById(R.id.tv_ave_omit)
        var mTvMaxOmit: TextView = view.findViewById(R.id.tv_max_omit)
        var mTvPreOmit: TextView = view.findViewById(R.id.tv_pre_omit)
        var mTvCurrentOmit: TextView = view.findViewById(R.id.tv_current_omit)
        var mTvContinuousChance: TextView = view.findViewById(R.id.tv_continuous_chance)
        var mTvBeforehandFrequency: TextView = view.findViewById(R.id.tv_beforehand_frequency)
        var mTvAnaplerosisFrequency: TextView = view.findViewById(R.id.tv_anaplerosis_frequency)
        var mLayoutRoot: LinearLayout = view.findViewById(R.id.layout_root)
    }
}
