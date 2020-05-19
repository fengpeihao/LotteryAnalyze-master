package com.fph.lotteryanalyze.activity

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fph.lotteryanalyze.R
import com.fph.lotteryanalyze.base.BaseActivity
import com.fph.lotteryanalyze.bean.ArrangeThreeAnalyzeBean
import com.fph.lotteryanalyze.bean.ArrangeThreeGuessBean
import com.fph.lotteryanalyze.db.ETCFDaoUtils
import com.fph.lotteryanalyze.utils.ETCFUtils
import kotlinx.android.synthetic.main.fragment_analyze.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ETCFAnalyzeActivity : BaseActivity() {

    val mList = ArrayList<ArrangeThreeGuessBean>()
    lateinit var mAdapter: Adapter
    override fun getLayoutId(): Int {
        return R.layout.fragment_analyze
    }

    override fun init() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        mAdapter = Adapter()
        recycler_view.adapter = mAdapter
        analyze()
    }

    fun analyze() {
        showLoading()
        GlobalScope.launch {
            for (i in 0..100) {
                var guessBean = ArrangeThreeGuessBean()
                val utils = ETCFUtils(this@ETCFAnalyzeActivity)
                val result = utils.analyze(i)
                var list = arrayListOf<ArrangeThreeAnalyzeBean>()
                for (item in result) {
                    val bean = ArrangeThreeAnalyzeBean()
                    bean.number = item.number
                    bean.chance = item.chance
                    bean.coveringChance = item.coveringChance
                    bean.continuousChance = item.continuousFrequency
                    list.add(setAggregate(bean, item.currentOmit, item.continuousFrequency))
                }
                list = sortList(list)
                guessBean.period = getPeriod(utils.period)
                guessBean.resultNumber = ETCFDaoUtils(this@ETCFAnalyzeActivity).queryDataByPeriod(guessBean.period)?.openNumber
                        ?: ""
                mList.add(guessResult(guessBean, list))
            }
            launch(context = Dispatchers.Main) {
                cancelLoading()
                mAdapter.notifyDataSetChanged()
                setGuessResult()
            }
        }
    }

    private fun getPeriod(period: String): String {
        val split = period.split("-")
        var date = split[0]
        var p = split[1]
        p = (p.toInt() + 1).toString()
        if (p.toInt() > 42) {
            date = getDate(date)
            p = "01"
        } else if (p.toInt() < 10) {
            p = String.format("0%s", p)
        }
        return String.format("%s-%s", date, p)
    }

    private fun getDate(oldDate: String): String {
        var y = oldDate.substring(0, 2)
        var m = oldDate.substring(2, 4)
        var d = oldDate.substring(4)
        d = (d.toInt() + 1).toString()
        if (d.toInt() < 10) {
            d = String.format("0%s", d)
        }
        if (((m == "01" || m == "03" || m == "05" || m == "07" || m == "08" || m == "10" || m == "12") && d.toInt() > 31) ||
                (m == "02" && ((y.toInt() - 20) % 4 == 0) && d.toInt() > 29) ||
                (m == "02" && ((y.toInt() - 20) % 4 == 0) && d.toInt() > 28) ||
                ((m == "04" || m == "06" || m == "09" || m == "11") && d.toInt() > 30)) {
            m = (m.toInt() + 1).toString()
            if (m.toInt() < 10) {
                m = String.format("0%s", m)
            }
            d = "01"
        }
        if (m.toInt() > 12) {
            y = (y.toInt() + 1).toString()
            m = "01"
        }
        return y + m + d
    }

    private fun setAggregate(bean: ArrangeThreeAnalyzeBean, currentOmit: Int, continuousFrequency: Float): ArrangeThreeAnalyzeBean {
        bean.aggregate = if (currentOmit == 0) {
            bean.chance + bean.coveringChance + continuousFrequency
        } else {
            bean.chance + bean.coveringChance
        }
        return bean
    }

    private fun sortList(list: ArrayList<ArrangeThreeAnalyzeBean>): ArrayList<ArrangeThreeAnalyzeBean> {
        list.sortWith(object : Comparator<ArrangeThreeAnalyzeBean> {
            override fun compare(o1: ArrangeThreeAnalyzeBean, o2: ArrangeThreeAnalyzeBean): Int {
                return if (o1.aggregate > o2.aggregate) -1 else 1
            }
        })
        return list
    }

    private fun guessResult(guessBean: ArrangeThreeGuessBean, list: ArrayList<ArrangeThreeAnalyzeBean>): ArrangeThreeGuessBean {
        for (i in list.indices) {
            guessBean.guessNumber += list[i].number
            if (i < list.size - 1) {
                guessBean.guessNumber += ","
            }
        }
        val split = guessBean.guessNumber.split(",")
        guessBean.isCorrectly = !guessBean.resultNumber.contains(split.last()) &&
                !guessBean.resultNumber.contains(split[9]) &&
                !guessBean.resultNumber.contains(split[8])
        return guessBean
    }

    private fun setGuessResult() {
        var num = -1
        for (item in mList) {
            if (item.isCorrectly) {
                num++
            }
        }
        tv_result.text = "猜中${num}次"
    }

    inner class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_etcf_guess, parent, false)
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