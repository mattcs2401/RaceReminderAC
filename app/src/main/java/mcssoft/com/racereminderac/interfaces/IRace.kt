package mcssoft.com.racereminderac.interfaces

import mcssoft.com.racereminderac.entity.Race

interface IRace {

    interface IRaceSelect {
        /**
         * The (database) id of the Race object selected from the list (from the object).
         */
        fun onRaceSelect(id: Long)
    }
}