package mcssoft.com.racereminderac.repository

import android.app.Application
import android.os.AsyncTask
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

    private var raceDao: RaceDAO
    private var allRaces: LiveData<MutableList<Race>>

    init {
        raceDao = RaceDatabase.getInstance(application)!!.raceDao()
        allRaces = raceDao.getAllRaces()
    }

    internal fun getAllRaces(): LiveData<MutableList<Race>> = allRaces

    internal fun getRaceLD(id: Long): LiveData<Race> = raceDao.getRaceLD(id)

    internal fun getRace(id: Long): Race? {
        var getRaceAsync = GetRaceAsync(id, raceDao)
        val bp = ""
        return null
    }

    internal fun insert(race: Race) {
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
            // TBA
        }
    }

    internal fun update(race: Race) {
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
            // TBA
        }
    }

    private class GetRaceAsync(id: Long, raceDao: RaceDAO) : AsyncTask<Long, Void, Race>() {
        var id: Long = -1
        lateinit var raceDao: RaceDAO
        init {
            this.id = id
            this.raceDao = raceDao
        }

        override fun doInBackground(vararg params: Long?): Race {
            var race = this.raceDao.getRace(id)
            return race
        }

        override fun onPostExecute(result: Race?) {
            super.onPostExecute(result)
            val bp = ""
        }
    }
}
