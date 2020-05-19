package com.fph.lotteryanalyze.fragment

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fph.lotteryanalyze.R
import com.fph.lotteryanalyze.base.BaseFragment
import com.fph.lotteryanalyze.bean.ArrangeThreeGuessBean
import kotlinx.android.synthetic.main.fragment_analyze.*

class ArrangeThreeGuessFragment : BaseFragment() {

    var mList: ArrayList<ArrangeThreeGuessBean> = ArrayList()
    lateinit var mAdapter: Adapter
    override fun getLayoutId(): Int {
        return R.layout.fragment_analyze
    }

    override fun init(savedInstanceState: Bundle?) {
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        mAdapter = Adapter()
        recycler_view.adapter = mAdapter
    }

    fun setData(list: ArrayList<ArrangeThreeGuessBean>) {
        mList = list
        mAdapter.notifyDataSetChanged()
        var num = -1
        var doubleNum = -1
        for (item in mList) {
            if (item.isCorrectly) {
                num++
            }
            if (isDoubleNumber(item.resultNumber)) {
                doubleNum++
            }
        }
        tv_result.text = "猜中${num}次,豹子号码出现${doubleNum}次"
    }

    private fun isDoubleNumber(number: String): Boolean {
        if (number.isEmpty()) return true
        val split = number.split(" ")
        return split[0] == split[1] || split[0] == split[2] || split[1] == split[2]
    }

    inner class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_arrange_three_guess, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return mList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val bean = mList[position]
            holder.tvPeriod.text = bean.period
            holder.tvGuessNumber.text = bean.guessNumber
            holder.tvResultPeriod.text = bean.resultNumber
            holder.tvCorrectly.text = if (bean.isCorrectly) "是" else "否"
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvPeriod = itemView.findViewById<TextView>(R.id.tv_periods)
            val tvGuessNumber = itemView.findViewById<TextView>(R.id.tv_guess_number)
            val tvResultPeriod = itemView.findViewById<TextView>(R.id.tv_result_number)
            val tvCorrectly = itemView.findViewById<TextView>(R.id.tv_correctly)
        }
    }
}