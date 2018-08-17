package mcssoft.com.racereminderac.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.background.InsertWorker
import mcssoft.com.racereminderac.background.UpdateWorker
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.database.RaceDatabase

class RaceRepository(application: Application) {

    private val raceDao : RaceDAO = RaceDatabase.getInstance(application)!!.raceDao()

    internal fun getAllRaces() : LiveData<MutableList<Race>> = raceDao.getAllRaces()

    internal fun insert(race : Race) {
        try {
            val data : Data = Data.Builder().putStringArray("key", race.toArray()).build()
            val request: OneTimeWorkRequest = OneTimeWorkRequest.Builder(InsertWorker::class.java)
                    .setInputData(data)
                    .build()
            val workMgr : WorkManager = WorkManager.getInstance()
            return workMgr.enqueue(request)
        } catch(e: Exception) {
            Log.d("RaceRepository","fun insert: " + e.message)
        } finally {

        }
    }

    fun update(race: Race) {
        try {
            val data : Data = Data.Builder().putStringArray("key", race.toArray()).build()
            val request: OneTimeWorkRequest = OneTimeWorkRequest.Builder(UpdateWorker::class.java)
                    .setInputData(data)
                    .build()
            val workMgr : WorkManager = WorkManager.getInstance()
            return workMgr.enqueue(request)
        } catch(e: Exception) {
            Log.d("RaceRepository","fun update: " + e.message)
        } finally {

        }
    }

}