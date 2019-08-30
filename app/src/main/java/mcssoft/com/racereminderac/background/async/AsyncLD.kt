package mcssoft.com.racereminderac.background.async

import android.os.AsyncTask
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.entity.RaceDetails
import mcssoft.com.racereminderac.utility.Constants

/**
 * Utility class for database operations. An AsyncTask will take an object as a parameter,
 * e.g. a Race object, while Workers (by design) only take primitives and their array variants.
 *
 * The Insert/Update etc statements in the DAO will take an object.
 */
class AsyncLD(private var type: Int, private var dao: RaceDAO) : AsyncTask<RaceDetails, Void, Long>() {

    override fun doInBackground(vararg params: RaceDetails) : Long? {
        var value: Long = Constants.NO_VALUE.toLong()
        when(type) {
            Constants.INSERT -> {
                // Insert statements return Long (the inserted row id).
                value = dao.insertRace(params[0])
            }
            Constants.UPDATE -> {
                // Update statements return Int (the number of rows updated).
                value = dao.updateRace(params[0]).toLong()
            }
            Constants.DELETE -> {
                dao.deleteRace(params[0])
            }
            Constants.DELETE_ALL -> {
                dao.deleteAll()
            }
        }
        return value
    }

    override fun onPostExecute(result: Long) {
        super.onPostExecute(result)
        // TBA
    }
}
