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
                @ColumnInfo(name = "RaceTime") var raceTime: String) {//} : Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id") var id: Long = 0

    @ColumnInfo(name = "ArchvRace") var archvRace: String = "N"

    /**
     * Utility function; return the Race details as a Array<String>.
     */
    fun toArray(): Array<String> {
        val array: Array<String> = Array(5, {i -> ""})
//        array.set(0, id.toString())
        array.set(0, cityCode)
        array.set(1, raceCode)
        array.set(2, raceNum)
        array.set(3, raceSel)
        array.set(4, raceTime)
        return array
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Parcelable">
//    // https://proandroiddev.com/parcelable-in-kotlin-here-comes-parcelize-b998d5a5fcac
//    // Note: This done mainly so we can put a Race or List<Race> into a Bundle.
//    constructor(parcel: Parcel) : this (
//            parcel.readString(),
//            parcel.readString(),
//            parcel.readString(),
//            parcel.readString(),
//            parcel.readString() )
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(cityCode)
//        parcel.writeString(raceCode)
//        parcel.writeString(raceNum)
//        parcel.writeString(raceSel)
//        parcel.writeString(raceTime)
//    }
//
//    companion object CREATOR: Parcelable.Creator<Race> {
//        override fun createFromParcel(parcel: Parcel): Race {
//            return Race(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Race?> {
//            return arrayOfNulls(size)
//        }
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
    //</editor-fold>
}