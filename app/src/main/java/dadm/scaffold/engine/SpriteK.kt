package dadm.scaffold.engine

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable

abstract class SpriteK protected constructor(gameEngine: GameEngineK, drawableRes: Int) : ScreenGameObjectK() {

    protected var rotation: Double = 0.toDouble()

    protected var pixelFactor: Double = 0.toDouble()

    private val bitmap: Bitmap

    private val matrix = Matrix()

    init {
        val r = gameEngine.context.resources
        val spriteDrawable = r.getDrawable(drawableRes)

        this.pixelFactor = gameEngine.pixelFactor

        this.width = (spriteDrawable.intrinsicHeight * this.pixelFactor).toInt()
        this.height = (spriteDrawable.intrinsicWidth * this.pixelFactor).toInt()

        this.bitmap = (spriteDrawable as BitmapDrawable).bitmap

        radius = (Math.max(height, width) / 2).toDouble()
    }

    override fun onDraw(canvas: Canvas) {
        if (positionX > canvas.width
                || positionY > canvas.height
                || positionX < -width
                || positionY < -height) {
            return
        }
        matrix.reset()
        matrix.postScale(pixelFactor.toFloat(), pixelFactor.toFloat())
        matrix.postTranslate(positionX.toFloat(), positionY.toFloat())
        matrix.postRotate(rotation.toFloat(), (positionX + width / 2).toFloat(), (positionY + height / 2).toFloat())
        canvas.drawBitmap(bitmap, matrix, null)
    }
}
