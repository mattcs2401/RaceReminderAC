package mcssoft.com.racereminderac.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley

class NetworkManager constructor(context: Context) : Response.ErrorListener, Response.Listener<String> {

    companion object {
        @Volatile
        private var INSTANCE: NetworkManager? = null

        fun getInstance(context: Context) =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: NetworkManager(context).also {
                        INSTANCE = it
                    }
                }
    }

    // Volley error response listener.
    override fun onErrorResponse(error: VolleyError?) {
        // TBA
    }

    // Volley response listener.
    override fun onResponse(response: String?) {
        // TBA
    }

    fun <T> addToRequestQueue(request: Request<T>) {
        requestQueue.add(request)
    }

    private val requestQueue: RequestQueue by lazy {
        // From doco:
        // applicationContext is key, it keeps you from leaking the Activity or BroadcastReceiver if one passed one in.
        Volley.newRequestQueue(context.applicationContext)
    }

}