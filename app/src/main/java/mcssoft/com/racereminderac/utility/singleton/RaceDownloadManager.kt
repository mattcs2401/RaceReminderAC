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
/*
    private void beginDownload(){
        File file=new File(getExternalFilesDir(null),"Dummy");
        /*
        Create a DownloadManager.Request with all the information necessary to start the download
         */
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse("http://speedtest.ftp.otenet.gr/files/test10Mb.db"))
                .setTitle("Dummy File")// Title of the Download Notification
                .setDescription("Downloading")// Description of the Download Notification
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                .setDestinationUri(Uri.fromFile(file))// Uri of the destination file
                .setRequiresCharging(false)// Set if charging is required to begin the download
                .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
        DownloadManager downloadManager= (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.
    }
 */