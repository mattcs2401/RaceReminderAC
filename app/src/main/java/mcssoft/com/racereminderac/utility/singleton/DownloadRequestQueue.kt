package mcssoft.com.racereminderac.utility.singleton

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import mcssoft.com.racereminderac.utility.singleton.base.SingletonBase

class DownloadRequestQueue private constructor (private val context: Context) {

    private val requestQueue = Volley.newRequestQueue(context)

    companion object : SingletonBase<DownloadRequestQueue, Context>(::DownloadRequestQueue)

    fun <T> addToRequestQueue(request: Request<T>) {
        requestQueue.add(request)
    }

    fun cancelAll() {
        requestQueue?.cancelAll(null)
    }

}