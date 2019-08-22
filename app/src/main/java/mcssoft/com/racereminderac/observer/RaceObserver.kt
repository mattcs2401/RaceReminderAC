package mcssoft.com.racereminderac.observer

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.edit_fragment.view.*
import mcssoft.com.racereminderac.entity.RaceDetails

class RaceObserver(race: LiveData<RaceDetails>, var view: View) : Observer<RaceDetails> {

    /* Note: Parameter == null if we're entering details for a new Race. */
    override fun onChanged(race: RaceDetails?) {
        if(race != null) {
            view.id_np_city_code.value = findInArray(view.id_np_city_code.displayedValues,race.cityCode)
            view.id_np_race_code.value = findInArray(view.id_np_race_code.displayedValues,race.raceCode)
            view.id_np_race_num.value = findInArray(view.id_np_race_num.displayedValues,race.raceNum)
            view.id_np_race_sel.value = findInArray(view.id_np_race_sel.displayedValues,race.raceSel)
            view.id_tv_multi_sel0.text = race.raceSel
            view.id_tv_multi_sel1.text = race.raceSel2
            view.id_tv_multi_sel2.text = race.raceSel3
            view.id_tv_multi_sel3.text = race.raceSel4
            view.id_btn_time.text = race.raceTimeS
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

