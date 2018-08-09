package mcssoft.com.racereminderac.ui.main

import android.app.Application
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
import com.google.android.material.snackbar.Snackbar
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.adapter.RaceAdapter
import mcssoft.com.racereminderac.interfaces.IClick
import mcssoft.com.racereminderac.model.RaceViewModel

class MainFragment : Fragment(), IClick.ItemClick {

    companion object {
        //fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        rootView = inflater.inflate(R.layout.main_fragment, container, false)

        recyclerView = rootView.findViewById(R.id.id_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        raceAdapter = RaceAdapter(this.context!!)
        raceAdapter.setClickListener(this)

        recyclerView.adapter = raceAdapter

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.findViewById<Toolbar>(R.id.id_toolbar)?.title = "Race Reminder"

        // If FAB was previously hidden by a New or Edit etc, then show again.
        val fab = activity?.findViewById(R.id.id_fab) as FloatingActionButton
        if(fab.isOrWillBeHidden) {
            fab.show()
        }

        // Set the view model.
        raceViewModel = ViewModelProviders.of(this.activity!!).get(RaceViewModel::class.java)

        raceViewModel!!.getAllRaces().observe(this, Observer { races ->
            raceAdapter.swapData(races)
        })

    }

    override fun onItemClick(view: View, lPos: Int) {
        // TODO - callback to the Activity to launch an Edit ??
        Snackbar.make(view, "TODO, launch an Edit", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
    }

    private lateinit var rootView: View
    private lateinit var raceAdapter: RaceAdapter
    private var raceViewModel: RaceViewModel? = null
    private lateinit var recyclerView: RecyclerView

}

