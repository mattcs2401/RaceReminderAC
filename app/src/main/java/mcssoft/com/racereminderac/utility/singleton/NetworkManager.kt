package mcssoft.com.racereminderac.utility.singleton

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.Volley
import mcssoft.com.racereminderac.interfaces.IDownload
import mcssoft.com.racereminderac.utility.Constants
import mcssoft.com.racereminderac.utility.DownloadRequest
import mcssoft.com.racereminderac.utility.singleton.base.SingletonBase
import java.lang.Exception

class NetworkManager private constructor (private val context: Context) : IDownload, Response.ErrorListener, Response.Listener<List<*>> {

    private val connMgr: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    companion object : SingletonBase<NetworkManager, Context>(::NetworkManager)

    fun queueRequest(raceUrl: String) {
        val downloadRequest = DownloadRequest(Request.Method.GET, raceUrl, this, this)
        DownloadRequestQueue.getInstance(context).addToRequestQueue(downloadRequest)
    }

    /**
     * Get if a network connection exists.
     * @return True if connection exists, else false.
     */
    fun isNetworkConnected(): Boolean {
        return if(connMgr.activeNetworkInfo != null) {
            connMgr.activeNetworkInfo.isConnected
        } else {
            false
        }
    }

    /**
     * Get the network transport type.
     * @return Type as : NETWORK_MOB, NETWORK_WIFI or NETWORK_NONE.
     * Note: If WiFi and Mob are both selected, then WiFi seems to take preference on the system.
     *       That's possibly ok ?
     */
    fun getTransport() : Int {
        if(isNetworkConnected()) {
            if (networkCapabilities.hasTransport(TRANSPORT_CELLULAR)) {
                return Constants.NETWORK_MOB
            } else if (networkCapabilities.hasTransport(TRANSPORT_WIFI)) {
                return Constants.NETWORK_WIFI
            }
        }
        return Constants.NETWORK_NONE
    }

//    val isNetworkActive : Boolean
//        get() = connMgr.isDefaultNetworkActive
//
//    val activeNetworkInfo: NetworkInfo?
//        get() = connMgr.activeNetworkInfo
//
//    val allNetworks: Array<out Network>?
//        get() = connMgr.getAllNetworks()

    //<editor-fold default state="collapsed" desc="Region: Volley response">
    // Error response listener.
    override fun onErrorResponse(error: VolleyError) {
        // TODO - maybe some sort of Volley error message dialog ?
        volleyError = error.message
        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
    }

    // Volley response listener.
    override fun onResponse(response: List<*>?) {
        volleyError = null        // we'll assume because there is a response then there's no error.
        volleyResponse = response.toString()
        Toast.makeText(context, "Volley download success.", Toast.LENGTH_SHORT).show()
    }
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: IDownload">
    override fun onDownload(): String = volleyResponse!!

    override fun onDownloadError(): String = volleyError!!
    //</editor-fold>

    private val networkCapabilities: NetworkCapabilities
        get() = connMgr.getNetworkCapabilities(connMgr.activeNetwork)

    private var volleyResponse: String? = null
    private var volleyError: String? = null

}