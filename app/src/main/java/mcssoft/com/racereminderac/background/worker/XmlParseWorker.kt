package mcssoft.com.racereminderac.background.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import mcssoft.com.racereminderac.entity.RaceDetails
import mcssoft.com.racereminderac.utility.singleton.DeSerialiseRaceDetails
import mcssoft.com.racereminderac.utility.singleton.NetworkManager
import mcssoft.com.racereminderac.utility.singleton.Url

class XmlParseWorker(private val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // TBA - inputData has the raw xml

        return Result.success()
    }


    private var raceDetails: RaceDetails? = null // local copy TBA
}
//https://tatts.com/pagedata/racing/YYYY/M(M)/D(D)/NR1.xml