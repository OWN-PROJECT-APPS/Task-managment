package com.example.task_managment
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.task_managment.adapter.UserListAdapter
import com.example.task_managment.models.User
import com.example.task_managment.services.Database
import org.json.JSONException


class MainActivity : AppCompatActivity() , UserListAdapter.OnItemClickListener {
    private lateinit var usersRecyclerView : RecyclerView
    private var usersList : ArrayList<User> = ArrayList()
    private var requestQueue: RequestQueue? = null
    private lateinit var progressBar : ProgressBar
    private  lateinit var database:Database
    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "List of users"
        database =   Database(this)
        requestQueue = Volley.newRequestQueue(this)
        loadAllUsers()
        // getting the recyclerview by its id
        usersRecyclerView = findViewById(R.id.userRecyclerview)
        usersRecyclerView.visibility = View.GONE
        progressBar  = findViewById(R.id.progressBar)
        // this creates a vertical layout Manager
        usersRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
        // This will pass the ArrayList to our Adapter
        val adapter = UserListAdapter(usersList,this)
        // Setting the Adapter with the recyclerview
        usersRecyclerView.adapter = adapter

    }

    override fun onItemClick(position:Int) {
        val intent =  Intent(this,TaskActivity::class.java).apply {
            putExtra("userId",position)
        }
        startActivity(intent)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun loadAllUsers(){
            val usersUrl = " https://jsonplaceholder.typicode.com/users/"
            val request = JsonArrayRequest(Request.Method.GET, usersUrl, null, {
                    response ->try {

                for (i in 0 until response.length()) {
                    val user = response.getJSONObject(i)
                    val name = user.getString("name")
                    val username = user.getString("username")
                    val email = user.getString("email")
                    val id = user.getInt("id")
                    usersList.add(User(id,name,username,email))
                }
                progressBar.visibility = View.GONE
                usersRecyclerView.visibility = View.VISIBLE
                usersRecyclerView.adapter?.notifyDataSetChanged()

                database.addUsers(usersList)
            } catch (e: JSONException) {
                usersList.clear()
                usersList.addAll(database.getAllUsers())
                progressBar.visibility = View.GONE
                usersRecyclerView.visibility = View.VISIBLE
                usersRecyclerView.adapter?.notifyDataSetChanged()

            }
            }, {
                usersList.clear()
                usersList.addAll(database.getAllUsers())
                progressBar.visibility = View.GONE
                usersRecyclerView.visibility = View.VISIBLE
                usersRecyclerView.adapter?.notifyDataSetChanged()
            })
            requestQueue?.add(request)
        }


}