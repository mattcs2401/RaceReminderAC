package mcssoft.com.racereminderac.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import mcssoft.com.racereminderac.background.async.AsyncLD
import mcssoft.com.racereminderac.background.async.AsyncNoLD
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.dao.RaceDAO2
import mcssoft.com.racereminderac.database.RaceDatabase
import mcssoft.com.racereminderac.entity.RaceDetails
import androidx.paging.Config
import androidx.paging.toLiveData

class RaceRepository2(application: Application) {

    private var raceDao: RaceDAO2 = RaceDatabase.getInstance(application)!!.raceDao2()

    val allRaces = raceDao.getAllRaces()
            .toLiveData(Config(pageSize = 20, maxSize = 200))

//    private var raceDao: RaceDAO = RaceDatabase.getInstance(application)!!.raceDao()
//    private var allRaces: LiveData<MutableList<RaceDetails>>
//
//    init {
//        allRaces = raceDao.getAllRaces()
//    }
//
//    /**
//     * Get a list of all the Race objects from the database.
//     */
//    internal fun getAllRaces(): LiveData<MutableList<RaceDetails>> {
////        allRaces = raceDao.getAllRaces()
//        return allRaces
//    }
//
//    /**
//     * Get a Race by it's database row id.
//     * @param id: The id.
//     */
//    internal fun getRaceLD(id: Long): LiveData<RaceDetails> = raceDao.getRaceLD(id)
//
//    internal fun getRaceNoLD(id: Long) {
//        val taskAsync = AsyncNoLD(raceDao)
//        taskAsync.execute(id)
//    }
//
//    /**
//     * Do a database operation.
//     * @param type: The operation type, one of AsyncLD.UPDATE / DELETE / INSERT.
//     * @param race: The Race object.
//     */
//    internal fun doDatabaseOperation(type: Int, race: RaceDetails?): Long {
//        val taskAsync = AsyncLD(type, raceDao)
//        taskAsync.execute(race)
//        return taskAsync.get()
//    }

}
