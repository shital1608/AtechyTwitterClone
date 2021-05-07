package com.atechy.atechytwitterclone.teamreply

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atechy.atechytwitterclone.R
import com.atechy.atechytwitterclone.teamreply.TeamReplyAdapter.*

/**
 * @author Shital Awathe
 *
 * This is messaging adapter class
 */
class TeamReplyAdapter(private val messagesList: List<Message>) : RecyclerView.Adapter<TeamReplyHolder>() {
    class TeamReplyHolder(v: View) : RecyclerView.ViewHolder(v) {
        val messageText: TextView = v.findViewById(R.id.textViewMessage)
        val textName: TextView = v.findViewById(R.id.textName)
        val textEmail: TextView = v.findViewById(R.id.textEmail)
        val verticalView: View = v.findViewById(R.id.verticalView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamReplyHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_replay, parent, false)
        return TeamReplyHolder(view)
    }

    override fun getItemCount()= messagesList.size

    override fun onBindViewHolder(holder: TeamReplyHolder, position: Int) {
        val messages = messagesList[position]
        holder.messageText.text = messages.message
        holder.textName.text = messages.name
        holder.textEmail.text = "@"+messages.email

        if(messagesList.lastIndex== position){
            holder.verticalView.visibility = View.GONE
        }else{
            holder.verticalView.visibility = View.VISIBLE
        }
    }
}


