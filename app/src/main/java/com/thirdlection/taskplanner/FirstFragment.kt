package com.thirdlection.taskplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.thirdlection.taskplanner.database.DataBaseHandler
import com.thirdlection.taskplanner.database.Task

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment :
    Fragment(),
    ItemSwipeManger.SwipeListener,
    TaskAdapter.OnTaskListener {

    lateinit var itemSwipeManger: ItemSwipeManger
    lateinit var taskViewId: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskViewId = view.findViewById(R.id.list_recycler_view)
        val linearLayoutManager = LinearLayoutManager(context)
        taskViewId.layoutManager = linearLayoutManager
        val dataBase = DataBaseHandler(context)
        val allTasks: ArrayList<Task> = dataBase.listTasks()
        val mAdapter = TaskAdapter(requireContext(), allTasks, this)
        if (allTasks.size > 0) {
            taskViewId.visibility = View.VISIBLE
            taskViewId.adapter = mAdapter
        } else {
            taskViewId.visibility = View.GONE
            Toast.makeText(
                context,
                "Отдыхайте! У вас нет дел",
                Toast.LENGTH_LONG
            ).show()
        }
        itemSwipeManger = ItemSwipeManger(requireContext(), this)

        view.findViewById<FloatingActionButton>(R.id.plus).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
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
        val position = viewHolder.adapterPosition
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
        }
    }

    override fun onTaskClick(position: Int) {
        findNavController().navigate(R.id.action_FirstFragment_to_ThirdFragment)
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
    }
}
