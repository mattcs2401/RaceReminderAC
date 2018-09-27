package mcssoft.com.racereminderac.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*
import kotlinx.android.synthetic.main.toolbar_base.*
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.adapter.RaceAdapter
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.interfaces.IClick
import mcssoft.com.racereminderac.interfaces.IRace
import mcssoft.com.racereminderac.model.RaceViewModel

class MainFragment : Fragment(), IClick.ItemSelect {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        rootView = inflater.inflate(R.layout.main_fragment, container, false)

        recyclerView = rootView.id_recyclerView

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        raceAdapter = RaceAdapter(this.context!!)
        raceAdapter.setClickListener(this)

        recyclerView.adapter = raceAdapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
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
            raceAdapter.swapData(races)
        })

//        raceViewModel.getAllRaces().observe(activity!!, RaceListObserver(raceAdapter)) //raceObserver)

    }

    override fun onItemSelect(lPos: Int) {
        // callback to the Activity with the selected Race object
        (activity as IRace.IRaceSelect).onRaceSelect(raceAdapter.getRace(lPos).id!!)
//        Snackbar.make(rootView, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
    }

    private lateinit var rootView: View
    private lateinit var raceAdapter: RaceAdapter
    private lateinit var raceViewModel: RaceViewModel
    private lateinit var recyclerView: RecyclerView

}

