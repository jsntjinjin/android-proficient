package com.fastaoe.proficient.component.views.weight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by jinjin on 17/7/6.
 * description:
 */
class LockPatternView : View {

    // 点的集合[3][3]
    private var mPoints: Array<Array<Point?>> = Array(3) { Array<Point?>(3, { null }) }
    // 是否初始化点
    private var isInit = false
    // 外圆的半径
    private var mDotRadius: Int = 0
    // 画笔
    private lateinit var mLinePaint: Paint
    private lateinit var mPressedPaint: Paint
    private lateinit var mErrorPaint: Paint
    private lateinit var mNormalPaint: Paint
    private lateinit var mArrowPaint: Paint
    // 颜色
    private val mOuterPressedColor = 0xff8cbad8.toInt()
    private val mInnerPressedColor = 0xff0596f6.toInt()
    private val mOuterNormalColor = 0xffd9d9d9.toInt()
    private val mInnerNormalColor = 0xff929292.toInt()
    private val mOuterErrorColor = 0xff901032.toInt()
    private val mInnerErrorColor = 0xffea0945.toInt()

    var offsetY = 0
    var offsetX = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        if (!isInit) {
            isInit = true
            initDot()
            initPaint()
        }

        drawShow(canvas)
    }

    private fun drawShow(canvas: Canvas?) {
        for (mPoint in mPoints) {
            for (point in mPoint) {
                // 外圆
                mNormalPaint.color = mOuterNormalColor
                canvas!!.drawCircle(
                        point!!.centerX.toFloat(),
                        point.centerY.toFloat(),
                        mDotRadius.toFloat(),
                        mNormalPaint)

                // 内圆
                mNormalPaint.color = mInnerNormalColor
                canvas!!.drawCircle(
                        point!!.centerX.toFloat(),
                        point.centerY.toFloat(),
                        mDotRadius.toFloat() / 6,
                        mNormalPaint)

            }
        }
    }

    /**
     * 初始化画笔
     *
     */
    private fun initPaint() {
        // 线的画笔
        mLinePaint = Paint()
        mLinePaint.color = mInnerPressedColor
        mLinePaint.style = Paint.Style.STROKE
        mLinePaint.isAntiAlias = true
        mLinePaint.strokeWidth = (mDotRadius / 9).toFloat()
        // 按下的画笔
        mPressedPaint = Paint()
        mPressedPaint.style = Paint.Style.STROKE
        mPressedPaint.isAntiAlias = true
        mPressedPaint.strokeWidth = (mDotRadius / 6).toFloat()
        // 错误的画笔
        mErrorPaint = Paint()
        mErrorPaint.style = Paint.Style.STROKE
        mErrorPaint.isAntiAlias = true
        mErrorPaint.strokeWidth = (mDotRadius / 6).toFloat()
        // 默认的画笔
        mNormalPaint = Paint()
        mNormalPaint.style = Paint.Style.STROKE
        mNormalPaint.isAntiAlias = true
        mNormalPaint.strokeWidth = (mDotRadius / 9).toFloat()
        // 箭头的画笔
        mArrowPaint = Paint()
        mArrowPaint.color = mInnerPressedColor
        mArrowPaint.style = Paint.Style.FILL
        mArrowPaint.isAntiAlias = true
    }

    /**
     * 初始化点
     */
    private fun initDot() {
        var width = this.width
        var height = this.height

        var pointWidth = width / 3

        // 兼容横竖屏
        if (width > height) {
            // 横屏
            offsetX = (width - height) / 2
            offsetY = 0
        } else {
            // 竖屏
            offsetY = (height - width) / 2
            offsetX = 0
        }

        // 外圆的半径
        mDotRadius = pointWidth / 3

        mPoints[0][0] = Point(offsetX + pointWidth * 1 / 2, offsetY + pointWidth * 1 / 2, 0)
        mPoints[0][1] = Point(offsetX + pointWidth * 3 / 2, offsetY + pointWidth * 1 / 2, 1)
        mPoints[0][2] = Point(offsetX + pointWidth * 5 / 2, offsetY + pointWidth * 1 / 2, 2)

        mPoints[1][0] = Point(offsetX + pointWidth * 1 / 2, offsetY + pointWidth * 3 / 2, 3)
        mPoints[1][1] = Point(offsetX + pointWidth * 3 / 2, offsetY + pointWidth * 3 / 2, 4)
        mPoints[1][2] = Point(offsetX + pointWidth * 5 / 2, offsetY + pointWidth * 3 / 2, 5)

        mPoints[2][0] = Point(offsetX + pointWidth * 1 / 2, offsetY + pointWidth * 5 / 2, 6)
        mPoints[2][1] = Point(offsetX + pointWidth * 3 / 2, offsetY + pointWidth * 5 / 2, 7)
        mPoints[2][2] = Point(offsetX + pointWidth * 5 / 2, offsetY + pointWidth * 5 / 2, 8)
    }

    class Point(var centerX: Int, var centerY: Int, var index: Int) {
        private var STATUS_NORMAL = 1
        private var STATUS_PRESSED = 2
        private var STATUS_ERROR = 3

        private var status = STATUS_NORMAL

    }

}