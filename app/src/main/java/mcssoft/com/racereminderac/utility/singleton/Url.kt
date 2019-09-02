package mcssoft.com.racereminderac.utility.singleton

import android.content.Context
import android.net.Uri
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.entity.RaceDetails
import mcssoft.com.racereminderac.utility.singleton.base.SingletonBase

/**
 * Helper class to manipulate the Tatts racing Url;
 * E.g. base Url for a single Race:
 * https://tatts.com/pagedata/racing/YYYY/M(M)/D(D)/NR1.xml
 */
class Url private constructor (private val context: Context) {

    companion object : SingletonBase<Url, Context>(::Url)

    fun constructRaceUrl(raceDetails: RaceDetails): String {
        val builder = Uri.Builder()
        val raceDate = arrayListOf(raceDetails.raceDate.split("/"))
        builder.encodedPath(context.getString(R.string.tatts_base_url_path))
                .appendPath(raceDate[0][2])                     // year
                .appendPath(checkForLeading(raceDate[0][1]))    // month
                .appendPath(checkForLeading(raceDate[0][0]))    // day
                .appendPath(raceDetails.cityCode + raceDetails.raceCode + raceDetails.raceNum + ".xml")
        builder.build()
        return builder.toString()
    }

    /**
     * Utility to strip leading zero from day or month values. Tatts Url doesn't use them.
     * @param value: E.g. "09" for a day or month value.
     */
    private fun checkForLeading(value: String): String {
        val str = value.split("0")
        if(str[0] == "") {
            return str[1]
        } else {
            return value
        }
    }

}
