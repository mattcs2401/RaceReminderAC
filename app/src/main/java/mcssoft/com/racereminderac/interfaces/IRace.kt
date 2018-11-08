package mcssoft.com.racereminderac.interfaces

import mcssoft.com.racereminderac.entity.Race

interface IRace {

    interface IRaceSelect {
        /**
         * Get the database (row) id of the adapter selection.
         * @param id: The id of the Race object selected from the adapter listing.
         * @note: Used by the EditFragment to show Race details.
         */
        fun onRaceSelect(id: Long)
    }
}