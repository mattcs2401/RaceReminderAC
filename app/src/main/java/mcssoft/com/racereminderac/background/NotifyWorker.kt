package mcssoft.com.racereminderac.background

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.database.RaceDatabase
import mcssoft.com.racereminderac.utility.RaceTime

class NotifyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val context: Context
    private val workerParams: WorkerParameters
    private var raceDao: RaceDAO

    init {
        this.context = context
        this.workerParams = workerParams
        raceDao = RaceDatabase.getInstance(context)!!.raceDao()
    }

    override fun doWork(): Result {
        // Get the current time, and compare against the entries in the database.
        val time = RaceTime.getInstance(context).getFormattedTime()

        val races = raceDao.getAllRacesBasic()

        for(race in races) {

            val bp = ""
        }

        return Result.SUCCESS
    }
}