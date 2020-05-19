package com.fph.lotteryanalyze.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fph.lotteryanalyze.R
import com.fph.lotteryanalyze.bean.LotteryGuessBean

class VerifyAdapter(private var type: String) : RecyclerView.Adapter<VerifyAdapter.ViewHolder>() {

    private var mList = arrayListOf<LotteryGuessBean>()

    fun setList(list: List<LotteryGuessBean>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_verify, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val lotteryGuessBean = mList[i]
        with(viewHolder) {
            tvPeriods.text = lotteryGuessBean.period
            if (type == "red") {
                tvOpencode.text = lotteryGuessBean.openRedNumber
                tvBeforehand.text = lotteryGuessBean.guessRedNumber
                lotteryGuessBean.guessRedNumber.let {
                    if (it.isNotEmpty()) {
                        val spannableString = SpannableString(it)
                        val split = it.split(",")
                        for (item in split) {
                            if (!lotteryGuessBean.openRedNumber.contains(item)) {
                                spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(tvBeforehand.context, android.R.color.black)),
                                        it.indexOf(item), it.indexOf(item) + 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                            } else {
                                spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(tvBeforehand.context, android.R.color.holo_red_light)),
                                        it.indexOf(item), it.indexOf(item) + 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                            }
                        }
                        tvBeforehand.text = spannableString
                    }
                }
            } else {
                tvOpencode.text = lotteryGuessBean.openBlueNumber
                tvBeforehand.text = lotteryGuessBean.guessBlueNumber
                lotteryGuessBean.guessBlueNumber.let {
                    if (it.isNotEmpty()) {
                        val spannableString = SpannableString(it)
                        val split = it.split(",")
                        for (item in split) {
                            if (lotteryGuessBean.openBlueNumber == item) {
                                spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(tvBeforehand.context, android.R.color.holo_red_light)),
                                        it.indexOf(item), it.indexOf(item) + 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                            } else {
                                spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(tvBeforehand.context, android.R.color.black)),
                                        it.indexOf(item), it.indexOf(item) + 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                            }
                        }
                        tvBeforehand.text = spannableString
                    }
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPeriods = view.findViewById<TextView>(R.id.tv_periods)
        val tvBeforehand = view.findViewById<TextView>(R.id.tv_beforehand)
        val tvOpencode = view.findViewById<TextView>(R.id.tv_opencode)
    }
}
