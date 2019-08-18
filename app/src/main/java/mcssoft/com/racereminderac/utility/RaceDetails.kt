package mcssoft.com.racereminderac.utility

import android.content.Context

class RaceDetails constructor(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: RaceDetails? = null

        fun getInstance(context: Context) =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: RaceDetails(context).also {
                        INSTANCE = it
                    }
                }
    }

    fun addRaceDay(details: List<String>) {
        raceDay = details
    }

    fun addMeeting(details: List<String>) {
        meeting = details
    }

    fun addRace(details: List<String>) {
        race = details
    }

    fun addRunners(details: List<List<String>>) {
        runners = details
    }

    lateinit var raceDay : List<String>
    lateinit var meeting : List<String>
    lateinit var race : List<String>
    lateinit var runners: List<List<String>>
}