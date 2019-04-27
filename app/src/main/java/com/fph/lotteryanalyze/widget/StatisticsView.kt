package com.fph.lotteryanalyze.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import com.fph.lotteryanalyze.R

/**
 * Created by fengpeihao on 2018/4/19.
 */
class StatisticsView : View {
    private val circleRadius = resources.getDimension(R.dimen.dp_10)
    private val color_red = ContextCompat.getColor(context, android.R.color.holo_red_light)
    private val color_blue = ContextCompat.getColor(context, android.R.color.holo_blue_light)
    private val color_white = ContextCompat.getColor(context, android.R.color.white)
    private val color_gray = ContextCompat.getColor(context, android.R.color.darker_gray)
    private val padding = resources.getDimension(R.dimen.dp_5)
    private val offset = resources.getDimension(R.dimen.dp_3)
    private var type = "ssq"//双色球1-33 1-16  大乐透1-35  1-12
    private var redMap = HashMap<Int, Int>()
    private var blueMap = HashMap<Int, Int>()
    private val mPaint = Paint()
    private val screenWidth = getScreenWidth()
    private val screenHeight = getScreenHeight()
    private val num = ((screenWidth - offset * 2 - padding) / (circleRadius * 2 + padding)).toInt()

    constructor(context: Context?) : super(context) {}

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    fun setType(type: String, redMap: HashMap<Int, Int>, blueMap: HashMap<Int, Int>) {
        this.type = type
        this.redMap = redMap
        this.blueMap = blueMap
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var redMaxNum = 35
        var blueMaxNum = 12
        if ("ssq".equals(type)) {
            redMaxNum = 33
            blueMaxNum = 16
        }
        val fontHeight = getFontHeight(resources.getDimension(R.dimen.sp_12))
        for (i in 0..(getRedRowNum() - 1)) {
            for (j in 0..(num - 1)) {
                val ballNo = num * i + j + 1
                var ballNoStr = ballNo.toString()
                if (ballNo < 10) {
                    ballNoStr = "0".plus(ballNoStr)
                }
                if (ballNo > redMaxNum) break
                val cx = circleRadius + circleRadius * 2 * j + padding * j + offset
                val cy = circleRadius + offset + i * (circleRadius * 2 + fontHeight + offset)
                mPaint.color = color_red
                canvas?.drawCircle(cx, cy, circleRadius, mPaint)
                val textSize = resources.getDimension(R.dimen.sp_12)
                mPaint.color = color_white
                mPaint.textSize = textSize
                val x = cx - getTextWidth(mPaint, ballNoStr) / 2
                val y = cy + getFontHeight(textSize) / 3
                canvas?.drawText(ballNoStr, x, y, mPaint)
                mPaint.color = color_gray
                val numStr = (redMap[ballNo] ?: 0).toString()
                val x2 = cx - getTextWidth(mPaint, numStr) / 2
                val y2 = circleRadius + offset + cy + getFontHeight(textSize) * 2 / 3
                canvas?.drawText(numStr, x2, y2, mPaint)
            }
        }
        for (i in 0..(getBlueRowNum() - 1)) {
            for (j in 0..(num - 1)) {
                var ballNo = num * i + j + 1
                var ballStr = ballNo.toString()
                if (ballNo < 10) {
                    ballStr = "0".plus(ballStr)
                }
                if (ballNo > blueMaxNum) break
                val cx = circleRadius + circleRadius * 2 * j + padding * j + offset
                val cy = circleRadius + offset + i * (circleRadius * 2 + fontHeight + offset) + (circleRadius * 2 + fontHeight + offset) * getRedRowNum()
                mPaint.color = color_blue
                canvas?.drawCircle(cx, cy, circleRadius, mPaint)
                val textSize = resources.getDimension(R.dimen.sp_12)
                mPaint.color = color_white
                mPaint.textSize = textSize
                val x = cx - getTextWidth(mPaint, ballStr) / 2
                val y = cy + getFontHeight(textSize) / 3
                canvas?.drawText(ballStr, x, y, mPaint)
                mPaint.color = color_gray
                val numStr = (blueMap[ballNo] ?: 0).toString()
                val x2 = cx - getTextWidth(mPaint, numStr) / 2
                val y2 = circleRadius + offset + cy + getFontHeight(textSize) * 2 / 3
                canvas?.drawText(numStr, x2, y2, mPaint)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        val fontHeight = getFontHeight(resources.getDimension(R.dimen.sp_12))

        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST) {
            width = ((circleRadius * 2 + padding) * num + offset * 2 + padding).toInt()
        }
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            height = ((circleRadius * 2 + offset * 3 + fontHeight) * getRowNum()).toInt()
        }
        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec))
    }

    private fun getRowNum(): Int {
        return getRedRowNum() + getBlueRowNum()
    }

    private fun getRedRowNum(): Int {
        var row = 0
        if ("ssq".equals(type)) {
            row += 33 / num
            if (33 % num != 0) row += 1
        } else {
            row = 35 / num
            if (35 % num != 0) row += 1
        }
        return row
    }

    private fun getBlueRowNum(): Int {
        var row = 0
        if ("ssq".equals(type)) {
            row += 16 / num
            if (16 % num != 0) row += 1
        } else {
            row += 12 / num
            if (12 % num != 0) row += 1
        }
        return row
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

    fun getScreenWidth(): Int {
        val dm = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

    fun getScreenHeight(): Int {
        val dm = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(dm)
        return dm.heightPixels
    }
}