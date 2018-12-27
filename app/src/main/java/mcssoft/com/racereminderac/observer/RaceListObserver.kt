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

class RaceListObserver(lRaces: LiveData<MutableList<Race>>, private var adapter: RaceAdapter) : Observer<MutableList<Race>> {

//    private var lIds: ArrayList<Long>
//
//    init {
//        lIds = ArrayList()
//    }

    override fun onChanged(lRaces: MutableList<Race>?) {
        if((lRaces != null) && (!lRaces.isEmpty())) {
            if (lRaces.size > 1) {
                lRaces.sort()
            }
            // time check, even if only one race entry exists.
            timeCheck(lRaces)
            // set adapter backing data.
            adapter.swapData(lRaces as ArrayList<Race>)
        }
    }

/* Compare notes, for a result of:
   -1: the current time is before that given - the race time is in the future.
    0: the current time is equal that given.
    1: the current time is after that given - the race time is in the past.
 */
    private fun timeCheck(lRaces: MutableList<Race>) {
        val raceTime = RaceTime.getInstance()!!
        // Check the race time against the current time.
        for(race in lRaces) {
            // The time as per the Race object.
            val raceTimeMillis = race.raceTimeL
            // The value of the comparison.
            val compare = raceTime.compareTo(raceTimeMillis)

            when(compare) {
                Constants.CURRENT_TIME_BEFORE -> {
                    // 5 minutes prior time window.
                    val comp = raceTime.compareTo(raceTimeMillis - Constants.FIVE_MINUTES)
                    if(comp == Constants.CURRENT_TIME_SAME ||
                            comp == Constants.CURRENT_TIME_AFTER) {
                        race.metaColour = "2"
                        if(preferenceCheck()) {
                            postNotification(race)
                        }
                    } else {
                        race.metaColour = "1"
                    }
                }
                Constants.CURRENT_TIME_SAME -> {
                    race.metaColour = "3"
                }
                Constants.CURRENT_TIME_AFTER -> {
                    race.metaColour = "3"
                }
            }
        }
    }

    /**
     * Post a notification that the Race is nearing race time.
     * @param race: Used to derive notification values.
     */
    private fun postNotification(race: Race) {
        // Notes: add the object id to a list, so we don't process again.
        val id = race.id
//        if(!lIds.contains(id)) {

//            lIds.add(id!!)

            val data = Data.Builder()
                    .putAll(mapOf("key_id" to id,
                            "key_cc" to race.cityCode,
                            "key_rc" to race.raceCode,
                            "key_rn" to race.raceNum,
                            "key_rs" to race.raceSel,
                            "key_rt" to race.raceTimeS)).build()
        val notifyWork = OneTimeWorkRequestBuilder<NotifyWorker>()
                .setInputData(data)
                .build()
        WorkManager.getInstance().enqueue(notifyWork)

//        }
    }

    private fun preferenceCheck() : Boolean {
// TBA        RacePreferences.getInstance()
        return true
    }
}
