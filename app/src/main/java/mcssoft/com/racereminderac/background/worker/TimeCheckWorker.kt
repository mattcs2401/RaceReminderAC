package mcssoft.com.racereminderac.background.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.database.RaceDatabase

class TimeCheckWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val context: Context
    private val workerParams: WorkerParameters
    private var raceDao: RaceDAO

    init {
        this.context = context
        this.workerParams = workerParams
        raceDao = RaceDatabase.getInstance(context)!!.raceDao()
    }

    override fun doWork(): Result {

        return Result.SUCCESS
    }
}