package mcssoft.com.racereminderac.entity.xml

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

/**
 * Data class to model the <Race></Race> tag of the Tatts xml page data.
 * Note: This format is derived from, e.g. /YYYY/M(M)/D(D)/<city-code><race-code><race-num>
 */
@Entity(indices = [Index(name = "idxMtgId", value = ["MtgId"], unique = true)],
        foreignKeys = [ForeignKey(entity = Meeting::class,
                parentColumns = ["MtgId"], childColumns = ["MtgId"], onDelete = CASCADE)])
class Race(var mId: Long, var rNo: Long) {

    @PrimaryKey
    @ColumnInfo(name = "RaceNo") var raceNo = rNo

    // Foreign key.
    @ColumnInfo(name = "MtgId") var mtgId = mId

    // Other columns
    @ColumnInfo(name = "RaceTime") var raceTime: String = ""
    @ColumnInfo(name = "RaceName") var raceName: String = ""
    @ColumnInfo(name = "Distance") var distance: String = ""

//    // Race details.
//    lateinit var raceNo: String   // e.g. "1"
//    lateinit var raceTime: String // e.g. "2019-08-13T12:35:00"
//    lateinit var raceName: String // e.g. "LEGACY WEEK 1-8 SEPTEMBER MAIDEN PLATE"
//    lateinit var distance: String // e.g. "1600"

}