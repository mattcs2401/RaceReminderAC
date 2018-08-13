package mcssoft.com.racereminderac.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.interfaces.IClick

class RaceViewHolder(view : View, message : String) : RecyclerView.ViewHolder(view), View.OnClickListener {

    init {
        if(message != "") (view.findViewById<View>(R.id.id_tv_empty) as TextView).text = message
    }

    constructor(view: View, message: String, icListener: IClick.ItemClick) : this(view, message) {
        this.icListener = icListener
        view.setOnClickListener(this)

        tvCityCode = view.findViewById(R.id.id_tv_city_code)
        tvRaceCode = view.findViewById(R.id.id_tv_race_code)
        tvRaceNo = view.findViewById(R.id.id_tv_race_no)
        tvRaceSel = view.findViewById(R.id.id_tv_race_sel)
        tvRaceTime = view.findViewById(R.id.id_tv_race_time)
        //tvRaceDay = view.findViewById(R.id.id_tv_race_day)
    }

    override fun onClick(view : View) {
        icListener.onItemClick(view, adapterPosition)
    }

    private lateinit var icListener: IClick.ItemClick

    var tvCityCode: TextView? = null
    var tvRaceCode: TextView? = null
    var tvRaceNo: TextView? = null
    var tvRaceSel: TextView? = null
    var tvRaceTime: TextView? = null
    //var tvRaceDay: TextView? = null
}