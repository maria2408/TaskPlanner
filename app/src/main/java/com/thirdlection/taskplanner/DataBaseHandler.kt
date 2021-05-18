package com.thirdlection.taskplanner

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

object Constants {
    const val DatabaseName: String = "MyTasks"
    const val TableName: String = "Tasks"
    const val ColId: String = "id"
    const val ColName: String = "name"
    const val ColDescrip: String = "description"
    const val ColDeadlineDate: String = "Deadline_date"
    const val ColDeadlineTime: String = "Deadline_time"
    const val ColDurStartD: String = "Duration_start_date"
    const val ColDurEndD: String = "Duration_end_date"
    const val ColDurStartT: String = "Duration_start_time"
    const val ColDurEndT: String = "Duration_end_time"
    const val ColImp: String = "Importance"
}

class DataBaseHandler(private var context: Context?) :
    SQLiteOpenHelper(
        context,
        Constants.DatabaseName,
        null,
        1
    ) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + Constants.TableName + " (" +
            Constants.ColId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Constants.ColName + " VARCHAR(256)," +
            Constants.ColDescrip + " VARCHAR(256)," +
            Constants.ColDeadlineDate + " VARCHAR(256)," +
            Constants.ColDeadlineTime + " VARCHAR(256)," +
            Constants.ColDurStartD + " VARCHAR(256)," +
            Constants.ColDurEndD + " VARCHAR(256)," +
            Constants.ColDurStartT + " VARCHAR(256)," +
            Constants.ColDurEndT + " VARCHAR(256)," +
            Constants.ColImp + " INTEGER" +
            ") "
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(task: Task) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(Constants.ColName, task.name)
        cv.put(Constants.ColDescrip, task.desc)
        cv.put(Constants.ColDeadlineDate, task.deadlineDate)
        cv.put(Constants.ColDeadlineTime, task.deadlineTime)
        cv.put(Constants.ColDurStartD, task.durStartDate)
        cv.put(Constants.ColDurEndD, task.durEndDate)
        cv.put(Constants.ColDurStartT, task.durStartTime)
        cv.put(Constants.ColDurEndT, task.durEndTime)
        cv.put(Constants.ColImp, task.importance)
        val result = db.insert(Constants.TableName, null, cv)
        if (result == -1.toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
    }
}
