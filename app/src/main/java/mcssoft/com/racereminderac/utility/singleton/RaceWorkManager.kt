package mcssoft.com.racereminderac.utility.singleton

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import mcssoft.com.racereminderac.background.worker.XmlParseWorker
import mcssoft.com.racereminderac.ui.activity.MainActivity
import mcssoft.com.racereminderac.utility.singleton.base.SingletonBase

/**
 * Wrapper class for the WorkManager.
 */
class RaceWorkManager private constructor (private val context: Context) {

    private val workManager = WorkManager.getInstance()

    companion object : SingletonBase<RaceWorkManager, Context>(::RaceWorkManager)

    fun processRaceDetails(details: String) {
        val data = workDataOf("key" to details)
        val xmlParseRequest = OneTimeWorkRequestBuilder<XmlParseWorker>()
                .setInputData(data)
                .build()
//        val id = xmlParseRequest.id

        workManager.apply {
            enqueue(xmlParseRequest)
//            // observe work status
//            getWorkInfoByIdLiveData(workId)
//                    .observe(this@MainActivity, Observer { status ->
//                        val isFinished = status?.state?.isFinished
//                        Log.d(TAG, "Job $workId; finished: $isFinished")
//                    })
        }
    }


}
