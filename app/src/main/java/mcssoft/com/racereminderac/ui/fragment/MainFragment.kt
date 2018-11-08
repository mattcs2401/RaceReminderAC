package mcssoft.com.racereminderac.ui.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_fragment.view.*
import kotlinx.android.synthetic.main.toolbar_base.*
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.adapter.RaceAdapter
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.interfaces.IClick
import mcssoft.com.racereminderac.interfaces.IRace
import mcssoft.com.racereminderac.model.RaceViewModel
import mcssoft.com.racereminderac.utility.DialogMessage
import mcssoft.com.racereminderac.utility.TouchHelper
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainFragment : Fragment(), IClick.ItemSelect {

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

        raceAdapter = RaceAdapter(activity!!.id_container)
        raceAdapter.setClickListener(this)


        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = raceAdapter
//        recyclerView.setHasFixedSize(true)

        val touchHelper = TouchHelper(context!!, raceAdapter)
        val itemTouchHelper = ItemTouchHelper(touchHelper)

        raceAdapter.setTouchHelper(itemTouchHelper)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity?.id_toolbar)?.title = "Race Reminder"

        // If FAB was previously hidden by a New or Edit etc, then show again.
        val fab = activity?.id_fab as FloatingActionButton
        if(fab.isOrWillBeHidden) {
            fab.show()
        }
        // Set the view model.
        raceViewModel = ViewModelProviders.of(activity!!).get(RaceViewModel::class.java)

        raceViewModel.getAllRaces().observe(activity!!, Observer<List<Race>> { races ->
            raceAdapter.swapData(races as ArrayList<Race>)
        })
    }
    //</editor-fold>

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    /**
     * EventBus returns here.
     * @param dialog - The EventBus message object.
     */
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onMessageEvent(dialog: DialogMessage) {
        if(!dialog.message.isBlank()) {
//            doOnMessageEvent(dialog)
        } else {
            // Nothing was selected in the dialog except for the OK button.
//            doSnackbar(dialog.ident, dialog.ctx)
        }
    }

    /**
     * Interface IClick.ItemSelect
     */
    override fun onItemSelect(view: View, lPos: Int) {
        // callback to the Activity with the selected Race object
        // TBA - use EventBus ?
        (activity as IRace.IRaceSelect).onRaceSelect(raceAdapter.getRace(lPos).id!!)

        // This is for the alternate layout (refer race_row2.xml)
//        when(view.id) {
//            R.id.id_recycler_layout -> {
//                (activity as IRace.IRaceSelect).onRaceSelect(raceAdapter.getRace(lPos).id!!)
//            }
//            R.id.id_ib_delete -> {
//                raceViewModel.delete(raceAdapter.getRace(lPos))
//
//            }
//        }
    }

    private lateinit var rootView: View
    private lateinit var raceAdapter: RaceAdapter
    private lateinit var raceViewModel: RaceViewModel
    private lateinit var recyclerView: RecyclerView

}

