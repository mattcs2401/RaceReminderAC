package mcssoft.com.racereminderac.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import mcssoft.com.racereminderac.background.DeleteWorker
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.background.InsertWorker
import mcssoft.com.racereminderac.background.UpdateWorker
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.database.RaceDatabase

class RaceRepository(application: Application) {

    private var raceDao: RaceDAO
    private var workManager: WorkManager
    private var allRaces: LiveData<MutableList<Race>>
    private var workInfo: LiveData<List<WorkInfo>>

    init {
        raceDao = RaceDatabase.getInstance(application)!!.raceDao()
        allRaces = raceDao.getAllRaces()
        workManager = WorkManager.getInstance()
        workInfo = workManager.getWorkInfosByTagLiveData("OUTPUT")
    }

    internal fun getAllRaces(): LiveData<MutableList<Race>> {
        allRaces = raceDao.getAllRaces()
        return allRaces
    }

    internal fun getRace(id: Long): LiveData<Race> = raceDao.getRace(id)

    internal fun doDatabaseOperation(type: String, race: Race) {
        var request: OneTimeWorkRequest? = null
        try {
            val data= Data.Builder().putStringArray("key", race.toArray()).build()
            when (type) {
                "insert" -> {
                    request = OneTimeWorkRequest.Builder(InsertWorker::class.java)
                            .setInputData(data)
                            .build()
                }
                "update" -> {
                    request = OneTimeWorkRequest.Builder(UpdateWorker::class.java)
                            .setInputData(data)
                            .build()
                }
                "delete" -> {
                    request = OneTimeWorkRequest.Builder(DeleteWorker::class.java)
                            .setInputData(data)
                            .addTag("OUTPUT")
                            .build()
                }
            }
            workManager.enqueue(request!!)
        } catch (ex: Exception) {
            Log.d("RaceRepository: ", ex.message)
        } finally {
            // update the repository (regardless).
//            allRaces = raceDao.getAllRaces()
        }
    }

    internal fun getOutputStatus(): LiveData<List<WorkInfo>> {
        return workInfo
    }

}
