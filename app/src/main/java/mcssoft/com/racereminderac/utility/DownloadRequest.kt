package mcssoft.com.racereminderac.utility

import android.content.Context
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.ErrorListener
import com.android.volley.VolleyError

class DownloadRequest<List>(method: Int,
                         url: String,
                         /*private var context: Context,*/
                         private var listener: Response.Listener<List>,
                         errorListener: Response.ErrorListener) : Request<List>(method, url, errorListener) {

    //    private var errListener: Response.ErrorListener = errorListener     // error listener callback.

    // From the documentation, runs on a background worker thread.
    override fun parseNetworkResponse(response: NetworkResponse?): Response<List>? {
        val theResult: List? = null

        return Response.success<List>(theResult, null)
    }

    override fun deliverResponse(response: List) {
        listener.onResponse(response)
    }

    override fun deliverError(error: VolleyError) {
        this.errorListener?.onErrorResponse(error)
    }


}
