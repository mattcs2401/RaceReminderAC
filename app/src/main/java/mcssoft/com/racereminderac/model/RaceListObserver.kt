package mcssoft.com.racereminderac.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import mcssoft.com.racereminderac.adapter.RaceAdapter
import mcssoft.com.racereminderac.entity.Race

class RaceListObserver(lRaces: LiveData<MutableList<Race>>, private var adapter: RaceAdapter) : Observer<MutableList<Race>> {

    override fun onChanged(lRaces: MutableList<Race>?) {
        if(lRaces != null && (lRaces.size > 1)) {
            lRaces.sort()
        }

        for(race in lRaces!!) {
            // TODO - check Race.raceTime against current time and set Race.metaColur as applicable.
            val time = race.raceTime
            val bp = ""
        }

        adapter.swapData(lRaces as ArrayList<Race>)
    }
}