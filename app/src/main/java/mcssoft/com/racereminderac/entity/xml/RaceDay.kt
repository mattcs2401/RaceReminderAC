package mcssoft.com.racereminderac.entity.xml

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "race_day")
class RaceDay(@ColumnInfo(name = "RaceYear") var raceYear: String,
              @ColumnInfo(name = "RaceMonth") var raceMonth: String,
              @ColumnInfo(name = "RaceDay")  var raceDay: String) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id") var id: Long? = null    // value inserted by Room.

    // Other columns.
    @ColumnInfo(name = "RaceDayDate") var raceDayDate: String = ""
    @ColumnInfo(name = "RaceMonthLong") var raceMonthLong: String = ""
    @ColumnInfo(name = "RaceDayOfTheWeek") var raceDayOfTheWeek: String = ""

//    lateinit var raceDayDate: String        // e.g. "2019-08-13T00:00:00"
//    lateinit var raceYear: String           // e.g. "2019"
//    lateinit var raceMonth: String          // e.g. "8"
//    lateinit var raceMonthLong: String      // e.g. "August"
//    lateinit var raceDay: String            // e.g. "13"
//    lateinit var raceDayOfTheWeek: String   // e.g. "Tuesday"
//
//    fun getRaceDayDetails() : List<String> {
//        return listOf(raceDayDate, raceYear, raceMonth, raceMonthLong, raceDay, raceDayOfTheWeek)
//    }
}