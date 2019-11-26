package dadm.scaffold.space

import dadm.scaffold.R
import dadm.scaffold.engine.GameEngineK
import dadm.scaffold.engine.ScreenGameObjectK
import dadm.scaffold.engine.SpriteK

class AsteroidK(private val gameController: GameControllerK, gameEngine: GameEngineK) : SpriteK(gameEngine, R.drawable.a10000) {

    private val speed: Double
    private var speedX: Double = 0.toDouble()
    private var speedY: Double = 0.toDouble()
    private var rotationSpeed: Double = 0.toDouble()

    init {
        this.speed = 200.0 * pixelFactor / 1000.0
    }

    fun init(gameEngine: GameEngineK) {
        // They initialize in a [-30, 30] degrees angle
        val angle = gameEngine.random.nextDouble() * Math.PI / 3.0 - Math.PI / 6.0
        speedX = speed * Math.sin(angle)
        speedY = speed * Math.cos(angle)
        // Asteroids initialize in the central 50% of the screen horizontally
        positionX = (gameEngine.random.nextInt(gameEngine.width / 2) + gameEngine.width / 4).toDouble()
        // They initialize outside of the screen vertically
        positionY = (-height).toDouble()
        rotationSpeed = angle * (180.0 / Math.PI) / 250.0 // They rotate 4 times their ange in a second.
        rotation = gameEngine.random.nextInt(360).toDouble()
    }

    override fun startGame() {}

    fun removeObject(gameEngine: GameEngineK) {
        // Return to the pool
        gameEngine.removeGameObject(this)
        gameController.returnToPool(this)
    }

    override fun onUpdate(elapsedMillis: Long, gameEngine: GameEngineK) {
        positionX += speedX * elapsedMillis
        positionY += speedY * elapsedMillis
        rotation += rotationSpeed * elapsedMillis
        if (rotation > 360) {
            rotation = 0.0
        } else if (rotation < 0) {
            rotation = 360.0
        }
        // Check of the sprite goes out of the screen and return it to the pool if so
        if (positionY > gameEngine.height) {
            // Return to the pool
            gameEngine.removeGameObject(this)
            gameController.returnToPool(this)
        }
    }

    override fun onCollision(gameEngine: GameEngineK, otherObject: ScreenGameObjectK) {

    }
}
