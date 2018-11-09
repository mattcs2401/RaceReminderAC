package mcssoft.com.racereminderac.utility.eventbus

import mcssoft.com.racereminderac.entity.Race

class RemoveMessage(var race: Race) {

    val theRace: Race get() = race

}