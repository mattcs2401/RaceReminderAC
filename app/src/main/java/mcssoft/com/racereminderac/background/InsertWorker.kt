package mcssoft.com.racereminder.background

import androidx.work.Worker
import mcssoft.com.racereminder.dao.RaceDAO
import mcssoft.com.racereminder.database.RaceDatabase
import mcssoft.com.racereminder.entity.Race

class InsertWorker : Worker() {

    private var raceDao: RaceDAO? = null

    override fun doWork(): Result {

        raceDao = RaceDatabase.getInstance(applicationContext)!!.raceDao()

        if(raceDao != null) {
            val theRace : Array<String> = getInputData().getStringArray("key")!!
            val race = Race(theRace.get(0), theRace.get(1), theRace.get(2), theRace.get(3), theRace.get(4))
            raceDao!!.insertRace(race)

            return Result.SUCCESS
        }

        return Result.FAILURE
    }

}