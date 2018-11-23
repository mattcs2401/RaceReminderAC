package mcssoft.com.racereminderac.ui.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.PeriodicWorkRequest
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_fragment.view.*
import kotlinx.android.synthetic.main.toolbar_base.*
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.adapter.RaceAdapter
import mcssoft.com.racereminderac.interfaces.IClick
import mcssoft.com.racereminderac.interfaces.IRace
import mcssoft.com.racereminderac.model.RaceListObserver
import mcssoft.com.racereminderac.model.RaceViewModel
import mcssoft.com.racereminderac.utility.eventbus.TimeMessage
import mcssoft.com.racereminderac.utility.eventbus.RemoveMessage
import mcssoft.com.racereminderac.utility.TouchHelper
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainFragment : Fragment(), IClick.ItemSelect {//}, IDelete {

    //<editor-fold defaultstate="collapsed" desc="Region: Lifecycle">
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        rootView = inflater.inflate(R.layout.main_fragment, container, false)

        recyclerView = rootView.id_recyclerView

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = this.context

        raceAdapter = RaceAdapter(activity!!.id_container, this)
        raceAdapter.setClickListener(this)


        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = raceAdapter

        val touchHelper = TouchHelper(context!!, raceAdapter)
        val itemTouchHelper = ItemTouchHelper(touchHelper)

        raceAdapter.setTouchHelper(itemTouchHelper)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        (activity?.id_toolbar)?.title = getString(R.string.title_race_reminder)

        // If FAB was previously hidden by a New or Edit etc, then show again.
        val fab = activity?.id_fab as FloatingActionButton
        if(fab.isOrWillBeHidden) {
            fab.show()
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
    /**
     * EventBus returns here.
     * @param time - The EventBus message object.
     */
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onMessageEvent(time: TimeMessage) {
        if(!time.message.isBlank()) {
//            doOnMessageEvent(dialog)
        } else {
            // Nothing was selected in the dialog except for the OK button.
//            doSnackbar(dialog.ident, dialog.ctx)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onMessageEvent(remove: RemoveMessage) {
        raceViewModel.delete(remove.theRace)
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Interface IClick.ItemSelect">
    /**
     * Interface IClick.ItemSelect
     */
    override fun onItemSelect(view: View, lPos: Int) {
        // callback to the Activity with the selected Race object
        // TBA - use EventBus ?
        (activity as IRace.IRaceSelect).onRaceSelect(raceAdapter.getRace(lPos).id!!)
    }
    //</editor-fold>

//    override fun onDelete(race: Race) {
//        raceViewModel.delete(race)
//    }

    private lateinit var rootView: View
    private lateinit var raceAdapter: RaceAdapter
    private lateinit var raceViewModel: RaceViewModel
    private lateinit var recyclerView: RecyclerView

    private lateinit var notifyWork: PeriodicWorkRequest

}

