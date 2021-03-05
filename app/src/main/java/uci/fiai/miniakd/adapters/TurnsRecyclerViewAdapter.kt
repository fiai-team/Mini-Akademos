package uci.fiai.miniakd.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.recyclerviewitem_turn.view.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.database.entities.Turn
import uci.fiai.miniakd.extensions.InteractiveAdapter
import uci.fiai.miniakd.utils.DateUtils
import java.util.*

class TurnsRecyclerViewAdapter(private val context: Context, private val listener: TurnsRecyclerViewAdapterListener, private val turns: ArrayList<Turn>) : InteractiveAdapter<Turn, TurnsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.recyclerviewitem_turn, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return turns.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val turn = turns[position]
        with(holder) {
            turn.subject?.let {
                turnSubjectTextView.text = context.getString(R.string.turnSubjectTitle, it.name, turn.typology.initialLetter)
                timeTextView.text = DateUtils.getTurnTime(turn.timeTurn)
            }
            turnItemLayout.setOnClickListener {
                listener.onTurnItemClick(turns[position], position)
            }
        }
    }

    override fun removeItem(position: Int) {
        turns.remove(turns[position])
        notifyItemRemoved(position)
    }

    override fun restoreItem(obj: Turn, position: Int) {
        turns.add(position, obj)
        notifyItemInserted(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val turnItemLayout: MaterialCardView = itemView.turnItemLayout
        val turnSubjectTextView: TextView = itemView.turnSubjectTextView
        val timeTextView: TextView = itemView.timeTextView
    }

    interface TurnsRecyclerViewAdapterListener {
        fun onTurnItemClick(turn: Turn, position: Int)
    }
}