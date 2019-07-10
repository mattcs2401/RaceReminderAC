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
         * @param sel0: Multi select selection 1.
         * @param sel1: Multi select selection 2.
         * @param sel2: Multi select selection 3.
         * @param sel3: Multi select selection 4.
         * @param multiSel: Flag to indicate multi select exists.
         */
        fun onRaceSelect(id: Long, sel0: String, sel1: String, sel2: String, sel3: String, multiSel: Boolean)
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