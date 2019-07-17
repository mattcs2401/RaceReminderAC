package mcssoft.com.racereminderac.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.VISIBLE
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_fragment.view.id_recyclerView
import kotlinx.android.synthetic.main.toolbar_base.*
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.adapter.RaceAdapter
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.interfaces.IRace
import mcssoft.com.racereminderac.observer.RaceListObserver
import mcssoft.com.racereminderac.model.RaceViewModel
import mcssoft.com.racereminderac.ui.dialog.DeleteAllDialog
import mcssoft.com.racereminderac.utility.Constants
import mcssoft.com.racereminderac.utility.RaceAlarm
import mcssoft.com.racereminderac.utility.RacePreferences
import mcssoft.com.racereminderac.utility.TouchHelper
import mcssoft.com.racereminderac.utility.callback.BackPressCB
import mcssoft.com.racereminderac.utility.eventbus.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainFragment : Fragment() {

    //<editor-fold defaultstate="collapsed" desc="Region: Lifecycle">
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
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
        val bottomNavView = activity?.findViewById<BottomNavigationView>(R.id.id_bottom_nav_view)
        if(bottomNavView?.visibility == View.GONE) {
            bottomNavView.visibility = VISIBLE
        }

        Log.d("tag","MainFragment.onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Set the view model and observe.
        raceViewModel = ViewModelProviders.of(this).get(RaceViewModel::class.java)

        val lRaces = raceViewModel.getAllRaces()
        lRaces.observe(viewLifecycleOwner, RaceListObserver(lRaces, raceAdapter))

        Log.d("tag","MainFragment.onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        // Set alarm if set in Preferences.
        // TODO: This needs work WRT colours that display.
        // Bottom refresh menu item.
        val menuItem = activity?.id_bottom_nav_view?.menu?.findItem(R.id.id_mnu_bnv_refresh)
        if(RacePreferences.getInstance()!!.getRefreshInterval(activity!!)) {
            // alarm is set in preferences.
            val interval = RacePreferences.getInstance()!!.getRefreshIntervalVal(activity!!).toLong()
            RaceAlarm.getInstance()?.setAlarm(activity!!, interval)
            menuItem?.isVisible = false
        } else {
            RaceAlarm.getInstance()?.cancelAlarm()
            menuItem?.isVisible = true
        }

        // EventBus registration.
        EventBus.getDefault().register(this)

        // Refresh main UI colours.
        EventBus.getDefault().post(ManualRefreshMessage())

        // Add on back pressed handler.
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressCallback)

        Log.d("tag","MainFragment.onStart")
    }

//    override fun onResume() {
//        super.onResume()
//        Log.d("tag","MainFragment.onResume")
//    }

    override fun onStop() {
        super.onStop()
        // Cancel alarm (method checks if previously cancelled).
        RaceAlarm.getInstance()?.cancelAlarm()
        // EventBus unregister.
        EventBus.getDefault().unregister(this)
        // Remove back press handler callback.
        backPressCallback.removeCallback()

        Log.d("tag","MainFragment.onStop")
    }

//    override fun onPrepareOptionsMenu(menu: Menu) {
//        super.onPrepareOptionsMenu(menu)
//        Log.d("tag","MainFragment.onPrepareOptionsMenu")
//    }

    /* Example: https://stablekernel.com/using-custom-views-as-menu-items/  ??*/
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)

        setToolbarMenuItems(menu)

        Log.d("tag","MainFragment.onCreateOptionsMenu")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /* Note:
           Delete all is Preference controlled (icon may not be displayed).
         */
        when(item.itemId) {
            R.id.id_mnu_delete_all -> {
                if (!raceAdapter.isEmpty()) {
                    val dialog = DeleteAllDialog(activity!!)
                    dialog.show(activity!!.supportFragmentManager, getString(R.string.tag_delete_all_dialog))
                } else {
                    Toast.makeText(activity!!, getString(R.string.toast_nothing_to_delete), Toast.LENGTH_SHORT).show()
                }
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: EventBus">
    /**
     * The delete message.
     * @param delete: The delete message object. Contains the Race to delete.
     */
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onMessageEvent(delete: DeleteMessage) {
        raceViewModel.delete(delete.theRace)
    }

    /**
     * The refresh message.
     * @param refresh: Not actually used. Message is just a signal to refresh the display.
     */
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onMessageEvent(refresh: ManualRefreshMessage) {
        /*
         Note:
         The down side to this is the observer will react twice.
        */
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
        val race = raceAdapter.getRace(select.getPos)
        val selects = arrayOf(race.raceSel, race.raceSel2, race.raceSel3, race.raceSel4)

        when(select.getSelType) {
            Constants.ITEM_SELECT -> {
                (activity as IRace.IRaceSelect).onRaceSelect(race.id!!, selects)
            }
            Constants.ITEM_LONG_SELECT -> {
                (activity as IRace.IRaceLongSelect).onRaceLongSelect(race.id!!, selects)
            }
        }
    }

    /**
     * The delete all message.
     * @param delete: Not actually used. Message is just a signal to delete all displayed.
     */
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onMessageEvent(delete: DeleteAllMessage) {
        raceViewModel.deleteAll()
    }

    /**
     * The update message.
     * @param update: The update message object. See the UpdateMessage notes.
     */
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onMessageEvent(update: UpdateMessage) {
        val race: Race = raceAdapter.getRace(update.pos)
        when(update.update) {
            R.id.id_cb_bet_placed -> {
                race.betPlaced = update.value as Boolean
            }
        }
        raceViewModel.update(race)
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility.">
    private fun setToolbarMenuItems(menu: Menu) {
        setDeleteMenuItem(menu)
        setRefreshMenuItem(menu)
    }

    /**
     * ToolBar: Delete (all) option.
     */
    private fun setDeleteMenuItem(menu: Menu) {
        val menuItem= menu.findItem(R.id.id_mnu_delete_all)
        menuItem.isVisible = RacePreferences.getInstance()!!.getRaceBulkDelete(activity!!)
        // TODO - this needs a rethink when there are no Races to display.
    }

    /**
     * ToolBar: Refresh interval indicator.
     */
    private fun setRefreshMenuItem(menu: Menu) {
        // TODO - link this to the number of races in the list, i.e. if no Races to display,
        //        do we need the menu item to show ?
        val rootView: FrameLayout
        val menuItem= menu.findItem(R.id.id_mnu_refresh)
        val interval = RacePreferences.getInstance()!!.getRefreshIntervalVal(activity!!)

        if(RacePreferences.getInstance()!!.getRefreshInterval(activity!!) && (interval > 0)) {
            rootView = menuItem.actionView as FrameLayout
            val redCircle = rootView.findViewById<FrameLayout>(R.id.id_view_refresh_red_circle)
            val intervalTextView = rootView.findViewById<TextView>(R.id.id_tv_refresh_period)
            intervalTextView.text = interval.toString()
            redCircle.visibility = VISIBLE
        } else {
            menuItem.isVisible = false
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Private vars.">
    private lateinit var raceAdapter: RaceAdapter
    private lateinit var raceViewModel: RaceViewModel
    private lateinit var recyclerView: RecyclerView

    private var backPressCallback = BackPressCB(true)
    //</editor-fold>
}

