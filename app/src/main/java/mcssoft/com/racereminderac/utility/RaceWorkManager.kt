package mcssoft.com.racereminderac.utility

/**
 * Wrapper for WorkManager to handle the Worker/s for app Notifications and Xml parsing.
 */
class RaceWorkManager {

    companion object {
        @Volatile
        private var INSTANCE: RaceWorkManager? = null

        fun getInstance() =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                            ?: RaceWorkManager().also {
                        INSTANCE = it
                    }
                }
    }



}