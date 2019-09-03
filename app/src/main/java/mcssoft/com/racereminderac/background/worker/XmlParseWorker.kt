package mcssoft.com.racereminderac.background.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import mcssoft.com.racereminderac.utility.RaceMeetingParser
import java.io.ByteArrayInputStream

/**
 * utility class, wrapper for a WorkManager Worker.
 */
class XmlParseWorker(private val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    /*
      Note: A data object has a limitation of approx 10240 characters.
     */

    override fun doWork(): Result {
        try {
            // inputData has the raw xml.
            val data = inputData

            val parser = RaceMeetingParser(context)
            val stream = ByteArrayInputStream(data.toString().toByteArray())
            parser.parse(stream)

            Log.d("XmlParseWorker: ", " Success :)")
            return Result.success()
        } catch(ex: Exception) {
            // TBA
            Log.d("XmlParseWorker: ", " Exception: $ex")
        } finally {
            // TBA
        }
        Log.d("XmlParseWorker: ", " Failure :(")
        return Result.failure()
    }

}
//https://tatts.com/pagedata/racing/YYYY/M(M)/D(D)/NR1.xml