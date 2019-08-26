package mcssoft.com.racereminderac.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.Volley
import mcssoft.com.racereminderac.interfaces.IDownload

class NetworkManager (val context: Context) : IDownload, Response.ErrorListener, Response.Listener<String> {

    private val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

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

//    val activeNetworkInfo: NetworkInfo?
//        get() = connMgr.activeNetworkInfo

//    val allNetworks: Array<out Network>?
//        get() = connMgr.getAllNetworks()

    private val networkCapabilities: NetworkCapabilities
        get() = connMgr.getNetworkCapabilities(connMgr.activeNetwork)

    fun <T> addToRequestQueue(request: Request<T>) = requestQueue.add(request)

    fun queueRequest(url: String) {
        val request = DownloadRequest(url, this)
        addToRequestQueue(request)
    }

    //<editor-fold default state="collapsed" desc="Region: Volley response">
    // Error response listener.
    override fun onErrorResponse(error: VolleyError) {
        // TODO - maybe some sort of Volley error message dialog ?
        volleyError = error.message
        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
    }

    // Volley response listener.
    override fun onResponse(response: String) {
        volleyError = null        // we'll assume because there is a response then there's no error.
        volleyRespnse = response
        Toast.makeText(context, "Volley download success.", Toast.LENGTH_SHORT).show()
    }
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: IDownload">
    override fun onDownload(): String = volleyRespnse!!

    override fun onDownloadError(): String = volleyError!!
    //</editor-fold>

    class DownloadRequest(url: String, listener: Response.ErrorListener) : Request<String>(url, listener) {
        override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
            // TBA
            return Response.success(response?.data.toString(), null)
        }

        override fun deliverResponse(response: String?) {
            // TBA
            val bp = ""
        }
    }

    private var volleyRespnse: String? = null
    private var volleyError: String? = null

    private val requestQueue: RequestQueue by lazy {
        // From doco: applicationContext is key, it keeps you from leaking the Activity or
        // BroadcastReceiver if one passed one in.
        Volley.newRequestQueue(context.applicationContext)
    }

}