package mcssoft.com.racereminderac.utility.singleton

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import mcssoft.com.racereminderac.utility.singleton.base.SingletonBase
import java.io.File

class RaceDownloadManager private constructor (private val context: Context) {

    companion object : SingletonBase<RaceDownloadManager, Context>(::RaceDownloadManager)

    fun downLoad(url: String, name: String) {
        val file: File = File(context.getExternalFilesDir(null), "$name.xml")
        val dlRequest = DownloadManager.Request(Uri.parse(url))
                .setTitle("$name Downloading")
                .setDescription("Downloading details for $name")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setDestinationUri(Uri.fromFile(file))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val dlMgr = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val dlId = dlMgr.enqueue(dlRequest)
    }

}
