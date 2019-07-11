package mcssoft.com.racereminderac.interfaces

interface IRace {

    interface IRaceSelect {
        /**
         * Get the database (row) id of the adapter selection.
         * @param id: The id of the Race object selected from the adapter listing.
         * @note: Used by the EditFragment to show Race details.
         */
        fun onRaceSelect(id: Long)

        /**
         * @param id: The id of the Race object selected from the adapter listing.
         * @param selects: The selections for a Race.
         */
        fun onRaceSelect(id: Long, selects: Array<String>)
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