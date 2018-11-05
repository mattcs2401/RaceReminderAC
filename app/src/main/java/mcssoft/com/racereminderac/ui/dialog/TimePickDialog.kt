package mcssoft.com.racereminderac.ui.dialog

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.utility.DialogMessage
import org.greenrobot.eventbus.EventBus
import java.util.Calendar

class TimePickDialog : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        var time = hourOfDay.toString() + ":" + minute.toString()
        EventBus.getDefault().post(DialogMessage(time, R.integer.time_pick_dialog_id, -1))
        this.dialog.cancel()
    }
}