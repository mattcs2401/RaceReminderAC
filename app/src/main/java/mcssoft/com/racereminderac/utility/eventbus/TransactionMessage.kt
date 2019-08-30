package mcssoft.com.racereminderac.utility.eventbus

import mcssoft.com.racereminderac.entity.RaceDetails

/**
 * Database transaction message.
 * @param opType: Transaction type, e.g. Constants.INSERT/UPDATE/DELETE
 * @param raceDetails: The Race object.
 */
class TransactionMessage(private val opType: Int, private val raceDetails: RaceDetails) {

    val theOpType: Int get() = opType
    val theRaceDetails: RaceDetails get() = raceDetails
}