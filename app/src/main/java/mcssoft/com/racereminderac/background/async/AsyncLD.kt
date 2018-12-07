package mcssoft.com.racereminderac.background.async

import android.os.AsyncTask
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.utility.Constants

/**
 * Utility class for database operations. Mainly because couldn't seem to get Android's Worker(s)
 * to work properly. An AsyncTask will take an object as a parameter, e.g. Race, while Workers only
 * take primitives, e.g. Int, String etc
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
        }
        return null
    }

}
