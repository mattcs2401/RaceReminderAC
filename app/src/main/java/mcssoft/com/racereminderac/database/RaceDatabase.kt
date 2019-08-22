package mcssoft.com.racereminderac.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.dao.RaceDayDAO
import mcssoft.com.racereminderac.entity.RaceDetails
import mcssoft.com.racereminderac.entity.xml.Meeting
import mcssoft.com.racereminderac.entity.xml.Race
import mcssoft.com.racereminderac.entity.xml.RaceDay

@Database(entities = [RaceDetails::class, RaceDay::class, Meeting::class, Race::class/*, Runner::class*/],
        version = 1, exportSchema = false)
abstract class RaceDatabase : RoomDatabase() {

    internal abstract fun raceDao(): RaceDAO
    internal abstract fun raceDayDao(): RaceDayDAO

    companion object {

        @Volatile private var instance: RaceDatabase? = null

        fun getInstance(context: Context): RaceDatabase? {
            if (instance == null) {
                synchronized(RaceDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext,
                            RaceDatabase::class.java, "Races.db")
                            .build()
                }
            }
            return instance
        }
    }
}