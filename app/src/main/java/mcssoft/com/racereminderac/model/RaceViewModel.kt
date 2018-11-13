package mcssoft.com.racereminderac.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.repository.RaceRepository

class RaceViewModel(application: Application) : AndroidViewModel(application) {

    private val raceRepository: RaceRepository = RaceRepository(application)

    private var allRaces: LiveData<MutableList<Race>>

    init {
        allRaces = raceRepository.getAllRaces()
    }

    fun getRace(id: Long): LiveData<Race> = raceRepository.getRace(id)

    fun getAllRaces(): LiveData<MutableList<Race>> = allRaces

    fun getAllRacesBasic(): List<Race> = raceRepository.getAllRacesBasic()

    fun getCountRaces(): Int = raceRepository.getCountRaces()

//    fun insert(race: Race) = raceRepository.insert(race)
    fun insert(race: Race) = raceRepository.doDatabaseOperation("insert", race)

//    fun update(race: Race) = raceRepository.update(race)
    fun update(race: Race) = raceRepository.doDatabaseOperation("update", race)

//    fun deleteRace(race: Race) = raceRepository.deleteRace(race)
    fun delete(race: Race) = raceRepository.doDatabaseOperation("delete", race)
}