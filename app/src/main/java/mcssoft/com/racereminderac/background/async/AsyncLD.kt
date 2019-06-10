package mcssoft.com.racereminderac.background.async

import android.os.AsyncTask
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.utility.Constants

/**
 * Utility class for database operations. An AsyncTask will take an object as a parameter,
 * e.g. a Race object, while Workers (by design) only take primitives and their array variants.
 *
 * The Insert/Update etc statements in the DAO will take an object.
 */
class AsyncLD(private var type: Int, private var dao: RaceDAO) : AsyncTask<Race, Void, Void>() {

    override fun doInBackground(vararg params: Race) : Void? {
        when(type) {
            Constants.INSERT -> {
                dao.insertRace(params[0])
            }
            Constants.UPDATE -> {
                dao.updateRace(params[0])
            }
            Constants.DELETE -> {
                dao.deleteRace(params[0])
            }
            Constants.DELETE_ALL -> {
                dao.deleteAll()
            }
        }
        return null
    }

}
