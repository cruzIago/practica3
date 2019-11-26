package dadm.scaffold.input

import android.view.MotionEvent
import android.view.View
import android.widget.Button

import dadm.scaffold.R

class BasicInputControllerK //Atención: se usa OnTouchListener para saber cuando el botón
//sigue siendo apretado
(view: View) : InputControllerK(), View.OnTouchListener {

    init {

        view.findViewById<Button>(R.id.keypad_up).setOnTouchListener(this)
        view.findViewById<Button>(R.id.keypad_down).setOnTouchListener(this)
        view.findViewById<Button>(R.id.keypad_left).setOnTouchListener(this)
        view.findViewById<Button>(R.id.keypad_right).setOnTouchListener(this)
        view.findViewById<Button>(R.id.keypad_fire).setOnTouchListener(this)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        //Se usa getActionMasked para manejar el multitouch
        val action = event.actionMasked
        val id = v.id
        if (action == MotionEvent.ACTION_DOWN) {
            // User started pressing a key
            if (id == R.id.keypad_up) {
                verticalFactor -= 1.0
            } else if (id == R.id.keypad_down) {
                verticalFactor += 1.0
            } else if (id == R.id.keypad_left) {
                horizontalFactor -= 1.0
            } else if (id == R.id.keypad_right) {
                horizontalFactor += 1.0
            } else if (id == R.id.keypad_fire) {
                isFiring = true
            }
        } else if (action == MotionEvent.ACTION_UP) {
            if (id == R.id.keypad_up) {
                verticalFactor += 1.0
            } else if (id == R.id.keypad_down) {
                verticalFactor -= 1.0
            } else if (id == R.id.keypad_left) {
                horizontalFactor += 1.0
            } else if (id == R.id.keypad_right) {
                horizontalFactor -= 1.0
            } else if (id == R.id.keypad_fire) {
                isFiring = false
            }
        }
        return false
    }
}
