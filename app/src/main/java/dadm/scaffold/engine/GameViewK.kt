package dadm.scaffold.engine

import android.content.Context

interface GameViewK{

    val width: Int?

    val height: Int?

    val paddingLeft: Int?

    val paddingRight: Int?

    val paddingTop: Int?

    val paddingBottom: Int?

    //val context: Context?

    fun draw()
    fun setGameObjects(gameObjects: List<GameObjectK>)
}

