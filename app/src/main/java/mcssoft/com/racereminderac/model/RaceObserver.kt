package mcssoft.com.racereminderac.model

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.edit_fragment.view.*
import mcssoft.com.racereminderac.entity.Race

class RaceObserver(race: LiveData<Race>, view: View) : Observer<Race> {

    var rootView: View

    init {
        this.rootView = view
    }

    override fun onChanged(race: Race?) {
        if(race != null) {
            /* surround with null check so will skip whole block if is null. */
            rootView.etCityCode.setText(race.cityCode)
            rootView.etRaceCode.setText(race.raceCode)
            rootView.etRaceNum.setText(race.raceNum)
            rootView.etRaceSel.setText(race.raceSel)
            rootView.etRaceTime.setText(race.raceTime)
        }
    }

}