package mcssoft.com.racereminderac.interfaces

interface IRace {

    interface IRaceSelect {
        /**
         * Get the database (row) id of the adapter selection.
         * @param id: The id of the Race object selected from the adapter listing.
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
         * @param selects: The selections for a Race.
         */
        fun onRaceLongSelect(id: Long, selects: Array<String>)
    }

}