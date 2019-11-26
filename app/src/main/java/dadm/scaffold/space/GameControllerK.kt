package dadm.scaffold.space

import android.graphics.Canvas

import java.util.ArrayList

import dadm.scaffold.engine.GameEngineK
import dadm.scaffold.engine.GameObjectK

class GameControllerK(gameEngine: GameEngineK) : GameObjectK() {
    private var currentMillis: Long = 0
    private val asteroidPool = ArrayList<AsteroidK>()
    private var enemiesSpawned: Int = 0

    init {
        // We initialize the pool of items now
        for (i in 0..9) {
            asteroidPool.add(AsteroidK(this, gameEngine))
        }
    }

    override fun startGame() {
        currentMillis = 0
        enemiesSpawned = 0
    }

    override fun onUpdate(elapsedMillis: Long, gameEngine: GameEngineK) {
        currentMillis += elapsedMillis

        val waveTimestamp = (enemiesSpawned * TIME_BETWEEN_ENEMIES).toLong()
        if (currentMillis > waveTimestamp) {
            // Spawn a new enemy
            val a = asteroidPool.removeAt(0)
            a.init(gameEngine)
            gameEngine.addGameObject(a)
            enemiesSpawned++
            return
        }
    }

    override fun onDraw(canvas: Canvas) {
        // This game object does not draw anything
    }

    fun returnToPool(asteroid: AsteroidK) {
        asteroidPool.add(asteroid)
    }

    companion object {

        private val TIME_BETWEEN_ENEMIES = 500
    }
}
