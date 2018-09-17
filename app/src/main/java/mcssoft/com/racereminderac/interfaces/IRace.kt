package mcssoft.com.racereminderac.interfaces

import mcssoft.com.racereminderac.entity.Race

interface IRace {

    interface IRaceSelect {
        /**
         * The (database) id of the Race object selected from the list (from the object).
         */
        fun onRaceSelect(id: Long)
    }

    interface IRaceNum {
        /**
         * The race number selected from the xxx dialog.
         */
        fun onRaceNum(raceNum: String)
    }

    interface IRaceSel {
        /**
         * The race selection for a particular race number from the xxx dialog.
         */
        fun onRaceSel(raceSel: String)
    }

}