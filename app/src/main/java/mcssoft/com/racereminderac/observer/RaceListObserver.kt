package mcssoft.com.racereminderac.observer

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import mcssoft.com.racereminderac.adapter.RaceAdapter
import mcssoft.com.racereminderac.background.worker.NotifyWorker
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.utility.Constants
import mcssoft.com.racereminderac.utility.RacePreferences
import mcssoft.com.racereminderac.utility.RaceTime

class RaceListObserver(private var adapter: RaceAdapter) : Observer<MutableList<Race>> {

    /**
     * The observer's onChange method.
     * @param lRaces: The list of mutable Race objects.
     */
    override fun onChanged(lRaces: MutableList<Race>?) {
        if((lRaces != null) && (lRaces.isNotEmpty())) {
            if (lRaces.size > 1) {
                lRaces.sort()
            }
            // time check, even if only one race entry exists.
            timeCheck(lRaces)
            // set adapter backing data.
            adapter.swapData(lRaces as ArrayList<Race>)
        } else {
            adapter.clear()
        }
    }

/* Compare notes, for a result of:
   -1: the current time is before that given - i.e. the race time is in the future.
    0: the current time is equal that given.
    1: the current time is after that given - i.e. the race time is in the past.
 */
    /**
     * Check Race date/time and set colours accordingly.
     * @param lRaces: The list of current Races.
     */
    private fun timeCheck(lRaces: MutableList<Race>) {
        val raceTime = RaceTime.getInstance()!!
        // Check the race time against the current time.
        for(race in lRaces) {
            // The time as per the Race object.
            val raceTimeMillis = race.raceTimeL

            // If the Race day is today, then process, else ignore.
            if (raceTime.compareToDay(raceTimeMillis) == Constants.DAY_CURRENT) {
                // The value of the comparison.
                val compare = raceTime.compareToTime(raceTimeMillis)

                when (compare) {
                    Constants.CURRENT_TIME_BEFORE -> {
                        // 5 minutes prior time window.
                        val comp = raceTime.compareToTime(raceTimeMillis - Constants.FIVE_MINUTES)
                        if (comp == Constants.CURRENT_TIME_SAME ||
                                comp == Constants.CURRENT_TIME_AFTER) {
                            race.metaColour = Constants.META_COLOUR_2
                            if (preferenceCheck()) {
                                postNotification(race)
                            }
                        } else {
                            race.metaColour = Constants.META_COLOUR_1
                        }
                    }
                    Constants.CURRENT_TIME_SAME -> {
                        race.metaColour = Constants.META_COLOUR_3
                    }
                    Constants.CURRENT_TIME_AFTER -> {
                        race.metaColour = Constants.META_COLOUR_3
                    }
                }
            } else {
                race.metaColour = Constants.META_COLOUR_4
            }
        }
    }

    /**
     * Post a notification that the Race is nearing race time.
     * @param race: Used to derive notification values.
     */
    private fun postNotification(race: Race) {
        // TODO: Need to update to cater for Multi Select.
        val data = Data.Builder()
            .putAll(mapOf("key_id" to race.id,
                          "key_cc" to race.cityCode,
                          "key_rc" to race.raceCode,
                          "key_rn" to race.raceNum,
                          "key_rs" to race.raceSel,
                          "key_rt" to race.raceTimeS))
            .build()
        val notifyWork = OneTimeWorkRequestBuilder<NotifyWorker>()
                .setInputData(data)
                .build()
        WorkManager.getInstance().enqueue(notifyWork)
    }

    private fun preferenceCheck() : Boolean {
        if(RacePreferences.getInstance()?.getRaceNotifPost(adapter.getContext())!!) {
            return true
        }
        return false
    }
}
