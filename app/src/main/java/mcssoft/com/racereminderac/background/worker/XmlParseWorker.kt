package mcssoft.com.racereminderac.background.worker

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import mcssoft.com.racereminderac.utility.RaceMeetingParser
import mcssoft.com.racereminderac.utility.singleton.RaceDownloadManager
import java.io.ByteArrayInputStream
import java.io.InputStream

/**
 * utility class, wrapper for a WorkManager Worker.
 */
class XmlParseWorker(private val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    /*
      Note: A WorkManager.Data object has a limitation of approx 10240 characters.
     */
    override fun doWork(): Result {
        var id: Long = -1
        var stream: InputStream? = null
        try {
            id = inputData.getLong("key", -1)
            stream = RaceDownloadManager.getInstance(context).getFile(id)

            val parser = RaceMeetingParser(context)

            parser.parse(stream)

            Log.d("XmlParseWorker: ", " Success :)")

            return Result.success()
        } catch(ex: Exception) {
            // TBA
            Log.d("XmlParseWorker: ", " Exception: $ex")
        } finally {
            stream?.close()
            RaceDownloadManager.getInstance(context).removeFile(id)
        }
        Log.d("XmlParseWorker: ", " Failure :(")
        return Result.failure()
    }

}

//https://tatts.com/pagedata/racing/YYYY/M(M)/D(D)/NR1.xml