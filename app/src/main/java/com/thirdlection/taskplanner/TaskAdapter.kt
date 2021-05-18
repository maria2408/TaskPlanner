package com.thirdlection.taskplanner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.thirdlection.taskplanner.database.DataBaseHandler
import com.thirdlection.taskplanner.database.Task

class TaskAdapter(context: Context, listTasks: ArrayList<Task>) :
    RecyclerView.Adapter<TaskAdapter.TaskHolder>() {
    private var listTasks: ArrayList<Task>
    private val mArrayList: ArrayList<Task>
    private val mDataBase: DataBaseHandler
    init {
        this.listTasks = listTasks
        this.mArrayList = listTasks
        this.mDataBase = DataBaseHandler(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task_1, parent, false)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val task = listTasks[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        println("Tasks size = ${listTasks.size}")
        return listTasks.size
    }

    class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tName: TextView = itemView.findViewById(R.id.task_name)
        private val tDescription: TextView = itemView.findViewById(R.id.task_description)
        private val tDate: TextView = itemView.findViewById(R.id.task_date)
        private val tTime: TextView = itemView.findViewById(R.id.task_time)

        init {
            itemView.setOnClickListener {
                Toast.makeText(
                    itemView.context,
                    tName.text,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        fun bind(task: Task) {
            tName.text = task.name
            tDescription.text = task.desc
            tDate.text = task.deadlineDate
            tTime.text = task.deadlineTime
        }
    }
}
