package mcssoft.com.racereminderac.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.model.RaceViewModel

class EditFragment : Fragment() {

    companion object {
        //fun newInstance() = EditFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.edit_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO - this will depend on what the fragment is used for, e.g. New, Edit etc
        (activity?.findViewById(R.id.id_toolbar) as Toolbar).title = "New Race"

        // Hide the FAB.
        (activity?.findViewById(R.id.id_fab) as FloatingActionButton).hide()

        viewModel = ViewModelProviders.of(this).get(RaceViewModel::class.java)
        // TODO: Use the ViewModel

        var race = arguments?.getParcelable<Race>(getString(R.string.key_edit_existing))
    }

    private lateinit var viewModel: RaceViewModel

}
