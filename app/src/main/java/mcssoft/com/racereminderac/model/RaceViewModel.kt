package mcssoft.com.racereminderac.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.WorkManager
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.repository.RaceRepository

class RaceViewModel(application: Application) : AndroidViewModel(application) {

    private val raceRepository: RaceRepository = RaceRepository(application)

    private var allRaces: LiveData<MutableList<Race>>

//    private val workManager: WorkManager

    init {
        allRaces = raceRepository.getAllRaces()
//        workManager = WorkManager.getInstance()
    }

    fun getRace(id: Long): LiveData<Race> = raceRepository.getRace(id)

    fun getAllRaces(): LiveData<MutableList<Race>> = allRaces

    fun getCountRaces() : Int = mutableListOf(allRaces).size

    // see notes in RaceRepository
    //fun getCountRaces() : Int = raceRepository.getCountRaces()

    fun insert(race: Race) = raceRepository.doDatabaseOperation("insert", race)

    fun update(race: Race) = raceRepository.doDatabaseOperation("update", race)

    fun delete(race: Race) = raceRepository.doDatabaseOperation("delete", race)

    fun getOutputStatus() = raceRepository.getOutputStatus()
}