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
    var tvRaceSel: TextView = view.findViewById(R.id.id_tv_race_sel)
    var tvRaceTime: TextView = view.findViewById(R.id.id_tv_race_time)
    var tvRaceDate: TextView = view.findViewById(R.id.id_tv_race_date)
    var cbBetPlaced: CheckBox = view.findViewById(R.id.id_cb_bet_placed)

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
                EventBus.getDefault().post(SelectMessage(Constants.ITEM_SELECT, adapterPosition))
            }
        }
    }

    override fun onLongClick(view: View): Boolean {
        EventBus.getDefault().post(SelectMessage(Constants.ITEM_LONG_SELECT, adapterPosition))
        return true
    }
}