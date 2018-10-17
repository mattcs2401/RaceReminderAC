package mcssoft.com.racereminderac.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.interfaces.IClick
import mcssoft.com.racereminderac.utility.TouchHelper

class RaceAdapter(context : Context) : RecyclerView.Adapter<RaceViewHolder>(), TouchHelper.SwipeAction {

    private var context : Context
    private var lRaces : ArrayList<Race>

    init {
        this.context = context
        lRaces = ArrayList<Race>(0)
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
            MEETING_VIEW -> {
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
            //holder.tvRaceDay?.text = race.raceDay
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
        } else MEETING_VIEW
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

    fun setTouchHelper(itemTouchHelper: ItemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper
    }

    /**
     * Interface TouchHelper.SwipeAction.
     */
    override fun onViewSwiped(position: Int) {
        lRaces.removeAt(position)
        notifyItemRemoved(position)
    }

    private var viewType : Int = 0
    private var isEmptyView : Boolean = false

    private lateinit var icListener : IClick.ItemSelect
    private lateinit var raceViewHolder : RaceViewHolder
    private lateinit var itemTouchHelper: ItemTouchHelper

    private val EMPTY_VIEW = 0
    private val MEETING_VIEW = 1
}