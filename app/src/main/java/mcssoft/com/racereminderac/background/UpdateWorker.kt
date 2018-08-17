package mcssoft.com.racereminderac.background

import androidx.work.Worker
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.database.RaceDatabase
import mcssoft.com.racereminderac.entity.Race

class UpdateWorker : Worker() {

    private var raceDao: RaceDAO? = null

    override fun doWork(): Result {

        raceDao = RaceDatabase.getInstance(applicationContext)!!.raceDao()

        if(raceDao != null) {
            val theRace : Array<String> = getInputData().getStringArray("key")!!
            val race = Race(theRace.get(0), theRace.get(1), theRace.get(2), theRace.get(3), theRace.get(4))
            raceDao!!.updateRace(race)

            return Result.SUCCESS
        }

        return Result.FAILURE
    }

}