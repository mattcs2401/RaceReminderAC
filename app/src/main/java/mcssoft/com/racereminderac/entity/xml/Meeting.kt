package mcssoft.com.racereminderac.entity.xml

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "race_meeting",
        indices = [Index(value = ["MtgId", "MeetingCode"], unique = true)])
class Meeting(@ColumnInfo(name = "MtgId") var mtgId: String,
              @ColumnInfo(name = "MeetingCode") var meetingCode: String) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id") var id: Long? = null    // value inserted by Room.

    // Other columns.
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
//
//    fun getMeetingDetails() : List<String> {
//        return listOf(meetingCode, mtgId, venueName, mtgType, trackDesc, trackRating, weatherDesc, mtgAbandoned)
//    }
}