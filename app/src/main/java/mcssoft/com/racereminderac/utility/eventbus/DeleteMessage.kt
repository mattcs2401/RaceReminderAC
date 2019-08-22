package mcssoft.com.racereminderac.utility.eventbus

import mcssoft.com.racereminderac.entity.RaceDetails

/**
 * Utility class to represent an event message posted on the EventBus.
 * @param race: The message (the Race object to remove).
 */
class DeleteMessage(var race: RaceDetails) {

    val theRace: RaceDetails get() = race
}