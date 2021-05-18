package com.thirdlection.taskplanner

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.thirdlection.taskplanner.database.DataBaseHandler
import com.thirdlection.taskplanner.database.Task
import com.thirdlection.taskplanner.timeAndDataPickers.DatePickerFragment
import com.thirdlection.taskplanner.timeAndDataPickers.TimePickerFragment

class SecondFragment :
    Fragment(),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    var d = 0
    var t = 0
    companion object {
        const val case1 = 1
        const val case2 = 2
        const val case3 = 3
        const val n = 10
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nameid = view.findViewById<EditText>(R.id.entername)
        val descid = view.findViewById<EditText>(R.id.enterdescr)
        val dDateid = view.findViewById<TextView>(R.id.enterdate)
        val dTimeid = view.findViewById<TextView>(R.id.entertime)
        val durSD = view.findViewById<TextView>(R.id.enterdurdatestart)
        val durED = view.findViewById<TextView>(R.id.enterdurdateend)
        val durST = view.findViewById<TextView>(R.id.enterdurtimestart)
        val durET = view.findViewById<TextView>(R.id.enterdurtimeend)
        val imp = view.findViewById<CheckBox>(R.id.enterImp)

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        view.findViewById<Button>(R.id.button_third).setOnClickListener {
            if (nameid.text.toString().isNotEmpty()) {
                val task = Task(
                    nameid.text.toString(),
                    descid.text.toString(),
                    dDateid.text.toString(),
                    dTimeid.text.toString(),
                    durSD.text.toString(),
                    durED.text.toString(),
                    durST.text.toString(),
                    durET.text.toString(),
                    imp.isChecked.compareTo(false)
                )
                val db = DataBaseHandler(context)
                db.insertData(task)
            } else
                Toast.makeText(context, "Заполните название задачи", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        view.findViewById<TextView>(R.id.enterdate).setOnClickListener {
            d = case1
            showDatePickerDialog()
        }
        view.findViewById<TextView>(R.id.entertime).setOnClickListener {
            t = case1
            showTimePickerDialog()
        }

        view.findViewById<TextView>(R.id.enterdurdatestart).setOnClickListener {
            d = case2
            showDatePickerDialog()
        }
        view.findViewById<TextView>(R.id.enterdurdateend).setOnClickListener {
            d = case3
            showDatePickerDialog()
        }
        view.findViewById<TextView>(R.id.enterdurtimestart).setOnClickListener {
            t = case2
            showTimePickerDialog()
        }
        view.findViewById<TextView>(R.id.enterdurtimeend).setOnClickListener {
            t = case3
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
        when (d) {
            case1 ->
                if (day < n)
                    requireView().findViewById<TextView>(R.id.enterdate).text =
                        "0$day.${month + 1}.$year"
                else requireView().findViewById<TextView>(R.id.enterdate).text =
                    "$day.${month + 1}.$year"
            case2 ->
                if (day < n)
                    requireView().findViewById<TextView>(R.id.enterdurdatestart).text =
                        "0$day.${month + 1}.$year"
                else requireView().findViewById<TextView>(R.id.enterdurdatestart).text =
                    "$day.${month + 1}.$year"
            case3 ->
                if (day < n)
                    requireView().findViewById<TextView>(R.id.enterdurdateend).text =
                        "0$day.${month + 1}.$year"
                else requireView().findViewById<TextView>(R.id.enterdurdateend).text =
                    "$day.${month + 1}.$year"
        }
    }
    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        when (t) {
            case1 ->
                if (hourOfDay < n)
                    requireView().findViewById<TextView>(R.id.entertime).text =
                        "0$hourOfDay:$minute"
                else requireView().findViewById<TextView>(R.id.entertime).text =
                    "$hourOfDay:$minute"
            case2 ->
                if (hourOfDay < n)
                    requireView().findViewById<TextView>(R.id.enterdurtimestart).text =
                        "0$hourOfDay:$minute"
                else requireView().findViewById<TextView>(R.id.enterdurtimestart).text =
                    "$hourOfDay:$minute"
            case3 ->
                if (hourOfDay < n)
                    requireView().findViewById<TextView>(R.id.enterdurtimeend).text =
                        "0$hourOfDay:$minute"
                else requireView().findViewById<TextView>(R.id.enterdurtimeend).text =
                    "$hourOfDay:$minute"
        }
    }
}
