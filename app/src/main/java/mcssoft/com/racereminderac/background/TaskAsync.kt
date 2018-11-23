package mcssoft.com.racereminderac.background

import android.os.AsyncTask
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.entity.Race

class TaskAsync(type: String, dao: RaceDAO) : AsyncTask<Race, Void, Void>() {

    private var type: String
    private var dao: RaceDAO

    init {
        this.type = type
        this.dao = dao
    }

    override fun doInBackground(vararg params: Race?) : Void? {
        when(type) {
            "insert" -> {
                dao.insertRace(params[0]!!)
            }
            "update" -> {
                dao.updateRace(params[0]!!)
            }
            "delete" -> {
                dao.deleteRace(params[0]!!)
            }
        }
        return null
    }

    override fun onPostExecute(result: Void?) {
        // TBA super.onPostExecute(result)
    }
}
