package mcssoft.com.racereminderac.dao

import androidx.paging.DataSource
import androidx.room.*
import mcssoft.com.racereminderac.entity.RaceDetails

/**
 * For Paging.
 */
@Dao
interface RaceDAO2 {

    @Query("select * from race_details where archvRace = 'N'")
    fun getAllRaces(): DataSource.Factory<Int, RaceDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(lRaceDetails: List<RaceDetails>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(raceDetails: RaceDetails): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateRace(raceDetails: RaceDetails): Int

    @Delete
    fun delete(raceDetails: RaceDetails)
}