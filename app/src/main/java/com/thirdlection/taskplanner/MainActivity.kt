package com.thirdlection.taskplanner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thirdlection.taskplanner.database.DataBaseHandler

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val db = DataBaseHandler(this)
        val allTasks = db.listTasks()
        val rv = findViewById<RecyclerView>(R.id.list_recycler_view)
        rv.layoutManager = LinearLayoutManager(this)
        val adapter = TaskAdapter(this, allTasks, FirstFragment())
        rv.adapter = adapter
        when (item.itemId) {
            R.id.action_sortByname -> {
                FirstFragment().allTasks = allTasks
                allTasks.sortBy { it.name }
                adapter.notifyDataSetChanged()
                db.sortByName()
            }
            R.id.action_SortByImp -> {
                FirstFragment().allTasks = allTasks
                allTasks.sortByDescending { it.importance }
                adapter.notifyDataSetChanged()
            }
        }
        super.onOptionsItemSelected(item)
        return true
    }
}
