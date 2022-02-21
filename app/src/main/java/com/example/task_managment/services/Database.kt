package com.example.task_managment.services
import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.example.task_managment.models.Task
import com.example.task_managment.models.User

class Database(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "TaskManagement.db"
        private const val TABLE_USERS = "Users"
        private const val TABLE_TASK = "Tasks"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        //creating table with fields
        val createUsersTable = ("CREATE TABLE  $TABLE_USERS (userId INTEGER PRIMARY KEY , name TEXT , username TEXT , email TEXT ) ")
        val createTasksTable = ("CREATE TABLE  $TABLE_TASK (taskId INTEGER PRIMARY KEY , title TEXT , status INTEGER , userId INTEGER ) ")
        db?.execSQL(createUsersTable)
        db?.execSQL(createTasksTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS  $TABLE_USERS")
        db?.execSQL("DROP TABLE IF EXISTS  $TABLE_TASK")
        onCreate(db)
    }


    // insert users
    @SuppressLint("Recycle")
    fun addUsers(users: List<User>) {
        val db = this.writableDatabase
        for(user:User in users) {

            val contentValues = ContentValues()
            contentValues.put("userId", user.getUserId())
            contentValues.put("name", user.getName())
            contentValues.put("username", user.getUsername())
            contentValues.put("email", user.getEmail())
            // Inserting Row
            // check if exist
           val cursor:Cursor = db.rawQuery(
               "SELECT * FROM $TABLE_USERS WHERE userId = ${user.getUserId()}",
               null
           )
            if(cursor.count == 0){
                db.insert(TABLE_USERS, null, contentValues)
            }

        }
        db.close() // Closing database connection

    }

    //     get all users
    @SuppressLint("Range", "Recycle")
    fun getAllUsers():List<User>{
            val usersList:ArrayList<User> = ArrayList()
            val selectQuery = "SELECT  * FROM $TABLE_USERS"
            val db = this.readableDatabase
        val cursor: Cursor?
        try{
                cursor = db.rawQuery(selectQuery, null)
            }catch (e: SQLiteException) {
                db.execSQL(selectQuery)
                return ArrayList()
            }
            var userId: Int
            var name: String
            var username: String
             var email: String
            if (cursor.moveToFirst()) {
                do {
                    userId = cursor.getInt(cursor.getColumnIndex("userId"))
                    name = cursor.getString(cursor.getColumnIndex("name"))
                    username = cursor.getString(cursor.getColumnIndex("username"))
                    email = cursor.getString(cursor.getColumnIndex("email"))
                    usersList.add(    User(userId,name, username ,email))
                } while (cursor.moveToNext())
            }
            return usersList
        }


    // insert Tasks
    @SuppressLint("Recycle")
    fun addTasks(tasks: List<Task>) {
        val db = this.writableDatabase
        for(task:Task in tasks) {

            val contentValues = ContentValues()
            contentValues.put("taskId", task.getTaskId())
            contentValues.put("title", task.getTaskName())
            contentValues.put("status", task.getTaskAsInt())
            contentValues.put("userId", task.getUserId())
            // Inserting Row
            // check if exist
            val cursor:Cursor = db.rawQuery(
                "SELECT * FROM $TABLE_TASK WHERE taskId = ${task.getTaskId()}",
                null
            )
            if(cursor.count == 0){
                db.insert(TABLE_TASK, null, contentValues)
            }

        }
        db.close() // Closing database connection

    }

    //     get all users
    @SuppressLint("Range", "Recycle")
    fun getTaskViaUserId(userId:Int):List<Task>{
        val tasksList:ArrayList<Task> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_TASK WHERE userId = $userId"
        val db = this.readableDatabase
        val cursor: Cursor?
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var userIid: Int
        var taskId: Int
        var title: String
        var status: Int
        if (cursor.moveToFirst()) {
            do {
                userIid = cursor.getInt(cursor.getColumnIndex("userId"))
                taskId = cursor.getInt(cursor.getColumnIndex("taskId"))
                title = cursor.getString(cursor.getColumnIndex("title"))
                status = cursor.getInt(cursor.getColumnIndex("status"))

                tasksList.add( Task(userIid,taskId, title ,status == 1))
            } while (cursor.moveToNext())
        }
        return tasksList
    }


}