package mcssoft.com.racereminderac.interfaces

import mcssoft.com.racereminderac.entity.Race

interface IDelete {

    fun onDelete(race: Race)
}