package mcssoft.com.racereminderac.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import mcssoft.com.racereminderac.adapter.RaceAdapter
import mcssoft.com.racereminderac.entity.Race
import java.util.Collections

class RaceListObserver(lRaces: LiveData<MutableList<Race>>, adapter: RaceAdapter) : Observer<MutableList<Race>> {

    private var adapter: RaceAdapter

    init {
        this.adapter = adapter
    }

    override fun onChanged(lRaces: MutableList<Race>?) {
        if(!(lRaces == null) && (lRaces.size > 1)) {
            Collections.sort(lRaces)
            adapter.swapData(lRaces as ArrayList<Race>)
        }
    }

}