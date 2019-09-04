package mcssoft.com.racereminderac.utility.singleton

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.ParcelFileDescriptor
import mcssoft.com.racereminderac.utility.singleton.base.SingletonBase
import java.io.File

class RaceDownloadManager private constructor (private val context: Context) {

    companion object : SingletonBase<RaceDownloadManager, Context>(::RaceDownloadManager)

    val dlMgr = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager

    // TODO - file location ? and make private to app ?

    /**
     * Download a page from tatts.com with the given url.
     * @param url: The page base url.
     * @param name: The page.
     * Note: The page is saved in local storage with the filename "name".xml
     */
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
        val dlId = dlMgr.enqueue(dlRequest)
    }

//    /**
//     * Get downloaded file from the local storage.
//     * @param name The file (page) name less the base url.
//     * @return A ByteArray.
//     * Note: The name given is translated to a filename of "name".xml
//     */
//    fun getFile(name: String): ByteArray {
//        val stream = context.openFileInput("$name.xml")
//        return stream.toString().toByteArray()
//    }

    /**
     * Get downloaded file from the local storage.
     * @param id: The downloaded file id as returned in DownloadManager.EXTRA_DOWNLOAD_ID.
     * @return A ByteArray.
     */
    fun getFile(id: Long): String {
        val file = dlMgr.openDownloadedFile(id)
        val inStream = ParcelFileDescriptor.AutoCloseInputStream(file)
        return inStream.readBytes().contentToString()
    }

    /**
     * Remove the file with the given id.
     * @param ids: The file id.
     * From the documentation:
     * Cancel downloads and remove them from the download manager. Each download will be stopped if
     * it was running, and it will no longer be accessible through the download manager. If there is
     * a downloaded file, partial or complete, it is deleted.
     */
    fun removeFile(id: Long): Int {
        return dlMgr.remove(id)
    }
}
