package mcssoft.com.racereminderac.utility.eventbus

import mcssoft.com.racereminderac.entity.RaceDetails

class DateTimeMessage(var race: RaceDetails) {

    val theRace: RaceDetails get() = race
}