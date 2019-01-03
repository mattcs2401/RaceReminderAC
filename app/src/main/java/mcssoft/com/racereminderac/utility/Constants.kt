package mcssoft.com.racereminderac.utility

/**
 * Utility object to define a set of app constants.
 */
object Constants {
    const val CURRENT_TIME_BEFORE = -1      // Current time is before the Race time.
    const val CURRENT_TIME_SAME = 0         // Current time is the same as the Race time.
    const val CURRENT_TIME_AFTER = 1        //  Current time is after the Race time.

    const val EDIT_RACE_NEW: Int = 0        // Edit action is for a new Race.
    const val EDIT_RACE_UPDATE: Int = 1     // Edit action is to update/amend a Race.
    const val EDIT_RACE_COPY: Int = 2       // Edit action is to copy a Race.

    const val FIVE_MINUTES: Int = 300000    // For a 5 minute window before race time.
    const val FIFTEEN_MINUTES: Int = 900000 // Periodic work request limitation (can't be less).

    const val TIME: Int = 1                 // Get date/time as time only.
    const val DATE: Int = 2                 // Get date/time as date only.
    const val TIME_FORMAT_24 = "kk:mm"      // Time value is displayed in 24 hour format.
    const val DATE_FORMAT = "dd/MM/yyyy"    // Date value display format.

    const val INSERT = 1                    // Signifies a Race insert.
    const val UPDATE = 2                    // Signifies a Race update.
    const val DELETE = 3                    // Signifies a Race delete.

    const val META_COLOUR_1 = "1"           // The current time is before the Race time.
    const val META_COLOUR_2 = "2"           // The current time is nearing the Race time.
    const val META_COLOUR_3 = "3"           // The current time is after the Race time.

    const val DAY_BEFORE = 0                // The given day is before today.
    const val DAY_TODAY = 1                 // The given day is the same as today.
}