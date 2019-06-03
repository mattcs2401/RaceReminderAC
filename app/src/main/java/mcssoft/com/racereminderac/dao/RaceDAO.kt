package mcssoft.com.racereminderac.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query
import mcssoft.com.racereminderac.entity.Race

@Dao
interface RaceDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRace(race: Race): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateRace(race: Race): Int

    @Delete
    fun deleteRace(race: Race)

    @Query("delete from race_details where _id = :id")
    fun deleteRace(id: Long): Int

//    @Query("select count(*) from race_details")
//    fun getCountRaces(): Int

    @Query("select * from race_details where _id = :id")
    fun getRaceLD(id: Long): LiveData<Race>

    @Query("select * from race_details where _id = :id")
    fun getRaceNoLD(id: Long): Race

    @Query("select * from race_details where archvRace = 'N'")
    fun getAllRaces(): LiveData<MutableList<Race>> // Room doesn't know how to construct an ArrayList

    @Query("delete from race_details")
    fun deleteAll()

    @Query("delete from race_details where archvRace = 'Y'")
    fun deleteAllArchived()
}