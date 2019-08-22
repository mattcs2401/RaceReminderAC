package mcssoft.com.racereminderac.entity.xml

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

/**
 * Data class to model the <Race></Race> tag of the Tatts xml page data.
 */
@Entity(tableName = "race",
        /*indices = [Index(value = ["MtgId", "MeetingCode", "RaceNo", "RaceTime"], unique = true)],*/
        foreignKeys = [ForeignKey(entity = Meeting::class,
                parentColumns = ["MtgId"],
                childColumns = ["MtgId"],
                onDelete = CASCADE)])
class Race(@ColumnInfo(name = "RaceNo") var raceNo: String) {

    @PrimaryKey
    @ColumnInfo(name = "_id") var id: Long? = raceNo.toLong()

    // Meeting foreign keys.
    @ColumnInfo(name = "MtgId") var mtgId: String = ""    // e.g. "2032403456"

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