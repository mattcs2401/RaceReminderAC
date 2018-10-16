package mcssoft.com.racereminderac.model

import android.view.View
import android.widget.NumberPicker
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.edit_fragment2.view.*
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.entity.Race

class RaceObserver(race: LiveData<Race>, view: View) : Observer<Race> {

    var rootView: View

    init {
        this.rootView = view
    }

    override fun onChanged(race: Race?) {
        if(race != null) {
            /* surround with null check so will skip whole block if is null. */
            rootView.id_np_city_code.value = findInArray(rootView.id_np_city_code.displayedValues,race.cityCode)
            rootView.id_np_race_code.value = findInArray(rootView.id_np_race_code.displayedValues,race.raceCode)
            rootView.id_np_race_num.value = findInArray(rootView.id_np_race_num.displayedValues,race.raceNum)
            rootView.id_np_race_sel.value = findInArray(rootView.id_np_race_sel.displayedValues,race.raceSel)
            rootView.id_btn_time.setText(race.raceTime)
        }
    }

    /**
     * Utility function to get the index of a string within a string array.
     * @param elements: The Array<String> of the NumberPicker's values.
     * @param value: The value to find in the array.
     * @return: The index of the value within the array, or -1.
     */
    private fun findInArray(elements: Array<String>, value: String) : Int {
        if(elements.contains(value)) {
            return elements.indexOf(value)
        }
        return -1
    }

}
//private fun collateValues(): Race {
//    // TODO - get NumberPicker values.
//    val race = Race(ccVals.get(npCityCode.value),
//            rcVals.get(npRaceCode.value),
//            rnVals.get(npRaceNo.value),
//            rsVals.get(npRaceSel.value),
//            btnTime.text.toString())
//    race.id = raceId
//    return race
//}
