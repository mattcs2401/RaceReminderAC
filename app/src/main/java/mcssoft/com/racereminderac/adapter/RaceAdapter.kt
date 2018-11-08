package mcssoft.com.racereminderac.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.interfaces.IClick
import mcssoft.com.racereminderac.interfaces.ISwipe

class RaceAdapter(anchorView: View) : RecyclerView.Adapter<RaceViewHolder>(), View.OnClickListener, ISwipe {

    private var anchorView: View

    init {
        // the view that the 'UNDO' SnackBar is anchored to.
        this.anchorView = anchorView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RaceViewHolder {
        val view: View?
        this.viewType = viewType

        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            EMPTY_VIEW -> {
                view = inflater.inflate(R.layout.row_empty, parent, false)
                raceViewHolder = RaceViewHolder(view, "Nothing to show.")
            }
            RACE_VIEW -> {
                view = inflater.inflate(R.layout.row_race, parent, false)
                raceViewHolder = RaceViewHolder(view, "", icListener)
            }
        }
        return raceViewHolder
    }

    override fun onBindViewHolder(holder : RaceViewHolder, position : Int) {
        if (!isEmptyView) {
            val race = lRaces.get(position)

            holder.tvCityCode.text = race.cityCode
            holder.tvRaceCode.text = race.raceCode
            holder.tvRaceNo.text = race.raceNum
            holder.tvRaceSel.text = race.raceSel
            holder.tvRaceTime.text = race.raceTime
        }
    }

    override fun getItemCount() : Int {
        return if (isEmptyView) {
            1                     // need to do this so the onCreateViewHolder fires.
        } else {
             lRaces.size
        }
    }

    override fun getItemViewType(position : Int) : Int {
        return if (isEmptyView) {
            EMPTY_VIEW
        } else RACE_VIEW
    }

    override fun onClick(view: View) {
        // TODO - undo the removal from the list.
        reinstateRace(raceUndo!!, posUndo)
        Toast.makeText(anchorView.context, "Race re-instated.", Toast.LENGTH_SHORT).show()
//        Toast.makeText(anchorView.context, "UNDO button clicked", Toast.LENGTH_SHORT).show()
    }

    fun setClickListener(icListener : IClick.ItemSelect) {
        this.icListener = icListener
    }

    fun swapData(lRaces : ArrayList<Race>) {
        this.lRaces = lRaces
        isEmptyView = if (lRaces.size < 1) true else false
        notifyDataSetChanged()
    }

    /**
     * Return the Meeting object at the adapter position.
     * @param lPos The adapter position (0 based).
     * @return The Race object.
     */
    fun getRace(lPos : Int) : Race = lRaces.get(lPos)

    fun deleteRace(lPos: Int) {
        posUndo = lPos
        raceUndo = lRaces.removeAt(lPos)
        isEmptyView = if (lRaces.size < 1) true else false
        notifyItemRemoved(lPos)
    }

    fun reinstateRace(race: Race, lPos: Int) {
        lRaces.add(lPos, race)
        notifyItemInserted(lPos)
    }

    fun setTouchHelper(itemTouchHelper: ItemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper
    }

    /**
     * Interface ISwipe.
     */
    override fun onViewSwiped(pos: Int) {
        deleteRace(pos)
        val snackBar = Snackbar.make(anchorView, "Item removed.", Snackbar.LENGTH_LONG)
        snackBar.setAction("UNDO", this)
        snackBar.show()
    }

    private var viewType: Int = 0
    private var isEmptyView: Boolean = false
    private var lRaces = ArrayList<Race>(0)

    private lateinit var icListener: IClick.ItemSelect
    private lateinit var raceViewHolder: RaceViewHolder
    private lateinit var itemTouchHelper: ItemTouchHelper

    private val EMPTY_VIEW = 0
    private val RACE_VIEW = 1

    private var raceUndo: Race? = null      // local copy for any UNDO
    private var posUndo: Int = -1           // "     "    "   "   "
}