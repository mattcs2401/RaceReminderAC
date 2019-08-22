package mcssoft.com.racereminderac.entity.xml

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "race_xml",
        indices = [Index(value = ["MtgId", "MeetingCode", "RaceNo", "RaceTime"], unique = true)],
        foreignKeys = [ForeignKey(entity = Meeting::class,
                parentColumns = ["MtgId","MeetingCode"],
                childColumns = ["MtgId","MeetingCode"],
                onDelete = CASCADE)])
class RaceXml(@ColumnInfo(name = "MtgId") var mtgId: String,
              @ColumnInfo(name = "MeetingCode") var meetingCode: String,
              @ColumnInfo(name = "RaceNo") var raceNo: String,
              @ColumnInfo(name = "RaceTime") var raceTime: String) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id") var id: Long? = null    // value inserted by Room.

//    // Meeting foreign keys.
//    @ColumnInfo(name = "MtgId") var mtgId: String    // e.g. "2032403456"
//    @ColumnInfo(name = "MeetingCode") var meetingCode: String   // e.g. "NR"

    // Other columns
    @ColumnInfo(name = "RaceName") var raceName: String = ""
    @ColumnInfo(name = "Distance") var distance: String = ""

//    // RaceXml details.
//    lateinit var raceNo: String   // e.g. "1"
//    lateinit var raceTime: String // e.g. "2019-08-13T12:35:00"
//    lateinit var raceName: String // e.g. "LEGACY WEEK 1-8 SEPTEMBER MAIDEN PLATE"
//    lateinit var distance: String // e.g. "1600"

//    override fun toString(): String {
//        return """RaceXml: $raceNo Time: ${raceTime.split("T")[1]} Name: $raceName"""
//    }

//    fun getRaceDetails() : List<String> {
//        return listOf(mtgId, meetingCode, raceNo, raceTime, raceName, distance)
//    }
}