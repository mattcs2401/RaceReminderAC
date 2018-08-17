package mcssoft.com.racereminderac.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.background.InitWorker
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
//                            .addCallback(callback)
                            .build()
                }
            }
            return instance
        }

        private val callback = object : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // pre-populate database - dummy data
                val request: OneTimeWorkRequest = OneTimeWorkRequest.Builder(InitWorker::class.java).build()
                val workMgr : WorkManager = WorkManager.getInstance()
                return workMgr.enqueue(request)
            }
        }
    }

}