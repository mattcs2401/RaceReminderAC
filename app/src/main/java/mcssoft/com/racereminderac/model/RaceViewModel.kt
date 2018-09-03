package mcssoft.com.racereminderac.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.repository.RaceRepository

class RaceViewModel(application: Application) : AndroidViewModel(application) {

    private val raceRepository: RaceRepository = RaceRepository(application)

    private var allRaces: LiveData<MutableList<Race>>

    init {
        allRaces = raceRepository.getAllRaces()
    }

    fun getRaceLD(id: Long): LiveData<Race> = raceRepository.getRaceLD(id)

    fun getRace(id: Long): Race = raceRepository.getRace(id)!!

    fun getAllRaces(): LiveData<MutableList<Race>> = allRaces

    fun insert(race: Race) = raceRepository.insert(race)

    fun update(race: Race) = raceRepository.update(race)
}