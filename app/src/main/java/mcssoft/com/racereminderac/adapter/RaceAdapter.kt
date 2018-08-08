package mcssoft.com.racereminderac.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindString
import butterknife.ButterKnife
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.interfaces.IClick

class RaceAdapter(context : Context) : RecyclerView.Adapter<RaceViewHolder>() {

    private var context : Context
    private var lRaces : List<Race>

    init {
        this.context = context
        lRaces = ArrayList<Race>(0)
        //ButterKnife.bind(this, View(context))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RaceViewHolder {
        val view: View?
        this.viewType = viewType

        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            EMPTY_VIEW -> {
                view = inflater.inflate(R.layout.row_empty, parent, false)
                raceViewHolder = RaceViewHolder(view, "Nothing to show.")//this.nothingToShow)
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

            holder.tvCityCode?.text = race.cityCode
            holder.tvRaceCode?.text = race.raceCode
            holder.tvRaceNo?.text = race.raceNum
            holder.tvRaceSel?.text = race.raceSel
            holder.tvRaceTime?.text = race.raceTime
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

    fun setClickListener(icListener : IClick.ItemClick) {
        this.icListener = icListener
    }

    fun swapData(lRaces : List<Race>) {
        this.lRaces = lRaces
        if (lRaces.size < 1) {
            setEmptyView(true)
        } else {
            setEmptyView(false)
        }
        notifyDataSetChanged()
    }

    /**
     * Return the Meeting object at the adapter position.
     * @param lPos The adapter position (0 based).
     * @return The Meeting object, or NULL.
     */
    fun getMeeting(lPos : Int) : Race? {
        return if (lPos > -1) {
            lRaces.get(lPos)
        } else null
    }


    fun setEmptyView(isEmptyView : Boolean) {
        this.isEmptyView = isEmptyView
    }

    private var viewType : Int = 0
    private var isEmptyView : Boolean = false

    private lateinit var icListener : IClick.ItemClick
    private lateinit var raceViewHolder : RaceViewHolder

    private val EMPTY_VIEW = 0
    private val MEETING_VIEW = 1

    //@BindString(R.string.nothing_to_show) lateinit var nothingToShow: String
}