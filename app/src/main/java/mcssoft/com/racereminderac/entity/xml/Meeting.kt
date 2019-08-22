package mcssoft.com.racereminderac.entity.xml

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Data class to model the <Meeting></Meeting> tag of the Tatts xml page data.
 */
@Entity(tableName = "meeting",
        indices = [Index(value = ["MtgId"], unique = true)])
class Meeting(@ColumnInfo(name = "MtgId") var mtgId: String) {

    @PrimaryKey
    @ColumnInfo(name = "_id") var id: Long = mtgId.toLong()

    // Other columns.
    @ColumnInfo(name = "MeetingCode") var meetingCode: String = ""
    @ColumnInfo(name = "VenueName") var venueName: String = ""
    @ColumnInfo(name = "MtgType") var mtgType: String = ""
    @ColumnInfo(name = "TrackDesc") var trackDesc: String = ""
    @ColumnInfo(name = "TrackRating") var trackRating: String = ""
    @ColumnInfo(name = "WeatherDesc") var weatherDesc: String = ""
    @ColumnInfo(name = "MtgAbandoned") var mtgAbandoned: String = ""

//    lateinit var meetingCode: String   // e.g. "NR"
//    lateinit var mtgId: String         // e.g. "2032403456"
//    lateinit var venueName: String     // e.g. "Gosford"
//    lateinit var mtgType: String       // e.g. "R"
//    lateinit var trackDesc: String     // e.g. "Good"
//    lateinit var trackRating: String   // e.g. "4"
//    lateinit var weatherDesc: String   // e.g. "Fine"
//    lateinit var mtgAbandoned: String  // e.g. "N"
}