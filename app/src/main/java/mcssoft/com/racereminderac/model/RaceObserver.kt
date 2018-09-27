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
            rootView.id_etCityCode.setText(race.cityCode)
            rootView.id_etRaceCode.setText(race.raceCode)
            rootView.id_etRaceNum.setText(race.raceNum)
            rootView.id_etRaceSel.setText(race.raceSel)
            rootView.id_etRaceTime.setText(race.raceTime)
        }
    }

}