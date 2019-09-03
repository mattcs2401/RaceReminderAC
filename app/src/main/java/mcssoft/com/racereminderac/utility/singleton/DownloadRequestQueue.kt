package mcssoft.com.racereminderac.utility.singleton

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import mcssoft.com.racereminderac.utility.singleton.base.SingletonBase

class DownloadRequestQueue private constructor (private val context: Context) {

    companion object : SingletonBase<DownloadRequestQueue, Context>(::DownloadRequestQueue)

    fun <T> addToRequestQueue(request: Request<T>) {
        requestQueue.add(request)
    }

    fun cancelAll() {
        requestQueue.cancelAll(null)
    }

    private val requestQueue: RequestQueue by lazy {
        // From the documentation:
        // applicationContext is key, it keeps you from leaking the Activity or BroadcastReceiver if one passed one in.
        Volley.newRequestQueue(context.applicationContext)
    }

}