package mcssoft.com.racereminderac.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import mcssoft.com.racereminderac.adapter.RaceAdapter
import mcssoft.com.racereminderac.entity.Race

class RaceListObserver(lRaces: LiveData<MutableList<Race>>, adapter: RaceAdapter) : Observer<List<Race>> {

    private var adapter: RaceAdapter

    init {
        this.adapter = adapter
    }

    override fun onChanged(lRaces: List<Race>?) {
        adapter.swapData(lRaces as ArrayList<Race>)

        // TODO - races nearing race time check can be done here. Have access to the whole list.
    }


}