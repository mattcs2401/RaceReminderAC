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

    const val ONE_MINUTE: Long = 60000       // Alarm 1 minute interval.
    const val THREE_MINUTES: Long = 180000   // Alarm 3 minute interval.
    const val FIVE_MINUTES: Long = 300000    // For a 5 minute window before race time.
    const val FIFTEEN_MINUTES: Long = 900000 // Periodic work request limitation (can't be less).

    const val TIME: Int = 1                 // Get date/time as time only.
    const val DATE: Int = 2                 // Get date/time as date only.
    const val TIME_FORMAT_24 = "kk:mm"      // Time value is displayed in 24 hour format.
    const val DATE_FORMAT = "dd/MM/yyyy"    // Date value display format.

    const val INSERT = 1                    // Signifies a Race insert.
    const val UPDATE = 2                    // Signifies a Race update.
    const val DELETE = 3                    // Signifies a Race delete.
    const val DELETE_ALL = 4                // Signifies delete all Race entries.
    const val COUNT = 5                     // Signifies count all Race entries.

    const val NO_VALUE = -1                 // Generic signify no value returned.

    const val META_COLOUR_1 = "1"           // The current time is before the Race time.
    const val META_COLOUR_2 = "2"           // The current time is nearing the Race time.
    const val META_COLOUR_3 = "3"           // The current time is after the Race time.

    const val DAY_PRIOR = 0                 // The given day is before today.
    const val DAY_CURRENT = 1               // The given day is the same as today.

    const val CITY_CODE_DUMMY = "ZZ"        // Dummy city code value.

    const val MINUS_ONE = -1                // General initialiser value (Int or Long).

    const val NO_FLAGS = 0                  // PendingIntent related (RaceAlarm).
    const val REQ_CODE = 0                  // "             "       "

    const val ITEM_SELECT = 0               // Represents the click (press) on a list item.
    const val ITEM_LONG_SELECT = 1          // Represents the long click (long press) on a list item.

    const val DELETE_COLOUR = "#b80f0a"     // Swipe to delete background colour (red).

    const val REFRESH_MIN = 1               // Refresh interval minimum value (equates to 1 minute).
    const val REFRESH_DEFAULT = 3           // Refresh interval default value (equates to 3 minutes).

    const val CB_BET_PLACED = 1             // BetPlaced UI component (on adapter row).
}