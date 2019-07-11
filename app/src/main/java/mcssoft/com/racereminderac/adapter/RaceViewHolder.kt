package mcssoft.com.racereminderac.adapter

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.utility.Constants
import mcssoft.com.racereminderac.utility.eventbus.SelectMessage
import mcssoft.com.racereminderac.utility.eventbus.UpdateMessage
import org.greenrobot.eventbus.EventBus

class RaceViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

    // Set the components of the View.
    var tvCityCode: TextView = view.findViewById(R.id.id_tv_city_code)
    var tvRaceCode: TextView = view.findViewById(R.id.id_tv_race_code)
    var tvRaceNo: TextView = view.findViewById(R.id.id_tv_race_no)
    var tvRaceSel0: TextView = view.findViewById(R.id.id_tv_race_sel0)
    var tvRaceSel1: TextView = view.findViewById(R.id.id_tv_race_sel1)
    var tvRaceSel2: TextView = view.findViewById(R.id.id_tv_race_sel2)
    var tvRaceSel3: TextView = view.findViewById(R.id.id_tv_race_sel3)
    var tvRaceTime: TextView = view.findViewById(R.id.id_tv_race_time)
    var tvRaceDate: TextView = view.findViewById(R.id.id_tv_race_date)
    var cbBetPlaced: CheckBox = view.findViewById(R.id.id_cb_bet_placed)
    var tvMultiSelsNotify: TextView = view.findViewById(R.id.id_tv_multi_sels_display)

    init {
        // Set the listeners for the View.
        view.setOnClickListener(this)
        view.setOnLongClickListener(this)
        cbBetPlaced.setOnClickListener(this)
    }

    override fun onClick(view : View) {
        when(view.id) {
            R.id.id_cb_bet_placed -> {
                EventBus.getDefault().post(UpdateMessage(adapterPosition, R.id.id_cb_bet_placed, cbBetPlaced.isChecked))
            }
            else -> {
                // quick and dirty check as to a multi select.
                if(tvRaceSel1.text != "") {
                    EventBus.getDefault().post(SelectMessage(Constants.ITEM_SELECT, adapterPosition, true))
                } else {
                    EventBus.getDefault().post(SelectMessage(Constants.ITEM_SELECT, adapterPosition, false))
                }
            }
        }
    }

    override fun onLongClick(view: View): Boolean {
        EventBus.getDefault().post(SelectMessage(Constants.ITEM_LONG_SELECT, adapterPosition, false))
        return true
    }

}