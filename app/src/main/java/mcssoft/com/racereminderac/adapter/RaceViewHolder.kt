package mcssoft.com.racereminderac.adapter

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.interfaces.IClick

class RaceViewHolder(view : View, message : String) : RecyclerView.ViewHolder(view), View.OnClickListener {

    init {
        if(message != "") {
            (view.findViewById<View>(R.id.id_tv_empty) as TextView).text = message
        }
    }

    constructor(view: View, message: String, icListener: IClick.ItemSelect) : this(view, message) {
        this.icListener = icListener

        // Recycler view item, or "front" view.
        viewRecyclerItem = view.findViewById(R.id.id_recycler_layout)
        viewRecyclerItem.setOnClickListener(this)

        tvCityCode = viewRecyclerItem.findViewById(R.id.id_tv_city_code)
        tvRaceCode = viewRecyclerItem.findViewById(R.id.id_tv_race_code)
        tvRaceNo = viewRecyclerItem.findViewById(R.id.id_tv_race_no)
        tvRaceSel = viewRecyclerItem.findViewById(R.id.id_tv_race_sel)
        tvRaceTime = viewRecyclerItem.findViewById(R.id.id_tv_race_time)

        // Swipe view item, or "back" view.
        viewSwipeItem = view.findViewById(R.id.id_swipe_layout)
        btnDelete = viewSwipeItem.findViewById(R.id.id_ib_delete)
        btnDelete.setOnClickListener(this)
    }

    override fun onClick(view : View) {
        icListener.onItemSelect(view, adapterPosition)
    }

    private lateinit var icListener: IClick.ItemSelect

    lateinit var tvCityCode: TextView
    lateinit var tvRaceCode: TextView
    lateinit var tvRaceNo: TextView
    lateinit var tvRaceSel: TextView
    lateinit var tvRaceTime: TextView
    lateinit var viewRecyclerItem: View

    lateinit var viewSwipeItem: View
    lateinit var btnDelete: ImageButton
}