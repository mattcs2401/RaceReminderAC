package mcssoft.com.racereminderac.background.async

import android.os.AsyncTask
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.entity.RaceDetails
import mcssoft.com.racereminderac.utility.eventbus.DateTimeMessage
import org.greenrobot.eventbus.EventBus

/**
 * A utility class basically to get a Race object from the database based on that object's id,
 * without any of the, getting an object from the LiveData, issues. The idea is that the object is
 * simply for view/inspection, and not update/alteration.
 * @param dao: The Room data access object.
 * @note: Uses EventBus instead of an interface to return the result (a Race object).
 */
class AsyncNoLD(private var dao: RaceDAO) : AsyncTask<Long, Void, RaceDetails>() {

    override fun doInBackground(vararg params: Long?) : RaceDetails {
        return dao.getRaceNoLD(params[0]!!)
    }

    override fun onPostExecute(result: RaceDetails?) {
        EventBus.getDefault().post(DateTimeMessage(result!!))
    }
}
