package mcssoft.com.racereminderac.utility.eventbus

import mcssoft.com.racereminderac.entity.Race

class DateTimeMessage(var race: Race) {

    val theRace: Race get() = race
}