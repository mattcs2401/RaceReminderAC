package mcssoft.com.racereminder.dao

import androidx.lifecycle.LiveData
import mcssoft.com.racereminder.entity.Race

@Dao
internal interface RaceDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRace(race: Race)

    @Query("select * from race_details")
    fun getAllRaces(): LiveData<MutableList<Race>>

    @Query("delete from race_details")
    fun deleteAll()
}