package mcssoft.com.racereminderac.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.interfaces.ISwipe
import mcssoft.com.racereminderac.utility.Constants
import mcssoft.com.racereminderac.utility.RacePreferences
import mcssoft.com.racereminderac.utility.callback.SnackBarCB
import mcssoft.com.racereminderac.utility.eventbus.DataMessage
import mcssoft.com.racereminderac.utility.eventbus.DeleteMessage
import org.greenrobot.eventbus.EventBus

/**
 * The RaceAdapter (for the recycler view in the MainFragment).
 * @param anchorView: The view to anchor a SnackBar for Undo functionality.
 * @param context: Activity level context for general use.
 */
class RaceAdapter(private var anchorView: View, private var context: Context) :
        RecyclerView.Adapter<RaceViewHolder>(), View.OnClickListener, ISwipe {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RaceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_race, parent, false)
        raceViewHolder = RaceViewHolder(view)
        return raceViewHolder
    }

    override fun onBindViewHolder(holder : RaceViewHolder, position : Int) {
        val race = lRaces[position]
        // City, code, time, selections etc.
        setBaseDisplayValues(holder, race)
        // Set display colours based on the RaceXml object's metaColour value.
        setDisplayColourValues(holder, race)
        //  Setup for multi select if applicable.
        setMultiSelect(holder, race)
        // Set the adapter item count value (1st entry is 1).
        holder.tvRaceCount.text = (position + 1).toString()
    }
    
    override fun getItemCount() : Int = lRaces.size

    /**
     * Refresh the backing data.
     * @param lRaces: The list of RaceXml objects that comprise the data.
     */
    internal fun swapData(lRaces: ArrayList<Race>) {
        this.lRaces = lRaces
        notifyDataSetChanged()
        // Post message to MainFragment as to whether adapter has items (used for refresh if enabled).
        EventBus.getDefault().post(DataMessage(isEmpty()))
    }

    /**
     * Clear out the backing data (primarily used when delete all).
     */
    internal fun clear() {
        lRaces.clear()
        notifyDataSetChanged()
    }

    /**
     * Return the RaceXml object at the adapter position.
     * @param lPos: The adapter position (0 based).
     * @return The RaceXml object.
     */
    internal fun getRace(lPos : Int) : Race = lRaces[lPos]

    /**
     * Set the TouchHelper associated with the adapter.
     */
    internal fun setTouchHelper(itemTouchHelper: ItemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper
    }

    /**
     * Primarily for the list observer.
     */
    internal fun getContext(): Context = context

    internal fun isEmpty() : Boolean = lRaces.isEmpty()

    //<editor-fold defaultstate="collapsed" desc="Region: Interface - ISwipe">
    /**
     * Interface ISwipe.
     * Note: Tried to use EventBus instead of this interface, but couldn't seem to get it to work.
     */
    override fun onViewSwiped(pos: Int) {
        // Delete from the backing data. Also sets an "undo" race object == that removed from the
        // backing data, and used in a Room delete statement.
        deleteRace(pos)
        // Do SnackBar (if Preference is set).
        if(RacePreferences.getInstance()!!.getRecoveryUndoLast(context)) {
            // Hide the bottom navigation view, as it hides the SnackBar.
            (anchorView.findViewById(R.id.id_bottom_nav_view) as BottomNavigationView).visibility = View.GONE
            // Show the SnackBar.
            doSnackBar()
        } else {
            // Delete from the database (the "UNDO" SnackBar does this as well if enabled in preferences).
            EventBus.getDefault().post(DeleteMessage(raceUndo!!))
            // Clear, now old, data.
            clearUndo()
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
    /**
     * Remove a RaceXml from the listing.
     * @param lPos: The position in the listing.
     */
    private fun deleteRace(lPos: Int) {
        // Keep backup in case of UNDO.
        posUndo = lPos
        raceUndo = lRaces.removeAt(lPos)
        // Notify the adapter.
        notifyItemRemoved(lPos)
    }

    /**
     * Reset the Undo data.
     */
    private fun clearUndo() {
        raceUndo = null
        posUndo = Constants.MINUS_ONE
    }

    /**
     * Utility method to set base display values.
     */
    private fun setBaseDisplayValues(holder: RaceViewHolder, race: Race) {
        holder.tvCityCode.text = race.cityCode
        holder.tvRaceCode.text = race.raceCode
        holder.tvRaceNo.text = race.raceNum
        holder.tvRaceSel0.text = race.raceSel
        holder.tvRaceSel1.text = race.raceSel2
        holder.tvRaceSel2.text = race.raceSel3
        holder.tvRaceSel3.text = race.raceSel4
        holder.tvRaceTime.text = race.raceTimeS
        holder.tvRaceDate.text = race.raceDate
        holder.cbBetPlaced.isChecked = race.betPlaced
    }

    /**
     * Utility method to set display colours.
     */
    private fun setDisplayColourValues(holder: RaceViewHolder, race: Race) {
        when(race.metaColour) {
            Constants.META_COLOUR_1 -> {
                holder.tvRaceTime.setTextColor(getColor(context, R.color.colorPrimary))
                holder.tvRaceDate.setTextColor(getColor(context, R.color.colorPrimary))
            }
            Constants.META_COLOUR_2 -> {
                holder.tvRaceTime.setTextColor(getColor(context, R.color.colorOrange))
                holder.tvRaceDate.setTextColor(getColor(context, R.color.colorOrange))
            }
            Constants.META_COLOUR_3 -> {
                holder.tvRaceTime.setTextColor(getColor(context, R.color.colorAccent))
                holder.tvRaceDate.setTextColor(getColor(context, R.color.colorAccent))
            }
            Constants.META_COLOUR_4 -> {
                holder.tvRaceTime.setTextColor(getColor(context, R.color.colourRed))
                holder.tvRaceDate.setTextColor(getColor(context, R.color.colourRed))
            }
        }
    }

    /**
     * Utility method to setup when there are multiple selections.
     */
    private fun setMultiSelect(holder: RaceViewHolder, race: Race) {
        if(race.raceSel2 != "") {
            // There are at least two selections.
            holder.tvMultiSelsNotify.visibility = View.VISIBLE
            holder.tvRaceSel1.text = race.raceSel2
            holder.tvRaceSel2.text = race.raceSel3    // these may or not be actually set.
            holder.tvRaceSel3.text = race.raceSel4    // " " "
        } else {
            // Not multi sel
            holder.tvMultiSelsNotify.visibility = View.GONE
            holder.tvRaceSel1.text = ""
            holder.tvRaceSel2.text = ""
            holder.tvRaceSel3.text = ""
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: SnackBar Undo and related">
    /**
     * OnClick for the Snackbar Undo.
     */
    override fun onClick(view: View) {
        when (view.id) {
            R.id.snackbar_action -> {  // this is a system default id.
                reinstateRace(raceUndo!!, posUndo)
                Toast.makeText(context, "RaceXml re-instated.", Toast.LENGTH_SHORT).show()
            }
        }
    }

     /**
     * Re-instate a previously removed RaceXml.
     * @param race: The RaceXml object at time of last UNDO.
     * @param lPos: The position in the list at time of last UNDO.
     */
    private fun reinstateRace(race: Race, lPos: Int) {
        // Put RaceXml back into the list.
        lRaces.add(lPos, race)
        // Notify the adapter.
        notifyItemInserted(lPos)
        // Reset values.
        clearUndo()
    }

    private fun doSnackBar() {
        val snackBar = Snackbar.make(anchorView, "Item removed.", Snackbar.LENGTH_LONG)
        snackBar.setAction("UNDO", this)
        snackBar.addCallback(SnackBarCB(anchorView, raceUndo!!))
        snackBar.show()
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Private Vars">
    private var lRaces = ArrayList<Race>(0)   // backing data.
//    private var isEmpty: Boolean = true

    private lateinit var raceViewHolder: RaceViewHolder    //
    private lateinit var itemTouchHelper: ItemTouchHelper  //

    private var raceUndo: Race? = null                // local copy for any UNDO action.
    private var posUndo: Int = Constants.MINUS_ONE    // "     "    "   "   "    "
    //</editor-fold>
}