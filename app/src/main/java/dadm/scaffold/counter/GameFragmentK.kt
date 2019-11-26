package dadm.scaffold.counter

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Button
import dadm.scaffold.BaseFragmentK
import dadm.scaffold.MainActivity

import dadm.scaffold.R
import dadm.scaffold.engine.*
import dadm.scaffold.input.JoystickInputControllerK
import dadm.scaffold.space.GameControllerK
import dadm.scaffold.space.SpaceShipPlayerK


class GameFragmentK : BaseFragmentK(), View.OnClickListener {
    private var theGameEngine: GameEngineK?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_play_pause).setOnClickListener(this)
        val observer = view.viewTreeObserver
        observer.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                //Para evitar que sea llamado múltiples veces,
                //se elimina el listener en cuanto es llamado
                observer.removeOnGlobalLayoutListener(this)
                val gameView = getView()?.findViewById<View>(R.id.gameView) as GameView
                theGameEngine = GameEngineK(activity, gameView)
                theGameEngine!!.setTheInputController(JoystickInputControllerK(getView()!!))
                theGameEngine!!.addGameObject(SpaceShipPlayerK(theGameEngine?:return))
                theGameEngine!!.addGameObject(FramesPerSecondCounterK(theGameEngine!!))
                theGameEngine!!.addGameObject(GameControllerK(theGameEngine?:return))
                theGameEngine!!.startGame()
            }
        })


    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_play_pause) {
            pauseGameAndShowPauseDialog()
        }
    }

    override fun onPause() {
        super.onPause()
        if (theGameEngine!!.isRunning) {
            pauseGameAndShowPauseDialog()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        theGameEngine!!.stopGame()
    }

    override fun onBackPressed(): Boolean {
        if (theGameEngine!!.isRunning) {
            pauseGameAndShowPauseDialog()
            return true
        }
        return false
    }

    private fun pauseGameAndShowPauseDialog() {
        theGameEngine!!.pauseGame()
        AlertDialog.Builder(activity)
                .setTitle(R.string.pause_dialog_title)
                .setMessage(R.string.pause_dialog_message)
                .setPositiveButton(R.string.resume) { dialog, which ->
                    dialog.dismiss()
                    theGameEngine!!.resumeGame()
                }
                .setNegativeButton(R.string.stop) { dialog, which ->
                    dialog.dismiss()
                    theGameEngine!!.stopGame()
                    (activity as MainActivity).navigateBack()
                }
                .setOnCancelListener { theGameEngine!!.resumeGame() }
                .create()
                .show()

    }

    private fun playOrPause() {
        val button = view!!.findViewById(R.id.btn_play_pause) as Button
        if (theGameEngine!!.isPaused) {
            theGameEngine!!.resumeGame()
            button.setText(R.string.pause)
        } else {
            theGameEngine!!.pauseGame()
            button.setText(R.string.resume)
        }
    }
}
