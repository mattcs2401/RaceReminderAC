package mcssoft.com.racereminderac.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley
import mcssoft.com.racereminderac.interfaces.IDownload

class NetworkManager (val context: Context) : IDownload, Response.ErrorListener, Response.Listener<String> {

    private val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val isNetworkConnected : Boolean
        get() = connMgr.activeNetworkInfo.isConnected

    val isNetworkActive : Boolean
        get() = connMgr.isDefaultNetworkActive

    val activeNetworkInfo: NetworkInfo
        get() = connMgr.activeNetworkInfo

    fun <T> addToRequestQueue(request: Request<T>) = requestQueue.add(request)

    //<editor-fold default state="collapsed" desc="Region: Volley response">
    // Error response listener.
    override fun onErrorResponse(error: VolleyError?) {
        // TODO - maybe some sort of Volley error message dialog ?
        volleyError = error?.message
        Toast.makeText(context, error?.message, Toast.LENGTH_SHORT).show()
    }

    // Volley response listener.
    override fun onResponse(response: String?) {
        volleyRespnse = response
        Toast.makeText(context, "Volley download success.", Toast.LENGTH_SHORT).show()
    }
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: IDownload">
    override fun onDownload(): String = volleyRespnse!!

    override fun onDownloadError(): String = volleyError!!
    //</editor-fold>

    private var volleyRespnse: String? = null
    private var volleyError: String? = null

    private val requestQueue: RequestQueue by lazy {
        // From doco: applicationContext is key, it keeps you from leaking the Activity or
        // BroadcastReceiver if one passed one in.
        Volley.newRequestQueue(context.applicationContext)
    }

}