package mcssoft.com.racereminderac.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.interfaces.IClick

class RaceViewHolder(view : View, message : String) : RecyclerView.ViewHolder(view), View.OnClickListener {

    init {
        if(message != "") (view.findViewById<View>(R.id.id_tv_empty) as TextView).text = message
    }

    constructor(view: View, message: String, icListener: IClick.ItemClick) : this(view, message) {
        this.icListener = icListener
        view.setOnClickListener(this)
        ButterKnife.bind(this, view)
    }

    override fun onClick(view : View?) {
        icListener.onItemClick(view, adapterPosition)
    }

    private lateinit var icListener: IClick.ItemClick

//    var tvCityCode: TextView? = null
//    var tvRaceCode: TextView? = null
//    var tvRaceNo: TextView? = null
//    var tvRaceSel: TextView? = null
//    var tvRaceTime: TextView? = null
//    var tvRaceDay: TextView? = null

    @BindView(R.id.id_tv_city_code)  lateinit var tvCityCode: TextView
    @BindView(R.id.id_tv_race_code)  lateinit var tvRaceCode: TextView
    @BindView(R.id.id_tv_race_no)  lateinit var tvRaceNo: TextView
    @BindView(R.id.id_tv_race_sel)  lateinit var tvRaceSel: TextView
    @BindView(R.id.id_tv_race_time)  lateinit var tvRaceTime: TextView
    @BindView(R.id.id_tv_race_day)  lateinit var tvRaceDay: TextView
}