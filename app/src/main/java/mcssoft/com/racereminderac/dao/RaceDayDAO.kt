package mcssoft.com.racereminderac.dao

import androidx.room.*
import mcssoft.com.racereminderac.entity.xml.Meeting
import mcssoft.com.racereminderac.entity.xml.Race
import mcssoft.com.racereminderac.entity.xml.RaceDay
import mcssoft.com.racereminderac.entity.xml.Runner

@Dao
interface RaceDayDAO {

    // Inserts.
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertRaceDay(raceDay: RaceDay): Long
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertMeeting(meeting: Meeting): Long
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertRace(race: Race): Long
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertRunner(runner: Runner): Long

    // Updates.
//    @Update(onConflict = OnConflictStrategy.IGNORE)
//    fun updateRaceDay(raceDay: RaceDay): Int
//
//    @Update(onConflict = OnConflictStrategy.IGNORE)
//    fun updateMeeting(meeting: Meeting): Int
//
//    @Update(onConflict = OnConflictStrategy.IGNORE)
//    fun updateRace(race: Race): Int
//
//    @Update(onConflict = OnConflictStrategy.IGNORE)
//    fun updateRunner(runner: Runner): Int

    // Selects.


    // Deletes TBA
//    @Delete
//    fun deleteRaceDay(raceDay: RaceDay)
}