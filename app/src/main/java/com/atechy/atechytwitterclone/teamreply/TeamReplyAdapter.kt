package com.atechy.atechytwitterclone.teamreply

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.atechy.atechytwitterclone.R
import com.atechy.atechytwitterclone.base.BaseAdapter
import com.atechy.atechytwitterclone.databinding.ItemReplayBinding
import com.atechy.atechytwitterclone.teamreply.TeamReplyAdapter.*

/**
 * @author Shital Awathe
 *
 * This is messaging adapter class
 */
class TeamReplyAdapter(private val messagesList: List<Message>) : BaseAdapter<Message>() {

    override fun getItemCount() = messagesList.size

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                getItemLayoutId(0),
                parent,
                false
        )
    }

    override fun getItemLayoutId(viewType: Int) = R.layout.item_replay

    override fun bind(binding: ViewDataBinding, position: Int) {
        val bind = binding as ItemReplayBinding
        val messages = messagesList[position]
        bind.textViewMessage.text = messages.message
        bind.textName.text = messages.name
        val indexOfAtChar = messages.email?.indexOf("@", 0)
        if (indexOfAtChar != -1) {
            val initialOfEmail = indexOfAtChar?.let { messages.email!!.substring(0, it) }
            bind.textEmail.text = "@$initialOfEmail"
        } else {
            bind.textEmail.visibility = View.INVISIBLE
        }

        if (messagesList.lastIndex == position) {
            bind.verticalView.visibility = View.GONE
        } else {
            bind.verticalView.visibility = View.VISIBLE
        }
    }

    override fun bindWithPayload(binding: ViewDataBinding, position: Int, any: Any) {

    }
}


