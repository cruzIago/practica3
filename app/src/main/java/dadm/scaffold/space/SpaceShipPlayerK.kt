package dadm.scaffold.space

import java.util.ArrayList

import dadm.scaffold.R
import dadm.scaffold.engine.GameEngineK
import dadm.scaffold.engine.ScreenGameObjectK
import dadm.scaffold.engine.SpriteK
import dadm.scaffold.input.InputControllerK

class SpaceShipPlayerK(gameEngine: GameEngineK) : SpriteK(gameEngine, R.drawable.ship) {
    internal var bullets: MutableList<BulletK> = ArrayList()
    private var timeSinceLastFire: Long = 0

    private val maxX: Int
    private val maxY: Int
    private val speedFactor: Double

    private val bullet: BulletK?
        get() = if (bullets.isEmpty()) {
            null
        } else bullets.removeAt(0)


    init {
        speedFactor = pixelFactor * 100.0 / 1000.0 // We want to move at 100px per second on a 400px tall screen
        maxX = gameEngine.width - width
        maxY = gameEngine.height - height

        initBulletPool(gameEngine)
    }

    private fun initBulletPool(gameEngine: GameEngineK) {
        for (i in 0 until INITIAL_BULLET_POOL_AMOUNT) {
            bullets.add(BulletK(gameEngine))
        }
    }

    internal fun releaseBullet(bullet: BulletK) {
        bullets.add(bullet)
    }


    override fun startGame() {
        positionX = (maxX / 2).toDouble()
        positionY = (maxY / 2).toDouble()
    }

    override fun onUpdate(elapsedMillis: Long, gameEngine: GameEngineK) {
        // Get the info from the inputController
        updatePosition(elapsedMillis, gameEngine.getTheInputController())
        checkFiring(elapsedMillis, gameEngine)
    }

    private fun updatePosition(elapsedMillis: Long, inputController: InputControllerK?) {
        positionX += speedFactor * inputController!!.horizontalFactor * elapsedMillis.toDouble()
        if (positionX < 0) {
            positionX = 0.0
        }
        if (positionX > maxX) {
            positionX = maxX.toDouble()
        }
        positionY += speedFactor * inputController.verticalFactor * elapsedMillis.toDouble()
        if (positionY < 0) {
            positionY = 0.0
        }
        if (positionY > maxY) {
            positionY = maxY.toDouble()
        }
    }

    private fun checkFiring(elapsedMillis: Long, gameEngine: GameEngineK) {
        if (gameEngine.getTheInputController()!!.isFiring && timeSinceLastFire > TIME_BETWEEN_BULLETS) {
            val bullet = bullet ?: return
            bullet.init(this, positionX + width / 2, positionY)
            gameEngine.addGameObject(bullet)
            timeSinceLastFire = 0
        } else {
            timeSinceLastFire += elapsedMillis
        }
    }

    override fun onCollision(gameEngine: GameEngineK, otherObject: ScreenGameObjectK) {
        if (otherObject is AsteroidK) {
            gameEngine.removeGameObject(this)
            //gameEngine.stopGame();
            otherObject.removeObject(gameEngine)
        }
    }

    companion object {

        private val INITIAL_BULLET_POOL_AMOUNT = 6
        private val TIME_BETWEEN_BULLETS: Long = 250
    }
}
