package mcssoft.com.racereminderac.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import mcssoft.com.racereminderac.background.DeleteWorker
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.background.InsertWorker
import mcssoft.com.racereminderac.background.UpdateWorker
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.database.RaceDatabase

class RaceRepository(application: Application) {

    private var raceDao: RaceDAO
    private var allRaces: LiveData<MutableList<Race>>

    init {
        raceDao = RaceDatabase.getInstance(application)!!.raceDao()
        allRaces = raceDao.getAllRaces()
    }

    internal fun getAllRaces(): LiveData<MutableList<Race>> = allRaces

    internal fun getRace(id: Long): LiveData<Race> = raceDao.getRaceLD(id)

    internal fun doDatabaseOperation(type: String, race: Race) {
        var request: OneTimeWorkRequest? = null
        try {
            val workManager = WorkManager.getInstance()
            val data: Data = Data.Builder().putStringArray("key", race.toArray()).build()
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
                            .build()
                }
            }
            return workManager.enqueue(request)
        } catch (e: java.lang.Exception) {
            Log.d("RaceRepository: ", e.message)
        } finally {
            // update the repository (regardless).
            allRaces = raceDao.getAllRaces()
        }
    }

    // TBA - this needs to be a Worker, but need to get output of Worker.
    //fun getCountRaces(): Int = raceDao.getCountRaces()

}
