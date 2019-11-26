package dadm.scaffold.engine

import android.app.Activity
import android.content.Context

import java.util.ArrayList
import java.util.Random

import dadm.scaffold.input.InputControllerK

class GameEngineK(private val mainActivity: Activity?, private val theGameView: GameView) {


    private val gameObjects = ArrayList<GameObjectK>()
    private val objectsToAdd = ArrayList<GameObjectK>()
    private val objectsToRemove = ArrayList<GameObjectK>()

    private var theUpdateThread: UpdateThreadK? = null
    private var theDrawThread: DrawThreadK? = null
    private var theInputController: InputControllerK? = null

    var random = Random()

    var width: Int = 0
    var height: Int = 0
    var pixelFactor: Double = 0.toDouble()

    val isRunning: Boolean
        get() = theUpdateThread != null && theUpdateThread!!.isGameRunning

    val isPaused: Boolean
        get() = theUpdateThread != null && theUpdateThread!!.isGamePaused

    val context: Context
        get() = theGameView.context

    init {
        theGameView.setGameObjects(this.gameObjects)
        this.width = (theGameView.width
                - theGameView.paddingRight - theGameView.paddingLeft)
        this.height = (theGameView.height
                - theGameView.paddingTop - theGameView.paddingTop)

        this.pixelFactor = this.height / 400.0


    }

    fun setTheInputController(inputController: InputControllerK) {
        theInputController = inputController
    }

    fun getTheInputController(): InputControllerK? {
        return theInputController
    }

    fun startGame() {
        // Stop a game if it is running
        stopGame()

        // Setup the game objects
        val numGameObjects = gameObjects.size
        for (i in 0 until numGameObjects) {
            gameObjects[i].startGame()
        }

        // Start the update thread
        theUpdateThread = UpdateThreadK(this)
        theUpdateThread!!.start()

        // Start the drawing thread
        theDrawThread = DrawThreadK(this)
        theDrawThread!!.start()
    }

    fun stopGame() {
        if (theUpdateThread != null) {
            theUpdateThread!!.stopGame()
        }
        if (theDrawThread != null) {
            theDrawThread!!.stopGame()
        }
    }

    fun pauseGame() {
        if (theUpdateThread != null) {
            theUpdateThread!!.pauseGame()
        }
        if (theDrawThread != null) {
            theDrawThread!!.pauseGame()
        }
    }

    fun resumeGame() {
        if (theUpdateThread != null) {
            theUpdateThread!!.resumeGame()
        }
        if (theDrawThread != null) {
            theDrawThread!!.resumeGame()
        }
    }

    fun addGameObject(gameObject: GameObjectK) {
        if (isRunning) {
            objectsToAdd.add(gameObject)
        } else {
            gameObjects.add(gameObject)
        }
        mainActivity!!.runOnUiThread(gameObject.onAddedRunnable)
    }

    fun removeGameObject(gameObject: GameObjectK) {
        objectsToRemove.add(gameObject)
        mainActivity!!.runOnUiThread(gameObject.onRemovedRunnable)
    }

    fun onUpdate(elapsedMillis: Long) {
        val numGameObjects = gameObjects.size
        for (i in 0 until numGameObjects) {
            val go = gameObjects[i]
            go.onUpdate(elapsedMillis, this)
            if (go is ScreenGameObjectK) {
                go.onPostUpdate(this)
            }
        }
        checkCollisions()
        synchronized(gameObjects) {
            while (!objectsToRemove.isEmpty()) {
                gameObjects.remove(objectsToRemove.removeAt(0))
            }
            while (!objectsToAdd.isEmpty()) {
                gameObjects.add(objectsToAdd.removeAt(0))
            }
        }
    }

    fun onDraw() {
        theGameView.draw()
    }

    private fun checkCollisions() {
        val numObjects = gameObjects.size
        for (i in 0 until numObjects) {
            if (gameObjects[i] is ScreenGameObjectK) {
                val objectA = gameObjects[i] as ScreenGameObjectK
                for (j in i + 1 until numObjects) {
                    if (gameObjects[j] is ScreenGameObjectK) {
                        val objectB = gameObjects[j] as ScreenGameObjectK
                        if (objectA.checkCollision(objectB)) {
                            objectA.onCollision(this, objectB)
                            objectB.onCollision(this, objectA)
                        }
                    }
                }
            }
        }
    }
}
