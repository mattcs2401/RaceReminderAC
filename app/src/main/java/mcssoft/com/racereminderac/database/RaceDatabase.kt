package mcssoft.com.racereminderac.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.dao.RaceDAO

@Database(entities = arrayOf(Race::class), version = 1, exportSchema = false)
abstract class RaceDatabase : RoomDatabase() {

    internal abstract fun raceDao(): RaceDAO

    companion object {

        @Volatile private var instance: RaceDatabase? = null

        fun getInstance(context: Context): RaceDatabase? {
            if (instance == null) {
                synchronized(RaceDatabase::class) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            RaceDatabase::class.java, "Races.db")
                            .build()
                }
            }
            return instance
        }
    }
}