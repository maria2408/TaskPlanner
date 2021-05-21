package com.thirdlection.taskplanner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thirdlection.taskplanner.database.DataBaseHandler
import com.thirdlection.taskplanner.database.Task

class TaskAdapter(context: Context, listTasks: ArrayList<Task>, onTaskListener: OnTaskListener) :
    RecyclerView.Adapter<TaskAdapter.TaskHolder>() {
    private var listTasks: ArrayList<Task>
    private val mArrayList: ArrayList<Task>
    private val mDataBase: DataBaseHandler
    private val onTaskListener: OnTaskListener
    init {
        this.listTasks = listTasks
        this.mArrayList = listTasks
        this.mDataBase = DataBaseHandler(context)
        this.onTaskListener = onTaskListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task_1, parent, false)
        return TaskHolder(view, onTaskListener)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val task = listTasks[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        println("Tasks size = ${listTasks.size}")
        return listTasks.size
    }

    fun removeItem(position: Int, context: Context, id: Int) {
        listTasks.removeAt(position)
        notifyItemRemoved(position)
        DataBaseHandler(context).deleteData(id)
    }

    class TaskHolder(
        itemView: View,
        onTaskListener: OnTaskListener
    ) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val tName: TextView = itemView.findViewById(R.id.task_name)
        private val tDescription: TextView = itemView.findViewById(R.id.task_description)
        private val tDate: TextView = itemView.findViewById(R.id.task_date)
        private val tTime: TextView = itemView.findViewById(R.id.task_time)
        private val checkBox: CheckBox = itemView.findViewById(R.id.tick)
        var onTaskListener: OnTaskListener = onTaskListener

        init {
            itemView.setOnClickListener(this)
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked)
                    onTaskListener.onCheckboxClick(adapterPosition)
            }
        }
        fun bind(task: Task) {
            tName.text = task.name
            tDescription.text = task.desc
            tDate.text = task.deadlineDate
            tTime.text = task.deadlineTime
        }

        override fun onClick(v: View?) {
            onTaskListener.onTaskClick(adapterPosition)
        }
    }
    interface OnTaskListener {
        fun onTaskClick(position: Int)
        fun onCheckboxClick(position: Int)
    }
}
