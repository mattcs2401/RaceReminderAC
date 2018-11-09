package mcssoft.com.racereminderac.ui.dialog

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.utility.eventbus.TimeMessage
import org.greenrobot.eventbus.EventBus
import java.util.*

class TimePickDialog : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        time = arguments?.getString("key")?.split(":")

        if(time != null) {
            // Use the values passed in the arguments.
            hour = time!![0].toInt()
            minute = time!![1].toInt()
        } else {
            // Use the current time as the default values for the picker
            val calendar = Calendar.getInstance()
            hour = calendar.get(Calendar.HOUR_OF_DAY)
            minute = calendar.get(Calendar.MINUTE)
        }

        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    /**
     * TimePickerDialog.OnTimeSetListener
     */
    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        val time = hourOfDay.toString() + ":" + minute.toString()
        EventBus.getDefault().post(TimeMessage(time, R.integer.time_pick_dialog_id, -1))
        this.dialog.cancel()
    }

    var time: List<String>? = null
    var hour: Int = -1
    var minute: Int = -1

}