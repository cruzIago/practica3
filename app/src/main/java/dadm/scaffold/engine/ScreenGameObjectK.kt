package dadm.scaffold.engine

import android.graphics.Rect

abstract class ScreenGameObjectK : GameObjectK() {

    protected var positionX: Double = 0.toDouble()
    protected var positionY: Double = 0.toDouble()

    protected var width: Int = 0
    protected var height: Int = 0

    var radius: Double = 0.toDouble()

    var mBoundingRect = Rect(-1, -1, -1, -1)

    abstract fun onCollision(gameEngine: GameEngineK, otherObject: ScreenGameObjectK)

    fun onPostUpdate(gameEngine: GameEngineK) {
        mBoundingRect.set(
                positionX.toInt(),
                positionY.toInt(),
                positionX.toInt() + width,
                positionY.toInt() + height)
    }

    fun checkCollision(otherObject: ScreenGameObjectK): Boolean {
        return checkCircularCollision(otherObject)
    }

    private fun checkCircularCollision(other: ScreenGameObjectK): Boolean {
        val distanceX = positionX + width / 2 - (other.positionX + other.width / 2)
        val distanceY = positionY + height / 2 - (other.positionY + other.height / 2)
        val squareDistance = distanceX * distanceX + distanceY * distanceY
        val collisionDistance = radius + other.radius
        return squareDistance <= collisionDistance * collisionDistance
    }

}
