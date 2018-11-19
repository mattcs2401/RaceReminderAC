package mcssoft.com.racereminderac.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "race_details")
data class Race(@ColumnInfo(name = "CityCode") var cityCode: String,
                @ColumnInfo(name = "RaceCode") var raceCode: String,
                @ColumnInfo(name = "RaceNum")  var raceNum: String,
                @ColumnInfo(name = "RaceSel")  var raceSel: String,
                @ColumnInfo(name = "RaceTime") var raceTime: String) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id") var id: Long? = null

    @ColumnInfo(name = "ArchvRace") var archvRace: String = "N"

    /**
     * Utility function; return the Race details as a Array<String>.
     */
    fun toArray(): Array<String> {
        val array: Array<String> = Array(7) { i -> ""}
        array.set(0, id.toString())
        array.set(1, cityCode)
        array.set(2, raceCode)
        array.set(3, raceNum)
        array.set(4, raceSel)
        array.set(5, raceTime)
        array.set(6, archvRace)
        return array
    }

}