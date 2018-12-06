package mcssoft.com.racereminderac.utility

/**
 * Utility class to define
 * a set of constants.
 */
class Constants {
    companion object {
        const val CURRENT_TIME_BEFORE: Int = -1  // Current time is before the Race time.
        const val CURRENT_TIME_SAME: Int = 0     // Current time is the same as the Race time.
        const val CURRENT_TIME_AFTER: Int = 1    // Current time is after the Race time.

        const val EDIT_RACE_NEW: Int = 0         // Edit action is for a new Race.
        const val EDIT_RACE_UPDATE: Int = 1      // Edit action is to update/amend a Race.
        const val EDIT_RACE_COPY: Int = 2        // Edit action is to copy a Race.
        const val EDIT_RACE_DELETE: Int = 3      // TBA
    }
}