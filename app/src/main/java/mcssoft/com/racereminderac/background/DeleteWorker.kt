package mcssoft.com.racereminderac.background

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.database.RaceDatabase
import mcssoft.com.racereminderac.entity.Race

class DeleteWorker : Worker {

    constructor(context: Context, workerParams: WorkerParameters) : super(context, workerParams) {
        raceDao = RaceDatabase.getInstance(context)!!.raceDao()
    }

    override fun doWork(): Result {
        if(raceDao != null) {
            // get the elements of that will comprise a Race object.
            val theRace : Array<String> = getInputData().getStringArray("key")!!
            // construct generic object.
            val race = Race(theRace[1], theRace[2], theRace[3], theRace[4], theRace[5])
            // add in the id as it's not part of the constructor as the id is auto generate.
            race.id = theRace[0].toLong()
            // let Room do it's thing.
            raceDao!!.deleteRace(race)

            return Result.SUCCESS
        }
        return Result.FAILURE
    }

    private var raceDao: RaceDAO

}