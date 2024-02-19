package com.ntphoi.weatherforecast.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathEffect
import android.graphics.DashPathEffect
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

class CustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val circlePaint: Paint = Paint()
    private val handPaint: Paint = Paint()
    private val textPaint: Paint = Paint()
    private var direction = 0
    private var speed = 0

    init {
        // Initialization of Paint for the circle representing the clock
        circlePaint.color = Color.parseColor("#787878")
        circlePaint.style = Paint.Style.STROKE
        circlePaint.strokeWidth = 20f

        // Initialization of Paint for the direction hands
        handPaint.color = Color.BLACK
        handPaint.strokeWidth = 5f
        handPaint.strokeCap = Paint.Cap.ROUND

        // Initialization of Paint for text
        textPaint.color = Color.parseColor("#BCBCBC")
        textPaint.textSize = 30f
        textPaint.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f

        // Bán kính được tính sao cho vòng tròn ôm chọn 4 chữ
        val radius = (Math.min(width, height) / 2 * 0.94).toFloat()

        // Draw dashed circle
        drawDashedCircle(canvas, centerX, centerY, radius)

        // Vẽ kim chỉ hướng
        val handAngle = Math.toRadians(direction.toDouble())
        val handLength = radius - 20
        val handX = centerX + handLength * Math.cos(handAngle).toFloat()
        val handY = centerY + handLength * Math.sin(handAngle).toFloat()
        canvas.drawLine(centerX, centerY, handX, handY, handPaint)

        val arrowSize = 10
        val arrowAngle = Math.toRadians(30.0) // Góc mũi tên
        val arrowX1 = handX - arrowSize * cos(handAngle + arrowAngle).toFloat()
        val arrowY1 = handY - arrowSize * sin(handAngle + arrowAngle).toFloat()
        val arrowX2 = handX - arrowSize * cos(handAngle - arrowAngle).toFloat()
        val arrowY2 = handY - arrowSize * sin(handAngle - arrowAngle).toFloat()

        canvas.drawLine(centerX, centerY, handX, handY, handPaint)
        canvas.drawLine(handX, handY, arrowX1, arrowY1, handPaint)
        canvas.drawLine(handX, handY, arrowX2, arrowY2, handPaint)

        // Draw directions with arrows
        drawArrowText(canvas, "B", centerX, centerY - radius + textPaint.textSize + 26)
        drawArrowText(canvas, "Đ", centerX + radius - textPaint.textSize, centerY)
        drawArrowText(canvas, "T", centerX - radius + textPaint.textSize, centerY)
        drawArrowText(canvas, "N", centerX, centerY + radius - textPaint.textSize + 10)

        val textMargin = 65f
        val textDistance = radius - textMargin
        val speedText = speed.toString()
        canvas.drawText(speedText, centerX, centerY - textDistance + textPaint.textSize, textPaint)
        canvas.drawText("km/h", centerX, centerY - textDistance + textPaint.textSize * 2, textPaint)
        super.onDraw(canvas)
    }

    fun setDirection(newDirection: Int) {
        // Phương thức để cập nhật giá trị của direction từ bên ngoài
        direction = newDirection
        invalidate()  // Yêu cầu vẽ lại View
    }

    fun setSpeed(newSpeed: Int) {
        speed = newSpeed
        invalidate()
    }

    private fun drawArrowText(canvas: Canvas, text: String, x: Float, y: Float) {
        val arrowLength = 20f
        val arrowAngle = 30.0
        val arrowPath = Path()

        // Move to the starting point
        arrowPath.moveTo(x, y - arrowLength)

        // Draw the arrow on top of the text
        arrowPath.lineTo(
            (x + arrowLength * sin(Math.toRadians(arrowAngle))).toFloat(),
            (y - arrowLength * cos(Math.toRadians(arrowAngle))).toFloat()
        )
        arrowPath.moveTo(x, y - arrowLength)
        arrowPath.lineTo(
            (x - arrowLength * sin(Math.toRadians(arrowAngle))).toFloat(),
            (y - arrowLength * cos(Math.toRadians(arrowAngle))).toFloat()
        )

        // Draw the text
        canvas.drawText(text, x, y, textPaint)
        canvas.drawPath(arrowPath, handPaint)
    }

    private fun drawDashedCircle(canvas: Canvas, x: Float, y: Float, radius: Float) {
        val paint = Paint()
        paint.color = Color.parseColor("#787878")
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 20f

        // Draw dashed circle
        val intervals = floatArrayOf(4f, 4f) // Độ dài đoạn và khoảng cách giữa đoạn
        val effect: PathEffect = DashPathEffect(intervals, 0f)
        paint.pathEffect = effect

        val dashPath = Path()
        dashPath.addCircle(x, y, radius, Path.Direction.CW)
        canvas.drawPath(dashPath, paint)
    }
}