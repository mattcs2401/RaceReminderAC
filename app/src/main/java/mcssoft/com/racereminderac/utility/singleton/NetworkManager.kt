package mcssoft.com.racereminderac.utility.singleton

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.widget.Toast
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import mcssoft.com.racereminderac.background.worker.XmlParseWorker
import mcssoft.com.racereminderac.interfaces.IDownload
import mcssoft.com.racereminderac.utility.Constants
import mcssoft.com.racereminderac.utility.RaceMeetingParser
import mcssoft.com.racereminderac.utility.singleton.base.SingletonBase
import java.io.ByteArrayInputStream

class NetworkManager private constructor (private val context: Context) : IDownload, Response.ErrorListener, Response.Listener<String> {

    private val connMgr: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    companion object : SingletonBase<NetworkManager, Context>(::NetworkManager)

    fun queueRequest(raceUrl: String, name: String) {
        val raceDLMgr = RaceDownloadManager.getInstance(context).downLoad(raceUrl, name)

        val bp = name
//        val stringRequest = StringRequest(Request.Method.GET, raceUrl, this, this)
//        DownloadRequestQueue.getInstance(context).addToRequestQueue(stringRequest)
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
    // Error error response listener.
    override fun onErrorResponse(error: VolleyError) {
        // TODO - maybe some sort of Volley error message dialog ?
        volleyError = error.message
        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
    }

    // Volley response listener.
    /**
     * Volley response listener.
     * @param response: The result of the download.
     * Note: Returns on main thread so need to off load the Xml parsing of the response.
     */
    override fun onResponse(response: String) {
        // Note: Need to trim off the first few characters of the response. Throws an exception
        // otherwise:
        // Caused by: XmlPullParserException: Unexpected token (position:TEXT Ã¯Â»¿@1:6 in
        // java.io.InputStreamReader
        val response: String = response.substring(3)

        RaceWorkManager.getInstance(context).processRaceDetails(response)

        volleyError = null        // we'll assume because there is a response then there's no error.
        volleyResponse = response

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