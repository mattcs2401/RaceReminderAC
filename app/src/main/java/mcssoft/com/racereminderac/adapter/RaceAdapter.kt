package mcssoft.com.racereminderac.adapter

import android.view.LayoutInflater
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
        // the view that any SnackBar is anchored to.
        this.anchorView = anchorView   // the view that any SnackBar is anchored to.
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
                // TBA as to which view to use.
                //view = inflater.inflate(R.layout.row_race2, parent, false)

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
        Toast.makeText(anchorView.context, "UNDO button clicked", Toast.LENGTH_SHORT).show()
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
        lRaces.removeAt(lPos)
        if(lRaces.size < 1) isEmptyView = true
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
        val snackBar = Snackbar.make(anchorView, "Item removed.", Snackbar.LENGTH_SHORT)
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
}