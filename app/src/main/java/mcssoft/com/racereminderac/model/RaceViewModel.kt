package mcssoft.com.racereminderac.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import mcssoft.com.racereminderac.entity.RaceDetails
import mcssoft.com.racereminderac.repository.RaceRepository
import mcssoft.com.racereminderac.utility.Constants

class RaceViewModel(application: Application) : AndroidViewModel(application) {

    private val raceRepository: RaceRepository = RaceRepository(application)

    private var allRaces: LiveData<MutableList<RaceDetails>>

    init {
        allRaces = raceRepository.getAllRaces()
    }

    fun getRace(id: Long): LiveData<RaceDetails> = raceRepository.getRaceLD(id)

    fun getRaceNoLD(id: Long) = raceRepository.getRaceNoLD(id) // uses EventBus.

    fun getAllRaces(): LiveData<MutableList<RaceDetails>> = allRaces

    fun insert(race: RaceDetails): Long {
        return raceRepository.doDatabaseOperation(Constants.INSERT, race)
    }

    fun update(race: RaceDetails): Long {
        return raceRepository.doDatabaseOperation(Constants.UPDATE, race)
    }

    fun delete(race: RaceDetails) = raceRepository.doDatabaseOperation(Constants.DELETE, race)

    fun deleteAll() = raceRepository.doDatabaseOperation(Constants.DELETE_ALL, null)

//    fun getCount() = raceRepository.doDatabaseOperation(Constants.COUNT, null)
}