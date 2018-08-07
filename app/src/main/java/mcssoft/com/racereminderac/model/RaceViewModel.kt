package mcssoft.com.racereminderac.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.repository.RaceRepository

class RaceViewModel(application: Application) : AndroidViewModel(application) {

    private val raceRepository: RaceRepository = RaceRepository(application)
    private var allRaces: LiveData<MutableList<Race>> //= MutableLiveData()

    init {
        allRaces = getAllRaces()
    }

    internal fun getAllRaces(): LiveData<MutableList<Race>> = raceRepository.getAllRaces()

    internal fun insert(race: Race) = raceRepository.insert(race)
}