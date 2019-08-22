package mcssoft.com.racereminderac.utility.eventbus

import mcssoft.com.racereminderac.entity.Race

/**
 * Utility class to represent an event message posted on the EventBus.
 * @param race: The message (the RaceXml object to remove).
 */
class DeleteMessage(var race: Race) {

    val theRace: Race get() = race
}