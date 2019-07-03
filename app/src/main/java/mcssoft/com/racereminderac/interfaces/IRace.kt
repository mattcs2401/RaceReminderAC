package mcssoft.com.racereminderac.interfaces

interface IRace {

    interface IRaceSelect {
        /**
         * Get the database (row) id of the adapter selection.
         * @param id: The id of the Race object selected from the adapter listing.
         * @param multiSel: Flag to indicate a multi select scenario exists.
         * @note: Used by the EditFragment to show Race details.
         */
        fun onRaceSelect(id: Long, multiSel: Boolean)
    }

    interface IRaceLongSelect {
        /**
         * Get the database (row) id of the adapter selection.
         * @param id: The id of the Race object selected from the adapter listing.
         * @note: Used by the EditFragment to show Race details.
         */
        fun onRaceLongSelect(id: Long)
    }

}