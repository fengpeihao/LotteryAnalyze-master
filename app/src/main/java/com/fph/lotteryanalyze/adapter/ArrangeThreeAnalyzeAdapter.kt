package com.fph.lotteryanalyze.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fph.lotteryanalyze.R
import com.fph.lotteryanalyze.bean.ArrangeThreeAnalyzeBean
import com.fph.lotteryanalyze.db.ArrangeThreeEntity

class ArrangeThreeAnalyzeAdapter : RecyclerView.Adapter<ArrangeThreeAnalyzeAdapter.ViewHolder>() {

    private var mList: ArrayList<ArrangeThreeAnalyzeBean> = ArrayList()

    fun setData(list: ArrayList<ArrangeThreeAnalyzeBean>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_arrange_three_analyze, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val entity = mList[position]
        viewHolder.run {
            tvPeriods.text = entity.periods
            tvNumber.text = entity.number
            tvChance.text = String.format("%.2f", entity.chance)
            tvCoveringChance.text = String.format("%.2f", entity.coveringChance)
            tvContinuousChance.text = String.format("%.2f", entity.continuousChance)
            tvAggregate.text = String.format("%.2f", entity.aggregate)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPeriods = itemView.findViewById<TextView>(R.id.tv_periods)
        val tvNumber = itemView.findViewById<TextView>(R.id.tv_number)
        val tvChance = itemView.findViewById<TextView>(R.id.tv_chance)
        val tvCoveringChance = itemView.findViewById<TextView>(R.id.tv_covering_chance)
        val tvContinuousChance = itemView.findViewById<TextView>(R.id.tv_continuous_chance)
        val tvAggregate = itemView.findViewById<TextView>(R.id.tv_aggregate)
    }
}