package ali.khaleghi.earphone.ui.adapter

import ali.khaleghi.earphone.R
import ali.khaleghi.earphone.const.Const
import ali.khaleghi.earphone.model.Action
import ali.khaleghi.earphone.util.toPx
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView


class ActionAdapter internal constructor(context: Context?, data: List<Action>) :
    RecyclerView.Adapter<ActionAdapter.ViewHolder>() {

    private val actionList: List<Action> = data
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var onClickListener: ItemClickListener? = null

    var prefs: SharedPreferences? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = layoutInflater.inflate(R.layout.item_action, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (prefs == null) {
            prefs = holder.root.context.getSharedPreferences(
                Const.sharedPreferencesName, Activity.MODE_PRIVATE
            )
        }
        val action = actionList[position]
        holder.title.text = action.title
        holder.root.setCardBackgroundColor(
            if (prefs!!.getInt(
                    Const.selectedActionKey,
                    0
                ) == position
            ) holder.root.context.resources.getColor(R.color.selected_action_background) else holder.root.context.resources.getColor(
                R.color.white
            )
        )
        holder.root.strokeColor = (
            if (prefs!!.getInt(Const.selectedActionKey, 0) == position)
                holder.root.context.resources.getColor(R.color.orange)
            else holder.root.context.resources.getColor(
                R.color.white)
        )
        if (prefs!!.getInt(Const.selectedActionKey, 0) == position)
            holder.root.strokeWidth = 2.toPx.toInt()
        else
            holder.root.strokeWidth = 0

        holder.icon.setImageDrawable(holder.icon.context.resources.getDrawable(action.iconResource))

        holder.itemView.setOnClickListener {
            onClickListener?.onItemClick(it, position)
        }

    }

    override fun getItemCount(): Int {
        return actionList.size
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.title)
        var icon: ImageView = itemView.findViewById(R.id.icon)
        var root: MaterialCardView = itemView.findViewById(R.id.root)
    }

    fun getItem(id: Int): Action {
        return actionList[id]
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        onClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

}