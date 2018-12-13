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
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.interfaces.IRace
import mcssoft.com.racereminderac.interfaces.ISelect
import mcssoft.com.racereminderac.model.RaceListObserver
import mcssoft.com.racereminderac.model.RaceViewModel
import mcssoft.com.racereminderac.utility.TouchHelper
import mcssoft.com.racereminderac.utility.eventbus.DeleteMessage
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainFragment : Fragment(), ISelect.ItemSelect, ISelect.ItemLongSelect {

    //<editor-fold defaultstate="collapsed" desc="Region: Lifecycle">
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        rootView = inflater.inflate(R.layout.main_fragment, container, false)
        recyclerView = rootView.id_recyclerView

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        raceAdapter = RaceAdapter(activity!!.id_container, activity!!)
        raceAdapter.setClickListener(this)
        raceAdapter.setLongClickListener(this)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = raceAdapter

        val touchHelper = TouchHelper(context!!, raceAdapter)
        val itemTouchHelper = ItemTouchHelper(touchHelper)

        raceAdapter.setTouchHelper(itemTouchHelper)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        (activity?.id_toolbar)?.title = getString(R.string.title_race_reminder)

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
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: EventBus">
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onMessageEvent(delete: DeleteMessage) {
        raceViewModel.delete(delete.theRace)
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Interface ISelect.ItemSelect">
    /**
     * Interface ISelect.ItemSelect
     */
    override fun onItemSelect(lPos: Int) {
        // callback to the Activity with the selected Race object's id.
        // TBA - use EventBus ?
        (activity as IRace.IRaceSelect).onRaceSelect(raceAdapter.getRace(lPos).id!!)
    }

    override fun onItemLongSelect(lPos: Int) {
        (activity as IRace.IRaceLongSelect).onRaceLongSelect(raceAdapter.getRace(lPos).id!!)
    }
    //</editor-fold>

    private fun klunkyForceChange() {
        val race = Race("B","R","1","1","00:00")
        race.archvRace = "Y"
        raceViewModel.insert(race)
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Private vars.">
    private lateinit var rootView: View
    private lateinit var raceAdapter: RaceAdapter
    private lateinit var raceViewModel: RaceViewModel
    private lateinit var recyclerView: RecyclerView
    //</editor-fold>
}

