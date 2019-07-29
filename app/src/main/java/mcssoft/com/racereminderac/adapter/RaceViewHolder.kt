package mcssoft.com.racereminderac.adapter

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
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
    var tvCityCode: TextView = view.findViewById(R.id.id_tv_city_code)    // city code.
    var tvRaceCode: TextView = view.findViewById(R.id.id_tv_race_code)    // race code.
    var tvRaceNo: TextView = view.findViewById(R.id.id_tv_race_no)        // race number.
    var tvRaceSel0: TextView = view.findViewById(R.id.id_tv_race_sel0)    // 1st runner selection.
    var tvRaceSel1: TextView = view.findViewById(R.id.id_tv_race_sel1)    // 2nd runner selection.
    var tvRaceSel2: TextView = view.findViewById(R.id.id_tv_race_sel2)    // 3rd runner selection.
    var tvRaceSel3: TextView = view.findViewById(R.id.id_tv_race_sel3)    // 4th runner selection.
    var tvRaceTime: TextView = view.findViewById(R.id.id_tv_race_time)    // race time.
    var tvRaceDate: TextView = view.findViewById(R.id.id_tv_race_date)    // race date.
    var cbBetPlaced: CheckBox = view.findViewById(R.id.id_cb_bet_placed)  // bet placed indicator.
    var tvMultiSelsNotify: TextView = view.findViewById(R.id.id_tv_multi) // multi sel indicator.
    var tvRaceCount: TextView = view.findViewById(R.id.id_tv_race_count)  // items count indicator.
    var ivExpand: ImageView = view.findViewById(R.id.id_iv_expand)        // expand image view.

    init {
        // Set the listeners for the View as a whole.
        view.setOnClickListener(this)
        view.setOnLongClickListener(this)
        // Listeners for specific view elements.
        cbBetPlaced.setOnClickListener(this)
        ivExpand.setOnClickListener(this)
    }

    override fun onClick(view : View) {
        when(view.id) {
            R.id.id_iv_expand -> {
                Toast.makeText(view.context, "Expand not implemented yet.", Toast.LENGTH_SHORT).show()
            }
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