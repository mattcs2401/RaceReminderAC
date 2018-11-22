package mcssoft.com.racereminderac.background

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.database.RaceDatabase
import mcssoft.com.racereminderac.entity.Race
import java.lang.Exception

class DeleteWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private var raceDao: RaceDAO

    init {
        raceDao = RaceDatabase.getInstance(context)!!.raceDao()
    }

    override fun doWork(): Result {
        if(raceDao != null) {
        //try {
            // Get the elements of that will comprise a Race object.
            val theRace: Array<String> = getInputData().getStringArray("key")!!
            // Construct generic object.
            val race = Race(theRace[0], theRace[1], theRace[2], theRace[3], theRace[4])
            // Add in the id as it's not part of the constructor because its auto generate.
            race.id = getInputData().getLong("key2",-1) //theRace[0].toLong()
            // Let Room do it's thing.
            raceDao.deleteRace(race.id!!) //race)
//            raceDao.deleteRace(theRace[0].toLong())
            setOutputData(createOutput("DeleteWorker success."))
            return Result.SUCCESS
        } else {
                setOutputData(createOutput("DeleteWorker failure."))
                return Result.FAILURE
//        catch (ex: Exception) {
//                Log.d("Delete Worker: ", ex.message)
//                setOutputData(createOutput("DeleteWorker failure."))
//                return Result.FAILURE
//            }
        }
    }

    private fun createOutput(msg: String) : Data {
        return Data.Builder().putString("key", msg).build()
    }
}