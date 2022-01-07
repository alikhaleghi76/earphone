package ali.khaleghi.earphone.ui.activity

import ali.khaleghi.earphone.R
import ali.khaleghi.earphone.const.Const
import ali.khaleghi.earphone.const.Const.Companion.isHelpDialogShown
import ali.khaleghi.earphone.const.Const.Companion.sharedPreferencesName
import ali.khaleghi.earphone.model.Action
import ali.khaleghi.earphone.ui.adapter.ActionAdapter
import ali.khaleghi.earphone.ui.dialog.AboutDialog
import ali.khaleghi.earphone.ui.dialog.HelpDialog
import ali.khaleghi.earphone.util.vibrate
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initUi()
    }

    private fun initUi() {
        initActionRecycler()
        initMenu()
        showHelp()
    }

    private fun initActionRecycler() {
        val prefs = getSharedPreferences(sharedPreferencesName, MODE_PRIVATE)

        R.id.actionRecycler
        R.id.selectedAction
        R.id.actionDetail

        val actionList = getActionList()
        actionDetail.text = actionList[prefs.getInt(Const.selectedActionKey, 0)].detail
        val actionAdapter = ActionAdapter(this@SettingsActivity, actionList)
        actionRecycler.adapter = actionAdapter
        actionRecycler.layoutManager =
            LinearLayoutManager(this@SettingsActivity, LinearLayoutManager.HORIZONTAL, false)
        actionAdapter.setClickListener(object : ActionAdapter.ItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                vibrate(this@SettingsActivity)
                val editor = prefs.edit()
                editor.putInt(Const.selectedActionKey, position)
                editor.apply()
                actionAdapter.notifyDataSetChanged()
                selectedAction.text = actionList[position].title
                actionDetail.text = actionList[position].detail
            }
        })

    }

    private fun initMenu() {
        R.id.menuButton
        menuButton.setOnClickListener {
            val popup = PopupMenu(this@SettingsActivity, menuButton)
            popup.menuInflater
                .inflate(R.menu.popup_menu, popup.menu)

            if (!isPackageInstalled("com.farsitel.bazaar", packageManager)) {
                popup.menu.findItem(R.id.sendCommentMenu).isVisible = false
            }

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.aboutMenu -> showAboutDialog()
                    R.id.helpMenu -> showHelpDialog()
                    R.id.otherApps -> showOtherApps()
                    R.id.sendCommentMenu -> sendComment()
                }
                true
            }
            popup.show()
        }
    }

    private fun showHelp() {
        val prefs = getSharedPreferences(sharedPreferencesName, MODE_PRIVATE)
        if (!prefs.getBoolean(isHelpDialogShown, false)) {
            showHelpDialog()
            prefs.edit().putBoolean(isHelpDialogShown, true).apply()
        }
    }

    private fun showAboutDialog() {
        val aboutDialog = AboutDialog(this@SettingsActivity)
        aboutDialog.show()
    }

    private fun showHelpDialog() {
        val helpDialog = HelpDialog(this@SettingsActivity)
        helpDialog.show()
    }

    private fun showOtherApps() {
        if (!isPackageInstalled("com.farsitel.bazaar", packageManager)) {
            val url = "https://cafebazaar.ir/developer/alikhaleghi"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        } else {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("bazaar://collection?slug=by_author&aid=alikhaleghi")
            intent.setPackage("com.farsitel.bazaar")
            startActivity(intent)
        }
    }

    private fun sendComment() {
        val intent = Intent(Intent.ACTION_EDIT)
        intent.data = Uri.parse("bazaar://details?id=ali.khaleghi.earphone")
        intent.setPackage("com.farsitel.bazaar")
        startActivity(intent)
    }

    private fun getActionList(): List<Action> {
        val actionList = ArrayList<Action>()
        actionList.add(
            Action(
                getString(R.string.next_previous),
                getString(R.string.next_previous_detail),
                R.drawable.ic_next_previous
            )
        )
        actionList.add(Action(getString(R.string.next), getString(R.string.next_detail), R.drawable.ic_skip_next))
        actionList.add(
            Action(
                getString(R.string.previous),
                getString(R.string.previous_detail),
                R.drawable.ic_previous
            )
        )
        actionList.add(
            Action(
                getString(R.string.volume_up_down),
                getString(R.string.volume_up_down_detail),
                R.drawable.ic_volume_up_down
            )
        )
        actionList.add(
            Action(
                getString(R.string.volume_up),
                getString(R.string.volume_up_detail),
                R.drawable.ic_volume_up
            )
        )
        actionList.add(
            Action(
                getString(R.string.volume_down),
                getString(R.string.volume_down_detail),
                R.drawable.ic_volume_down
            )
        )
        return actionList
    }

    private fun isPackageInstalled(packageName: String, packageManager: PackageManager): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

}