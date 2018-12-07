package mcssoft.com.racereminderac.model

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import mcssoft.com.racereminderac.background.async.AsyncLD
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

    fun insert(race: Race) = raceRepository.doDatabaseOperation(AsyncLD.INSERT, race)

    fun update(race: Race) = raceRepository.doDatabaseOperation(AsyncLD.UPDATE, race)

    fun delete(race: Race) = raceRepository.doDatabaseOperation(AsyncLD.DELETE, race)

}