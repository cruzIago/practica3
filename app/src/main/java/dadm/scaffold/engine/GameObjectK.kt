package dadm.scaffold.engine

import android.graphics.Canvas

abstract class GameObjectK {

    val onAddedRunnable: Runnable = Runnable { onAddedToGameUiThread() }

    val onRemovedRunnable: Runnable = Runnable { onRemovedFromGameUiThread() }

    abstract fun startGame()

    abstract fun onUpdate(elapsedMillis: Long, gameEngine: GameEngineK)

    abstract fun onDraw(canvas: Canvas)

    fun onAddedToGameUiThread() {}

    fun onRemovedFromGameUiThread() {}

}
