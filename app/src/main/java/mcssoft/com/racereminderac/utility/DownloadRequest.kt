package mcssoft.com.racereminderac.utility

import android.content.Context
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.ErrorListener
import com.android.volley.VolleyError
import java.io.ByteArrayInputStream

class DownloadRequest<List>(method: Int,
                         url: String,
                         private var context: Context,
                         private var listener: Response.Listener<List>,
                         errorListener: Response.ErrorListener) : Request<List>(method, url, errorListener) {

    // From the documentation, runs on a background worker thread.
    override fun parseNetworkResponse(response: NetworkResponse?): Response<List>? {
        val theResult: List? = null

        val parser: RaceMeetingParser = RaceMeetingParser(context)
        val inputStream = ByteArrayInputStream(response.toString().toByteArray())
        parser.parse(inputStream)
        return Response.success<List>(theResult, null)
    }

    override fun deliverResponse(response: List) = listener.onResponse(response)

    override fun deliverError(error: VolleyError): Unit = this.errorListener!!.onErrorResponse(error)

}
