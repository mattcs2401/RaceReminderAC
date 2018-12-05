package mcssoft.com.racereminderac.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import mcssoft.com.racereminderac.adapter.RaceAdapter
import mcssoft.com.racereminderac.entity.Race
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

    private fun timeCheck(lRaces: MutableList<Race>) {
        // Check the race time against the current time.
        for(race in lRaces) {
            // TODO - seems to work for the time value, but, need to check both date and time.
            // The time as per the Race object.
            val raceTimeMillis = raceTime.timeToMillis(race.raceTime)
            // The value of the comparison.
            val compare = raceTime.compareToCurrent(raceTimeMillis)

            if(compare == 0) {
                // TBA - the current time is equal the race time.
                val TBA = ""
            } else if(compare < 0) {
                // The current time is before the race time, i.e. Race time still to occur.
                // Check if we are within 5 minutes.
                val windowTimeMillis = raceTime.getTimePrior(raceTimeMillis, 5)
                val currentTimeMillis = raceTime.getCurrentTime()

                if(currentTimeMillis < windowTimeMillis) {
                    // We are > five minutes before the Race time.
                    race.metaColour = "1"
                } else if((currentTimeMillis > windowTimeMillis) && (currentTimeMillis < raceTimeMillis)) {
                    // We are < five minutes before the Race time.
                    race.metaColour = "2"
                }
                val TBA = ""
            } else if(compare > 0) {
                // The current time is after the Race time, i.e. Race time has passed.
                race.metaColour = "3"
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