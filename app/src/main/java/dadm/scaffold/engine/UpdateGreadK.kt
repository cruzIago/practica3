package dadm.scaffold.engine

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class UpdateThreadK (private val theGameEngine: GameEngineK) : Thread() {
    var isGameRunning = true
        private set
    var isGamePaused = false
        private set

    private val synchroLock = ReentrantLock()
    private val condition=synchroLock.newCondition()

    override fun start() {
        isGameRunning = true
        isGamePaused = false
        super.start()
    }

    fun stopGame() {
        isGameRunning = false
        resumeGame()
    }

    override fun run() {
        var previousTimeMillis: Long
        var currentTimeMillis: Long
        var elapsedMillis: Long
        previousTimeMillis = System.currentTimeMillis()

        while (isGameRunning) {
            currentTimeMillis = System.currentTimeMillis()
            elapsedMillis = currentTimeMillis - previousTimeMillis
            if (isGamePaused) {
                while (isGamePaused) {
                    try {
                        synchroLock.withLock {
                            condition.await()
                        }
                    } catch (e: InterruptedException) {
                        // We stay on the loop
                    }

                }
                currentTimeMillis = System.currentTimeMillis()
            }
            theGameEngine.onUpdate(elapsedMillis)
            previousTimeMillis = currentTimeMillis
        }
    }

    fun pauseGame() {
        isGamePaused = true
    }

    fun resumeGame() {
        if (isGamePaused == true) {
            isGamePaused = false
            synchroLock.withLock {
                condition.signal()
            }
        }
    }
}
