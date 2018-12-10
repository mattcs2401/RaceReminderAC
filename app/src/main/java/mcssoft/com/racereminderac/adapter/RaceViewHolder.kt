package mcssoft.com.racereminderac.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.interfaces.ISelect

class RaceViewHolder(view : View, message : String) : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

    init {
        if(message != "") {
            (view.findViewById<View>(R.id.id_tv_empty) as TextView).text = message
        }
    }

    constructor(view: View, message: String, itemSelect: ISelect.ItemSelect, itemLongSelect: ISelect.ItemLongSelect) : this(view, message) {

        // Set the interface callbacks for select and long select.
        this.itemSelect = itemSelect
        this.itemLongSelect = itemLongSelect

        // Set the listener for the View.
        view.setOnClickListener(this)
        view.setOnLongClickListener(this)

        // Set the components of the View.
        tvCityCode = view.findViewById(R.id.id_tv_city_code)
        tvRaceCode = view.findViewById(R.id.id_tv_race_code)
        tvRaceNo = view.findViewById(R.id.id_tv_race_no)
        tvRaceSel = view.findViewById(R.id.id_tv_race_sel)
        tvRaceTime = view.findViewById(R.id.id_tv_race_time)
        tvRaceDate = view.findViewById(R.id.id_tv_race_date)
    }

    /**
     * Call back through the ISelect.ItemSelect interface with View and adapter position info.
     */
    override fun onClick(view : View) {
        itemSelect.onItemSelect(adapterPosition)
    }

    override fun onLongClick(view: View): Boolean {
        itemLongSelect.onItemLongSelect(adapterPosition)
        return true
    }

    private lateinit var itemSelect: ISelect.ItemSelect
    private lateinit var itemLongSelect: ISelect.ItemLongSelect

    lateinit var tvCityCode: TextView
    lateinit var tvRaceCode: TextView
    lateinit var tvRaceNo: TextView
    lateinit var tvRaceSel: TextView
    lateinit var tvRaceTime: TextView
    lateinit var tvRaceDate: TextView
}