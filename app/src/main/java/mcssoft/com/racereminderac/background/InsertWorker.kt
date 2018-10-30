package mcssoft.com.racereminderac.background

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.database.RaceDatabase
import mcssoft.com.racereminderac.entity.Race

class InsertWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private var raceDao: RaceDAO

    init {
        raceDao = RaceDatabase.getInstance(context)!!.raceDao()
    }

    override fun doWork(): Result {
        if(raceDao != null) {
            // Get the elements of that will comprise a Race object.
            val theRace : Array<String> = getInputData().getStringArray("key")!!
            // Construct generic object (note: race.get(0) == -1)
            val race = Race(theRace.get(1), theRace.get(2), theRace.get(3), theRace.get(4), theRace.get(5))
            // Let Room do it's thing.
            raceDao!!.insertRace(race)

            return Result.SUCCESS
        }

        return Result.FAILURE
    }
}