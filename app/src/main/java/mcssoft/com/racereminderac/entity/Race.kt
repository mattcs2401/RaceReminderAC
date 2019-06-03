package mcssoft.com.racereminderac.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "race_details")
data class Race(@ColumnInfo(name = "CityCode") var cityCode: String,
                @ColumnInfo(name = "RaceCode") var raceCode: String,
                @ColumnInfo(name = "RaceNum")  var raceNum: String,
                @ColumnInfo(name = "RaceSel")  var raceSel: String,
                @ColumnInfo(name = "RaceTimeS") var raceTimeS: String) : Comparable<Race> {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id") var id: Long? = null    // value inserted by Room.

    // Arbitrary default values.
    @ColumnInfo(name = "RaceDate") var raceDate: String = "01/01/1970"
    @ColumnInfo(name = "RaceTimeL") var raceTimeL: Long = 0
    @ColumnInfo(name = "ArchvRace") var archvRace: String = "N"
    @ColumnInfo(name = "MetaColour") var metaColour: String = "1"

    /**
     * Simple compare on RaceTime. Used in, e.g.,  Collections.sort(List<Race>)
     * @param other: The object to compare (this) against.
     */
    override fun compareTo(other: Race): Int {
        var result = raceDate.compareTo(other.raceDate)
        if(result == 0) {
            // dates are equal, so compare time.
            result = raceTimeS.compareTo(other.raceTimeS)
        }
        return result
    }
}