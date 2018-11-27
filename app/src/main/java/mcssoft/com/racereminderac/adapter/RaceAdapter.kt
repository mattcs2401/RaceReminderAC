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
import mcssoft.com.racereminderac.utility.SnackBarCB
import mcssoft.com.racereminderac.utility.eventbus.RemoveMessage
import org.greenrobot.eventbus.EventBus

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
            holder.tvRaceDate.text = race.raceDate
        }
    }

    override fun getItemCount() : Int {
        return lRaces.size
    }

    override fun getItemViewType(position : Int) : Int {
        return if (isEmptyView) EMPTY_VIEW else RACE_VIEW
    }

    /**
     * OnClick for the Snackbar UNDO.
     */
    override fun onClick(view: View) {
        reinstateRace(raceUndo!!, posUndo)
        Toast.makeText(anchorView.context, "Race re-instated.", Toast.LENGTH_SHORT).show()
    }

    internal fun setClickListener(icListener : IClick.ItemSelect) {
        this.icListener = icListener
    }

    /**
     * Refresh the backing data.
     * @param lRaces: The list of Race objects that comprise the data.
     */
    internal fun swapData(lRaces: ArrayList<Race>) {
        this.lRaces = lRaces
        emptyViewCheck()
        notifyDataSetChanged()
    }

    /**
     * Return the Race object at the adapter position.
     * @param lPos: The adapter position (0 based).
     * @return The Race object.
     */
    internal fun getRace(lPos : Int) : Race = lRaces.get(lPos)

    /**
     * Remove a Race from the listing.
     * @param lPos: The position in the listing.
     */
    internal fun deleteRace(lPos: Int) {
        // keep backup in case of UNDO.
        posUndo = lPos
        raceUndo = lRaces.removeAt(lPos)
        // check list size.
        emptyViewCheck()
        // notify the adapter.
        notifyItemRemoved(lPos)
    }

    /**
     * Re-instate a previously removed Race.
     * @param race: The Race object at time of last UNDO.
     * @param lPos: The position in the list at time of last UNDO.
     */
    internal fun reinstateRace(race: Race, lPos: Int) {
        // put Race back into the list.
        lRaces.add(lPos, race)
        // quick check, last Race removed might have been only one.
        if(isEmptyView) {
            isEmptyView = false }
        // notify the adapter.
        notifyItemInserted(lPos)
    }

    /**
     * Set the TouchHelper associated with the adapter.
     */
    internal fun setTouchHelper(itemTouchHelper: ItemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper
    }

    /**
     * Interface ISwipe.
     */
    override fun onViewSwiped(pos: Int) {
        deleteRace(pos)
        EventBus.getDefault().post(RemoveMessage(raceUndo!!))
        val snackBar = Snackbar.make(anchorView, "Item removed.", Snackbar.LENGTH_LONG)
        snackBar.setAction("UNDO", this)
        snackBar.addCallback(SnackBarCB(raceUndo!!))
        snackBar.show()
    }

    /**
     * Set flag for view is empty of Races to display.
     */
    private fun emptyViewCheck() {
        isEmptyView = lRaces.isEmpty()
    }

    private var viewType: Int = -1                         // either EMPTY_VIEW or RACE_VIEW.
    private var isEmptyView: Boolean = false               // flag, view is empty.
    private var lRaces = ArrayList<Race>(0)   // listing backing data.

    private lateinit var icListener: IClick.ItemSelect     //
    private lateinit var raceViewHolder: RaceViewHolder    //
    private lateinit var itemTouchHelper: ItemTouchHelper  //

    private val EMPTY_VIEW = 0
    private val RACE_VIEW = 1

    var raceUndo: Race? = null      // local copy for any UNDO action.
    private var posUndo: Int = -1   // "     "    "   "   "    "
}