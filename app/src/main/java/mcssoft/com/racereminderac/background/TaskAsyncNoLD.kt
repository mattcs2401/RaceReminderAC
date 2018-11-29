package mcssoft.com.racereminderac.background

import android.os.AsyncTask
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.utility.eventbus.RaceMessage
import org.greenrobot.eventbus.EventBus

/**
 * A utility class basically to get a Race object from the database based on that object's id,
 * without any of the, getting an object from the LiveData, issues. The idea is that the object is
 * simply for view/inspection, and not update/alteration.
 * @param dao: The Room data access object.
 * @note: Uses EventBus instead of an interface to return the result (a Race object).
 */
class TaskAsyncNoLD(dao: RaceDAO) : AsyncTask<Long, Void, Race>() {

    private var dao: RaceDAO

    init {
        this.dao = dao
    }

    override fun doInBackground(vararg params: Long?) : Race? {
        val race = dao.getRaceNoLD(params[0]!!)
        return race
    }

    override fun onPostExecute(result: Race?) {
        EventBus.getDefault().post(RaceMessage(result!!))
    }
}