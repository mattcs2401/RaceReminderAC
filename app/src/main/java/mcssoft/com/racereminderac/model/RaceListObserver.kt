package mcssoft.com.racereminderac.model

import androidx.lifecycle.Observer
import mcssoft.com.racereminderac.adapter.RaceAdapter
import mcssoft.com.racereminderac.entity.Race

class RaceListObserver(raceAdapter: RaceAdapter) : Observer<MutableList<Race>> {

    private var raceAdapter: RaceAdapter? = null

    init {
        this.raceAdapter = raceAdapter
    }

    override fun onChanged(races: MutableList<Race>) {

//        val lRaces: List<Race> = races.toList()
        raceAdapter?.swapData(races.toList() as ArrayList<Race>)
    }

}