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
         * @param values: 0 - 3: The selections for a Race (multi select enabled or allowed).
         *                4 - 6: Extras info, trainer name, jockey name, horse name.
         */
        fun onRaceSelect(id: Long, values: Array<String>)
    }

    interface IRaceLongSelect {
        /**
         * Get the database (row) id of the adapter selection.
         * @param id: The id of the Race object selected from the adapter listing.
         * @param values: 0 - 3: The selections for a Race (multi select enabled or allowed).
         *                4 - 6: Extras info, trainer name, jockey name, horse name.
         */
        fun onRaceLongSelect(id: Long, values: Array<String>)
    }

}