package mcssoft.com.racereminderac.background.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class XmlParseWorker(private val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // TBA
        return Result.success()
    }
}