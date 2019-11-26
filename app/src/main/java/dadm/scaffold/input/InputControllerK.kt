package dadm.scaffold.input

open class InputControllerK {

    //region ||| Public properties |||

    var horizontalFactor: Double = 0.toDouble()
    var verticalFactor: Double = 0.toDouble()

    var isFiring: Boolean = false

    //endregion

    //region ||| Public methods |||

    fun onStart() {}

    fun onStop() {}

    fun onPause() {}

    fun onResume() {}

    fun onPreUpdate() {}

    //endregion
}
