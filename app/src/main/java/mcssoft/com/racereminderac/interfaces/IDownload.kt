package mcssoft.com.racereminderac.interfaces

interface IDownload {

    /**
     * Get the result of the network download.
     * @return The result of the network download.
     */
    fun onDownload() : String

    /**
     * Get the network download errorr message.
     * @return The network error message.
     */
    fun onDownloadError() : String
}