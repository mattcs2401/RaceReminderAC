package mcssoft.com.racereminderac.background.async

import android.os.AsyncTask
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.entity.Race

/**
 * Utility class for database operations. Mainly because couldn't seem to get Android's Worker(s)
 * to work properly. An AsyncTask will take an object as a parameter, e.g. Race, while Workers only
 * take primitives, e.g. Int, String etc
 */
class AsyncLD(type: Int, dao: RaceDAO) : AsyncTask<Race, Void, Void>() {

    private var type: Int
    private var dao: RaceDAO

    init {
        this.type = type
        this.dao = dao
    }

    // simply to provide some constants.
    companion object {
        val INSERT = 1
        val UPDATE = 2
        val DELETE = 3
    }

    override fun doInBackground(vararg params: Race) : Void? {
        when(type) {
            INSERT -> {
                dao.insertRace(params[0])
            }
            UPDATE -> {
                dao.updateRace(params[0])
            }
            DELETE -> {
                dao.deleteRace(params[0])
            }
        }
        return null
    }

}
