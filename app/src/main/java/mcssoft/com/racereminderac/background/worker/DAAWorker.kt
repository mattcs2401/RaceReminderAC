package mcssoft.com.racereminderac.background.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import mcssoft.com.racereminderac.database.RaceDatabase

/**
 * Utility class to Delete All Archived records.
 */
class DAAWorker(private val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        return try {
            RaceDatabase.getInstance(context)?.raceDao()?.deleteAllArchived()
            Result.success()
        } catch(ex: Exception) {
            Result.failure()
        }
    }
}