package mcssoft.com.racereminderac.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.repository.RaceRepository

class RaceViewModel(application: Application) : AndroidViewModel(application) {

    private val raceRepository: RaceRepository = RaceRepository(application)
    private var allRaces: LiveData<MutableList<Race>> //= MutableLiveData()

    init {
        allRaces = getAllRaces()
    }

//    fun getAllRaces(): LiveData<MutableList<Race>> = raceRepository.getAllRaces()
    fun getAllRaces() = raceRepository.getAllRaces()

    fun insert(race: Race) = raceRepository.insert(race)

    fun update(race: Race) = raceRepository.update(race)
}