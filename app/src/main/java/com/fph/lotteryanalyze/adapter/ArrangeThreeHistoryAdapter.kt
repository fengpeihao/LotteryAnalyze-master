package com.fph.lotteryanalyze.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fph.lotteryanalyze.R
import com.fph.lotteryanalyze.db.ArrangeThreeEntity

class ArrangeThreeHistoryAdapter : RecyclerView.Adapter<ArrangeThreeHistoryAdapter.ViewHolder>() {

    private var mList: ArrayList<ArrangeThreeEntity> = ArrayList()

    fun setData(list: List<ArrangeThreeEntity>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_arrange_three_history,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val entity = mList[position]
        viewHolder.run {
            tvDate.text = entity.date
            tvPeriods.text = entity.periods
            tvNumber.text = entity.number
        }
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tvDate = itemView.findViewById<TextView>(R.id.tv_date)
        val tvPeriods = itemView.findViewById<TextView>(R.id.tv_periods)
        val tvNumber = itemView.findViewById<TextView>(R.id.tv_number)
    }
}