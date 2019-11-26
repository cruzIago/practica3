package dadm.scaffold.space

import dadm.scaffold.R
import dadm.scaffold.engine.GameEngineK
import dadm.scaffold.engine.ScreenGameObjectK
import dadm.scaffold.engine.SpriteK

class BulletK(gameEngine: GameEngineK) : SpriteK(gameEngine, R.drawable.bullet) {

    private val speedFactor: Double

    private var parent: SpaceShipPlayerK? = null

    init {

        speedFactor = gameEngine.pixelFactor * -300.0 / 1000.0
    }

    override fun startGame() {}

    override fun onUpdate(elapsedMillis: Long, gameEngine: GameEngineK) {
        positionY += speedFactor * elapsedMillis
        if (positionY < -height) {
            gameEngine.removeGameObject(this)
            // And return it to the pool
            parent!!.releaseBullet(this)
        }
    }


    fun init(parentPlayer: SpaceShipPlayerK, initPositionX: Double, initPositionY: Double) {
        positionX = initPositionX - width / 2
        positionY = initPositionY - height / 2
        parent = parentPlayer
    }

    private fun removeObject(gameEngine: GameEngineK) {
        gameEngine.removeGameObject(this)
        // And return it to the pool
        parent!!.releaseBullet(this)
    }

    override fun onCollision(gameEngine: GameEngineK, otherObject: ScreenGameObjectK) {
        if (otherObject is AsteroidK) {
            // Remove both from the game (and return them to their pools)
            removeObject(gameEngine)
            otherObject.removeObject(gameEngine)
            // Add some score
        }
    }
}
