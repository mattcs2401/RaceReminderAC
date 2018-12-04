package mcssoft.com.racereminderac.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import mcssoft.com.racereminderac.adapter.RaceAdapter
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.utility.RaceTime

class RaceListObserver(lRaces: LiveData<MutableList<Race>>, private var adapter: RaceAdapter) : Observer<MutableList<Race>> {

    override fun onChanged(lRaces: MutableList<Race>?) {
        if(lRaces != null && (lRaces.size > 1)) {
            lRaces.sort()
        }

        raceTime = RaceTime.getInstance()

        for(race in lRaces!!) {
            // TODO - check Race.raceTime against current time and set Race.metaColur as applicable.

            // the time as per the Race object.
            val checkTime = raceTime.timeToMillis(race.raceTime)
            val compare = raceTime.compareTo(checkTime)

            if(compare == 0) {
                // TBA
                val bp = ""
            } else if(compare < 0) {
                // TBA
                val bp = ""
            } else if(compare > 0) {
                race.metaColour = "3"
            }
        }

        adapter.swapData(lRaces as ArrayList<Race>)
    }

    private lateinit var raceTime: RaceTime
}