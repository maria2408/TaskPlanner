package com.thirdlection.taskplanner.timeAndDataPickers

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment: DialogFragment() {
    private lateinit var timeListener: TimePickerDialog.OnTimeSetListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        // Create a new instance of DatePickerDialog and return it
        return TimePickerDialog(requireContext(), timeListener, hour, minute, true)
    }

    fun setTimeSetListener(listener: TimePickerDialog.OnTimeSetListener){
        timeListener = listener
    }

    companion object{
        fun newInstance(listener: TimePickerDialog.OnTimeSetListener): TimePickerFragment {
            val instance = TimePickerFragment()
            instance.setTimeSetListener(listener)
            return  instance
        }
    }
}
