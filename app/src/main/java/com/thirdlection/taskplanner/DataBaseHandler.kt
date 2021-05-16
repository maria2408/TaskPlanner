package com.thirdlection.taskplanner

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DatabaseName = "MyTasks"
val TableName = "Tasks"
val ColId = "id"
val ColName = "name"
val ColDescrip = "description"
val ColDeadlineDate = "Deadline_date"
val ColDeadlineTime = "Deadline_time"
val ColDurStartD = "Duration_start_date"
val ColDurEndD = "Duration_end_date"
val ColDurStartT = "Duration_start_time"
val ColDurEndT = "Duration_end_time"
val ColImp = "Importance"

class DataBaseHandler(var context: Context?) : SQLiteOpenHelper(context, DatabaseName, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TableName + " (" +
            ColId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ColName + " VARCHAR(256)," +
            ColDescrip + " VARCHAR(256)," +
            ColDeadlineDate + " VARCHAR(256)," +
            ColDeadlineTime + " VARCHAR(256)," +
            ColDurStartD + " VARCHAR(256)," +
            ColDurEndD + " VARCHAR(256)," +
            ColDurStartT + " VARCHAR(256)," +
            ColDurEndT + " VARCHAR(256)," +
            ColImp + " INTEGER" +
            ") "
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(task: Task) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(ColName, task.name)
        cv.put(ColDescrip, task.desc)
        cv.put(ColDeadlineDate, task.deadlineDate)
        cv.put(ColDeadlineTime, task.deadlineTime)
        cv.put(ColDurStartD, task.durStartDate)
        cv.put(ColDurEndD, task.durEndDate)
        cv.put(ColDurStartT, task.durStartTime)
        cv.put(ColDurEndT, task.durEndTime)
        cv.put(ColImp, task.importance)
        var result = db.insert(TableName, null, cv)
        if (result == -1.toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
    }
}
