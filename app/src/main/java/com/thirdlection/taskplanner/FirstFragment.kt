package com.thirdlection.taskplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.michalsvec.singlerowcalendar.calendar.CalendarChangesObserver
import com.michalsvec.singlerowcalendar.calendar.CalendarViewManager
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendarAdapter
import com.michalsvec.singlerowcalendar.selection.CalendarSelectionManager
import com.michalsvec.singlerowcalendar.utils.DateUtils
import com.thirdlection.taskplanner.database.DataBaseHandler
import com.thirdlection.taskplanner.database.Task
import java.util.Calendar
import java.util.Date
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment :
    Fragment(),
    ItemSwipeManger.SwipeListener,
    TaskAdapter.OnTaskListener {

    lateinit var itemSwipeManger: ItemSwipeManger
    lateinit var taskViewId: RecyclerView
    lateinit var allTasks: ArrayList<Task>

    private val calendar = Calendar.getInstance()
    private var currentMonth = 0

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskViewId = view.findViewById(R.id.list_recycler_view)
        taskViewId.setHasFixedSize(false)
        val linearLayoutManager = LinearLayoutManager(activity)
        taskViewId.layoutManager = linearLayoutManager
        val dataBase = DataBaseHandler(activity)
        allTasks = dataBase.listTasks()
        val mAdapter = TaskAdapter(requireContext(), allTasks, this)
        if (allTasks.size > 0) {
            taskViewId.visibility = View.VISIBLE
            taskViewId.adapter = mAdapter
        } else {
            taskViewId.visibility = View.GONE
            Toast.makeText(
                context,
                "Отдыхайте! У вас нет дел",
                Toast.LENGTH_SHORT
            ).show()
        }
        mAdapter.notifyDataSetChanged()
        itemSwipeManger = ItemSwipeManger(requireContext(), this)

        view.findViewById<FloatingActionButton>(R.id.plus).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        // set current date to calendar and current month to currentMonth variable
        calendar.time = Date()
        currentMonth = calendar[Calendar.MONTH]

        // calendar view manager is responsible for our displaying logic
        val myCalendarViewManager = object : CalendarViewManager {
            override fun setCalendarViewResourceId(
                position: Int,
                date: Date,
                isSelected: Boolean
            ): Int {
                // set date to calendar according to position where we are
                val cal = Calendar.getInstance()
                cal.time = date
                return R.layout.item_calendar
            }

            override fun bindDataToCalendarView(
                holder: SingleRowCalendarAdapter.CalendarViewHolder,
                date: Date,
                position: Int,
                isSelected: Boolean
            ) {
                // using this method we can bind data to calendar view
                holder.itemView.findViewById<TextView>(
                    R.id.tv_date_calendar_item
                ).text = DateUtils.getDayNumber(date)
                holder.itemView.findViewById<TextView>(
                    R.id.tv_day_calendar_item
                ).text = DateUtils.getDay3LettersName(date)
            }
        }

        // using calendar changes observer we can track changes in calendar
        val myCalendarChangesObserver = object : CalendarChangesObserver {
            override fun whenSelectionChanged(isSelected: Boolean, position: Int, date: Date) {
                view.findViewById<TextView>(
                    R.id.tvDate
                ).text = "${DateUtils.getMonthName(date)}, ${DateUtils.getDayNumber(date)} "
                view.findViewById<TextView>(
                    R.id.tvDay
                ).text = DateUtils.getDayName(date)
                super.whenSelectionChanged(isSelected, position, date)
            }
        }

        // selection manager is responsible for managing selection
        val mySelectionManager = object : CalendarSelectionManager {
            override fun canBeItemSelected(position: Int, date: Date): Boolean {
                // set date to calendar according to position
                val cal = Calendar.getInstance()
                cal.time = date
                return true
            }
        }

        // here we init our calendar
        view.findViewById<com.michalsvec.singlerowcalendar.calendar.SingleRowCalendar>(
            R.id.main_single_row_calendar
        ).apply {
            calendarViewManager = myCalendarViewManager
            calendarChangesObserver = myCalendarChangesObserver
            calendarSelectionManager = mySelectionManager
            setDates(getFutureDatesOfCurrentMonth())
            init()
        }
    }

    private fun getFutureDatesOfCurrentMonth(): List<Date> {
        // get all next dates of current month
        currentMonth = calendar[Calendar.MONTH]
        return getDates(mutableListOf())
    }

    private fun getDates(list: MutableList<Date>): List<Date> {
        // load dates of whole month
        calendar.set(Calendar.MONTH, currentMonth)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        list.add(calendar.time)
        while (currentMonth == calendar[Calendar.MONTH]) {
            calendar.add(Calendar.DATE, +1)
            if (calendar[Calendar.MONTH] == currentMonth)
                list.add(calendar.time)
        }
        calendar.add(Calendar.DATE, -1)
        return list
    }

    override fun onStart() {
        super.onStart()
        itemSwipeManger.attachToRecyclerView(taskViewId)
    }

    override fun onStop() {
        itemSwipeManger.detachFromRecyclerView()
        super.onStop()
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder) {
        val position = viewHolder.absoluteAdapterPosition
        if (position != RecyclerView.NO_POSITION) {
            val mAdapter = TaskAdapter(
                requireContext(),
                DataBaseHandler(context).listTasks(),
                this
            )
            mAdapter.removeItem(
                position,
                requireContext(),
                DataBaseHandler(context).listTasks().get(position).id
            )
            mAdapter.notifyItemRemoved(position)
        }
    }

    override fun onTaskClick(position: Int) {
        val pos = Bundle()
        pos.putInt("Position", position)
        findNavController().navigate(R.id.action_FirstFragment_to_ThirdFragment, pos)
    }

    override fun onCheckboxClick(position: Int) {
        val mAdapter = TaskAdapter(
            requireContext(),
            DataBaseHandler(context).listTasks(),
            this
        )
        mAdapter.removeItem(
            position,
            requireContext(),
            DataBaseHandler(context).listTasks().get(position).id
        )
        mAdapter.notifyItemRemoved(position)
    }
}
