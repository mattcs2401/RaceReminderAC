package mcssoft.com.racereminderac.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.interfaces.ISelect
import mcssoft.com.racereminderac.interfaces.ISwipe
import mcssoft.com.racereminderac.utility.Constants
import mcssoft.com.racereminderac.utility.SnackBarCB
import mcssoft.com.racereminderac.utility.eventbus.DeleteMessage
import org.greenrobot.eventbus.EventBus

class RaceAdapter(private var anchorView: View, private var context: Context) : RecyclerView.Adapter<RaceViewHolder>(), View.OnClickListener, ISwipe {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RaceViewHolder {
        val view: View?
        this.viewType = viewType

        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            Constants.EMPTY_VIEW -> {
                view = inflater.inflate(R.layout.row_empty, parent, false)
                raceViewHolder = RaceViewHolder(view, context.resources.getString(R.string.nothing_to_show))
            }
            Constants.RACE_VIEW -> {
                view = inflater.inflate(R.layout.row_race, parent, false)
                raceViewHolder = RaceViewHolder(view, "", itemSelect, itemLongSelect)
            }
        }
        return raceViewHolder
    }

    override fun onBindViewHolder(holder : RaceViewHolder, position : Int) {
        if (!isEmptyView) {
            val race = lRaces[position]

            holder.tvCityCode.text = race.cityCode
            holder.tvRaceCode.text = race.raceCode
            holder.tvRaceNo.text = race.raceNum
            holder.tvRaceSel.text = race.raceSel
            holder.tvRaceTime.text = race.raceTimeS
            holder.tvRaceDate.text = race.raceDate

            when(race.metaColour) {
                "1" -> {
                    holder.tvRaceTime.setTextColor(getColor(context, R.color.colorPrimary))
                    holder.tvRaceDate.setTextColor(getColor(context, R.color.colorPrimary))
                }
                "2" -> {
                    holder.tvRaceTime.setTextColor(getColor(context, R.color.colorOrange))
                    holder.tvRaceDate.setTextColor(getColor(context, R.color.colorOrange))
                }
                "3" -> {
                    holder.tvRaceTime.setTextColor(getColor(context, R.color.colorAccent))
                    holder.tvRaceDate.setTextColor(getColor(context, R.color.colorAccent))
                }
            }
        }
    }
    
    override fun getItemCount() : Int {
        if(isEmptyView) {
            return 1         // need this to still trigger the onCreateViewHolder.
        } else {
            return lRaces.size
        }
    }

    override fun getItemViewType(position : Int) : Int {
        return if (isEmptyView) Constants.EMPTY_VIEW else Constants.RACE_VIEW
    }

    /**
     * OnClick for the Snackbar UNDO.
     */
    override fun onClick(view: View) {
        reinstateRace(raceUndo!!, posUndo)
        Toast.makeText(anchorView.context, "Race re-instated.", Toast.LENGTH_SHORT).show()
    }

    /**
     * Set the interface ISelect.ItemSelect (when an item in the adapter's listing is selected).
     * @param itemSelect: The interface.
     */
    internal fun setClickListener(itemSelect: ISelect.ItemSelect) {
        this.itemSelect = itemSelect
    }

    /**
     * Set the interface ISelect.ItemLongSelect.
     * @param itemLongSelect: The interface.
     */
    internal fun setLongClickListener(itemLongSelect: ISelect.ItemLongSelect) {
        this.itemLongSelect = itemLongSelect
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
    internal fun getRace(lPos : Int) : Race = lRaces[lPos]

    /**
     * Remove a Race from the listing.
     * @param lPos: The position in the listing.
     */
    private fun deleteRace(lPos: Int) {
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
    private fun reinstateRace(race: Race, lPos: Int) {
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
        EventBus.getDefault().post(DeleteMessage(raceUndo!!))
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

    //<editor-fold defaultstate="collapsed" desc="Region: Private Vars">
    private var viewType: Int = -1                         // either EMPTY_VIEW or RACE_VIEW.
    private var isEmptyView: Boolean = false               // flag, view is empty.
    private var lRaces = ArrayList<Race>(0)   // backing data.

    private lateinit var itemSelect: ISelect.ItemSelect              //
    private lateinit var itemLongSelect: ISelect.ItemLongSelect      //

    private lateinit var raceViewHolder: RaceViewHolder    //
    private lateinit var itemTouchHelper: ItemTouchHelper  //

    private var raceUndo: Race? = null      // local copy for any UNDO action.
    private var posUndo: Int = -1   // "     "    "   "   "    "
    //</editor-fold>
}