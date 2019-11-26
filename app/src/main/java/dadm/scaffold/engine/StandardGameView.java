package dadm.scaffold.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class StandardGameView extends View implements GameView {

    private List<GameObjectK> gameObjects;

    public StandardGameView(Context context) {
        super(context);
        this.gameObjects = new ArrayList<GameObjectK>();
    }

    public StandardGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.gameObjects = new ArrayList<GameObjectK>();
    }

    public StandardGameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.gameObjects = new ArrayList<GameObjectK>();
    }

    @Override
    public void draw() {
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        synchronized (gameObjects) {
            int numObjects = gameObjects.size();
            for (int i = 0; i < numObjects; i++) {
                gameObjects.get(i).onDraw(canvas);
            }
        }
    }

    @Override
    public void setGameObjects(List<GameObjectK> gameObjects) {
        this.gameObjects = gameObjects;
    }
}
