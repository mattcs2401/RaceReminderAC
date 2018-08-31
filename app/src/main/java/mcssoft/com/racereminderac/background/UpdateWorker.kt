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
            // in an update statement, all the object values are required.
            val theRace : Array<String> = getInputData().getStringArray("key")!!
            // construct generic object.
            val race = Race(theRace[1], theRace[2], theRace[3], theRace[4], theRace[5])
            // add in the id.
            race.id = theRace[0].toLong()
            // let room do it's thing.
            raceDao!!.updateRace(race)

            return Result.SUCCESS
        }

        return Result.FAILURE
    }

}