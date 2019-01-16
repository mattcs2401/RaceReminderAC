package mcssoft.com.racereminderac.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_fragment.view.*
import kotlinx.android.synthetic.main.toolbar_base.*
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.adapter.RaceAdapter
import mcssoft.com.racereminderac.interfaces.IRace
import mcssoft.com.racereminderac.observer.RaceListObserver
import mcssoft.com.racereminderac.model.RaceViewModel
import mcssoft.com.racereminderac.utility.Constants
import mcssoft.com.racereminderac.utility.RaceAlarm
import mcssoft.com.racereminderac.utility.RacePreferences
import mcssoft.com.racereminderac.utility.TouchHelper
import mcssoft.com.racereminderac.utility.eventbus.DeleteMessage
import mcssoft.com.racereminderac.utility.eventbus.ManualRefreshMessage
import mcssoft.com.racereminderac.utility.eventbus.SelectMessage
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainFragment : Fragment() {

    //<editor-fold defaultstate="collapsed" desc="Region: Lifecycle">
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity?.id_toolbar)?.title = getString(R.string.title_race_reminder)

        raceAdapter = RaceAdapter(activity!!.id_container, activity!!)
        recyclerView = view.id_recyclerView

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = raceAdapter

        val touchHelper = TouchHelper(context!!, raceAdapter)
        val itemTouchHelper = ItemTouchHelper(touchHelper)

        raceAdapter.setTouchHelper(itemTouchHelper)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        // If bottom nav view was previously hidden by a New or Edit etc, then show again.
        val bnv = activity?.findViewById(R.id.id_bottom_nav_view) as BottomNavigationView
        if(bnv.visibility == View.GONE) {
            bnv.visibility = View.VISIBLE
        }

        // Set the view model.
        raceViewModel = ViewModelProviders.of(activity!!).get(RaceViewModel::class.java)

        val lRaces = raceViewModel.getAllRaces()
        lRaces.observe(viewLifecycleOwner, RaceListObserver(lRaces, raceAdapter))
    }

    override fun onStart() {
        super.onStart()
//        if(RacePreferences.getInstance()!!.getRefreshInterval(activity!!)) {
//            RaceAlarm.getInstance()?.setAlarm(activity!!)
//        }
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
//        RaceAlarm.getInstance()?.cancelAlarm()
        EventBus.getDefault().unregister(this)
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: EventBus">
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onMessageEvent(delete: DeleteMessage) {
        raceViewModel.delete(delete.theRace)
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onMessageEvent(refresh: ManualRefreshMessage) {
        /** Note: The down side to this is the observer will react twice. **/
        if(raceAdapter.itemCount > 0) {
           // Get arbitary 1st Race from backing data.
            val race = raceAdapter.getRace(0)
            val oldCC = race.cityCode

            // Set new temporary city code and update Race.
            race.cityCode = Constants.CITY_CODE_DUMMY
            raceViewModel.update(race)

            // Reset city code back to original.
            race.cityCode = oldCC
            raceViewModel.update(race)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onMessageEvent(select: SelectMessage) {
        val lPos = select.getPos
        when(select.getSelType) {
            Constants.ITEM_SELECT -> {
                (activity as IRace.IRaceSelect).onRaceSelect(raceAdapter.getRace(lPos).id!!)
            }
            Constants.ITEM_LONG_SELECT -> {
                (activity as IRace.IRaceLongSelect).onRaceLongSelect(raceAdapter.getRace(lPos).id!!)
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Private vars.">
    private lateinit var raceAdapter: RaceAdapter
    private lateinit var raceViewModel: RaceViewModel
    private lateinit var recyclerView: RecyclerView
    //</editor-fold>
}

