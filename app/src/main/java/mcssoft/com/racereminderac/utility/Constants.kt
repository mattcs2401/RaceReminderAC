package mcssoft.com.racereminderac.utility

/**
 * Utility object to define a set of app constants.
 */
object Constants {
    const val EDIT_RACE_NEW: Int = 0        // Edit action is for a new Race.
    const val EDIT_RACE_UPDATE: Int = 1     // Edit action is to update/amend a Race.
    const val EDIT_RACE_COPY: Int = 2       // Edit action is to copy a Race.
    const val EDIT_RACE_DELETE: Int = 3     // TBA

    const val FIVE_MINUTES: Int = 300000    // for a 5 minute window before race time.
    const val FIFTEEN_MINUTES: Int = 900000 // periodic work request limitation, can't be less.

    const val EMPTY_VIEW: Int = 0           // recycler view is empty, nothing to show.
    const val RACE_VIEW: Int = 1            // recycler view is not empty.

    const val TIME: Int = 1                 // get date/time as time only.
    const val DATE: Int = 2                 // get date/time as date only.
    const val TIME_FORMAT_24 = "kk:mm"      // time value is displayed in 24 hour format.
    const val DATE_FORMAT = "dd/MM/yyyy"    // date value display format.

    const val INSERT = 1                    //
    const val UPDATE = 2                    //
    const val DELETE = 3                    //

    const val META_COLOUR_1 = "1"           //
    const val META_COLOUR_2 = "2"           //
    const val META_COLOUR_3 = "3"           //
}