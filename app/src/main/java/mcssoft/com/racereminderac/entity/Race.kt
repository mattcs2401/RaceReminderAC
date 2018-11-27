package mcssoft.com.racereminderac.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "race_details")
data class Race(@ColumnInfo(name = "CityCode") var cityCode: String,
                @ColumnInfo(name = "RaceCode") var raceCode: String,
                @ColumnInfo(name = "RaceNum")  var raceNum: String,
                @ColumnInfo(name = "RaceSel")  var raceSel: String,
                @ColumnInfo(name = "RaceTime") var raceTime: String) : Comparable<Race> {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id") var id: Long? = null

    @ColumnInfo(name = "RaceDate") var raceDate: String = "01/01/2019"
    @ColumnInfo(name = "ArchvRace") var archvRace: String = "N"

    /**
     * Simple compare on RaceTime. Used in, e.g.,  Collections.sort(List<Race>)
     * @param other: The object to compare (this) against.
     */
    override fun compareTo(other: Race): Int {
        var result = raceDate.compareTo(other.raceDate)
        if(result == 0) {
            // dates are equal, so compare time.
            result = raceTime.compareTo(other.raceTime)
        }
        return result
    }
    // TODO - this compareTo will likely need tweaking.
}