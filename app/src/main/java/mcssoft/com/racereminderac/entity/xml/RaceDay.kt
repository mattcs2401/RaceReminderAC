package mcssoft.com.racereminderac.entity.xml

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class to model the <RaceDay></RaceDay> tag of the Tatts xml page data.
 * Note: This format is derived from, e.g. /YYYY/M(M)/D(D)/<city-code><race-code><race-num>
 */
@Entity(tableName = "race_day")
data class RaceDay(@ColumnInfo(name = "RaceDayDate") var raceDayDate: String) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "RdId") var rdId: Long? = null    // value inserted by Room.

    // Other columns.
    @ColumnInfo(name = "RaceYear") var raceYear: String = ""
    @ColumnInfo(name = "RaceMonth") var raceMonth: String = ""
    @ColumnInfo(name = "RaceDay")  var raceDay: String = ""
    @ColumnInfo(name = "RaceDayOfTheWeek") var raceDayOfTheWeek: String = ""
    @ColumnInfo(name = "RaceMonthLong") var raceMonthLong: String = ""

//    lateinit var raceDayDate: String        // e.g. "2019-08-13T00:00:00"
//    lateinit var raceYear: String           // e.g. "2019"
//    lateinit var raceMonth: String          // e.g. "8"
//    lateinit var raceMonthLong: String      // e.g. "August"
//    lateinit var raceDay: String            // e.g. "13"
//    lateinit var raceDayOfTheWeek: String   // e.g. "Tuesday"
}