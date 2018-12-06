package mcssoft.com.racereminderac.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import mcssoft.com.racereminderac.adapter.RaceAdapter
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.utility.Constants
import mcssoft.com.racereminderac.utility.RaceTime

class RaceListObserver(lRaces: LiveData<MutableList<Race>>, private var adapter: RaceAdapter) : Observer<MutableList<Race>> {

    override fun onChanged(lRaces: MutableList<Race>?) {
        if(lRaces != null) {

            if (lRaces.size > 1) { lRaces.sort() }
            raceTime = RaceTime.getInstance()
            timeCheck(lRaces)

            adapter.swapData(lRaces as ArrayList<Race>)
        }
    }

/*
-1: the current time is before that given - the race time is in the future.
 0: the current time is equal that given.
 1: the current time is after that given - the race time is in the past.
 */
    private fun timeCheck(lRaces: MutableList<Race>) {
        // Check the race time against the current time.
        for(race in lRaces) {
            // The time as per the Race object.
            val raceTimeMillis = race.raceTimeL
            // The value of the comparison.
            val compare = raceTime.compareTo(raceTimeMillis)

            when(compare) {
                Constants.BEFORE -> {
                    // TODO - 5 minutes prior time window.
                    race.metaColour = "1"
                }
                Constants.SAME -> {
                    race.metaColour = "3"
                }
                Constants.AFTER -> {
                    race.metaColour = "3"
                }
            }
        }
    }

    private lateinit var raceTime: RaceTime

}
/*
Example:
--------
Race time = 08:00
Window = race time - 5 minutes = 07:55

Current time < window - green
Current time > window, and, < race time - amber
Current time > window, and, > race time - red
*/