package mcssoft.com.racereminderac.entity.xml

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

/**
 * Data class to model the <Runner></Runner> tag of the Tatts xml page data.
 * Note: This format is derived from, e.g. /YYYY/M(M)/D(D)/<city-code><race-code><race-num>
 */
@Entity(indices = [Index(name = "idxRaceNo", value = ["RaceNo"], unique = true)],
        foreignKeys = [ForeignKey(entity = Race::class,
                parentColumns = ["RaceNo"], childColumns = ["RaceNo"], onDelete = CASCADE)])
class Runner(var rNo: Long, var runNo: Long) {

    @PrimaryKey
    @ColumnInfo(name = "runnerNo") var runnerNo = runNo

    // Foreign key.
    @ColumnInfo(name = "RaceNo") var raceNo = rNo

    // Other columns.
    @ColumnInfo(name = "RunnerName") var runnerName: String = ""
    @ColumnInfo(name = "scratched") var scratched: String = ""
    @ColumnInfo(name = "rider") var rider: String? = ""
    @ColumnInfo(name = "riderChanged") var riderChanged: String = ""
    @ColumnInfo(name = "barrier") var barrier: String = ""
    @ColumnInfo(name = "handicap") var handicap: String = ""
    @ColumnInfo(name = "weight") var weight: String = ""
    @ColumnInfo(name = "lastResult") var lastResult: String? = ""
    @ColumnInfo(name = "rtng") var rtng: String = ""

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
}
