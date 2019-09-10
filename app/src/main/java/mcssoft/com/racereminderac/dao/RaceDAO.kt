package mcssoft.com.racereminderac.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query
import mcssoft.com.racereminderac.entity.RaceDetails

@Dao
interface RaceDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRace(race: RaceDetails): Long


    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateRace(race: RaceDetails): Int
    @Delete
    fun deleteRace(race: RaceDetails)

    @Query("delete from race_details where _id = :id")
    fun deleteRace(id: Long): Int

    @Query("select count(*) from race_details where archvRace = 'N'")
    fun getCountRaces(): Int

    @Query("select * from race_details where _id = :id")
    fun getRaceLD(id: Long): LiveData<RaceDetails>

    @Query("select * from race_details where _id = :id")
    fun getRaceNoLD(id: Long): RaceDetails

    @Query("select * from race_details where archvRace = 'N'")
    fun getAllRaces(): LiveData<MutableList<RaceDetails>> // Room doesn't know how to construct an ArrayList

    @Query("delete from race_details")
    fun deleteAll()

    @Query("delete from race_details where archvRace = 'Y'")
    fun deleteAllArchived()

//    @Query("update race_details set BetPlaced = :bp where _id = :id")
//    fun updateBetPlaced(bp: Boolean, id: Long)
}