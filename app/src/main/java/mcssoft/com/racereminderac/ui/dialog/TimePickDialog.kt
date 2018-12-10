package mcssoft.com.racereminderac.ui.dialog

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import mcssoft.com.racereminderac.utility.RaceTime
import mcssoft.com.racereminderac.utility.eventbus.TimeMessage
import org.greenrobot.eventbus.EventBus
import java.util.*

class TimePickDialog : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var hour: Int = -1
        var minute: Int = -1
        val time = arguments?.getString("key")?.split(":")

        if(time != null) {
            // Use the values passed in the arguments.
            hour = time[0].toInt()
            minute = time[1].toInt()
        } else {
            // Use the current time as the default values for the picker
            val calendar = Calendar.getInstance()
            hour = calendar.get(Calendar.HOUR_OF_DAY)
            minute = calendar.get(Calendar.MINUTE)
        }

        // A time picker with 24 hour format.
        return TimePickerDialog(activity, this, hour, minute, true)
    }

    /**
     * TimePickerDialog.OnTimeSetListener
     */
    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        val raceTime = RaceTime.getInstance().timeToMillis(hourOfDay, minute)
        EventBus.getDefault().post(TimeMessage(raceTime))
        this.dialog.cancel()
    }
}