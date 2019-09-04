package mcssoft.com.racereminderac.background.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import mcssoft.com.racereminderac.utility.RaceMeetingParser
import mcssoft.com.racereminderac.utility.singleton.RaceDownloadManager
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
//            val stream = ByteArrayInputStream(inputData.toString().toByteArray())
            var stream = RaceDownloadManager.getInstance(context)
                    .getFile(inputData.getLong("key", -1))

//            val stream = ByteArrayInputStream(RaceDownloadManager.getInstance(context)
//                    .getFile(inputData.getLong("key", -1)))

            /** See Note 1 below **/
            stream = stream.substring(3)

            val parser = RaceMeetingParser(context)
            parser.parse(ByteArrayInputStream(stream.toByteArray()))

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
/*
 * Note 1:
 * Need to trim off the first few characters of the download. Throws an exception otherwise:
     Caused by: XmlPullParserException: Unexpected token (position:TEXT Ã¯Â»¿@1:6 in
     java.io.InputStreamReader
*/
//https://tatts.com/pagedata/racing/YYYY/M(M)/D(D)/NR1.xml