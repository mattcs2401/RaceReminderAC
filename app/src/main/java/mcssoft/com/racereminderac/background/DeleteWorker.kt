package mcssoft.com.racereminderac.background

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.database.RaceDatabase
import mcssoft.com.racereminderac.entity.Race

class DeleteWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private var raceDao: RaceDAO

    init {
        raceDao = RaceDatabase.getInstance(context)!!.raceDao()
    }

    override fun doWork(): Result {
        if(raceDao != null) {
            // Get the elements of that will comprise a Race object.
            val theRace : Array<String> = getInputData().getStringArray("key")!!
            // Construct generic object.
            val race = Race(theRace[1], theRace[2], theRace[3], theRace[4], theRace[5])
            // Add in the id as it's not part of the constructor because its auto generate.
            race.id = theRace[0].toLong()
            // Let Room do it's thing.
            raceDao!!.deleteRace(race)

            return Result.SUCCESS
        }
        return Result.FAILURE
    }
}