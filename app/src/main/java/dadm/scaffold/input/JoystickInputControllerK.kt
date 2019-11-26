package dadm.scaffold.input

import android.view.MotionEvent
import android.view.View

import dadm.scaffold.R

class JoystickInputControllerK(view: View) : InputControllerK() {

    private var startingPositionX: Float = 0.toFloat()
    private var startingPositionY: Float = 0.toFloat()

    private val maxDistance: Double

    init {
        view.findViewById<View>(R.id.joystick_main).setOnTouchListener(JoystickTouchListener())
        view.findViewById<View>(R.id.joystick_touch).setOnTouchListener(FireButtonTouchListener())

        val pixelFactor = view.height / 400.0
        maxDistance = 50 * pixelFactor
    }

    private inner class JoystickTouchListener : View.OnTouchListener {
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            val action = event.actionMasked
            if (action == MotionEvent.ACTION_DOWN) {
                startingPositionX = event.getX(0)
                startingPositionY = event.getY(0)
            } else if (action == MotionEvent.ACTION_UP) {
                horizontalFactor = 0.0
                verticalFactor = 0.0
            } else if (action == MotionEvent.ACTION_MOVE) {
                // Get the proportion to the max
                horizontalFactor = (event.getX(0) - startingPositionX) / maxDistance
                if (horizontalFactor > 1) {
                    horizontalFactor = 1.0
                } else if (horizontalFactor < -1) {
                    horizontalFactor = -1.0
                }
                verticalFactor = (event.getY(0) - startingPositionY) / maxDistance
                if (verticalFactor > 1) {
                    verticalFactor = 1.0
                } else if (verticalFactor < -1) {
                    verticalFactor = -1.0
                }
            }
            return true
        }
    }

    private inner class FireButtonTouchListener : View.OnTouchListener {
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            val action = event.actionMasked
            if (action == MotionEvent.ACTION_DOWN) {
                isFiring = true
            } else if (action == MotionEvent.ACTION_UP) {
                isFiring = false
            }
            return true
        }
    }
}

