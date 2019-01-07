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
import mcssoft.com.racereminderac.interfaces.ISelect
import mcssoft.com.racereminderac.interfaces.ISwipe
import mcssoft.com.racereminderac.utility.Constants
import mcssoft.com.racereminderac.utility.RacePreferences
import mcssoft.com.racereminderac.utility.SnackBarCB

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
        raceViewHolder = RaceViewHolder(view, itemSelect, itemLongSelect)
        return raceViewHolder
    }

    override fun onBindViewHolder(holder : RaceViewHolder, position : Int) {
        val race = lRaces[position]

        holder.tvCityCode.text = race.cityCode
        holder.tvRaceCode.text = race.raceCode
        holder.tvRaceNo.text = race.raceNum
        holder.tvRaceSel.text = race.raceSel
        holder.tvRaceTime.text = race.raceTimeS
        holder.tvRaceDate.text = race.raceDate

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
        }
    }
    
    override fun getItemCount() : Int = lRaces.size

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
     * Set the TouchHelper associated with the adapter.
     */
    internal fun setTouchHelper(itemTouchHelper: ItemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper
    }

    /**
     * TBA
     */
    internal fun getContext(): Context {
        return context
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Interface - ISelect">
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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Interface - ISwipe">
    /**
     * Interface ISwipe.
     */
    override fun onViewSwiped(pos: Int) {
        // Hide the bottom navigation view, as it hides the SnackBar.
        (anchorView.findViewById(R.id.id_bottom_nav_view) as BottomNavigationView).visibility = View.GONE
        // Delete from backing data.
        deleteRace(pos)
        // Do SnackBar (if Preference is set).
        if(RacePreferences.getInstance()!!.getRecoveryUndoLast(context)) {
            doSnackBar()
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
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
     * Set flag for view is empty of Races to display.
     */
    private fun emptyViewCheck() {
        isEmptyView = lRaces.isEmpty()
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: SnackBar and related">
    /**
     * OnClick for the Snackbar Undo.
     */
    override fun onClick(view: View) {
        reinstateRace(raceUndo!!, posUndo)
        Toast.makeText(context, "Race re-instated.", Toast.LENGTH_SHORT).show()
    }

     /**
     * Re-instate a previously removed Race.
     * @param race: The Race object at time of last UNDO.
     * @param lPos: The position in the list at time of last UNDO.
     */
    private fun reinstateRace(race: Race, lPos: Int) {
        // Put Race back into the list.
        lRaces.add(lPos, race)
        // Quick check, last Race removed might have been only one.
        if(isEmptyView) {
            isEmptyView = false
        }
        // Notify the adapter.
        notifyItemInserted(lPos)
        // Reset values.
        raceUndo = null
        posUndo = -1
    }

    private fun doSnackBar() {
        val snackBar = Snackbar.make(anchorView, "Item removed.", Snackbar.LENGTH_LONG)
        snackBar.setAction("UNDO", this)
        snackBar.addCallback(SnackBarCB(anchorView, raceUndo!!))
        snackBar.show()
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Private Vars">
    private var isEmptyView: Boolean = false               // flag, view is empty.
    private var lRaces = ArrayList<Race>(0)   // backing data.

    private lateinit var itemSelect: ISelect.ItemSelect              //
    private lateinit var itemLongSelect: ISelect.ItemLongSelect      //

    private lateinit var raceViewHolder: RaceViewHolder    //
    private lateinit var itemTouchHelper: ItemTouchHelper  //

    private var raceUndo: Race? = null      // local copy for any UNDO action.
    private var posUndo: Int = -1           // "     "    "   "   "    "
    //</editor-fold>
}