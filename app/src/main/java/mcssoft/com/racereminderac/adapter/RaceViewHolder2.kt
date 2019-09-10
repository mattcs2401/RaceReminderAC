package mcssoft.com.racereminderac.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.adapter.base.ParentViewHolder
import mcssoft.com.racereminderac.entity.RaceDetails
import mcssoft.com.racereminderac.utility.Constants
import mcssoft.com.racereminderac.utility.eventbus.SelectMessage
import mcssoft.com.racereminderac.utility.eventbus.UpdateMessage
import org.greenrobot.eventbus.EventBus

/**
 * For Paging.
 */
class RaceViewHolder2(parent : ViewGroup) : ParentViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.row_race, parent, false)) {

    var tvCityCode: TextView = itemView.findViewById(R.id.id_tv_city_code)    // city code.
    var tvRaceCode: TextView = itemView.findViewById(R.id.id_tv_race_code)    // race code.
    var tvRaceNo: TextView = itemView.findViewById(R.id.id_tv_race_no)        // race number.
    var tvRaceSel0: TextView = itemView.findViewById(R.id.id_tv_race_sel0)    // 1st runner selection.
    var tvRaceSel1: TextView = itemView.findViewById(R.id.id_tv_race_sel1)    // 2nd runner selection.
    var tvRaceSel2: TextView = itemView.findViewById(R.id.id_tv_race_sel2)    // 3rd runner selection.
    var tvRaceSel3: TextView = itemView.findViewById(R.id.id_tv_race_sel3)    // 4th runner selection.
    var tvRaceTime: TextView = itemView.findViewById(R.id.id_tv_race_time)    // race time.
    var tvRaceDate: TextView = itemView.findViewById(R.id.id_tv_race_date)    // race date.
    var cbBetPlaced: CheckBox = itemView.findViewById(R.id.id_cb_bet_placed)  // bet placed indicator.
    var tvMultiSelsNotify: TextView = itemView.findViewById(R.id.id_tv_multi) // multi sel indicator.
    var tvRaceCount: TextView = itemView.findViewById(R.id.id_tv_race_count)  // items count indicator.

    init {
        // Set the listeners for the View as a whole.
        parent.setOnClickListener(this)
        parent.setOnLongClickListener(this)
        // Listeners for specific view elements.
        cbBetPlaced.setOnClickListener(this)
    }

    override fun onClick(view : View) {
        when(view.id) {
            R.id.id_cb_bet_placed -> {
                EventBus.getDefault().post(UpdateMessage(adapterPosition, R.id.id_cb_bet_placed, cbBetPlaced.isChecked))
            }
            else -> {
                EventBus.getDefault().post(SelectMessage(Constants.ITEM_SELECT, adapterPosition, null))
            }
        }
    }

    override fun onLongClick(view: View): Boolean {
        EventBus.getDefault().post(SelectMessage(Constants.ITEM_LONG_SELECT, adapterPosition, view))
        return true
    }

    fun bindTo(raceDetails: RaceDetails?, position: Int) {
        this.raceDetails = raceDetails

        tvCityCode.text = raceDetails?.cityCode
        tvRaceCode.text = raceDetails?.raceCode
        tvRaceNo.text = raceDetails?.raceNum
        tvRaceSel0.text = raceDetails?.raceSel
        tvRaceSel1.text = raceDetails?.raceSel2
        tvRaceSel2.text = raceDetails?.raceSel3
        tvRaceSel3.text = raceDetails?.raceSel4
        tvRaceTime.text = raceDetails?.raceTimeS
        tvRaceDate.text = raceDetails?.raceDate
        cbBetPlaced.isChecked = raceDetails?.betPlaced!!
//        tvMultiSelsNotify.text = raceDetails?.
        tvRaceCount.text = (position + 1).toString()
    }

    private var raceDetails: RaceDetails? = null
}