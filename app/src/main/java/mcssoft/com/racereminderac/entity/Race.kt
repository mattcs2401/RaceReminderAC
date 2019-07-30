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

    // Non-mandatory or default values.
    @ColumnInfo(name = "RaceDate") var raceDate: String = "01/01/1970"    // race date.
    @ColumnInfo(name = "RaceTimeL") var raceTimeL: Long = 0               // race time as Long val.
    @ColumnInfo(name = "ArchvRace") var archvRace: String = "N"           // record's archive flag TBA.
    @ColumnInfo(name = "MetaColour") var metaColour: String = "1"         // race info display colour.
    @ColumnInfo(name = "BetPlaced") var betPlaced: Boolean = false        // bet placed indicator.
    @ColumnInfo(name = "RaceSel2") var raceSel2: String = ""              // 2nd race sel (multi sel).
    @ColumnInfo(name = "RaceSel3") var raceSel3: String = ""              // 3rd "    "    "
    @ColumnInfo(name = "RaceSel4") var raceSel4: String = ""              // 4th "    "    "

    // Additional (horse, jokey and trainer names).
    @ColumnInfo(name = "RaceHorse") var raceHorse: String = ""
    @ColumnInfo(name = "RaceJockey") var raceJockey: String = ""
    @ColumnInfo(name = "RaceTrainer") var raceTrainer: String = ""

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