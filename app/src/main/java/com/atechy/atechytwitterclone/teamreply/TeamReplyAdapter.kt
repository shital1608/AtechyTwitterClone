package com.atechy.atechytwitterclone.teamreply

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TeamReplyHolder, position: Int) {
        val messages = messagesList[position]
        holder.messageText.text = messages.message
        holder.textName.text = messages.name
        val indexOfAtChar = messages.email?.indexOf("@", 0)
        Log.e(">>>>", ">>>>>$indexOfAtChar")
        if(indexOfAtChar!= -1){
            val initialOfEmail = indexOfAtChar?.let { messages.email!!.substring(0, it) }
            holder.textEmail.text = "@$initialOfEmail"
        }else{
            holder.textEmail.visibility = View.INVISIBLE
        }

        if(messagesList.lastIndex== position){
            holder.verticalView.visibility = View.GONE
        }else{
            holder.verticalView.visibility = View.VISIBLE
        }
    }
}


