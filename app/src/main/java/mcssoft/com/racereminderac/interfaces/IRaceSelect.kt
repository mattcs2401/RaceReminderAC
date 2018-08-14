package mcssoft.com.racereminderac.interfaces

import mcssoft.com.racereminderac.entity.Race

interface IRaceSelect {

    //fun onRaceSelect(view: View, lPos: Int)
    fun onRaceSelect(race: Race)
}