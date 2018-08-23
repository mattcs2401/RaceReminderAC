package mcssoft.com.racereminderac.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import mcssoft.com.racereminderac.entity.Race

class RaceObserver(race: LiveData<Race>) : Observer<Race> {

    override fun onChanged(race: Race?) {

        val bp = ""
    }
}