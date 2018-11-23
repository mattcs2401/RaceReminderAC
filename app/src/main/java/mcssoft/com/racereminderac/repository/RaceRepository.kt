package mcssoft.com.racereminderac.repository

import android.app.Application
import androidx.lifecycle.LiveData
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.database.RaceDatabase
import mcssoft.com.racereminderac.background.TaskAsync

class RaceRepository(application: Application) {

    private var raceDao: RaceDAO
    private var allRaces: LiveData<MutableList<Race>>

    init {
        raceDao = RaceDatabase.getInstance(application)!!.raceDao()
        allRaces = raceDao.getAllRaces()
    }

    /**
     * Get a list of all the Race objects from the database.
     */
    internal fun getAllRaces(): LiveData<MutableList<Race>> {
        allRaces = raceDao.getAllRaces()
        return allRaces
    }

    /**
     * Get a Race by it's database row id.
     * @param id: The id.
     */
    internal fun getRace(id: Long): LiveData<Race> = raceDao.getRace(id)

    /**
     * Do a database operation.
     * @param type: The operation type, one of "insert", "update" or "delete".
     * @param race: The Race object.
     */
    internal fun doDatabaseOperation(type: String, race: Race) {
        val taskAsync = TaskAsync(type, raceDao)
        taskAsync.execute(race)
    }

}
