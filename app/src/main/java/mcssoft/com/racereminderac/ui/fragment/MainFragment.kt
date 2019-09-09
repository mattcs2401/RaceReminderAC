package mcssoft.com.racereminderac.ui.fragment

import android.app.DownloadManager
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.VISIBLE
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_fragment.view.*
import kotlinx.android.synthetic.main.toolbar_base.*
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.adapter.RaceAdapter
import mcssoft.com.racereminderac.entity.RaceDetails
import mcssoft.com.racereminderac.interfaces.IRace
import mcssoft.com.racereminderac.model.RaceViewModel
import mcssoft.com.racereminderac.observer.RaceListObserver
import mcssoft.com.racereminderac.receiver.RaceAlarmReceiver
import mcssoft.com.racereminderac.receiver.RaceReceiver
import mcssoft.com.racereminderac.utility.Constants
import mcssoft.com.racereminderac.utility.DeSerialiseRace
import mcssoft.com.racereminderac.utility.RaceUrl
import mcssoft.com.racereminderac.utility.TouchHelper
import mcssoft.com.racereminderac.utility.callback.BackPressCB
import mcssoft.com.racereminderac.utility.eventbus.*
import mcssoft.com.racereminderac.utility.singleton.DialogManager
import mcssoft.com.racereminderac.utility.singleton.NetworkManager
import mcssoft.com.racereminderac.utility.singleton.RaceAlarm
import mcssoft.com.racereminderac.utility.singleton.RacePreferences
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainFragment : Fragment(R.layout.main_fragment) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        raceReceiver = RaceReceiver()
        raceFilter = IntentFilter()
        raceFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        
        raceAlarmReceiver = RaceAlarmReceiver()
        raceAlarmFilter = IntentFilter()
    }

    //<editor-fold default state="collapsed" desc="Region: Lifecycle">
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity?.id_toolbar)?.title = getString(R.string.title_race_reminder)

        setHasOptionsMenu(true)

        processForDownload() // this should go off onto a separate thread.

        raceAdapter = RaceAdapter(activity!!.id_container, activity!!)

        recyclerView = view.id_recyclerView

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = raceAdapter

        val touchHelper = TouchHelper(raceAdapter)
        val itemTouchHelper = ItemTouchHelper(touchHelper)

        raceAdapter.setTouchHelper(itemTouchHelper)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        // If bottom nav view was previously hidden by a New or Edit etc, then show again.
        val bottomNavView = activity?.findViewById<BottomNavigationView>(R.id.id_bottom_nav_view)
        if(bottomNavView?.visibility == View.GONE) {
            bottomNavView.visibility = VISIBLE
        }

        Log.d("TAG","MainFragment.onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Set the view model and observe.
        raceViewModel = ViewModelProvider(this).get(RaceViewModel::class.java)

        raceViewModel.getAllRaces().observe(viewLifecycleOwner, RaceListObserver(raceAdapter))

        Log.d("TAG","MainFragment.onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        // EventBus registration.
        EventBus.getDefault().register(this)

        // Refresh main UI colours.
        EventBus.getDefault().post(ManualRefreshMessage())

        // Add on back pressed handler.
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressCallback)

        activity?.registerReceiver(raceReceiver, raceFilter)
        activity?.registerReceiver(raceAlarmReceiver, raceAlarmFilter)
        Log.d("TAG","MainFragment.onStart")
    }

    override fun onStop() {
        super.onStop()
        // Cancel alarm (method checks if previously cancelled).
        RaceAlarm.getInstance(activity!!).cancelAlarm()
        // EventBus unregister.
        EventBus.getDefault().unregister(this)
        // Remove back press handler callback.
        backPressCallback.removeCallback()

        Log.d("TAG","MainFragment.onStop")
    }

    override fun onDestroy() {
        activity?.unregisterReceiver(raceReceiver)
        activity?.unregisterReceiver(raceAlarmReceiver)
        super.onDestroy()
        Log.d("TAG","MainFragment.onDestroy")
    }

    /* Example: https://stablekernel.com/using-custom-views-as-menu-items/  ??*/
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)

        refreshMenuItem = menu.findItem(R.id.id_mnu_refresh_interval)
        deleteMenuItem = menu.findItem(R.id.id_mnu_delete_all)
        notifyMenuItem = menu.findItem(R.id.id_mnu_notifications)
        multiSelMenuItem = menu.findItem(R.id.id_mnu_multi_select)

        if(!raceAdapter.isEmpty()) {
            setToolbarIcons(true)
        } else {
            setToolbarIcons(false)
        }

        Log.d("TAG","MainFragment.onCreateOptionsMenu")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /* Note: Delete all is Preference controlled (icon may not be displayed). */
        when(item.itemId) {
            R.id.id_mnu_delete_all -> {
                if (!raceAdapter.isEmpty()) {
                    val args = Bundle()
                    DialogManager.getInstance()?.showDialog(Constants.DIALOG_DELETE_ALL, args,
                            activity?.supportFragmentManager!!.beginTransaction())
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

        if(!raceAdapter.isEmpty()) {
            setToolbarIcons(true)
        } else {
            setToolbarIcons(false)
        }
    }

    /**
     * The refresh message.
     * @param refresh: Not actually used. Message is just a signal to refresh the display.
     */
    @Subscribe
    fun onMessageEvent(refresh: ManualRefreshMessage) {
        /*
         Note: The down side to this is that the observer will react twice.
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

    /**
     * Called from the RaceViewHolder.onClick method.
     * @param select: Details of the selected Race. See SelectMessage notes.
     */
    @Subscribe
    fun onMessageEvent(select: SelectMessage) {
        val race = raceAdapter.getRace(select.getPos)
        val values = arrayOf(race.raceSel, race.raceSel2, race.raceSel3, race.raceSel4)

        when(select.getSelType) {
            Constants.ITEM_SELECT -> {
                // Used for Edit function.
                (activity as IRace.IRaceSelect).onRaceSelect(race.id!!, values)
            }
            Constants.ITEM_LONG_SELECT -> {
                // User for Copy function.
                (activity as IRace.IRaceLongSelect).onRaceLongSelect(race.id!!, values)
            }
        }
    }

    /**
     * The delete all message.
     * @param delete: Not actually used. Message is just a signal to delete all displayed.
     */
    @Subscribe
    fun onMessageEvent(delete: DeleteAllMessage) {
        // Hide toolbar icons.
        setToolbarIcons(false)
        // Remove all items from adapter.
        raceViewModel.deleteAll()
    }

    /**
     * Called from the RaceViewHolder.onClick method.
     * @param update: Details of the Race to be updated. See UpdateMessage notes.
     */
    @Subscribe
    fun onMessageEvent(update: UpdateMessage) {
        val race: RaceDetails = raceAdapter.getRace(update.pos)
        when(update.update) {
            R.id.id_cb_bet_placed -> {
                race.betPlaced = update.value as Boolean
            }
        }
        raceViewModel.update(race)
    }

    /**
     * Called by the RaceAdapter.swapData method.
     * @param data: Basically a boolean indicating whether the adapter has data or not.
     */
    @Subscribe
    fun onMessageEvent(data: DataMessage) = if(data.getIsEmpty) {
        // No items in the adapter.
        setToolbarIcons(false)
    } else {
        setToolbarIcons(true)
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility - Toolbar.">
    private fun setToolbarIcons(setIcon: Boolean) {
        setRefreshIntervalMenuItem(setIcon)
        setDeleteMenuItem(setIcon)
        setNotifyMenuItem(setIcon)
        setMultiSelMenuItem(setIcon)
    }

    /**
     * ToolBar: Delete (all) option.
     */
    private fun setDeleteMenuItem(doSetDelete: Boolean) {
        if(doSetDelete) {
            if (RacePreferences.getInstance(activity!!).getRaceBulkDelete()) {
                // The Delete all preference is enabled.
                deleteMenuItem.isVisible = true
            }
        } else {
            deleteMenuItem.isVisible = false
        }
    }

    /**
     * Toolbar: Notifications being sent indicator.
     */
    private fun setNotifyMenuItem(doSetNotify: Boolean) {
        if(doSetNotify) {
            if (RacePreferences.getInstance(activity!!).getRaceNotifyPost()) {
                // The notifications preference is enabled.
                notifyMenuItem.isVisible = true
            }
        } else {
            notifyMenuItem.isVisible = false
        }
    }

    /**
     * Toolbar. Multi Select is set indicator.
     */
    private fun setMultiSelMenuItem(doSetMulti: Boolean) {
        if(doSetMulti) {
            if(RacePreferences.getInstance(activity!!).getRaceMultiSelect()) {
                multiSelMenuItem.isVisible = true
            }
        } else {
            multiSelMenuItem.isVisible = false
        }
    }

    /**
     * ToolBar: Refresh interval indicator.
     * @Note: Set by the DataMessage EventBus type (from RaceAdapter swapData() method).
     */
    private fun setRefreshIntervalMenuItem(doSetRefresh: Boolean) {
        if(doSetRefresh) {
            if (RacePreferences.getInstance(activity!!).getRefreshInterval()) {
                // Preference is set.
                refreshMenuItem.isVisible = true
                val interval = RacePreferences.getInstance(activity!!).getRefreshIntervalVal()
                val rootView = refreshMenuItem.actionView as FrameLayout
                val redCircle = rootView.findViewById<FrameLayout>(R.id.id_view_refresh_red_circle)
                val intervalTextView = rootView.findViewById<TextView>(R.id.id_tv_refresh_period)
                intervalTextView.text = interval.toString()
                redCircle.visibility = VISIBLE

                // Set alarm.
                RaceAlarm.getInstance(activity!!).setAlarm(interval.toLong())
            }
        } else {
            refreshMenuItem.isVisible = false
            // Cancel alarm.
            RaceAlarm.getInstance(activity!!).cancelAlarm()
        }
    }
    //</editor-fold>

    /**
     * Utility method that kicks off the parsing of the RaceDetails info returned in the arguments
     * from the EditFragment to the MainFragment.
     * EditFragment has done the checks; (1) a network connection exists, (2) use of a network
     * connection is enabled in the preferences, (3) only for New/Copy.
     */
    private fun processForDownload() {
        if (arguments != null && arguments!!.containsKey("edit_arguments_key")) {
            // RaceDetails values as a String (from EditFragment passed in Navigation args).
            val details = arguments?.getString("edit_arguments_key")
            // Convert values in RaceDetails object
            val dsRace = DeSerialiseRace()
            val raceDetails = dsRace.getRaceDetails(details!!)
            // Create Race meeting Url.
            val raceUrl = RaceUrl(activity!!)
            // Queue for download by RaceDownloadManager
            NetworkManager.getInstance(activity!!).queueRequest(raceUrl.constructRaceUrl(raceDetails!!),
                    raceDetails.meetingCodeNum())
        }
    }

    //<editor-fold default state="collapsed" desc="Region: Private vars.">
    private lateinit var refreshMenuItem: MenuItem
    private lateinit var deleteMenuItem: MenuItem
    private lateinit var notifyMenuItem: MenuItem
    private lateinit var multiSelMenuItem: MenuItem

    private lateinit var raceAdapter: RaceAdapter
    private lateinit var raceViewModel: RaceViewModel
    private lateinit var recyclerView: RecyclerView

    private lateinit var raceReceiver: RaceReceiver
    private lateinit var raceFilter: IntentFilter
    private lateinit var raceAlarmReceiver: RaceAlarmReceiver   // TBA
    private lateinit var raceAlarmFilter: IntentFilter          // TBA

    private var backPressCallback = BackPressCB(true)
    //</editor-fold>
}

