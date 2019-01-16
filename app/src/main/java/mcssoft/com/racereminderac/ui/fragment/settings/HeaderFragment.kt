package mcssoft.com.racereminderac.ui.fragment.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.header_fragment.*
import mcssoft.com.racereminderac.R

/**
 * Utility type class as starting point for the preferences navigation. Basically just displays a
 * "switchboard" and the navigation swaps the applicable preference fragments.
 */
class HeaderFragment : Fragment(), View.OnClickListener {

    //<editor-fold defaultstate="collapsed" desc="Region: Lifecycle">
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.header_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvDefaults = view.findViewById(R.id.id_tv_defaults)
        tvDefaults.setOnClickListener(this)
        tvNotification = view.findViewById(R.id.id_tv_notification)
        tvNotification.setOnClickListener(this)
        tvRefresh = view.findViewById(R.id.id_tv_refresh)
        tvRefresh.setOnClickListener(this)
        tvMaintenance = view.findViewById(R.id.id_tv_maintenance)
        tvMaintenance.setOnClickListener(this)
    }
    //</editor-fold>

    override fun onClick(view: View) {
        when(view.id) {
            R.id.id_tv_defaults -> {
                view.findNavController().navigate(R.id.action_id_header_fragment_to_defaultsFragment)
            }
            R.id.id_tv_notification -> {
                view.findNavController().navigate(R.id.action_id_header_fragment_to_notificationFragment)
            }
            R.id.id_tv_refresh -> {
                view.findNavController().navigate(R.id.action_id_header_fragment_to_refreshFragment)
            }
            R.id.id_tv_maintenance -> {
                view.findNavController().navigate(R.id.action_id_header_fragment_to_maintenanceFragment)
            }
        }
    }

    private lateinit var tvDefaults: TextView
    private lateinit var tvNotification: TextView
    private lateinit var tvRefresh: TextView
    private lateinit var tvMaintenance: TextView

}

