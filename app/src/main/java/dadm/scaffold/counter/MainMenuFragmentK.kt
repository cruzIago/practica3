package dadm.scaffold.counter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import dadm.scaffold.BaseFragmentK
import dadm.scaffold.MainActivity

import dadm.scaffold.R


class MainMenuFragmentK : BaseFragmentK(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_start).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        (activity as MainActivity).startGame()
    }
}
