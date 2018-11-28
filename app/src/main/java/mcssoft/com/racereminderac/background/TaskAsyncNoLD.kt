package mcssoft.com.racereminderac.background

import android.os.AsyncTask
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.utility.eventbus.RaceMessage
import org.greenrobot.eventbus.EventBus

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
