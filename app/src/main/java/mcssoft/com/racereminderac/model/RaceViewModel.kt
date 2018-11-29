package mcssoft.com.racereminderac.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import mcssoft.com.racereminderac.background.TaskAsyncLD
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.repository.RaceRepository

class RaceViewModel(application: Application) : AndroidViewModel(application) {

    private val raceRepository: RaceRepository = RaceRepository(application)

    private var allRaces: LiveData<MutableList<Race>>

    init {
        allRaces = raceRepository.getAllRaces()
    }

    fun getRace(id: Long): LiveData<Race> = raceRepository.getRaceLD(id)

    fun getRaceNoLD(id: Long) = raceRepository.getRaceNoLD(id) // uses EventBus.

    fun getAllRaces(): LiveData<MutableList<Race>> = allRaces

//    fun insert(race: Race) = raceRepository.doDatabaseOperation("insert", race)
    fun insert(race: Race) = raceRepository.doDatabaseOperation(TaskAsyncLD.INSERT, race)

    fun update(race: Race) = raceRepository.doDatabaseOperation(TaskAsyncLD.UPDATE, race)

    fun delete(race: Race) = raceRepository.doDatabaseOperation(TaskAsyncLD.DELETE, race)

}