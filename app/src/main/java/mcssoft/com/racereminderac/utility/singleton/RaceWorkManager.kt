package mcssoft.com.racereminderac.utility.singleton

import android.content.Context
import androidx.work.WorkManager
import mcssoft.com.racereminderac.utility.singleton.base.SingletonBase

/**
 * Wrapper class for the WorkManager.
 */
class RaceWorkManager private constructor (private val context: Context) {

    private val workManager = WorkManager.getInstance()

    companion object : SingletonBase<RaceWorkManager, Context>(::RaceWorkManager)


}