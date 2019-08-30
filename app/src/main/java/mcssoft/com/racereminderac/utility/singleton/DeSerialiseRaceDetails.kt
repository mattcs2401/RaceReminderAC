package mcssoft.com.racereminderac.utility.singleton

import android.content.Context
import mcssoft.com.racereminderac.entity.RaceDetails
import mcssoft.com.racereminderac.utility.singleton.base.SingletonBase

/**
 * Utility class to convert a 'serialized' RaceDetails object into an actual RaceDetails object.
 */
class DeSerialiseRaceDetails private constructor (private val context: Context) {

    companion object : SingletonBase<DeSerialiseRaceDetails, Context>(::DeSerialiseRaceDetails)

    fun getRaceDetails(details: String) : RaceDetails? {
        val lRaceInfo = ArrayList<String>()
        val lDetails = details.split(",")

        for (value: String in lDetails) {
            val str = value.split("=")[1]
            lRaceInfo.add(str)
        }

        val raceDetails = RaceDetails(lRaceInfo[0],lRaceInfo[1],lRaceInfo[2],lRaceInfo[3],lRaceInfo[4])

        raceDetails.id = lRaceInfo[5].toLong()
        raceDetails.raceDate = lRaceInfo[6]
        raceDetails.raceTimeL = lRaceInfo[7].toLong()
        raceDetails.archvRace = lRaceInfo[8]
        raceDetails.metaColour = lRaceInfo[9]
        raceDetails.betPlaced = lRaceInfo[10].toBoolean()
        raceDetails.raceSel2 = lRaceInfo[11]
        raceDetails.raceSel3 = lRaceInfo[12]
        raceDetails.raceSel4 = lRaceInfo[13]

        return raceDetails
    }
}
