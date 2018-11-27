package mcssoft.com.racereminderac.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import mcssoft.com.racereminderac.adapter.RaceAdapter
import mcssoft.com.racereminderac.entity.Race
import java.util.*
import java.util.Collections.sort

class RaceListObserver(lRaces: LiveData<MutableList<Race>>, adapter: RaceAdapter) : Observer<MutableList<Race>> {

    private var adapter: RaceAdapter

    init {
        this.adapter = adapter
    }

    override fun onChanged(lRaces: MutableList<Race>?) {
        if((lRaces == null) || (lRaces.size < 2)) {
            return
        } else {
            Collections.sort(lRaces)
            adapter.swapData(lRaces as ArrayList<Race>)

        }


        // TODO - races nearing race time check can be done here. Have access to the whole list.
    }

}