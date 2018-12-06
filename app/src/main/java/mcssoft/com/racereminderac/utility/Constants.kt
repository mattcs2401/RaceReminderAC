package mcssoft.com.racereminderac.utility

/**
 * Utility class to "house" a set of constants.
 */
class Constants {
    companion object {
        const val BEFORE: Int = -1     // Current time is before the Race time.
        const val SAME: Int = 0        // Current time is the same as the Race time.
        const val AFTER: Int = 1       // Current time is after the Race time.
    }
}