package com.fph.lotteryanalyze.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.fph.lotteryanalyze.R

/**
 * Created by fengpeihao on 2018/4/19.
 */
class LotteryView : View {

    private val circleRadius = resources.getDimension(R.dimen.dp_10)
    private val color_red = ContextCompat.getColor(context, android.R.color.holo_red_light)
    private val color_blue = ContextCompat.getColor(context, android.R.color.holo_blue_light)
    private val color_white = ContextCompat.getColor(context, android.R.color.white)
    private val padding = resources.getDimension(R.dimen.dp_5)
    private val offset = resources.getDimension(R.dimen.dp_3)
    private var data = "03,04,06,12,18,22+05"
    private val mPaint = Paint()

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL
    }

    fun setData(data: String) {
        this.data = data
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val split = data.split("+")
        val s1 = split[0].split(",")
        val s2 = split[1].split(",")
        for (i in s1.indices) {
            val cx = circleRadius + circleRadius * 2 * i + padding * i + offset
            val cy = circleRadius + offset
            mPaint.color = color_red
            canvas?.drawCircle(cx, cy, circleRadius, mPaint)
            val textSize = resources.getDimension(R.dimen.sp_12)
            mPaint.color = color_white
            mPaint.textSize = textSize
            val x = circleRadius - getTextWidth(mPaint, s1[i]) / 2 + circleRadius * 2 * i + padding * i + offset
            val y = circleRadius + getFontHeight(textSize) / 3 + offset
            canvas?.drawText(s1[i], x, y, mPaint)
        }
        for (i in s2.indices) {
            val cx = circleRadius + circleRadius * 2 * (i + s1.size) + padding * (i + s1.size) + offset
            val cy = circleRadius + offset
            mPaint.color = color_blue
            canvas?.drawCircle(cx, cy, circleRadius, mPaint)
            val textSize = resources.getDimension(R.dimen.sp_12)
            mPaint.color = color_white
            mPaint.textSize = textSize
            val x = circleRadius - getTextWidth(mPaint, s2[i]) / 2 + circleRadius * 2 * (i + s1.size) + padding * (i + s1.size) + offset
            val y = circleRadius + getFontHeight(textSize) / 3 + offset
            canvas?.drawText(s2[i], x, y, mPaint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST) {
            width = (circleRadius * data.length * 2 + padding * (data.length - 1) + offset * 2).toInt()
        }
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            height = (circleRadius * 2 + offset * 2).toInt()
        }
        setMeasuredDimension(width, height)
    }

    /**
     * 获取该画笔写出文字的宽度
     *
     * @param paint
     * @return
     */
    fun getTextWidth(paint: Paint, str: String): Float {
        return paint.measureText(str)
    }

    /**
     * 获取字体高度
     *
     * @param fontSize
     * @return
     */
    fun getFontHeight(fontSize: Float): Float {
        val paint = Paint()
        paint.textSize = fontSize
        val fm = paint.fontMetrics
        return Math.ceil((fm.descent - fm.ascent).toDouble()).toFloat()
    }
}