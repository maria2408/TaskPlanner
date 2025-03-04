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

class ThirdFragment :
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt("Position")
        if (position != null) {
            fillScreen(position)
        }

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
        }
        view.findViewById<Button>(R.id.button_third).setOnClickListener {
            if (view.findViewById<EditText>(R.id.entername).text.toString().isNotEmpty()) {
                val t = Task(
                    view.findViewById<EditText>(R.id.entername).text.toString(),
                    view.findViewById<EditText>(R.id.enterdescr).text.toString(),
                    view.findViewById<TextView>(R.id.enterdate).text.toString(),
                    view.findViewById<TextView>(R.id.entertime).text.toString(),
                    view.findViewById<TextView>(R.id.enterdurdatestart).text.toString(),
                    view.findViewById<TextView>(R.id.enterdurdateend).text.toString(),
                    view.findViewById<TextView>(R.id.enterdurtimestart).text.toString(),
                    view.findViewById<TextView>(R.id.enterdurtimeend).text.toString(),
                    view.findViewById<CheckBox>(R.id.enterImp).isChecked.compareTo(false)
                )
                val db = DataBaseHandler(context)
                db.update(t, DataBaseHandler(context).listTasks().get(position!!).id)
                findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
            } else
                Toast.makeText(context, "Заполните название задачи", Toast.LENGTH_SHORT).show()
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
    private fun fillScreen(position: Int) {
        val task: Task = DataBaseHandler(context).listTasks().get(position)
        view?.findViewById<EditText>(R.id.entername)?.setText(task.name)
        view?.findViewById<EditText>(R.id.enterdescr)?.setText(task.desc)
        view?.findViewById<TextView>(R.id.enterdurdatestart)?.text = task.durStartDate
        view?.findViewById<TextView>(R.id.enterdurdateend)?.text = task.durEndDate
        view?.findViewById<TextView>(R.id.enterdurtimestart)?.text = task.durStartTime
        view?.findViewById<TextView>(R.id.enterdurtimeend)?.text = task.durEndTime
        view?.findViewById<TextView>(R.id.enterdate)?.text = task.deadlineDate
        view?.findViewById<TextView>(R.id.entertime)?.text = task.deadlineTime
        if (task.importance == 1)
            view?.findViewById<CheckBox>(R.id.enterImp)?.isChecked = true
        else view?.findViewById<CheckBox>(R.id.enterImp)?.isChecked = false
    }
}
