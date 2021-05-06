package com.atechy.atechytwitterclone.teamreply

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atechy.atechytwitterclone.R
import com.atechy.atechytwitterclone.teamreply.TeamReplyAdapter.*

class TeamReplyAdapter(val context: Context) : RecyclerView.Adapter<TeamReplyHolder>() {
    class TeamReplyHolder(v: View) : RecyclerView.ViewHolder(v) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamReplyHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_replay, parent, false)
        return TeamReplyHolder(view)
    }

    override fun getItemCount()= 10

    override fun onBindViewHolder(holder: TeamReplyHolder, position: Int) {

    }
}


