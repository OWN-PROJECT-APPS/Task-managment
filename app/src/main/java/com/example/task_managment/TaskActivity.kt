package com.example.task_managment

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.task_managment.adapter.TaskListAdapter
import com.example.task_managment.models.Task
import com.example.task_managment.services.Database
import org.json.JSONException


class TaskActivity : AppCompatActivity() {
    private lateinit var taskRecyclerView : RecyclerView
    private var tasksList : ArrayList<Task> = ArrayList()
    private var requestQueue: RequestQueue? = null
    private lateinit var progressBar : ProgressBar
    private  lateinit var database: Database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        supportActionBar?.title = "List of Tasks"
        database =   Database(this)
        requestQueue = Volley.newRequestQueue(this)
        val userId:Int = intent.getIntExtra("userId",1)
        loadAllTaskByUserId(userId)
        taskRecyclerView = findViewById(R.id.taskRecyclerview)
        taskRecyclerView.visibility = View.GONE
        progressBar  = findViewById(R.id.progressBar)
        // this creates a vertical layout Manager
        taskRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)

        // This will pass the ArrayList to our Adapter
        val adapter = TaskListAdapter(tasksList)
        taskRecyclerView.adapter =adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    fun loadAllTaskByUserId(userId:Int){
        val taskUrl = "https://jsonplaceholder.typicode.com/todos?userId=$userId"
        val request = JsonArrayRequest(Request.Method.GET, taskUrl, null, {
                response ->try {
            for (i in 0 until response.length()) {
                val task = response.getJSONObject(i)
                val taskId = task.getInt("id")
                val userIid = task.getInt("userId")
                val title = task.getString("title")
                val isCompleted = task.getBoolean("completed")

                tasksList.add(Task(taskId,userIid,title,isCompleted))
            }
            progressBar.visibility = View.GONE
            taskRecyclerView.visibility = View.VISIBLE
            taskRecyclerView.adapter?.notifyDataSetChanged()
        } catch (e: JSONException) {
            tasksList.clear()
            tasksList.addAll(database.getTaskViaUserId(userId))
            progressBar.visibility = View.GONE
            taskRecyclerView.visibility = View.VISIBLE
            taskRecyclerView.adapter?.notifyDataSetChanged()
        }
        }, {

            tasksList.clear()
            tasksList.addAll(database.getTaskViaUserId(userId))
            progressBar.visibility = View.GONE
            taskRecyclerView.visibility = View.VISIBLE
            taskRecyclerView.adapter?.notifyDataSetChanged()})
        requestQueue?.add(request)
    }
}