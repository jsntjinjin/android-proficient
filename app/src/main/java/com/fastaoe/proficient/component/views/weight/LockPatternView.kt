package com.fastaoe.proficient.component.views.weight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Created by jinjin on 17/7/6.
 * description:
 */
class LockPatternView : View {

    private var listener: LockPatternListener? = null
    fun setLockPatternListener(listener: LockPatternListener) {
        this.listener = listener
    }
    interface LockPatternListener{
        fun lock(password: String)
    }

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

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        if (!isInit) {
            isInit = true
            initDot()
            initPaint()
        }

        drawShow(canvas)
    }

    private var moveX = 0f
    private var moveY = 0f
    // 是否开始解锁
    private var isTouchPoint = false
    // 解锁的点的集合
    private var selectPoints = ArrayList<Point>()

    override fun onTouchEvent(event: MotionEvent): Boolean {
        moveX = event.x
        moveY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                var firstPoint = point
                if (firstPoint != null) {
                    // 选择了点
                    selectPoints.add(firstPoint)
                    isTouchPoint = true
                    firstPoint.setStatusPressed()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (isTouchPoint) {
                    var point = point
                    // 选择了点并且点不在选择点的集合中
                    if (point != null && !selectPoints.contains(point)) {
                        selectPoints.add(point)
                        point.setStatusPressed()
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                // 回调密码
                if (selectPoints.size == 1) {
                    // 只选择了一个点
                    clearSelectPoints()
                } else if (selectPoints.size < 4) {
                    // 选择的点过少
                    showSelectError()
                } else {
                    // 成功 -> 回调
                    lockCallback()
                }
                isTouchPoint = false
            }
        }

        invalidate()
        return true
    }

    private fun lockCallback() {
        // 成功回调
        var password = ""
        for (selectPoint in selectPoints) {
            password += selectPoint.index
        }
        listener!!.lock(password)
        clearSelectPoints()
    }

    private fun showSelectError() {
        // 密码太短
        for (selectPoint in selectPoints) {
            selectPoint.setStatusError()
        }

        postDelayed({
            clearSelectPoints()
            invalidate()
        }, 1000)
    }

    private fun clearSelectPoints() {
        for (selectPoint in selectPoints) {
            selectPoint.setStatusNormal()
        }
        selectPoints.clear()
    }

    /**
     * 绘制两个点之间的连线以及箭头
     */
    private fun drawLine(canvas: Canvas) {
        if (selectPoints.size >= 1) {
            // 两个点之间需要绘制一条线和箭头
            var lastPoint = selectPoints[0]

            // if (mSelectPoints.size != 1) {
            // for(int i=1;i<mSelectPoints.size-1;i++)
            for (index in 1..selectPoints.size - 1) {
                // 两个点之间绘制一条线
                drawLine(lastPoint, selectPoints[index], canvas, mLinePaint)
                // 两个点之间绘制一个箭头
                drawArrow(canvas, mArrowPaint!!, lastPoint, selectPoints[index], (mDotRadius / 5).toFloat(), 38)
                lastPoint = selectPoints[index]
            }
            //}


            // 绘制最后一个点到手指当前位置的连线
            // 如果手指在内圆里面就不要绘制
            var isInnerPoint = MathUtil.checkInRound(lastPoint.centerX.toFloat(), lastPoint.centerY.toFloat(),
                    mDotRadius.toFloat() / 4, moveX, moveY)
            if (!isInnerPoint && isTouchPoint) {
                drawLine(lastPoint, Point(moveX.toInt(), moveY.toInt(), -1), canvas, mLinePaint)
            }
        }
    }

    /**
     * 画箭头
     */
    private fun drawArrow(canvas: Canvas, paint: Paint, start: Point, end: Point, arrowHeight: Float, angle: Int) {
        val d = MathUtil.distance(start.centerX.toDouble(), start.centerY.toDouble(), end.centerX.toDouble(), end.centerY.toDouble())
        val sin_B = ((end.centerX - start.centerX) / d).toFloat()
        val cos_B = ((end.centerY - start.centerY) / d).toFloat()
        val tan_A = Math.tan(Math.toRadians(angle.toDouble())).toFloat()
        val h = (d - arrowHeight.toDouble() - mDotRadius * 1.1).toFloat()
        val l = arrowHeight * tan_A
        val a = l * sin_B
        val b = l * cos_B
        val x0 = h * sin_B
        val y0 = h * cos_B
        val x1 = start.centerX + (h + arrowHeight) * sin_B
        val y1 = start.centerY + (h + arrowHeight) * cos_B
        val x2 = start.centerX + x0 - b
        val y2 = start.centerY.toFloat() + y0 + a
        val x3 = start.centerX.toFloat() + x0 + b
        val y3 = start.centerY + y0 - a
        val path = Path()
        path.moveTo(x1, y1)
        path.lineTo(x2, y2)
        path.lineTo(x3, y3)
        path.close()
        canvas.drawPath(path, paint)
    }

    /**
     * 画线
     */
    private fun drawLine(start: Point, end: Point, canvas: Canvas, paint: Paint) {
        val pointDistance = MathUtil.distance(start.centerX.toDouble(), start.centerY.toDouble(),
                end.centerX.toDouble(), end.centerY.toDouble())

        var dx = end.centerX - start.centerX
        var dy = end.centerY - start.centerY

        val rx = (dx / pointDistance * (mDotRadius / 6.0)).toFloat()
        val ry = (dy / pointDistance * (mDotRadius / 6.0)).toFloat()
        canvas.drawLine(start.centerX + rx, start.centerY + ry, end.centerX - rx, end.centerY - ry, paint)
    }

    private fun drawShow(canvas: Canvas) {
        for (mPoint in mPoints) {
            for (point in mPoint) {
                if (point!!.statusIsNormal()) {
                    mNormalPaint.color = mOuterNormalColor
                    mNormalPaint.color = mInnerNormalColor
                } else if (point.statusIsPressed()) {
                    mNormalPaint.color = mOuterPressedColor
                    mNormalPaint.color = mInnerPressedColor
                } else {
                    mNormalPaint.color = mOuterErrorColor
                    mNormalPaint.color = mInnerErrorColor
                }
                // 外圆
                canvas.drawCircle(
                        point.centerX.toFloat(),
                        point.centerY.toFloat(),
                        mDotRadius.toFloat(),
                        mNormalPaint)

                // 内圆
                canvas.drawCircle(
                        point.centerX.toFloat(),
                        point.centerY.toFloat(),
                        mDotRadius.toFloat() / 6,
                        mNormalPaint)

            }
        }
        drawLine(canvas)
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

    private var offsetY = 0
    private var offsetX = 0

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

    /**
     * 获取按下的点
     * @return 当前按下的点
     */
    private val point: Point?
        get() {
            for (i in mPoints.indices) {
                for (j in 0..mPoints[i].size - 1) {
                    val point = mPoints[i][j]
                    if (point != null) {
                        if (MathUtil.checkInRound(point.centerX.toFloat(), point.centerY.toFloat(), mDotRadius.toFloat(), moveX, moveY)) {
                            return point
                        }
                    }
                }
            }
            return null
        }

    class Point(var centerX: Int, var centerY: Int, var index: Int) {
        private var STATUS_NORMAL = 1
        private var STATUS_PRESSED = 2
        private var STATUS_ERROR = 3

        private var status = STATUS_NORMAL

        fun setStatusPressed() {
            status = STATUS_PRESSED
        }

        fun setStatusNormal() {
            status = STATUS_NORMAL
        }

        fun setStatusError() {
            status = STATUS_ERROR
        }

        fun statusIsPressed(): Boolean {
            return status == STATUS_PRESSED
        }

        fun statusIsError(): Boolean {
            return status == STATUS_ERROR
        }

        fun statusIsNormal(): Boolean {
            return status == STATUS_NORMAL
        }

    }

    object MathUtil {

        /**
         * @param x1
         * *
         * @param y1
         * *
         * @param x2
         * *
         * @param y2
         * *
         * @return
         */
        fun distance(x1: Double, y1: Double, x2: Double, y2: Double): Double {
            return Math.sqrt(Math.abs(x1 - x2) * Math.abs(x1 - x2) + Math.abs(y1 - y2) * Math.abs(y1 - y2))
        }

        /**
         * @param x
         * *
         * @param y
         * *
         * @return
         */
        fun pointTotoDegrees(x: Double, y: Double): Double {
            return Math.toDegrees(Math.atan2(x, y))
        }

        fun checkInRound(sx: Float, sy: Float, r: Float, x: Float,
                         y: Float): Boolean {
            // x的平方 + y的平方 开根号 < 半径
            return Math.sqrt(((sx - x) * (sx - x) + (sy - y) * (sy - y)).toDouble()) < r
        }
    }
}