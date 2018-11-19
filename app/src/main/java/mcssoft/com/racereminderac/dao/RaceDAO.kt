package mcssoft.com.racereminderac.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import mcssoft.com.racereminderac.entity.Race

@Dao
internal interface RaceDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRace(race: Race): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateRace(race: Race)

    @Delete
    fun deleteRace(race: Race)

    @Query("select count(*) from race_details")
    fun getCountRaces(): Int

    @Query("select * from race_details where _id = :id")
    fun getRace(id: Long): LiveData<Race>

    @Query("delete from race_details where _id = :id")
    fun deleteRace(id: Long): Int

    @Query("select * from race_details")
    fun getAllRaces(): LiveData<MutableList<Race>>

    @Query("delete from race_details")
    fun deleteAll()

}