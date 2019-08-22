package mcssoft.com.racereminderac.entity.xml

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "runner",
        indices = [Index(value = ["RaceNo", "RaceTime"], unique = true)],
        foreignKeys = [ForeignKey(entity = Race::class,
                parentColumns = ["RaceNo", "RaceTime"],
                childColumns = ["RaceNo", "RaceTime"],
                onDelete = CASCADE)])
class Runner(@ColumnInfo(name = "RaceNo") var raceNo: String,
             @ColumnInfo(name = "RaceTime") var raceTime: String,
             @ColumnInfo(name = "RunnerNo") var runnerNo: String,
             @ColumnInfo(name = "RunnerName") var runnerName: String) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id") var id: Long? = null    // value inserted by Room.

    // Other columns.
    @ColumnInfo(name = "scratched") var scratched: String = ""
    @ColumnInfo(name = "riderChanged") var riderChanged: String = ""
    @ColumnInfo(name = "barrier") var barrier: String = ""
    @ColumnInfo(name = "handicap") var handicap: String = ""
    @ColumnInfo(name = "weight") var weight: String = ""
    @ColumnInfo(name = "rtng") var rtng: String = ""
    @ColumnInfo(name = "rider") var rider: String = ""
    @ColumnInfo(name = "lastResult") var lastResult: String = ""

//    // Race details.
//    lateinit var meetingCode: String   // e.g. "NR"
//    lateinit var raceNo: String        // e.g. "1"

//    // Runner details.
//    lateinit var runnerNo: String      // e.g. "1"
//    lateinit var runnerName: String    // e.g. "BADGE OF HONOUR"
//    lateinit var scratched: String     // e.g. "N"
//    lateinit var riderChanged: String  // e.g. "N"
//    lateinit var barrier: String       // e.g. "6"
//    lateinit var handicap: String      // e.g. "0"
//    lateinit var weight: String        // e.g. "59.0"
//    lateinit var rtng: String          // e.g. "82"
//    // Special case trial and error testing. May not exist.
//    var rider: String? = ""            // e.g. "K O'HARA"
//    var lastResult: String? = ""       // e.g. "477"

//    override fun toString(): String {
//        return """$runnerNo $runnerName $rider"""
//    }

//    fun getRunnerDetails() : List<String> {
//        return listOf(meetingCode, raceNo, runnerNo, runnerName, scratched, riderChanged, barrier,
//                handicap, weight, rider!!, lastResult!!)
//    }
}
