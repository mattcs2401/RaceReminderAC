package mcssoft.com.racereminderac.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley

class NetworkManager (val context: Context) : Response.ErrorListener, Response.Listener<String>, ConnectivityManager.OnNetworkActiveListener {

    private val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

//    companion object {
//        @Volatile
//        private var INSTANCE: NetworkManager? = null
//
//        fun getInstance() =
//                INSTANCE ?: synchronized(this) {
//                    INSTANCE ?: NetworkManager().also {
//                        INSTANCE = it
//                    }
//                }
//    }

    fun networkConnected() : Boolean = connMgr.activeNetworkInfo.isConnected

    // Volley error response listener.
    override fun onErrorResponse(error: VolleyError?) {
        // TBA
        val bp = ""
    }

    // Volley response listener.
    override fun onResponse(response: String?) {
        // TBA
        val bp = ""
    }

    // ConnectivityManager listener.
    override fun onNetworkActive() {
        Toast.makeText(context, "Network active", Toast.LENGTH_SHORT).show()
    }

    fun <T> addToRequestQueue(request: Request<T>) {
        requestQueue.add(request)
    }

    private val requestQueue: RequestQueue by lazy {
        // From doco:
        // applicationContext is key, it keeps you from leaking the Activity or BroadcastReceiver if one passed one in.
        Volley.newRequestQueue(context.applicationContext)
    }

//    private lateinit var connMgr: ConnectivityManager
    private lateinit var networkInfo: NetworkInfo

}