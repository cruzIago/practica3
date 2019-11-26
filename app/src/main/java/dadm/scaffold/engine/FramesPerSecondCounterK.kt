package dadm.scaffold.engine

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class FramesPerSecondCounterK(gameEngine: GameEngineK) : GameObjectK() {

    private val textWidth: Float
    private val textHeight: Float

    private val paint: Paint
    private var totalMillis: Long = 0
    private var draws: Int = 0
    private var framesPerSecond: Float = 0.toFloat()

    private var framesPerSecondText = ""

    init {
        paint = Paint()
        paint.textAlign = Paint.Align.CENTER
        textHeight = (25 * gameEngine.pixelFactor).toFloat()
        textWidth = (50 * gameEngine.pixelFactor).toFloat()
        paint.textSize = textHeight / 2
    }

    override fun startGame() {
        totalMillis = 0
    }

    override fun onUpdate(elapsedMillis: Long, gameEngine: GameEngineK) {
        totalMillis += elapsedMillis
        if (totalMillis > 1000) {
            framesPerSecond = (draws * 1000 / totalMillis).toFloat()
            framesPerSecondText = "$framesPerSecond fps"
            totalMillis = 0
            draws = 0
        }
    }

    override fun onDraw(canvas: Canvas) {
        paint.color = Color.BLACK
        canvas.drawRect(0f, (canvas.height - textHeight).toInt().toFloat(), textWidth, canvas.height.toFloat(), paint)
        paint.color = Color.WHITE
        canvas.drawText(framesPerSecondText, textWidth / 2, (canvas.height - textHeight / 2).toInt().toFloat(), paint)
        draws++
    }
}
