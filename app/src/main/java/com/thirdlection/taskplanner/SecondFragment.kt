package com.thirdlection.taskplanner

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.thirdlection.taskplanner.timeAndDataPickers.DatePickerFragment
import com.thirdlection.taskplanner.timeAndDataPickers.TimePickerFragment

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    var d = 0
    var t = 0
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        view.findViewById<TextView>(R.id.enterdate).setOnClickListener{
            d = 1
            showDatePickerDialog()
        }
        view.findViewById<TextView>(R.id.entertime).setOnClickListener{
            t = 1
            showTimePickerDialog()
        }

        view.findViewById<TextView>(R.id.enterdurdatestart).setOnClickListener{
            d = 2
            showDatePickerDialog()
        }
        view.findViewById<TextView>(R.id.enterdurdateend).setOnClickListener{
            d = 3
            showDatePickerDialog()
        }
        view.findViewById<TextView>(R.id.enterdurtimestart).setOnClickListener{
            t = 2
            showTimePickerDialog()
        }
        view.findViewById<TextView>(R.id.enterdurtimeend).setOnClickListener{
            t = 3
            showTimePickerDialog()
        }
    }

    private fun showTimePickerDialog() {
        val newFragment: DialogFragment = TimePickerFragment.newInstance(this)
        newFragment.show(requireActivity().supportFragmentManager, "timePicker")
    }
    private fun showDatePickerDialog() {
        val newFragment: DialogFragment = DatePickerFragment.newInstance(this)
        newFragment.show(requireActivity().supportFragmentManager, "datePicker")
    }

    @SuppressLint("SetTextI18n")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        when(d){
            1 -> requireView().findViewById<TextView>(R.id.enterdate).text = "$day.${month+1}.$year"
            2 -> requireView().findViewById<TextView>(R.id.enterdurdatestart).text = "$day.${month+1}.$year"
            3 -> requireView().findViewById<TextView>(R.id.enterdurdateend).text = "$day.${month+1}.$year"
        }
    }
    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        when (t) {
            1 -> requireView().findViewById<TextView>(R.id.entertime).text = "$hourOfDay:$minute"
            2 -> requireView().findViewById<TextView>(R.id.enterdurtimestart).text = "$hourOfDay:$minute"
            3 -> requireView().findViewById<TextView>(R.id.enterdurtimeend).text = "$hourOfDay:$minute"
        }
    }
}
