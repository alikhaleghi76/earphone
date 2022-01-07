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

class AboutDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_about)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        initUi()

    }

    private fun initUi() {
        R.id.closeButton
        R.id.githubButton

        githubButton.setOnClickListener {
            vibrate(context)
            openGithub()
        }

        closeButton.setOnClickListener {
            vibrate(context)
            dismiss()
        }
    }

    private fun openGithub() {
        val url = "https://github.com/alikhaleghi76"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        context.startActivity(i)
    }

}