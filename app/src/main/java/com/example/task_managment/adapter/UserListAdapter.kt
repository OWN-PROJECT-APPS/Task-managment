

package com.example.task_managment.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task_managment.R
import com.example.task_managment.models.User


class UserListAdapter(private val usersList: ArrayList<User>,
                      private var onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_card, parent, false)

        return ViewHolder(view,onItemClickListener)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userModel = usersList[position]
        holder.name.text = userModel.getName()
        holder.username.text = userModel.getUsername()
        holder.email.text = userModel.getEmail()


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun getItemId(position: Int): Long {
        return usersList[position].getUserId().toLong()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    // Holds the views for adding it to  texts
    class ViewHolder(itemView: View, private var onItemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{

        val name: TextView = itemView.findViewById(R.id.name)
        val username: TextView = itemView.findViewById(R.id.userName)
        val email: TextView = itemView.findViewById(R.id.email)

        init {

             itemView.setOnClickListener(this)
         }
        override fun onClick(view: View) {


            onItemClickListener.onItemClick( layoutPosition+1)
        }

    }


}
