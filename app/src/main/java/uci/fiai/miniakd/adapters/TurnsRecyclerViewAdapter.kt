package uci.fiai.miniakd.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerviewitem_turn.view.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.database.entities.Turn
import uci.fiai.miniakd.database.enums.initialLetter
import uci.fiai.miniakd.utils.DateUtils
import java.util.*

class TurnsRecyclerViewAdapter(private val context: Context, private val listener: TurnsRecyclerViewAdapterListener, private val turns: ArrayList<Turn>) : RecyclerView.Adapter<TurnsRecyclerViewAdapter.ViewHolder>() {

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

    fun removeItem(position: Int) {
        turns.remove(turns[position])
        notifyItemRemoved(position)
    }

    fun restoreItem(subject: Turn, position: Int) {
        turns.add(position, subject)
        notifyItemInserted(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val turnItemLayout = itemView.turnItemLayout
        val turnSubjectTextView = itemView.turnSubjectTextView
        val timeTextView = itemView.timeTextView
    }

    interface TurnsRecyclerViewAdapterListener {
        fun onTurnItemClick(turn: Turn, position: Int)
    }
}