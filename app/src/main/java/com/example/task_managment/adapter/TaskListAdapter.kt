package com.example.task_managment.adapter
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task_managment.R
import com.example.task_managment.models.Task

class TaskListAdapter(private val taskList: ArrayList<Task>) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_card, parent, false)
        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val taskModel = taskList[position]
        holder.title.text = taskModel.getTaskName()
        holder.status.text = taskModel.getIsCompleted()
        if(taskModel.getIsCompleted() == "Completed"){

                holder.status.setTextColor(Color.parseColor("#00ff00"))
        }else{


            holder.status.setTextColor(Color.parseColor("#0000ff"))
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return taskList.size
    }

    // Holds the views for adding it to  texts
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val title: TextView = itemView.findViewById(R.id.task_title)
        val status: TextView = itemView.findViewById(R.id.task_status)
    }
}
