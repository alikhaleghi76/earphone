package ali.khaleghi.earphone.ui.dialog

import ali.khaleghi.earphone.R
import ali.khaleghi.earphone.util.vibrate
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import kotlinx.android.synthetic.main.dialog_about.*

class HelpDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_help)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        initUi()

    }

    private fun initUi() {
        R.id.closeButton
        closeButton.setOnClickListener {
            vibrate(context)
            dismiss()
        }
    }

}