package com.thirdlection.taskplanner.timeAndDataPickers

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment() {
    private lateinit var dateListener: DatePickerDialog.OnDateSetListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireContext(), dateListener, year, month, day)
    }

    fun setDateSetListener(listener: DatePickerDialog.OnDateSetListener) {
        dateListener = listener
    }

    companion object {
        fun newInstance(listener: DatePickerDialog.OnDateSetListener): DatePickerFragment {
            val instance = DatePickerFragment()
            instance.setDateSetListener(listener)
            return instance
        }
    }
}
