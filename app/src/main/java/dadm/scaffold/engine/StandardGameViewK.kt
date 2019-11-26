package dadm.scaffold.engine

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

import java.util.ArrayList
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class StandardGameViewK : View {

    val width: Int?=null

    val height: Int? = null

    val paddingLeft: Int? = null

    val paddingRight: Int? = null

    val paddingTop: Int? = null

    val paddingBottom: Int? = null

    private var gameObjects: List<GameObjectK>? = null
    private var synchroLock = ReentrantLock()

    constructor(context: Context) : super(context) {
        this.gameObjects = ArrayList<GameObjectK>()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.gameObjects = ArrayList<GameObjectK>()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.gameObjects = ArrayList<GameObjectK>()
    }


    fun draw() {
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        synchroLock.withLock {
            val numObjects = gameObjects!!.size
            for (i in 0 until numObjects) {
                gameObjects!![i].onDraw(canvas)
            }
        }
    }

    fun setGameObjects(gameObjects: List<GameObjectK>) {
        this.gameObjects = gameObjects
    }
}
