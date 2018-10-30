package mcssoft.com.racereminderac.background

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.database.RaceDatabase
import mcssoft.com.racereminderac.entity.Race

class InitWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private var raceDao: RaceDAO

    init {
        raceDao = RaceDatabase.getInstance(context)!!.raceDao()
    }

    override fun doWork(): Result {
        if(raceDao?.getCountRaces()!! < 1) {
            // dummy data
            val race = Race("B", "R", "1", "2", "13:35")
            raceDao?.insertRace(race)

            return Result.SUCCESS
        }

    return Result.FAILURE
    }
}