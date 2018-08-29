package mcssoft.com.racereminderac.background

import androidx.work.Worker
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.database.RaceDatabase
import mcssoft.com.racereminderac.entity.Race

class InitWorker : Worker() {

    private var raceDao: RaceDAO? = null

    override fun doWork(): Result {

        raceDao = RaceDatabase.getInstance(applicationContext)?.raceDao()

        if(raceDao?.getCountRaces()!! < 1) {
            // dummy data
            val race = Race("B", "R", "1", "2", "13:35")
            raceDao?.insertRace(race)

            return Result.SUCCESS
        }

    return Result.FAILURE
    }
}